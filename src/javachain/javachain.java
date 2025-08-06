package src.javachain;
import java.util.ArrayList;
//import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.Security;
import java.security.*;

public class javachain{

  public static ArrayList<Block> blockchain = new ArrayList<Block>();
  public static int difficulty = 1;
    public static void main(String[] args){

	//Adding Bouncy Castle as a Security Provider
      Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	
      Wallet test1 = new Wallet();
      Wallet test2 = new Wallet();

      System.out.println("Public Key of wallet test 1 is: " + test1.publicKey + "\nPrivate Key for wallet test1 is: " + test1.privateKey);

      Transaction transfer = new Transaction(test1.publicKey,test2.publicKey,5,null);
	System.out.println("Generating Signature.....");
      transfer.generateSignature(test1.privateKey);
	System.out.println("Verifying Signature....");
	transfer.verifySignature();
/*	    blockchain.add(new Block("0","This is the First Block"));
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

    for(int i = 1; i< blockchain.size();i++){
    currentBlock  = blockchain.get(i);

   previousBlock = blockchain.get(i-1); 
	if(!currentBlock.hash.equals(currentBlock.calculateHash())){
	System.out.println("Hash is not Equal to Calculated Hash");
	return false;
	}
	if(!previousBlock.hash.equals(currentBlock.previousHash)){
	System.out.println("Hash of Previous Block Does not Matched with the Previous Block Hash of current Block.");
	return false;
	}
    }

    return true;
    }

}

