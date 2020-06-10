import java.security.*;
import java.util.*;
import java.io.*;


public class UnHash {
    private static Hash useHash = new Hash();
    private static int maxVal = Integer.MAX_VALUE;
    public static HashMap<String, Integer> exsitingHashes = new HashMap<String, Integer>();

    public static int unhash(String to_unhash) throws Exception {

        if(exsitingHashes.get(to_unhash)==null){
            return getNewHash(to_unhash);
        }
        return exsitingHashes.get(to_unhash);
    }

    public static int getNewHash(String to_unhash) throws Exception {
        for(int i = exsitingHashes.size()-1; i < maxVal; i++) {
            String hashedString = useHash.hash(i);
            exsitingHashes.put(hashedString, i);
            if(hashedString.equals(to_unhash)) {
                // System.out.println(i);
                return i;
            }
        }
        return 0;
    }

    public static ArrayList<Integer> unhashFromFile(String input_filepath)  throws Exception {
        ArrayList<Integer> answer = new ArrayList<Integer>();
        FileReader fr = new FileReader(input_filepath);
        try (BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
               int tempAns = unhash(line);
               System.out.println(tempAns);
               // answer.add(tempAns);
            }
        }
        return answer;
    }

    public static void main(String[] args) throws Exception {
        String inputArg = args[0];
        unhashFromFile(inputArg);
        // ArrayList<Integer> res = unhashFromFile(inputArg);
        // for(int i = 0; i<res.size(); i++) {
        //     System.out.println(res.get(i));
        // }

    }

}
