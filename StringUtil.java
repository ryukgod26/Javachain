import java.security.MessageDigest;
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
}