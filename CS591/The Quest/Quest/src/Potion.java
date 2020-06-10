
public class Potion extends Item {
	
	int increaseValue;
	int increaseType;
	String[] types = {"Agility", "Dexterity", "Attack", "Defense", "Hp", "Mana", "Strength"};
	
	Potion(int Price, int MinHeroLevel, String Name, int boost, int Type) {
		super(Price, MinHeroLevel, Name);
		increaseValue = boost;
		increaseType = Gamelogic.indexxof(types, Name);
		typeString = "Potion";
		
	}
	
	public int[] getTypeAndValue() {
		int[] ret = {increaseType, increaseValue};
		return ret;
	}
	
	public String toString() {
		String tab = "        ";
		 return  "Name: " + name + tab + "Price: " + price + tab + "Boost Value: " + 
				increaseValue + tab + "Boost Type: " + types[increaseType] +tab+ "Level Requirement: " + minHeroLevel;
	}
	
	
}
