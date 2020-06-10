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
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Ruihong1996
 * Security Account class
 * Need to be finalized with Stock function
 */

public class SecurityAccount extends Account{
	double unrealizedProfit;
	double realizedProfit;
	

	public SecurityAccount(int accountNumber, int customerId) {
		super(accountNumber, customerId);
		unrealizedProfit = 0;
		realizedProfit = 0;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType(){
		return "SecurityAccount";
	}
	
	public double getUnrealizedProfit(ArrayList<Stock> s){
		
		try {   
            String pathname = "StockPositionDatabase.txt";   
            File filename = new File(pathname);   
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));   
            BufferedReader br = new BufferedReader(reader);  
            String line = "";    
            line = br.readLine(); 
            while (line != null) { 
                line = br.readLine(); 
                if(line == null) {
                	break;
            	}
                String[] splited = line.split(" ");
                if (splited[1].equals(accountNumber)){
                	for (int i = 0; i < s.size(); i++){
                		if (s.get(i).getName().equals(splited[3])){
                			double oldValue = Double.parseDouble(splited[4]) * Integer.parseInt(splited[5]);
                			double newValue = s.get(i).getPrice() * Integer.parseInt(splited[5]);
                			double difference = newValue - oldValue;
                			unrealizedProfit += difference;
                		}
                	}
                }
            }
            br.close();
        } catch (NullPointerException e) {    
        	System.out.println("Transaction file is empty");
        } catch (FileNotFoundException e) {  
          System.out.println("Transaction file not found");
        } catch (IOException e) {
			// TODO: handle exception
        	System.out.println("Error loading Transaction file");
		} 
		
		return unrealizedProfit;
	}
	
	public double getRealizedProfit(){
		return realizedProfit;
	}
	
	
	public void buyStock(Stock s, int share){
		double totalprice = s.getPrice() * share;
		if (totalprice <= value){
			String original = this.getRecord();
			Random generator = new Random();
			int orderNumber = generator.nextInt(8999) + 1000;
			s.orderID = orderNumber;
			String content = orderNumber + " " + accountNumber + " " + customerId + " " + s.getName() + " " + s.getPrice()+ " " + share +"\r\n";
			
			try {
				File file = new File("StockPositionDatabase.txt");
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
			
			value -= totalprice;
			String replaceWith = this.getRecord();
			BankSetting.replaceSelectedAccount(replaceWith, original);
		}
		
		else {
			System.out.println("Insufficient fund in account");
			GUI.popupWindow(true,"Insufficient fund in account");
		}
		
	}
	
	public ArrayList<String> listStock (){
		ArrayList<String> stocks = new ArrayList<String>();
		try {   
            String pathname = "StockPositionDatabase.txt";   
            File filename = new File(pathname);   
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));   
            BufferedReader br = new BufferedReader(reader);  
            String line = "";    
            line = br.readLine(); 
            while (line != null) { 
                line = br.readLine(); 
                if(line == null) {
                	break;
            	}
                String[] splited = line.split(" ");
                if (splited[1].equals(accountNumber)){
                	System.out.println(line);
                	stocks.add(line);
                }
            }  
        } catch (NullPointerException e) {    
        	System.out.println("Transaction file is empty");
        } catch (FileNotFoundException e) {  
          System.out.println("Transaction file not found");
        } catch (IOException e) {
			// TODO: handle exception
        	System.out.println("Error loading Transaction file");
		} 
		
		if(stocks.size()==0) {
			System.out.println("Have no stock holdings");
		}
		return stocks;
		
	}
	
	public boolean sellStock(Stock stock, int share){
		String original = this.getRecord();
		boolean res = false;
		try {   
            String pathname = "StockPositionDatabase.txt";   
            File filename = new File(pathname);   
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));   
            BufferedReader br = new BufferedReader(reader);  
            String line = ""; 
            int orderID = stock.orderID;
            while (line != null && !res) { 
                line = br.readLine(); 
                String oldContent = line;
                if(line == null) {
                	break;
            	}
                String[] splited = line.split(" ");
                if (splited[3].equals(stock.getName()) && Integer.parseInt(splited[1]) == accountNumber){
                	if (share <= Integer.parseInt(splited[5]) && orderID == Integer.parseInt(splited[0])){
                		int remaining = Integer.parseInt(splited[5]) - share;
                		value += share * stock.getPrice();
                		realizedProfit = share * (stock.getPrice() - Double.parseDouble(splited[4]));
                		String newContent = splited[0] + " " + splited[1] + " " + splited[2] + " " + splited[3] + " " + splited[4] + " " + Integer.toString(remaining);
                		replaceSelectedStockPosition(newContent, oldContent);
                		System.out.printf("SOLD YOUR SHARE of %s!%n", stock.getName());
                		res = true;
                	} else {
                		System.out.println("Not enough shares on that order");
                	}
                }
            }  
            br.close();
            String replaceWith = this.getRecord();
    		// write change to account database
    		BankSetting.replaceSelectedAccount(replaceWith, original);
    		return res;
    		
        } catch (NullPointerException e) {    
        	System.out.println("Transaction file is empty");
        	return false;
        } catch (FileNotFoundException e) {  
        	System.out.println("Transaction file not found");
        	return false;
        } catch (IOException e) {
			// TODO: handle exception
        	System.out.println("Error loading Transaction file");
        	return false;
		} 
		
	}
	
	public static void replaceSelectedStockPosition(String replaceWith, String original) {
	    try {
	        // input the file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader("StockPositionDatabase.txt"));
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
 

	        FileOutputStream fileOut = new FileOutputStream("StockPositionDatabase.txt");
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();
	        

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
	}

}
