package src.javachain;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public String parentTransactionId;
    public float value;
    public PublicKey recepient;

    public TransactionOutput(PublicKey recepient, float value ,String parentTransactionId){

        this.parentTransactionId = parentTransactionId;
        this.value = value;
        this.recepient = recepient;
        id = StringUtil.applySha256(
            StringUtil.getStringFromKey(recepient) 
            + Float.toString(value)
            + parentTransactionId
        );
    }

    public boolean  isMine(PublicKey publicKey){
        return (recepient == publicKey);
    }

}
