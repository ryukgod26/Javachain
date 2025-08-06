package src.javachain;

import java.util.ArrayList;
import java.util.Date;


public class Block{
    //declaration of variables and constants
    public String previousHash;
    public String hash;
    // private String data;
    private long timeStamp;
    private int nonce;    
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    //constructor 
    public Block(String previousHash){
    
    // this.data = data;
    this.previousHash = previousHash;

    this.timeStamp = new Date().getTime();
    
    }
// Return the calculated hash with sha256
    public String calculateHash(){
        return StringUtil.applySha256(
        previousHash + Long.toString(timeStamp)  + Integer.toString(nonce) + merkleRoot
        );
    }

    public void mineBlock(int difficulty){
        //I am calculating hash in this function instead of constructor because we are using merkleRoot in the calculateHash function
        this.hash = calculateHash();
    merkleRoot = StringUtil.getMerkelRoot(transactions);
    String target = new String(new char[difficulty]).replace('\0','0');
    //int i =0;
    while(!hash.substring(0,difficulty).equals(target)){
    nonce++;
    hash = calculateHash();
   // System.out.printf("\tAttempt:%d",i);
   // i++;
    }

    System.out.println("Block Mined ...." + hash);

    }

    public boolean addTransaction(Transaction transaction){
        if(transaction==null) return false;

        //skip the processTransaction method for the genesis block
        //Ai taught me this method of comparing Strings I Just asked him beacuse I was getting warning from vscode he did not write the code.
        if(!"0".equals(previousHash)){

            if(transaction.processTransaction() != true){
                System.out.println("Transaction Failed to process. \nTransaction Failed!!!.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to the Block.");
        return true;
    }
    
}
