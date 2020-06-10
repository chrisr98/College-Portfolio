
public class Entity extends TileSpace{
	int hp;
	int mana;
	String name;
	int level;
	int atkDamage;
	int defense;
	
	Entity(int Hp, int Mana, String Name, int Level, int Atk, int Def) {
		hp = Hp;
		mana = Mana;
		name = Name;
		level = Level;
		atkDamage = Atk;
		defense = Def;
	}
	
	public int getAttackDamage() {
		return atkDamage;
	}
	
	public int getDef() {
		return defense;
	}
	
	public String getName() {
		return name;
	}
}
