import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.*;


/**
 * 
 */

/**
 * @author chrisr98
 * Bank implements an array list of accounts Bank 
 * has the ability to create and close, display, choose all accounts (per customer) 
 * bank should implement reserves 10%.
 * Able to display everything in different currency
 * 
 * 
 * TODO:
 * Need to limit access to account per user and give manager access to all.
 * display in different currencies
 * DEBUG
 * All classes should have default constructors
 */



public class Bank extends Currency{


	protected Map<Integer, ArrayList<Account>> list_of_account = new HashMap<Integer, ArrayList<Account>>(); 
	protected double totalVal = 0;
	protected double reserves = 0;
	protected int maxManager = 1;
	//Could use a list if we want to increase max managers --> remember to change mutator and accessor methods
	protected Member manager = new Manager();
	protected ArrayList<Customer> customers = new ArrayList<Customer>();
	protected ArrayList<Stock> stocks = new ArrayList<Stock>();
	protected BankSetting bankSetting = new BankSetting(0,20,20,20);
	protected LocalDate date = LocalDate.now(); //(yyyy-MM-dd)
	/**
	 * 
	 */
	public Bank() {
		// TODO Auto-generated constructor stub
	}
	
	public Bank(Manager newManager) {
		this.manager = newManager;
	}
	
	public void advanceDay() {
		System.out.println(date.toString());
		date = date.plusDays(1);
		System.out.println(date.toString());
	}

	/**
	 * @param args
	 */	
	
	public void addManager(Manager newManager) {
		this.manager = newManager;
	}
	
	public void addCustomer(Customer newCustomer) {
		this.customers.add(newCustomer);		
	}
	
	public void addStock(Stock newStock) {
		this.stocks.add(newStock);		
	}
	
	public void removeStock(Stock s) {
		this.stocks.remove(s);		
	}
	
	public void removeCustomer(Customer customer) {
		this.customers.remove(customer);
	}
	
	public int numberOfCustomers() {
		return this.customers.size();
	}
	
	// Adds an account
	public void addAccount(String uID, Account accountToAdd) {
		//Takes an account and adds it to the list
		int userID = Integer.parseInt(uID);
		try {
			ArrayList<Account> accList = list_of_account.get(userID);
			accList.add(accountToAdd);
			list_of_account.put(userID, accList);
		} catch (NullPointerException e) {
			// TODO: handle exception
			ArrayList<Account> accList = new ArrayList<Account>();
			accList.add(accountToAdd);
			list_of_account.put(userID, accList);
			
		}
	}
	// Removes an account
	// already combined with BankSetting class's closeAccount function

	public void removeAccount(String uID, String accNum) {
		int userID = Integer.parseInt(uID);
		int accountNum = Integer.parseInt(accNum);
		//Takes in the account number and removes it from the list
		//Needs to close the account with built in account functions.
		ArrayList<Account> accList = list_of_account.get(userID);
		Account rem = getUserAccount(uID, accNum);
		double closeFee = bankSetting.getCloseFee();
		if(bankSetting.closeAccount(rem)) {
			accList.remove(rem);
			list_of_account.put(userID, accList);
			System.out.printf("You will be charged a closing fee.%n");
			System.out.printf("%f will be deducted from your account before closing it.%n", closeFee);
		}
		
		
	}
	
	//Check if userID is in database
	public boolean checkUser(String uID) {
		int userID = Integer.parseInt(uID);
		for(Customer c: customers) {
			if (c.getIdNum() == userID) {
				return true;
			}
		}
		return false;
	}
	
	// Guides the user through the process of creating an account
	public void closeAccount(String userID, String accountNumber) {
				printUserAcc(userID, "1");
				removeAccount(userID, accountNumber);
				System.out.printf("The account %d has been closed.%n", accountNumber);		
	}
	
	//Get account based on account number
	public Account getUserAccount(String uID, String accNum) {
		int userID = Integer.parseInt(uID);
		int accountNumber = Integer.parseInt(accNum);
		try {
			ArrayList<Account> accList = list_of_account.get(userID);
			Account res = new Account(0,0);
			for(Account acc: accList) {
				if(accountNumber==acc.getAccountNumber()) {
					res = acc;
				}
			}
			return res;//accList.get(accountNumber);
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.printf("This account does not exist%n");
			return null;
		}	
	}
	
