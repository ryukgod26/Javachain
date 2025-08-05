import java.util.Date;


public class Block{
    //declaration of variables and constants
    public String previousHash;
    public String hash;
    private String data;
    private long timeStamp;
    private int nonce;    

    //constructor 
    public Block(String previousHash, String data){
    
    this.previousHash = previousHash;
    this.data = data;
    this.timeStamp = new Date().getTime();
    this.hash = calculateHash();
    }
// Return the calculated hash with sha256
    public String calculateHash(){
        return StringUtil.applySha256(
        previousHash + Long.toString(timeStamp)  + Integer.toString(nonce) + data
        );
    }

    public void mineBlock(int difficulty){
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

    
}
