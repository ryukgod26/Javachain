public class javachain{
    public static void main(String[] arga){
      Block block1 = new Block("hash","secret");
      Block block2 = new Block(block1.hash,"secret");
      Block block3 = new Block(block2.hash,"top-secret");
      Block block4 = new Block(block3.hash,"end");

      System.out.println(block1.hash);
      System.out.println(block2.hash);
      System.out.println(block3.hash);
      System.out.println(block4.hash);

    }
}