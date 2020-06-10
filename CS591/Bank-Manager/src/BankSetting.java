import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

public class BankSetting {
	protected static double openFee;
	protected static double closeFee;
	protected static double transactionFee;
	protected static double totalValue;
	
	
	public BankSetting (double totalValue, double openFee, double closeFee, double transactionFee){
		this.totalValue = totalValue;
		this.openFee = openFee;
		this.closeFee = closeFee;
		this.transactionFee = transactionFee;
	}
	
	public static double getTotalValue(){
		return totalValue;
	}
	
	public static double getOpenFee(){
		return openFee;
	}
	
	public static double getCloseFee(){
		return closeFee;
	}
	
	public static double getTransactionFee(){
		return transactionFee;
	}
	
	public static void setTotalValue(double v){
		totalValue = v;
	}
	
	public void setOpenFee(double o){
		openFee = o;
	}
	
	public void setCloseFee(double c){
		closeFee = c;
	}
	
	public void setTransactionFee(double t){
		transactionFee = t;
	}
	
	// create a new account class, generate account number, add it to database
	public static Account openAccount(String t, String cID){
		int type = Integer.parseInt(t);
		int customerId = Integer.parseInt(cID);
		Random generator = new Random();
		int accountNumber = generator.nextInt(8999) + 1000;
		Account temp = new Account(0,0);
		
		// Checking account
		if (type == 1){
			temp = new CheckingAccount (accountNumber, customerId);
			temp.setStatus(true);
			totalValue += openFee;
			String content = accountNumber + " " + customerId + " " + "CheckingAccount" + " " + temp.getStatus() + " " + temp.getValue()+"\r\n";
			
			try {
				File file = new File("AccountDatabase.txt");
				FileOutputStream fop = new FileOutputStream(file,true);
				OutputStreamWriter osw = new OutputStreamWriter(fop);
				BufferedWriter bw = new BufferedWriter(osw);
				//byte[] contentInBytes = content.getBytes();
				bw.write(content);
				//bw.newLine();
		        bw.flush();
		        bw.close();
		        osw.close();
		        fop.close();
		    
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// Saving account
		else if (type == 2){
			temp = new SavingAccount (accountNumber, customerId);
			temp.setStatus(true);
			totalValue += openFee;
			String content = accountNumber + " " + customerId + " " + "SavingAccount" + " " + temp.getStatus() + " " + temp.getValue()+"\r\n";
			
			try {
				File file = new File("AccountDatabase.txt");
				FileOutputStream fop = new FileOutputStream(file,true);
				OutputStreamWriter osw = new OutputStreamWriter(fop);
				BufferedWriter bw = new BufferedWriter(osw);
				//byte[] contentInBytes = content.getBytes();
				bw.write(content);
				//bw.newLine();
		        bw.flush();
		        bw.close();
		        osw.close();
		        fop.close();
		    
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Security account
		else if (type == 3){
			temp = new SecurityAccount (accountNumber, customerId);
			temp.setStatus(true);
			totalValue += openFee;
			String content = accountNumber + " " + customerId + " " + "SecurityAccount" + " " + temp.getStatus() + " " + temp.getValue()+"\r\n";
			
			try {
				File file = new File("AccountDatabase.txt");
				FileOutputStream fop = new FileOutputStream(file,true);
				OutputStreamWriter osw = new OutputStreamWriter(fop);
				BufferedWriter bw = new BufferedWriter(osw);
				//byte[] contentInBytes = content.getBytes();
				bw.write(content);
				//bw.newLine();
		        bw.flush();
		        bw.close();
		        osw.close();
		        fop.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return temp;
		
	}
	
	// set account status to false and update database
	public static boolean closeAccount (Account a){
		String original = a.getRecord();
		if (a.getValue() >= closeFee){
			totalValue = totalValue + closeFee - a.getValue();
			a.setValue(0);
			a.setStatus(false);
			String replaceWith = a.getRecord();
			replaceSelectedAccount(replaceWith, original);
			return true;
		} else {
			GUI.popupWindow(true, "Not enough balance left in this account to pay closing fee");
			System.out.println("Not enough balance left in this account to pay close fee");
			return false;
		}
	}
	
	// enter the original line and a new line to do the replacement in acccount database
	public static void replaceSelectedAccount(String replaceWith, String original) {
	    try {
	        // input the file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader("AccountDatabase.txt"));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;

	        while ((line = file.readLine()) != null) {
	            inputBuffer.append(line);
	            inputBuffer.append("\r\n");
	        }
	        file.close();
	        String inputStr = inputBuffer.toString();
	        
	        if(inputStr.contains(original)) {
	        	inputStr = inputStr.replace(original, replaceWith); 
	        }

	        FileOutputStream fileOut = new FileOutputStream("AccountDatabase.txt");
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();
	        

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
	}

}
