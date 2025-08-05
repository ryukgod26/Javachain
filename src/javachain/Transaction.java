package src.javachain;

import java.security.*;
import java.util.ArrayList;


public class Transaction{
    public String transactionId;
    public PublicKey sender;
    public PublicKey receiver;
    public  byte[] signature;
    public float value;

    public ArrayList<TransactionInput> transactionInput = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> transactionOutput = new ArrayList<TransactionOutput>();

    private static int sequence = 0; // a rough count of how many transactions has been maded

    //Constructor
    public Transaction(PublicKey from, PublicKey to, float value ,ArrayList<TransactionInput> tInput){
        this.sender = from;
        this.receiver = to;
        this.value = value;
        this.transactionInput = tInput;
    }

    private String calculateHash(){
        //To make Every Transaction Id Unique
        sequence++;
         StringUtil.applySha256(
          StringUtil.getStringFromKey(sender) + 
          StringUtil.getStringFromKey(receiver) + 
          Float.toString(value) + Integer.toString(sequence)
        );
    }
    
}