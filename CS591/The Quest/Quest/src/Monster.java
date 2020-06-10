import java.util.Random;
//import java.lang.Math;

public class Monster extends Entity{
	int dodge;
	String[] types = {"dragon", "exoskeleton", "spirit"};
	boolean isDead;
	
	Monster(int Hp, String Name, int Level, int Atk, int Def, int Dodge) {
		super(Hp, 0, Name, Level, Atk, Def);
		dodge = Dodge;
		isDead = (Hp <= 0);
	}
	
	public boolean receiveSpellDamage(int damage) {//is defense effective against spells?
		System.out.println(name + " has lost " + damage + " hp from a spell.");
		hp -= damage;
		if (hp <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getDodge() {
		return dodge;
	}
	
	//TODO: function takes probability and returns bool
	public boolean getAttacked(int damage) { //+ bool isSpell parameter //returns whether monster is killed
		int toSubtract = damage - defense;
		if (toSubtract < 0) toSubtract = 0;
		Random r = new Random();
		double random =  + r.nextDouble();
		
		if (random < dodge / 100) {
			System.out.println(name + "has dodged an attack.");
		} else {
			hp -= toSubtract;
			if (hp < 0) hp = 0;
			System.out.println(name + " has lost " + toSubtract + " hp and now its hp is " + hp);
		}
		if (hp <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//public getSpell...
	
	public void decreaseDodge(int value) {
		dodge -= value;
	}
	
	public void decreaseDef(int value) {
		defense -= value;
	}
	
	
	
	public String toString() {
		String tab = "        ";
		return "Name: " + name + tab + "Hp: " +  hp +tab+"Level: "+ level + tab +
				 "Attack: " + atkDamage + tab + "Dodge Chance: " + dodge +tab+ "Defense: " + defense;
	}
	
	public int getHP() {
		return hp;
	}
	
	public boolean isDead() {
		return (hp <= 0);
	}
	
	public int getLevel() {
		return level;
	}
}
