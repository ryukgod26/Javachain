import java.util.ArrayList;

import com.google.gson.Gson;


public class javachain{

  public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static void main(String[] args){
      

        String blockchainJson =  new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);		
		System.out.println(blockchainJson);
	}

}

