import java.security.*;
import java.util.*;
import java.io.*;

public class Hash {
    
    public static HashMap<String, String> exsitingStringHashes = new HashMap<String, String>(); //Concat string, hash string
    public static HashMap<Integer, String> exsitingIntegerHashes = new HashMap<Integer, String>(); //Int, hash string
    
    public Hash() {
        
    }
    
    public static String hashString(String to_hash) throws Exception {
        if(exsitingStringHashes.get(to_hash)==null){
            return getNewHashString(to_hash);
        }
        return exsitingStringHashes.get(to_hash);
    }
    
    public static String hash(int to_hash) throws Exception {
        if(exsitingIntegerHashes.get(to_hash)==null){
            return getNewHash(to_hash);
        }
        return exsitingIntegerHashes.get(to_hash);
    }

    public static String getNewHash(int to_hash) throws Exception {
        String toHash = Integer.toString(to_hash);
        MessageDigest temp = MessageDigest.getInstance("MD5");
        temp.update(toHash.getBytes()); 
        byte[] stringToDigest = temp.digest();
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < stringToDigest.length; i++){
            result.append(String.format("%02x", stringToDigest[i] & 0xff));
        }
        exsitingIntegerHashes.put(to_hash, result.toString());
        return (result.toString());
    }
    
    public static String getNewHashString(String to_hash) throws Exception {
        MessageDigest temp = MessageDigest.getInstance("MD5");
        temp.update(to_hash.getBytes()); 
        byte[] stringToDigest = temp.digest();
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < stringToDigest.length; i++){
            result.append(String.format("%02x", stringToDigest[i] & 0xff));
        }
        exsitingStringHashes.put(to_hash, result.toString());
        return (result.toString());
    }
    
    public static void main(String[] args) throws Exception {
        String res = hash(Integer.parseInt(args[0]));
        System.out.println(res);
    }
    
}