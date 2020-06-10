
public class Weapon extends Item{
	
	int damage; 
	boolean isTwoHanded;
	
	Weapon(int Price, int MinHeroLevel, String Name, int Damage, boolean twoHand) {
		super(Price, MinHeroLevel, Name);
		damage = Damage;
		isTwoHanded = twoHand;
		typeString = "Weapon";
	}
	
	public int getDamage() {
		return damage;
	}
}
