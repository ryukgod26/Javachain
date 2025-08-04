import java.security.MessageDigest;


public class StringUtil{
    public static String applySha256(String input){
try{
MessageDigest digest = MessageDigest.getInstance("SHA-256");
}
catch(Exception e){
    throw new RuntimeException(e);
}
    }
}