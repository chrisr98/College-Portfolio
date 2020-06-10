// package hw6;

import java.util.ArrayList;

public class hashThread extends Thread {
	
	public int index;
	public int end;
	public ArrayList<String> hashStrings; 
	public UnHash inst;
	public int res;

	public hashThread(int start, int end, ArrayList<String> hashStrings, UnHash inst ) throws Exception {
		// TODO Auto-generated constructor stub
		index = start;
		this.end = end;
		this.hashStrings = hashStrings;
		this.inst = inst;
	}
	

	public void run() {
		while(index < end) {
			try {
    			res = inst.unhash(hashStrings.get(index));
			} catch (Exception e) {
    			// e.printStackTrace();
			}
			
			System.out.println(res);
			index++;
		}
	}
	

}
