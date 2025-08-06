package src.javachain;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;
// import java.io.UnsupportedEncodingException;

public class StringUtil {
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            // System.out.println(hexString.toString());
            return hexString.toString();
        }

        // catch (UnsupportedEncodingException e) {
        //     System.out.print(e);
        // }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] applyECDSAsig(PrivateKey privateKey, String input){
        Signature dsa;
        byte[] output = new byte[0];
        try {
            dsa = Signature.getInstance("ECDSA","BC");
            dsa.initSign(privateKey);
            byte[] strByte = input.getBytes();
            dsa.update(strByte);
            byte[] realSign = dsa.sign();
            output = realSign;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Remove in PRoduction
        // System.out.println("Output from applyECDSAsig"+output);

        return output;
    }

    public static boolean verifyECDSA(PublicKey publicKey,String data, byte[] signature){
        try {
            Signature verifyECdsa = Signature.getInstance("ECDSA","BC");
            verifyECdsa.initVerify(publicKey);
            verifyECdsa.update(data.getBytes());
            return verifyECdsa.verify(signature);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static  String getStringFromKey(Key key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
//function to get the Merkel Root
public static String getMerkelRoot(ArrayList<Transaction> transactions)    {
    int count = transactions.size();
    ArrayList<String> previousTreeLayer = new ArrayList<String>();
    
    for(Transaction t : transactions){
        previousTreeLayer.add(t.transactionId);
    }

    //It is just for understanding you can set it to new ArrayList<String>
    ArrayList<String> currentLayer = previousTreeLayer;

    while(count >1){
        currentLayer = new ArrayList<String>();

        for(int i=0;i<previousTreeLayer.size();i++){
            currentLayer.add(applySha256(previousTreeLayer.get(i) + previousTreeLayer.get(i-1)));
        }
        count = currentLayer.size();
        previousTreeLayer = currentLayer;
    }
    //Get the Merkle Root From the cuurent Layer and using Ternary Operator to check the size of list
    //You can also use getFirst function on currentLayer I did not use d it because I never Used this function before.
    String merkleRoot = (currentLayer.size() ==1) ? currentLayer.get(0) : "";
    return merkleRoot;
}
}
