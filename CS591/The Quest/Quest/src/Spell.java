
public class Spell extends Item {
	int damage;
	int manaConsumption;
	int type;
	String[] types = {"ice", "fire", "lightning"};
	//type 0 = ice, 1 = fire, 2 = lightning
	
	Spell(int Price, int MinHeroLevel, String Name, int Damage, int ManaC, int Type) {
		super(Price, MinHeroLevel, Name);
		damage = Damage;
		manaConsumption = ManaC;
		type = Type;
		typeString = "Spell";
	}
	
	public String toString() {
		String tab = "        ";
		return  "Name: " + name + tab + "Price: " + price +tab+ "Level Requirement: " + minHeroLevel +tab+
				"Damage: " + damage + tab + "Mana Cost: " + manaConsumption;
	}
	
	public int getType() {
		return type;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getMC() {
		return manaConsumption;
	}
	
	/*
	 * An ice spell, apart from the damage it causes it also reduces the damage
	range of the enemy.
	A fire spell, apart from the damage it causes it also reduces the defense level
	of the enemy.
	A lightning spell, apart from the damage it causes it also reduces the dodge
	chance of the enemy
	
	*/
}