	//To print all accounts for a user
	public void printUserAcc(String uID, String currencyType) {
		int userID = Integer.parseInt(uID);
		try {
			if (currencyType.equals("1") || currencyType.equals("2") || currencyType.equals("3")) {
				ArrayList<Account> accList = list_of_account.get(userID);
				System.out.printf("Display account(s) for %d!%n", userID);
				for(Account acc: accList) {
					printInfoForAcc(acc, currencyType);
				}
			} else {
				System.out.printf("Not a valid input for currency at printUserAcc%n");
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.printf("This user has no accounts to show%n");
		}
	}
	
	// Show info for member 
	public void showMemberInfo(Member m) {
		int idNum = m.getIdNum();
		String firstName = m.getFirstName();
		String lastName = m.getLastName();
		String password = m.getPassword();
		String username = m.getUsername();
		System.out.printf("User Information!%n");
		System.out.printf("First Name: %s%nLast Name: %s%nUsername: %s%nPassword: %s%nUserID: %d%n", firstName, lastName, username, password, idNum);
		System.out.printf("%n");
	}

	public String getCustomerInfo(Customer m) {
		int idNum = m.getIdNum();
		String rtrn = "";
		rtrn = rtrn +"\nFirst Name: " + m.getFirstName();
		rtrn = rtrn +"\nLast Name: " + m.getLastName();
		rtrn = rtrn +"\nUsername: " + m.getUsername();

		if(list_of_account.get(m.idNum)!=null){
			for(Account a: list_of_account.get(m.idNum)){
				rtrn = rtrn +"\n"+a.getType()+" - "+a.getAccountNumber();
				rtrn = rtrn +"\n"+"Has a value of: "+a.getValue()+" Dollars == "+convertUSDToYuan(a.getValue())+" Yuan == "+convertUSDToEuro(a.getValue())+" Euros";
				if(a.getType().equals("SecurityAccount")){
					rtrn = rtrn +"\nUnrealized Profit: "+((SecurityAccount)a).getUnrealizedProfit(stocks)+", Realized Profit: "+((SecurityAccount)a).getRealizedProfit();
				}
			}
		}
		return rtrn;
	}
	
	
	//We want to show account number/Owner/status/value for a single account
	public void printInfoForAcc(Account account, String currencyType) {
		System.out.printf("The account owner ID is %s!%n", account.getOwner());
		System.out.printf("The account type is %s!%n", account.getType());
		System.out.printf("The account number is %d!%n",  account.getAccountNumber());
		//Checks currencyType
		// \u20AC euro symbol
		// \uffe5 yuan symbol
		if(currencyType.equals("1")) { // US Dollars
			System.out.printf("The account currently holds $%f!%n", account.getValue());
		} else if(currencyType.equals("2")) { // Chinese Yuan
			System.out.printf("The account currently holds \uffe5%f!%n", convertUSDToYuan(account.getValue()));
		} else if(currencyType.equals("3")) { // Euro
			System.out.printf("The account currently holds \u20AC%f!%n", convertUSDToEuro(account.getValue()));
		}
				
	}
	
	//creates a customer and updates database
	public Customer createCustomer(String password, String username, String firstName, String lastName) {
		Random generator = new Random();
		int customerId = generator.nextInt(899999) + 100000;
		int pocketMoney = 0; //Do we need a constructor with pocketmoney? What is pocketmoney?
		Customer c = new Customer(customerId, firstName, lastName, password, username, pocketMoney);
		
		// Write customer to database
		String content = c.getIdNum()+ " " + c.getFirstName() + " " + c.getLastName() + " " + c.getPassword() + " " + c.getUsername() + " " + c.getPocketMoney() + "\r\n";
		try {
			File file = new File("CustomerDatabase.txt");
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
		addCustomer(c);
		return c;
	}
	
	//get customer from database by checking username and password
	public Customer getCustomer(String username, String password) {
		for(Customer c: customers) {
			if(c.getUsername().equals(username) && c.getPassword().equals(password)) {
				return c;
			}
		}
		return null;
	}
	
	//creates a checking account
	public CheckingAccount createChecking(String uID, String deposit) {
		int money = Integer.parseInt(deposit);
		CheckingAccount newAcc = (CheckingAccount) bankSetting.openAccount("1", uID);
		newAcc.deposit(money);
		addAccount(uID, newAcc);
		return newAcc;
	}
	
	//creates a saving account
	public SavingAccount createSaving(String uID, String deposit) {
		int money = Integer.parseInt(deposit);
		SavingAccount newAcc = (SavingAccount) bankSetting.openAccount("2", uID);
		newAcc.deposit(money);
		addAccount(uID, newAcc);
		return newAcc;
	}
	
	//creates a security account
	public SecurityAccount createSecurity(Customer c, SavingAccount saveAcc, String amountToTransfer) {
		String uID = c.getIdNum()+"";
		SecurityAccount newAcc = (SecurityAccount) bankSetting.openAccount("3", uID);
		makeTransfer(c, amountToTransfer, saveAcc.getAccountNumber()+"", newAcc.getAccountNumber()+"");
		addAccount(uID, newAcc);
		return newAcc;
	}
	
	//Checks if a saving account is rich and maintainable enough to open a security account
	public boolean canOpenSecurityAccount(SavingAccount acc, String deposit) {
		//supposed to check if account is rich and maintainble and return true. Then you can open security account
		int amountToDeposit = Integer.parseInt(deposit);
		try {
			if(acc.isRich()) { 	
				if (acc.isMaintainable(amountToDeposit)) {
					return true;
				} else {
					System.out.printf("Saving account is not maintainable!%n");
					System.out.printf("Come back with more money in your saving account!%n");
				}				
			} else {
				System.out.printf("You do not have enough to open a security account and trade stocks!%n");
				System.out.printf("Come back with more money in your saving account!%n");
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.printf("Not a valid saving account!%n");
		}
		return false;
		
	}
	
	//make deposit
	public void makeDeposit(Customer c, String amountForTransaction, String accountNumber) {
		int money = Integer.parseInt(amountForTransaction);
		String userID = ""+c.getIdNum();
		Account acc = getUserAccount(userID, accountNumber);
		acc.deposit(money);
	}
	
	//make withdrawal
	public void makeWithdrawal(Customer c, String amountForTransaction, String accountNumber) {
		int money = Integer.parseInt(amountForTransaction);
		String userID = ""+c.getIdNum();
		Account acc = getUserAccount(userID, accountNumber);
		acc.withdraw(money);
	}
	
	//make transfer
	public void makeTransfer(Customer c, String amountForTransaction, String accountNumToTranserFrom, String accountNumToTranserTo) {
		int money = Integer.parseInt(amountForTransaction);
		String userID = ""+c.getIdNum();
		Account from = getUserAccount(userID, accountNumToTranserFrom);
		Account to = getUserAccount(userID, accountNumToTranserTo);
		from.transfer(to, money);
	}
	
	//get checking account of a user by their userID
	public CheckingAccount getCheckingAccount(String uID) {
		int userID = Integer.parseInt(uID);
		ArrayList<Account> accList = list_of_account.get(userID);
		try {
			for(Account account: accList) {
				if(account.getType().equals("CheckingAccount")) {
					return (CheckingAccount) account;
				}
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			return null;
		}
		return null;
	}
	
	//get saving account of a user by their userID	
	public SavingAccount getSavingAccount(String uID) {
		int userID = Integer.parseInt(uID);
		ArrayList<Account> accList = list_of_account.get(userID);
		try {
			for(Account account: accList) {
				if(account.getType().equals("SavingAccount")) {
					return (SavingAccount) account;
				}
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			return null;
		}
		return null;
	}
	
	//get security account of a user by their userID
	public SecurityAccount getSecurityAccount(String uID) {
		int userID = Integer.parseInt(uID);
		ArrayList<Account> accList = list_of_account.get(userID);
		try {
			for(Account account: accList) {
				if(account.getType().equals("SecurityAccount")) {
					return (SecurityAccount) account;
				}
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			return null;
		}
		return null;
	}
	
	//Take out a loan
	public void takeLoan(Customer c, String amountToBorrow) {
		try {
			String userID = ""+c.getIdNum();
			printUserAcc(userID, "1");
			System.out.printf("Loans can only be added to a checking account%n");
			CheckingAccount checkAcc = getCheckingAccount(userID);
			checkAcc.deposit(Double.parseDouble(amountToBorrow));
		} catch (NullPointerException e) {
			// TODO: handle exception
			GUI.popupWindow(true, "No such account exists at loan menu!");
			System.out.printf("No such account exists at loan menu!%n");
			
		}
	}
	
	// buy stock for this customer
	public void buyStocks(Customer c, String stockName, int share) {
		String userID = ""+c.getIdNum();
		SecurityAccount acc = getSecurityAccount(userID);
		if(acc != null) {
			//Here is where we read from stock database
			for (int i = 0; i < stocks.size(); i++){
				if(stocks.get(i).getName().equals(stockName)){
					acc.buyStock(stocks.get(i), share);
					System.out.printf("You bougt a share of %s!%n", stockName);
				}
			}
			
		} else {
			System.out.printf("You do not own a security account!%n");
			System.out.printf("You cannot buy/sell stocks!%n");
		}
	}
	
	// sell stock for this customer
	public void sellStocks(Customer c, String stockName, int share) {
		String userID = ""+c.getIdNum();
		SecurityAccount acc = getSecurityAccount(userID);
		if(acc != null) {
			//Here is where we read from stock database
			for (int i = 0; i < stocks.size(); i++){
				if(stocks.get(i).getName().equals(stockName)){
					acc.sellStock(stocks.get(i), share);
				}
			}
			
		} else {
			System.out.printf("You do not own a security account!%n");
			System.out.printf("You cannot buy/sell stocks!%n");
		}
	}
	
	// get all stock holdings of a customer
	public ArrayList<String> customerStockInfo(Customer c){
		String userID = ""+c.getIdNum();
		SecurityAccount acc = getSecurityAccount(userID);
		ArrayList<String> result = new ArrayList<String>();
		if(acc != null) {
			//Here is where we read from stock database
			result = acc.listStock();
			
		} else {
			System.out.printf("You do not own a security account!%n");
			System.out.printf("You cannot buy/sell stocks!%n");
		}
		return result;
	}
	
	// get realized stock profit of a customer
	public String getCustomerRealizedProfit(Customer c){
		String userID = ""+c.getIdNum();
		SecurityAccount acc = getSecurityAccount(userID);
		String result = "";
		if(acc != null) {
			//Here is where we read from stock database
			Double temp = acc.getRealizedProfit();
			result = Double.toString(temp);
			
		} else {
			System.out.printf("You do not own a security account!%n");
			System.out.printf("You cannot buy/sell stocks!%n");
		}
		return result;
	}
	
	// get unrealized stock profit of a customer
	public String getCustomerUnrealizedProfit(Customer c){
		String userID = ""+c.getIdNum();
		SecurityAccount acc = getSecurityAccount(userID);
		String result = "";
		if(acc != null) {
			//Here is where we read from stock database
			Double temp = acc.getUnrealizedProfit(stocks);
			result = Double.toString(temp);
			
		} else {
			System.out.printf("You do not own a security account!%n");
			System.out.printf("You cannot buy/sell stocks!%n");
		}
		return result;
	}
	
	public void manageAddStock(String name, double price) {
		//This is where manage can add stocks
		Stock s = new Stock(name, price);
		addStock(s);
		String content = name + " " + price + "\r\n";
		try {
			File file = new File("StockDatabase.txt");
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
	
	// change stock price
	public boolean changeStockPrice(String stockName, double newPrice){
		for (int i = 0; i < stocks.size(); i++){
			if(stocks.get(i).getName().equals(stockName)){
				String oldContent = "\n"+stockName + " " + stocks.get(i).getPrice();
				stocks.get(i).setPrice(newPrice);
				String newContent = stockName + " " + newPrice;
				replaceSelectedStock(newContent, oldContent);
				return true;
			} 
		}
		GUI.popupWindow(true, "Stock does not exist");
		return false;
	}
	
	// update stock database by replacing old record with new one
	public static void replaceSelectedStock(String replaceWith, String original) {
	    try {
	        // input the file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader("StockDatabase.txt"));
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

	        FileOutputStream fileOut = new FileOutputStream("StockDatabase.txt");
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();
	        

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
	}
	
	//Displays all customers in database to manager
	public void showAllCustomers() {
		for(Customer c: customers) {
			showMemberInfo(c);
		}
	}

	public String returnAllCustomers(){
		String rtrn = "";
		for(Customer c: customers){
			rtrn += getCustomerInfo(c)+"\n";
		}
		return rtrn;
	}

	public String returnAllStocks(){
		String rtrn = "";
		if(stocks!=null){
			for(Stock s: stocks){
				rtrn +="\nStock Name: "+s.getName()+", Stock Price: "+s.getPrice();
			}
		}
		return rtrn;
	}
	
	//prints all customer accounts in database for manager
	public String toString(String currencyType) {
		try {
			if (currencyType.equals("1") || currencyType.equals("2") || currencyType.equals("3")) {
				System.out.printf("This bank has %d accounts!%n", list_of_account.size());
				for (ArrayList<Account> accList: list_of_account.values()) {
					for(Account acc: accList) {
						System.out.printf(" %n");
						for(Customer c: customers) {
							if(c.getIdNum() == acc.getOwner()) {
								System.out.printf("Owner's name: %s %s%n", c.getFirstName(), c.getLastName());
							}
						}
						printInfoForAcc(acc, currencyType);
						System.out.printf(" %n");
					}
				}
			} else {
				System.out.printf("Not a valid input for currency at toString%n");
			}
				
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.printf("This bank has no accounts to show!%n");
		}
			
		return "";
	}
	
	
	//Update total bank value and reserve
	
	public void updateVal() {
		System.out.printf("BANK VALUE AND RESERVES IS BEING UPDATED!%n");
		try {
			
			for (ArrayList<Account> accList: list_of_account.values()) {
				for(Account acc: accList) {
					totalVal += acc.getValue();
				}
			}
			// Add all fee collected by bank
			totalVal += bankSetting.getTotalValue();
			reserves = totalVal/10;
			updateTotalValueDatabase(totalVal);
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.printf("This bank has no accounts for reserve!%n");
		}
		System.out.printf("Val:%f, Res:%f%n",totalVal, reserves);
		System.out.printf("BANK VALUE AND RESERVES HAS BEEN UPDATED!%n");
		
	}
	
	public void loadFromDB() {
		// read total value from database when the program start
		totalVal = getTotalValueDatabase();

		// Add existing customer from database to array
		try {
			String pathname = "CustomerDatabase.txt";
			File filename = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			line = br.readLine();
			while (line != null) {
				line = br.readLine();
				if (line == null) {
					break;
				}
				String[] splited = line.split(" ");

				int customerId = Integer.parseInt(splited[0]);
				String firstName = splited[1];
				String lastName = splited[2];
				String password = splited[3];
				String userName = splited[4];
				int pocketMoney = Integer.parseInt(splited[5]);

				Customer c = new Customer(customerId, firstName, lastName, password, userName, pocketMoney);
				addCustomer(c);

			}

		} catch (NullPointerException e) {
			System.out.println("Customer database is empty");
		} catch (FileNotFoundException e) {
			System.out.println("Customer database not found");
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Error loading Customer file");
		}

		// Add all existing account from database to array
		try {
			
			String pathname = "AccountDatabase.txt";
			File filename = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			line = br.readLine();
			while (line != null) {
				line = br.readLine();
				if (line == null) {
					break;
				}
				String[] splited = line.split(" ");
				if (splited[2].equals("CheckingAccount")) {
					int accountNumber = Integer.parseInt(splited[0]);
					int customerId = Integer.parseInt(splited[1]);
					boolean status = Boolean.parseBoolean(splited[3]);
					if (status == false) {
						continue;
					}
					double value = Double.parseDouble(splited[4]);
					CheckingAccount c = new CheckingAccount(accountNumber, customerId);
					c.setStatus(status);
					c.setValue(value);
					addAccount(""+customerId, c);
				}

				else if (splited[2].equals("SavingAccount")) {
					int accountNumber = Integer.parseInt(splited[0]);
					int customerId = Integer.parseInt(splited[1]);
					boolean status = Boolean.parseBoolean(splited[3]);
					if (status == false) {
						continue;
					}
					double value = Double.parseDouble(splited[4]);
					SavingAccount c = new SavingAccount(accountNumber, customerId);
					c.setStatus(status);
					c.setValue(value);
					addAccount(""+customerId, c);
				}

				else if (splited[2].equals("SecurityAccount")) {
					int accountNumber = Integer.parseInt(splited[0]);
					int customerId = Integer.parseInt(splited[1]);
					boolean status = Boolean.parseBoolean(splited[3]);
					if (status == false) {
						continue;
					}
					double value = Double.parseDouble(splited[4]);
					SecurityAccount c = new SecurityAccount(accountNumber, customerId);
					c.setStatus(status);
					c.setValue(value);
					addAccount(""+customerId, c);
				}

			}

		} catch (NullPointerException e) {
			System.out.println("Account file is empty");
		} catch (FileNotFoundException e) {
			System.out.println("Account file not found");
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Error loading Account file");
		}
		
		// Add existing stocks from database
		try {
			String pathname = "StockDatabase.txt";
			File filename = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			line = br.readLine();
			while (line != null) {
				line = br.readLine();
				if (line == null) {
					break;
				}
				String[] splited = line.split(" ");

				
				String stockName = splited[0];
				double price = Double.parseDouble(splited[1]);
				Stock s = new Stock (stockName, price);
				addStock(s);

			}

		} catch (NullPointerException e) {
			System.out.println("stock database is empty");
		} catch (FileNotFoundException e) {
			System.out.println("stock database not found");
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Error loading stock file");
		}
		
		
		

		Runnable updateRunnable = new Runnable() {
			public void run() {
				updateVal();
			}
		};

		ScheduledExecutorService valExecutor = Executors.newScheduledThreadPool(1);
		valExecutor.scheduleAtFixedRate(updateRunnable, 0, 60, TimeUnit.SECONDS);
		
		Runnable dayRunnable = new Runnable() {
			public void run() {
				advanceDay();
				applyInterestToSaving();
			}
		};

		ScheduledExecutorService dayExecutor = Executors.newScheduledThreadPool(2);
		dayExecutor.scheduleAtFixedRate(dayRunnable, 0, 300, TimeUnit.SECONDS);
			
	}

	public void applyInterestToSaving() {
		for(int uID: list_of_account.keySet()) {
			System.out.println(uID);
			SavingAccount acc = getSavingAccount(""+uID);
			acc.payInterest();
		}
		
	}
	// update bank's total value in database
	public void updateTotalValueDatabase (double newValue){
		String v = Double.toString(newValue);
		try {
		    File writeName = new File("TotalValueDatabase.txt"); 
		    FileWriter writer = new FileWriter(writeName);
		    BufferedWriter out = new BufferedWriter(writer);
		    out.write(v);
		    out.flush(); 
		    out.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	// get value from TotalValueDatabase.txt
	public Double getTotalValueDatabase (){
		Double result = (double) 0;
		try {
	        // input the file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader("TotalValueDatabase.txt"));
	        String line;
	        line = file.readLine();
	        result = Double.parseDouble(line);
	        file.close();

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
		return result;
	}
	
	// input a date (yyyy-MM-dd) and print all transaction that day
	public void generateDailyReport(String date){
		ArrayList<String> transactions = new ArrayList<String>();
		try {   
            String pathname = "TransactionDatabase.txt";   
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
                if (splited[0].equals(date)){
                	transactions.add(line);
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
		
		if(transactions.size()==0) {
			System.out.println("Nothing to show for "+date);
		}
		for (int i = 0; i < transactions.size(); i++) { 		      
	          System.out.println(transactions.get(i)); 		
	    }   
	}

	public String generateTodaysReport(){
		String d = date.toString();
		ArrayList<String> transactions = new ArrayList<String>();
		try {
			String pathname = "TransactionDatabase.txt";
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
				if (splited[0].equals(d)){
					transactions.add(line);
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

		if(transactions.size()==0) {
			return "Nothing to show for today";
		}
		String output = "";
		for (int i = 0; i < transactions.size(); i++) {
			output = transactions.get(i)+"\n"+output; //System.out.println(transactions.get(i));
		}
		return output;
	}
	

//	public static void main(String[] args) {
//		Bank bank = new Bank();
//		bank.addManager(new Manager(1111,"Ben", "Stevens", "1234", "abcd"));
//		bank.loadFromDB();
//		Customer c = bank.getCustomer("aaaa", "1111");
//		bank.showMemberInfo(c);
//		bank.makeTransfer(c, "2", "3596", "2492"); // Will transfer 2 dollars from saving (3596) to security (2492) 
//		bank.buyStocks(c, "LYFT", 100); // Will buy 100 shares of LYFT
//		bank.sellStocks(c, "LYFT", 1);	// Will sell 1 share of LYFT
//		bank.printUserAcc(c.getIdNum()+"", "1"); // The idNUM in string and currency type. 1 2 3 for all 3 type
//	}
	

}
