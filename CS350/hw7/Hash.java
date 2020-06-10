import java.security.*;


public class Hash {

    public Hash() {
        
    }

    public static String hash(int to_hash) throws Exception {
        String toHash = Integer.toString(to_hash);
        MessageDigest temp = MessageDigest.getInstance("MD5");
        temp.update(toHash.getBytes()); 
        byte[] stringToDigest = temp.digest();
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < stringToDigest.length; i++){
            result.append(String.format("%02x", stringToDigest[i] & 0xff));
        }
        return (result.toString());
    }
    
    public static void main(String[] args) throws Exception {
        String res = hash(Integer.parseInt(args[0]));
        System.out.println(res);
    }
    
}