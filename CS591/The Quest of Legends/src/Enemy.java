import java.util.Random;

public class Enemy implements PCorNPC{
	private static QuestInfo questInf;

	public Enemy() {
		// TODO Auto-generated constructor stub
		
	}
	
	public Enemy(QuestInfo data) {
		// TODO Auto-generated constructor stub
		questInf = data;
	}
	
	//Returns NPC or PC
	//Chooses a random enemy and it must match hero level	
	public String[] getPerson(int level) {
		String[] enemy;
	
		Random rand = new Random();
		//Only show 1 of the 3 hero classes at random.
		do {
			int whichEnemy = rand .nextInt(3 - 1 + 1) + 1;
			
			if (whichEnemy == 1) {
				int randEnemy = rand.nextInt((questInf.getDragonsSize()-1) - 0 + 1) + 0;
				enemy = questInf.getDragon(randEnemy);
			} else if (whichEnemy == 2) {
				int randEnemy = rand.nextInt((questInf.getSpiritsSize()-1) - 0 + 1) + 0;
				enemy = questInf.getSpirit(randEnemy);
			} else {
				int randEnemy = rand.nextInt((questInf.getExoskeletonsSize()-1) - 0 + 1) + 0;
				enemy = questInf.getExoskeleton(randEnemy);
			}
		} while (level > Integer.parseInt(enemy[1]));
		return enemy;
	}
	
	//returns how much damage enemy does
	public int damageDone(String[] enemy) {
		int attackDMG = Integer.parseInt(enemy[2]);
		return attackDMG;
	}
	
	//Return new enemy stats after damage is dealt to it
	public String[] damageTaken(String[] enemy, int damage) {
		int defense = Integer.parseInt(enemy[3]);
		int dodge = Integer.parseInt(enemy[4]);
		int newHP = Integer.parseInt(enemy[5]);
		
		Random rand = new Random();
		int chanceToDodge = rand .nextInt(100 - 1 + 1) + 1;
		
		if(chanceToDodge <= dodge) {
			System.out.printf("%s dodged.", enemy[0]);
		} else {
			int newDMG = Math.max(0, (damage -= defense));
			System.out.printf("%s's defense of %d has reduced the damage to %d.", enemy[0], defense, newDMG);
			newHP -= newDMG;
			
		}
		
		enemy[5] = newHP + "";
		
		return enemy;
	}
	
	public String[] spellDamageTaken(String[] enemy, int damage, String type) {
		int defense = Integer.parseInt(enemy[3]);
		int dodge = Integer.parseInt(enemy[4]);
		int newHP = Integer.parseInt(enemy[5]);
		
		Random rand = new Random();
		int chanceToDodge = rand .nextInt(100 - 1 + 1) + 1;
		
		if(chanceToDodge <= dodge) {
			System.out.printf("%s dodged.", enemy[0]);
		} else {
			enemy = crowdControl(enemy, type);
			int newDMG = Math.max(0, (damage -= defense));
			System.out.printf("%s's defense of %d has reduced the damage to %d.", enemy[0], defense, newDMG);
			newHP -= newDMG;
			
		}
		
		enemy[5] = newHP + "";
		
		return enemy;
	}
	
	//Return new enemy stats after spell deterioration
	public String[] crowdControl(String[] enemy, String type) {
		int damage = Integer.parseInt(enemy[2]);
		int defense = Integer.parseInt(enemy[3]);
		int dodge = Integer.parseInt(enemy[4]);
		
		//Recalculate each stat based on type.
		if(type.equals("f")) {
			int takeAway = damage/10; 
			damage = Math.max(0, (damage -= takeAway));
			System.out.printf("Spell has reduced %s's damage to %d.\n", enemy[0], damage);
		} else if(type.equals("i")) {
			int takeAway = defense/10;
			defense = Math.max(0, (defense -= takeAway));
			System.out.printf("Spell has reduced %s's defense to %d.\n", enemy[0], defense);
		} else {
			int takeAway = dodge/10;
			dodge = Math.max(0, (dodge -= takeAway));
			System.out.printf("Spell has reduced %s's dodge chance to %d.\n", enemy[0], dodge);
		}
		
		enemy[2] = damage + "";
		enemy[3] = defense + "";
		enemy[4] = dodge + "";
		
		return enemy;
	}
	
	//Return true or false if dead
	public boolean isDead(String[] enemy) {
		boolean dead = false;
		int HP = Integer.parseInt(enemy[5]);
		if(HP < 1) {
			dead = true;
			System.out.printf("%n%s is defeated.\n", enemy[0]);
		}
		
		return dead;
	}

	@Override
	public void setWeapon(String[] weapon) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
	}

	@Override
	public void setArmor(String[] armor) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
	}

	@Override
	public int spellDamage(String[] person, String[] spell) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return 0;
	}

	@Override
	public String[] setHP(String[] person) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	@Override
	public String[] levelUp(String[] person) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	@Override
	public String[] increaseSkills(String[] person) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	@Override
	public String[] addHP(String[] person, int addHP) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	@Override
	public String[] reduceHP(String[] person, int reduceHP) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	@Override
	public String[] addMoney(String[] person, int addMoney) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	@Override
	public String[] reduceMoney(String[] person, int reduceMoney) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	@Override
	public String[] addMana(String[] person, int addMana) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	@Override
	public String[] reduceMana(String[] person, String[] spell) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	@Override
	public String[] addEXP(String[] person, int EXP) {
		// TODO Auto-generated method stub
		System.out.println("Not applicable");
		return null;
	}

	@Override
	public String[] reviveHP(String[] person) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
