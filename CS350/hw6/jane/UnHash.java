// package hw6;
import java.io.*; 
import java.util.Scanner;
import java.util.HashMap;
import java.security.*;

public class UnHash {
	
	HashMap<String, Integer> values;
	static int num;
	
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
	
	public UnHash() throws Exception {
		values = new HashMap< String, Integer>();
	}
	public int unhash(String to_unhash) throws Exception {
		
	
		if(values.containsKey(to_unhash)) {
			return values.get(to_unhash);
		}
		
		for (int i = num; i < Integer.MAX_VALUE; i++) {
			
			String key = hash(i);
			values.put(key, i);
			if (key.equals(to_unhash)) {
				num =i;
				return i;
			}
		}
		return num;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		UnHash trial = new UnHash();
		trial.unhashFromFile(args[0]);

		
	}
	
	public void unhashFromFile(String input_filepath) throws Exception {
		FileInputStream fis =null;
		try {
			fis = new FileInputStream(input_filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
		Scanner sc=new Scanner(fis);    //file to be scanned  
		//returns true if there is another line to read  
		while(sc.hasNextLine())  
		{  
			System.out.println(unhash(sc.nextLine()));     //returns the line that was skipped  
		}  
		sc.close();

		
	}
	
	

}
