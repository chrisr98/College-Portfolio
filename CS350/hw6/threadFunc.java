import java.util.*;

public class threadFunc extends Thread {
    private int startIndex;
    private int endIndex;
    private static ArrayList<String> stringToUnhash;
    private static UnHash useUnHash = new UnHash();
    
    public threadFunc(int start, int end, ArrayList<String> hashList)  throws Exception {
        this.startIndex = start;
        this.endIndex = end;
        this.stringToUnhash = hashList;
        
        run();
        
    }
        
    public void run() {
        while(startIndex < endIndex) {
            try {
                System.out.println(useUnHash.unhash(stringToUnhash.get(startIndex)));
            } catch (Exception e) {
                    // e.printStackTrace();
            }
            startIndex++;
        }
    }

}