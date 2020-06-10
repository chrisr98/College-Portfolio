/**
 * Made to handle different types of currency and conversions
 * Converts US dollars into other currences
 */

/**
 * @author chrisr98
 *
 */


public class Currency {

	/**
	 * @param args
	 */
	// Ideal we would have a database for the exchange rates
	// Current exchange rates from USD to Euro and Yuan
	
	// \u20AC euro symbol
	// \uffe5 yuan symbol
	
	private static final double USDToEuroMod = 0.92; // 0.92 Euro
	private static final double USDToYuanMod = 7.08; //7.08 Chinese Yuan
	private static final double USDMod = 1;
	
	// Current exchange rates from Yuan to Euro and USD
	private static final double yuanToEuroMod = 0.13; // 0.13 Euro
	private static final double yuanMod = 1; 
	private static final double yuanToUSDMod = 0.14; // 0.14 United States Dollar
	
	// Current exchange rates from Euro to USD and Yuan
	private static final double euroMod = 1; 
	private static final double EuroToYuanMod = 7.66; //7.66 Chinese Yuan
	private static final double EuroToUSDMod = 1.08; //1.08 United States Dollar
	
	
	public Currency() {
		
	}
	
	
	// Converts any form to any other forms
	public double convertUSDToEuro(double amountInDollar) {
		return amountInDollar*USDToEuroMod;
	}
	
	public double convertUSDToYuan(double amountInDollar) {
		return amountInDollar*USDToYuanMod;
	}
	
	public double convertEuroToUSD(double amountInEuro) {
		return amountInEuro*EuroToUSDMod;
	}
	
	public double convertEuroToYuan(double amountInEuro) {
		return amountInEuro*EuroToYuanMod;
	}
	
	public double convertYuanToUSD(double amountInYuan) {
		return amountInYuan*yuanToUSDMod;
	}
	
	public double convertYuanToEuro(double amountInYuan) {
		return amountInYuan*yuanToEuroMod;
	}
	
	
	
	
	public String toString() {
		String str = "";
		return str;
	}
	
	
}
