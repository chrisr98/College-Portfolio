
public class Item {
	String typeString;
	int price;
	int minHeroLevel;
	String name;
	
	Item(int Price, int MinHeroLevel, String Name) {
		price = Price;
		minHeroLevel = MinHeroLevel;
		name = Name;
	}
	
	public int getReq() {
		return minHeroLevel;
	}
	
	public String toString() {
		return typeString + " " + name + " " + price + " " + minHeroLevel;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPrice() {
		return price;
	}
}
