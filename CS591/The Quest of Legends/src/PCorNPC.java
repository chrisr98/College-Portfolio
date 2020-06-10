//Interface for PCs and NPCs
public interface PCorNPC {
	
	//Returns NPC or PC	
	public String[] getPerson(int level);
	//returns how much damage done
	public int damageDone(String[] person);
	//Return new stats after damage is dealt
	public String[] damageTaken(String[] person, int damage);
	//Return new stats after spell deterioration
	public String[] crowdControl(String[] person, String type);
	//Return true or false if dead
	public boolean isDead(String[] person);
	//Set weapon
	public void setWeapon(String[] weapon);
	//Set armor
	public void setArmor(String[] armor);
	//return damage done by weapon
	public int spellDamage(String[] person, String[] spell);
	//Sets HP for NPC or PC
	public String[] setHP(String[] person);
	//Revive with half HP
	public String[] reviveHP(String[] person);
	//Add HP
	public String[] addHP(String[] person,  int addHP);
	//Reduce HP
	public String[] reduceHP(String[] person, int reduceHP);
	//Add money
	public String[] addMoney(String[] person, int addMoney);
	//Reduce money
	public String[] reduceMoney(String[] person, int reduceMoney);
	//Add mana
	public String[] addMana(String[] person, int addMana);
	//Reduce Mana
	public String[] reduceMana(String[] person, String[] spell);
	//Level up
	public String[] levelUp(String[] person);
	//Increase abilities
	public String[] increaseSkills(String[] person);
	//Add EXP
	public String[] addEXP(String[] person, int EXP);



}
