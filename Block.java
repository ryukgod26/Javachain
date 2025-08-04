import java.util.Date;


public class Block{
    //declaration of variables and constants
    public String previousHash;
    public String hash;
    private String data;
    private long timeStamp;
    

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
        previousHash + Long.toString(timeStamp) + data
        );
    }

    
}