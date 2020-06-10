/**
 * @author Ruihong1996
 * Saving Account class
 */

public class SavingAccount extends Account{
	private double interestRate;

	public SavingAccount(int accountNumber, int customerId) {
		super(accountNumber, customerId);
		this.interestRate = 0.01;
		// TODO Auto-generated constructor stub
	}
	
	public double getInterestRate (){
		return interestRate;
	}
	
	public void setInterestRate(double r){
		interestRate = r;
	}
	
	public boolean isRich(){
		boolean status = false;
		if (value >= 5000){
			status = true;
		}
		
		else{
			status = false;
		}
		
		return status;
	}
	
	
	public boolean isMaintainable(double amount){
		boolean status = false;
		if (value - amount >= 2500){
			status = true;
		}
		
		else{
			status = false;
		}
		
		return status;
	}
	
	public void payInterest (){
		deposit(value * interestRate);
	}
	
	public void transferToSecurity(SecurityAccount s, double amount){
		
		if (isMaintainable(amount) == true){
			transfer(s, amount);
		}
			
		else{
			System.out.println("Must maintain enough balance after transaction");
		}
		
	}
	@Override
	public String getType(){
		return "SavingAccount";
	}

}
