import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Ruihong1996
 * Account class
 */

public class Account {
	protected int accountNumber;
	protected int customerId;
	protected double value;
	protected boolean status;

	
	public Account(int accountNumber, int customerId){
		this.accountNumber = accountNumber;
		this.customerId = customerId;
		this.value = 0;
	}
	
	public int getAccountNumber(){
		return accountNumber;
	}
	
	public int getOwner(){
		return customerId;
	}
	
	public double getValue(){
		return value;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	public String getType(){
		return "Account";
	}
	
	public String getRecord(){
		String record = accountNumber + " " + customerId + " " + this.getType() + " " + status + " " + value;
		return record;
	}
	
	public void setValue(double v){
		value = v;
	}
	
	public void setStatus(boolean s){
		status = s;
	}
	
	
	public void deposit(double d){
		String original = this.getRecord();
		value += d;
		String replaceWith = this.getRecord();
		
		// write change to account database
		BankSetting.replaceSelectedAccount(replaceWith, original);
		
		// Write to transaction database
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = format1.format(cal.getTime());
		
		String content = formatted + " " + accountNumber + " " + customerId + " " + "deposit" + " " + d + "\r\n";
		try {
			File file = new File("TransactionDatabase.txt");
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
	
	public void withdraw(double w){
		String original = this.getRecord();
		value -= w;
		String replaceWith = this.getRecord();
		
		// write change to account database
		BankSetting.replaceSelectedAccount(replaceWith, original);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = format1.format(cal.getTime());
		
		String content = formatted + " " + accountNumber + " " + customerId + " " + "withdraw" + " " + w + "\r\n";
		try {
			File file = new File("TransactionDatabase.txt");
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
	
	public void transfer(Account a, double amount){
		String original1 = this.getRecord();
		String original2 = a.getRecord();
		value -= amount;
		a.setValue(a.getValue() + amount);
		String replaceWith1 = this.getRecord();
		String replaceWith2 = a.getRecord();
		
		// write change to account database
		BankSetting.replaceSelectedAccount(replaceWith1, original1);
		BankSetting.replaceSelectedAccount(replaceWith2, original2);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = format1.format(cal.getTime());
		
		String content1 = formatted + " " + accountNumber + " " + customerId + " " + "transfer_out" + " " + amount + "\r\n";
		String content2 = formatted + " " + a.getAccountNumber() + " " + a.getOwner() + " " + "transfer_in" + " " + amount + "\r\n";
		try {
			File file = new File("TransactionDatabase.txt");
			FileOutputStream fop = new FileOutputStream(file,true);
			OutputStreamWriter osw = new OutputStreamWriter(fop);
			BufferedWriter bw = new BufferedWriter(osw);
			//byte[] contentInBytes = content.getBytes();
			bw.write(content1);
			bw.write(content2);
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
	
}
