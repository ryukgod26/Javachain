package src.javachain;
import java.util.ArrayList;
//import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.Security;
import java.security.*;
import java.util.HashMap;

public class javachain{

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 3;
	public static HashMap<String , TransactionOutput> UTXOs= new HashMap<String, TransactionOutput>();
	public static float minimumTransactionValue = 0.1f;	
	public static Wallet wallet1;
	public static Wallet wallet2;
	public static Transaction genesisTransaction;


    public static void main(String[] args){
		//testing the blockchain

	//Adding Bouncy Castle as a Security Provider
	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

	//Creating new Wallets
	wallet1 =  new Wallet();
	wallet2 = new Wallet();
	Wallet richWallet = new Wallet();

	//create first(genesis) Transaction
	genesisTransaction = new Transaction(richWallet.publicKey, wallet1.publicKey, 100, null);
	genesisTransaction.generateSignature(richWallet.privateKey);
	//manually setting the transaction id
	genesisTransaction.transactionId = "0";
	//manually adding tyransaction output
	genesisTransaction.transactionOutputs.add(new TransactionOutput(genesisTransaction.receiver, genesisTransaction.value, genesisTransaction.transactionId));
	//Storing First Transaction in the UTOX list
	UTXOs.put(genesisTransaction.transactionOutputs.get(0).id, genesisTransaction.transactionOutputs.get(0));

	System.out.println("Creating and Mining genesis Block...");
	Block genesisB = new Block("0");
	genesisB.addTransaction(genesisTransaction);
	addBlock(genesisB);


	//testing other blocks
	Block block1 = new Block(genesisB.hash);
	System.out.println("The Balance of Wallet1 is: " + wallet1.getBalance());
	System.out.println("Wallet1 is Sending 10 coins to Wallet2.");;
	block1.addTransaction(wallet1.sendFunds(wallet2.publicKey, 10f));
	addBlock(block1);
	System.out.println("The Balance of Wallet1 is: " + wallet1.getBalance());
	System.out.println("The Balance of Wallet2 is: " + wallet2.getBalance());

	//Ading another Block
	Block block2 = new Block(block1.hash);
	System.out.println("Wallet2 is Sending 5 coins to Wallet5.");;
	block1.addTransaction(wallet2.sendFunds(wallet1.publicKey, 5f));
	addBlock(block2);
	System.out.println("The Balance of Wallet1 is: " + wallet1.getBalance());
	System.out.println("The Balance of Wallet2 is: " + wallet2.getBalance());
	
	if(ischainValid()){
		System.out.println("The Blochain is Correct and Working Perfectlt.");
	}
	else{
		System.out.println("The Blockchain is not valid and require debugging.");;
	}

/* 
	
      
	
      Wallet test1 = new Wallet();
      Wallet test2 = new Wallet();

      System.out.println("Public Key of wallet test 1 is: " + test1.publicKey + "\nPrivate Key for wallet test1 is: " + test1.privateKey);

      Transaction transfer = new Transaction(test1.publicKey,test2.publicKey,5,null);
	System.out.println("Generating Signature.....");
      transfer.generateSignature(test1.privateKey);
	System.out.println("Verifying Signature....");
	if(transfer.verifySignature()){
		System.out.println("Signature is Verified and correct.");
	}
	else{
		System.out.println("Signature is not Correct");
	}
	    blockchain.add(new Block("0","This is the First Block"));
/System.out.println("Block is getting Mined....");
blockchain.get(blockchain.size()-1).mineBlock(difficulty);
	    blockchain.add(new Block(blockchain.get(blockchain.size()-1).hash,"Secret data of second block"));
	    System.out.println("Block is getting Mined....");
	    blockchain.get(blockchain.size()-1).mineBlock(difficulty);
	    blockchain.add(new Block(blockchain.get(blockchain.size()-1).hash,"Secret data of third block"));
	    System.out.println("Block is getting Mined....");
	    blockchain.get(blockchain.size()-1).mineBlock(difficulty);
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);		
		System.out.println(blockchainJson);
	    System.out.println(ischainValid());*/




	}
    public static Boolean ischainValid(){
    Block currentBlock;
    Block previousBlock;
	String hashTarget = new String(new char[difficulty]).replace('\0', '0');
	HashMap<String,TransactionOutput> tempUTOX = new HashMap<String,TransactionOutput>();
	tempUTOX.put(genesisTransaction.transactionOutputs.get(0).id, genesisTransaction.transactionOutputs.get(0));

    for(int i = 1; i< blockchain.size();i++){
    currentBlock  = blockchain.get(i);

   previousBlock = blockchain.get(i-1); 

   //Current Block Hash Checking
	if(!currentBlock.hash.equals(currentBlock.calculateHash())){
	System.out.println("Hash is not Equal to Calculated Hash");
	return false;
	}

	//Previous Block Hash Checking
	if(!previousBlock.hash.equals(currentBlock.previousHash)){
	System.out.println("Hash of Previous Block Does not Matched with the Previous Block Hash of current Block.");
	return false;
	}

	//Checking if block is mined or not
	if(!currentBlock.hash.substring(0,difficulty).equals(hashTarget)){
		System.out.println("This Block is not Mined!!!!.");
		return false;
	}

	//looping throuygh the transactions of a block
	TransactionOutput tempOutput;
	for(int j =0; j< currentBlock.transactions.size();j++){
		Transaction currentTransaction = currentBlock.transactions.get(j);

		if(!currentTransaction.verifySignature()){
			System.out.println("Signature of this Block is not Valid :" + j);
			return false;
		}
		if(!(currentTransaction.getInputValue() == currentTransaction.getOutputValue())){
			System.out.println("Inputs and Oupts Values are not equal of the Block " + j);
			return  false;
		}

		for(TransactionInput t : currentTransaction.transactionInputs){
			tempOutput = tempUTOX.get(t.transferOutpuId);
			if(tempOutput == null){
				System.out.println("Referenced input on transaction (" + t + ") is missing.");
				return false;
			}
			if(t.UTXO.value != tempOutput.value){
				System.out.println("Referenced input on transaction (" + t + ") is Invalid.");
				return false;
			}
			tempUTOX.remove(t.transferOutpuId);
		}

		for(TransactionOutput o : currentTransaction.transactionOutputs){
			tempUTOX.put(o.id,o);
		}

		if(currentTransaction.transactionOutputs.get(0).recepient != currentTransaction.receiver){
			System.out.println("The Recepient of Transaction (" + j + ") is not correct.");
			return false;
		}

		if(currentTransaction.transactionOutputs.get(1).recepient != currentTransaction.sender){
			System.out.println("The Sender of Transaction (" + j + ") is not correct.");
			return false;
		}
	}


    }
	System.out.println("Blockchain is Valid.");
    return true;
    }

	public static void addBlock(Block newBlock){
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}

}

