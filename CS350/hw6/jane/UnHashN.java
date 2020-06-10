// package hw6;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UnHashN {
	
	public ArrayList<String> hashStrings; 
	public  UnHash unhashInst;
	public ArrayList<Thread> threads;
	
	public UnHashN() throws Exception {
		unhashInst = new UnHash();
		threads = new ArrayList<Thread>();
		hashStrings = new ArrayList<String>();
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		UnHashN trial = new UnHashN();
		trial.unhashNFromFile(args[0], Integer.parseInt(args[1]));

	}
	
	public void unhashNFromFile(String input_filepath, int N) throws Exception {
		
		//read the hash strings in line by line and store it as an 
		
		
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
			hashStrings.add((sc.nextLine()));      //returns the line that was skipped  
		}  
		sc.close();
		
		//creating all the threads and storing as array
		int length = hashStrings.size()/N;
		for (int i =0; i<N ; i++) {
			threads.add(new hashThread(i*length, (i*length)+(length), hashStrings, unhashInst));
			 
		}
		
		for( int i=0; i<N; i++) {
			threads.get(i).start();
		}
		
		
		for( int i=0; i<N; i++) {
			
		      try
		      {
		    	  threads.get(i).join ();
		      }
		      catch (InterruptedException e)
		      {
		      }
		}
	      
		
	}}

