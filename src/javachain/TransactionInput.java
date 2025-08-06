package src.javachain;

public class TransactionInput {
    public String transferOutpuId; 
    public TransactionOutput UTXO;

    public TransactionInput(String transferOutpuId){
        this.transferOutpuId = transferOutpuId;
    }
}
