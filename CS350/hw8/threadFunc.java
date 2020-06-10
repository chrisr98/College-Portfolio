import java.util.*;

public class threadFunc extends Thread {
    private int startIndex;
    private int endIndex;
    private static ArrayList<String> stringToUnhash1;
    private static ArrayList<String> hashToCompare;
    private ArrayList<Integer> currentList = new ArrayList<Integer>();
    private static ArrayList<String> hashConcatenation = new ArrayList<String>();
    private static UnHash useUnHash = new UnHash();
    private static Hash useHash = new Hash();
    
    
    public threadFunc(int start, int end, ArrayList<String> hashList1, ArrayList<String> hashList2)  throws Exception {
        this.startIndex = start;
        this.endIndex = end;
        this.stringToUnhash1 = hashList1;
        this.hashToCompare = hashList2;
        
        run();
        pair(currentList);

        
    }
        
    public void run() {
        while(startIndex < endIndex) {
            try {
                int x = useUnHash.unhash(stringToUnhash1.get(startIndex));
                currentList.add(x);
            } catch (Exception e) {
                    // e.printStackTrace();
            }
            startIndex++;
        }
        
    }
    
    public void pair(ArrayList<Integer> intList) throws Exception {
        for (int i = 0; i < intList.size(); i++) {
            for (int j = 0; j < intList.size() ; j++) {
                int firstHash = intList.get(i);
                int secondHash = intList.get(j);
                String str1 = ""+firstHash;
                String str2 = ","+secondHash;
                String concatenatedInt = str1+str2;
                String hashString = Hash.hashString(concatenatedInt);
                boolean isPair = equalTo(hashString);
                if(isPair) { 
                    System.out.println(concatenatedInt);
                }
            }
        }    
    }
    
    public boolean equalTo(String newlyHashed)throws Exception {
        for (int i = 0; i < hashToCompare.size(); i++) {
            if(newlyHashed.equals(hashToCompare.get(i))) {
                return true;
            }
        }
        return false;
    
    }
    
    
}