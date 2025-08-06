package src.javachain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Wallet {
    public PublicKey publicKey;
    public PrivateKey privateKey;

    public HashMap<String , TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();


    public Wallet(){
        generateKeyPair();
    }

    public float getBalance(){
        float sum = 0;
        for (Map.Entry<String, TransactionOutput> item : javachain.UTXOs.entrySet()) {
        TransactionOutput UTXO = item.getValue();
        if (UTXO.isMine(publicKey))            {
            UTXOs.put(UTXO.id, UTXO);
            sum += UTXO.value;
        }
        }
                return sum;

    }

    //Generate and returns a new Transaction from this Wallet
    public Transaction sendFunds(PublicKey receiver,float value){
        if (getBalance()<value) {
            System.out.println("Cannot Process the Transaction. The Balance is lower than the Requested Transfer Amount.");
            return null;
        }

        float total =0;

        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
        for(Map.Entry<String,TransactionOutput> o : UTXOs.entrySet()){
            TransactionOutput UTXO = o.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if(total>value) break;

        }

        Transaction newTransaction = new Transaction(publicKey, receiver, value, inputs);
        newTransaction.generateSignature(privateKey);

        for(TransactionInput i: inputs){
            UTXOs.remove(i.transferOutpuId);
        }

        return newTransaction;

    }

    //Gennerates the Public Key and Private Key of the Wallet
    public void generateKeyPair(){
        try {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        ECGenParameterSpec eSpec = new ECGenParameterSpec("prime192v1");
        // Initialize the key generator and generate a KeyPair
        keyGen.initialize(eSpec,random);//256 bytes provides an acceptable security level
        KeyPair keyPair = keyGen.generateKeyPair();
        // setting the public and private keys from key Pair
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        }

    }
}
