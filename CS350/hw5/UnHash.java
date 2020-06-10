import java.security.*;
import java.util.*;

public class UnHash {
    private static Hash useHash = new Hash();
    
    public static int unhash(String to_unhash) throws Exception {
        for(int i = 0; i < Integer.MAX_VALUE; i++) {
            String hashedString = useHash.hash(i);
            if(hashedString.equals(to_unhash)) {
                return i;
            }
        }
        return 0;
    }
    
    public static void main(String[] args) throws Exception {
        int res = unhash(args[0]);
        System.out.println(res);
    }

}