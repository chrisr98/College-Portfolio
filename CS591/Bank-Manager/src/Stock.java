
public class Stock {
	protected String name;
	protected double price;
	public int orderID;
	
	public Stock (String name, double price){
		this.name = name;
		this.price = price;
	}
	
	public String getName(){
		return name;
	}
	
	public Double getPrice(){
		return price;
	}
	
	public void setPrice(double newPrice){
		price = newPrice;
	}
}
