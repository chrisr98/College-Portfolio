/**
 * @author Ruihong1996
 * Checking Account class
 */

public class CheckingAccount extends Account{

	public CheckingAccount(int accountNumber, int customerId) {
		super(accountNumber, customerId);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void withdraw(double w){
		super.withdraw(w);
		String original = this.getRecord();
		value -= BankSetting.getTransactionFee();
		BankSetting.setTotalValue(BankSetting.getTotalValue() + BankSetting.getTransactionFee());
		String replaceWith = this.getRecord();
		BankSetting.replaceSelectedAccount(replaceWith, original);
	}
	
	@Override
	public void transfer(Account a, double amount){
		super.transfer(a, amount);
		String original = this.getRecord();
		value -= BankSetting.getTransactionFee();
		BankSetting.setTotalValue(BankSetting.getTotalValue() + BankSetting.getTransactionFee());
		String replaceWith = this.getRecord();
		BankSetting.replaceSelectedAccount(replaceWith, original);
	}
	
	@Override
	public String getType(){
		return "CheckingAccount";
	}
}
