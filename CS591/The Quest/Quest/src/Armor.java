
public class Armor extends Item {
	
	int defenseBoost;
	Armor(int Price, int MinHeroLevel, String Name, int defBoost) {
		super(Price, MinHeroLevel, Name);
		defenseBoost = defBoost;
		typeString = "Armor";
	}
	
	public int getDefBoost() {
		return defenseBoost;
	}
	
	public String toString() {
		String tab = "        ";
		return "Name: " + name + tab +"Price: " + price + tab + "Required Level: " + minHeroLevel + tab +
				"Defense Boost: " + defenseBoost;
	}
}
