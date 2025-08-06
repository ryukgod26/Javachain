package src.javachain;

import java.security.*;
import java.util.ArrayList;


public class Transaction{
    public String transactionId;
    public PublicKey sender;
    public PublicKey receiver;
    public  byte[] signature;
    public float value;

    public ArrayList<TransactionInput> transactionInputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> transactionOutputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0; // a rough count of how many transactions has been maded

    //Constructor
    public Transaction(PublicKey from, PublicKey to, float value ,ArrayList<TransactionInput> tInput){
        this.sender = from;
        this.receiver = to;
        this.value = value;
        this.transactionInputs = tInput;
    }

    private String calculateHash(){
        //To make Every Transaction Id Unique
        sequence++;
        return  StringUtil.applySha256(
          StringUtil.getStringFromKey(sender) + 
          StringUtil.getStringFromKey(receiver) + 
          Float.toString(value) + Integer.toString(sequence)
        );
    }
    public void generateSignature(PrivateKey privateKey){
    String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + Float.toString(value);
    signature = StringUtil.applyECDSAsig(privateKey,data);
    }

    public boolean verifySignature(){
   String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + Float.toString(value);
   return StringUtil.verifyECDSA(sender,data,signature);
    }

    public boolean  processTransaction(){

        //Verifying Signature
        if(verifySignature() == false){
            System.out.println("Signature Verification Failed!!!");
            return  false;
        }

        //Getting the unspent UTXOS
        for(TransactionInput t :  transactionInputs ){
            t.UTXO = javachain.UTXOs.get(t.transferOutpuId);
        }

        if(getInputValue() < javachain.minimumTransactionValue){
            System.out.println("!!!Transaction Input is very Small: " + getInputValue());
            return false;
        }

        float newBalance = getInputValue() - value;
        transactionId = calculateHash();
        transactionOutputs.add(new TransactionOutput(receiver, value, transactionId));
        transactionOutputs.add(new TransactionOutput(sender, newBalance, transactionId));

        //add Outputs to the not spent list
        for(TransactionOutput o : transactionOutputs){
            javachain.UTXOs.put(o.id, o);
        }

        //Remove Inputs from the UTXOs List
        for(TransactionInput i:transactionInputs){
            if(i != null){
                javachain.UTXOs.remove(i.UTXO.id);
            }
        }
        return true;

    }
    // Get The Sum of values of inputs
    public float getInputValue(){
        float sum = 0;
        for(TransactionInput i : transactionInputs){
            if(i.UTXO != null){
                sum += i.UTXO.value;
            }
        }
        return sum;
    }

    // Get the sum of outputs
    public float getOutputValue(){
        float sum =0;
        for(TransactionOutput o:transactionOutputs){
            sum += o.value;
        }
        return sum;
    }

}
