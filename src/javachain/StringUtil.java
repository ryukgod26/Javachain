package src.javachain;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
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
        System.out.println("Output from applyECDSAsig"+ output);

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
}