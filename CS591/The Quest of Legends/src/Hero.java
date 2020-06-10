import java.util.Random;

public class Hero implements PCorNPC {
	
	public String[] equippedWeapon = {"0", "0", "0", "0", "0"};
	public String[] equippedArmor = {"0", "0", "0", "0"};

	public Hero() {
		// TODO Auto-generated constructor stub
	}

	
	//Returns NPC or PC
	@Override
	public String[] getPerson(int level) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		//Handled somewhere else
		return null;
	}

	//returns how much damage done
	@Override
	public int damageDone(String[] hero) {
		// TODO Auto-generated method stub
		int strength = Integer.parseInt(hero[2]);
		int weaponDamage = Integer.parseInt(equippedWeapon[2]);
		int attackDMG = ((strength + weaponDamage)/2);
		return attackDMG;
	}

	//Return new stats after damage is dealt
	@Override
	public String[] damageTaken(String[] hero, int damage) {
		// TODO Auto-generated method stub
		int defense = Integer.parseInt(equippedArmor[3]);
		double agility = Integer.parseInt(hero[4])*0.02;
		int newHP = Integer.parseInt(hero[7]);
		int dodge = (int)agility;
		
		Random rand = new Random();
		int chanceToDodge = rand .nextInt(100 - 1 + 1) + 1;
		
		if(chanceToDodge <= dodge) {
			System.out.printf("%s dodged.", hero[0]);
		} else {
			int newDMG = Math.max(0, (damage -= defense));
			System.out.printf("%s's defense of %d has reduced the damage to %d.", hero[0], defense, newDMG);
			newHP -= newDMG;
		}
		
		hero[7] = newHP + "";
		
		return hero;
	}

	//Return new stats after spell deterioration
	@Override
	public String[] crowdControl(String[] hero, String type) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	//Return true or false if dead
	@Override
	public boolean isDead(String[] hero) {
		// TODO Auto-generated method stub
		boolean dead = false;
		int HP = Integer.parseInt(hero[7]);
		if(HP < 1) {
			dead = true;
			System.out.printf("%n%s is defeated.%n", hero[0]);
		}
		
		return dead;
	}


	@Override
	public void setWeapon(String[] weapon) {
		// TODO Auto-generated method stub
		equippedWeapon = weapon;
	}


	@Override
	public void setArmor(String[] armor) {
		// TODO Auto-generated method stub
		equippedArmor = armor;
	}
	
	//return damage done by weapon
	public int spellDamage(String[] hero, String[] spell) {
		int spells_base_damage = Integer.parseInt(spell[3]);
		int dexterity = Integer.parseInt(spell[4]);
		int damage = spells_base_damage + ((dexterity/10000)*spells_base_damage);
		return damage;
	}

	//Sets HP for NPC or PC
	public String[] setHP(String[] hero) {
		int level = Integer.parseInt(hero[6]);
		int HP = level*100;
		if(Integer.parseInt(hero[7])> 999) {
			hero[7] = "1500";
			return hero;
		}
		hero[7] = HP + "";
		return hero;
	}

	public String[] reviveHP(String[] hero) {
		int level = Integer.parseInt(hero[6]);
		int HP = (level*100)/2;
		hero[7] = HP + "";
		return hero;
	}

	//Add HP
	@Override
	public String[] addHP(String[] hero, int addHP) {
		// TODO Auto-generated method stub
		int HP = Integer.parseInt(hero[7]);
		HP += addHP;
		hero[7] = HP+"";
		return hero;
	}


	//Level up character when necessary
	@Override
	public String[] levelUp(String[] hero) {
		// TODO Auto-generated method stub
		int level = Integer.parseInt(hero[6]);
		int nextLvl = level*10;
		int EXP = Integer.parseInt(hero[8]);
		int change = 0;
		if(EXP >= nextLvl) {
			do {
				change = EXP - nextLvl;
				level+=1;
				System.out.printf("%n%s leveled up to level %d %n", hero[0], level);
				increaseSkills(hero);
				nextLvl = level*10;
				EXP = change;
				if(level == 10) {
					break;
				}
			} while(change > nextLvl);
			
		}
		hero[6] = level+"";
		hero[8] = "0";
		addEXP(hero, EXP);
		setHP(hero);
		return hero;
	}

	//Add EXP
	public String[] addEXP(String[] hero, int addEXP) {
		int EXP = Integer.parseInt(hero[8]);
		EXP += addEXP;
		hero[8] = EXP+"";
		return hero;
	}
	//
	@Override
	public String[] increaseSkills(String[] hero) {
		// TODO Auto-generated method stub
		//Name/mana/strength/agility/dexterity/starting money/LV/HP/EXP/class
		int mana = Integer.parseInt(hero[1]);
		int manaIncrease = mana/10;
		int STR = Integer.parseInt(hero[2]);
		int STRIncrease = STR/10;
		int AGL = Integer.parseInt(hero[3]);
		int AGLIncrease = AGL/10;
		int DEX = Integer.parseInt(hero[4]);
		int DEXIncrease = DEX/10;
		
		if(hero[9].equals("p")) {
			DEXIncrease += DEXIncrease;
		} else if(hero[9].equals("s")) {
			AGLIncrease += AGLIncrease;
		} else {
			STRIncrease += STRIncrease;
		}
		
		mana += manaIncrease;
		STR += STRIncrease;
		AGL += AGLIncrease;
		DEX += DEXIncrease;
	
		hero[1] = mana +"";
		hero[2] = STR +"";
		hero[3] = AGL +"";
		hero[4] = DEX +"";
		
		return hero;
	}

	//Reduce HP
	@Override
	public String[] reduceHP(String[] hero, int reduceHP) {
		// TODO Auto-generated method stub
		int HP = Integer.parseInt(hero[7]);
		HP -= reduceHP;
		hero[7] = HP+"";
		return hero;
	}

	//Add money
	@Override
	public String[] addMoney(String[] hero, int addMoney) {
		// TODO Auto-generated method stub
		int money = Integer.parseInt(hero[5]);
		money += addMoney;
		hero[5] = money+"";
		return hero;
	}

	//Reduce money
	@Override
	public String[] reduceMoney(String[] hero, int reduceMoney) {
		// TODO Auto-generated method stub
		int money = Integer.parseInt(hero[5]);
		money -= reduceMoney;
		hero[5] = money+"";
		return hero;
	}

	//Add Mana
	@Override
	public String[] addMana(String[] hero, int addMana) {
		// TODO Auto-generated method stub
		int mana = Integer.parseInt(hero[1]);
		mana += addMana;
		hero[1] = mana+"";
		return hero;
	}

	//reduce Mana
	@Override
	public String[] reduceMana(String[] hero, String[] spell) {
		// TODO Auto-generated method stub
		int mana = Integer.parseInt(hero[1]);
		int cost = Integer.parseInt(spell[4]);
		mana -= cost;
		hero[1] = mana+"";
		return hero;
	}

	
}
