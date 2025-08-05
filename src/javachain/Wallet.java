package src.javachain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
public class Wallet {
    private PublicKey publicKey;
    public PrivateKey privateKey;

    public Wallet(){
        generateKeyPair();
    }

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
