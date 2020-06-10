import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class fightSequence {
	private static QuestInfo questInf;
	private static TokenID player;
	private static Hero hero = new Hero();
	private static Enemy enemy = new Enemy();
	private static ArrayList<String[]> enemyList = new ArrayList<String[]>();
	private static AccessMenu menu = new AccessMenu();
	private CreateBoard board = new CreateBoard();
	private static ArrayList<Integer> deadHeroList = new ArrayList<Integer>();
	
	public fightSequence(TokenID token, QuestInfo data, CreateBoard visual) {
		// TODO Auto-generated constructor stub
		questInf = data;
		player = token;
		enemy = new Enemy(data);
		enemyTeam();
		board = visual;
	}
	
	//Create enemy team of appropirate level
	public void enemyTeam() {
		int numHeroes = player.totalHeroes();
		
		
		//This shows up at the very beginning
		System.out.println("You will be facing");
		//This shows up at the very beginning
		
		
		
		for (int i = 0; i < numHeroes; i++) {
			String[] hero = player.getHero(i);
			int heroLvl = Integer.parseInt(hero[6]);
			String[] enemy1 = enemy.getPerson(heroLvl);
			enemyList.add(enemy1);
		}
	}
	
	//weak enemy for debugging
//	public void enemyTeam() {
//		//Name/level/damage/defense/dodge chance
//		String[] weakEnemy = {"I'm weak", "0", "1", "1", "1", "2"};
//		String[] glassCannon = {"glassCannon", "0", "100", "1", "1", "500"};
//		String[] glassCannon2 = {"glassCannon2", "0", "1000", "1", "1", "500"};
//		enemyList.add(weakEnemy);
//		enemyList.add(glassCannon);
//		enemyList.add(glassCannon2);
//	}
	
	public void printEnemy() {
		System.out.println("\nEnemy # -->_______Name________:| level | damage | defense | dodge chance");
		for (int i = 0; i < enemyList.size(); i++) {
			String[] enemy = enemyList.get(i);
			System.out.printf("Enemy %d --> %s: %s %s %s %s %n", i+1, enemy[0], enemy[1], enemy[2], enemy[3], enemy[4]);
		}
	}
	
	public void printAllies() {
		System.out.println("\n_______________________________________________________________");
		System.out.println("YOUR TEAM!!");
		System.out.println("\nHero # -->_______Name________: | Mana | Strength | Agility | Dexterity | Money | EXP | HP"); 
		System.out.println("_______________________________________________________________");
		player.printHeroList();
	}
	
	private String[] chooseFirstEnemy(int index) {
		while(enemyList != null) {
			if(enemyList.size() < index+1) {
				String[] target = enemyList.get(0);
				return target;
			} else  {
				String[] target = enemyList.get(index);
				return target;
			}
			
		}
		return null;
	}
	
	public void resetEnemyList() {
		enemyList = new ArrayList<String[]>();
	}
	
	private String[] chooseFirstHero(int index) {
		if(player.getHeroList() != null) {
			if(player.totalHeroes() < index+1) {
				for (int i = 0; i < player.totalHeroes(); i++) {
					int hp = Integer.parseInt(player.getHero(i)[7]);
					if(hp > 0) {
						String[] target = player.getHero(i);
						return target;
					}
				}
			} else  {
				String[] target = player.getHero(index);
				return target;
			}
			
		}
		return null;
	}
	
	public int roundFight() {
		int res = 0;
		int numHeroes = player.totalHeroes();
		printAllies();
		for (int i = 0; i < numHeroes; i++) {
			if(hero.isDead(player.getHero(i))) {
				continue;
			}
			System.out.println("\n***");
			System.out.printf("Hero %d will take a turn%n", i+1);
			//in the future this is where to choose target
			String[] hero = player.getHero(i);
			String[] target = chooseFirstEnemy(i);
			if(target == null) {
				System.out.printf("%n%s cannot find a target\n", hero[0]);
				continue;
			}
			System.out.printf("%n%s HP is %s\n", target[0], target[5]);
			res = heroTurn(hero, target, i);
			System.out.printf("%n%s HP is %s\n", target[0], target[5]);
			if(res == -100) {
				System.out.println("Try Again");
				i -= 1;
				continue;
			} else if(res == 3) {
				i -= 1;
				board.printBoard();
				continue;
			} else if(res == 1) {
				//go to inventory without going to end of round
				menu = new AccessMenu(player, questInf, i);
				int goodVisit = 0;
				goodVisit = menu.accessInventory();
				if(goodVisit != 1) {
					System.out.println("Try Again");
					i -= 1;
					continue;
				}
				continue;
			} 
		}
		
		printEnemy();
		for (int j = 0; j < enemyList.size(); j++) {
			if(enemy.isDead(enemyList.get(j))) {
				enemyList.remove(j);
				j -= 1;
				continue;
			}
			System.out.printf("%nEnemy %d will take a turn%n", j+1);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//in the future this is where to choose target
			String[] enemy = enemyList.get(j);
			String[] heroTarget = chooseFirstHero(j);
			if(heroTarget == null) {
				System.out.printf("%n%s cannot find a target\n", enemy[0]);
				continue;
			}
			enemyTurn(enemy, heroTarget, j);
		}
		
		System.out.println("\nEND OF ROUND");
		endRoundRecovery();
		return res;
	}
	

	private void endRoundRecovery() {
		for (int i = 0; i < player.totalHeroes(); i++) {
			String[] recoveryHero = player.getHero(i);
			int heroHP = Integer.parseInt(recoveryHero[7]);
			int heroMana = Integer.parseInt(recoveryHero[1]);
			int manaIncrease = heroMana/5;
			int hpIncrease = heroHP/5;
			recoveryHero = hero.addHP(recoveryHero, hpIncrease);
			recoveryHero = hero.addMana(recoveryHero, manaIncrease);
		}
		
	}
	
	//Revive dead heroes
	public void reviveHero() {
		
		for (int i = 0; i < deadHeroList.size(); i++) {
			int heroIndex = deadHeroList.get(i);
			String[] deadHero = player.getHero(heroIndex);
			System.out.printf("%n%s is being revived%n", deadHero[0]);
			deadHero = hero.reviveHP(deadHero);
			player.getHero(heroIndex)[7] = deadHero[7];
			
		}
	}
	
	public void resetDeadHeroList() {
		deadHeroList = new ArrayList<Integer>();
	}
	//if this return -100, do same turn again
	private int heroTurn(String[] myTurn, String[] target, int heroNumber) {
//		During a round of the fight, when it is the turn of the heroes, the player chooses 
//		for each hero whether they will do a regular attack or 
//		if they will cast a spell or if they will use a potion or if they will change their armor/weapon.
//		At each round a hero can perform only one of the above.
//		At each round the player can display the stats of a hero or a monster
		Scanner scan = new Scanner(System.in);
		
		int itemSelection;
		int userInput; 
		
		System.out.println("What will you do for your action?");
		System.out.println("Attack(0) or use inventory(1)");
		
		try {
			userInput = scan.nextInt();
		} catch (InputMismatchException e) {
			// TODO: handle exception
			return -100;
		}
		
		if(userInput == 0) {
			System.out.println("Would you like to attack with your weapon or spells?");
			System.out.println("Weapon(0) or Spells(1)");
			try {
				itemSelection = scan.nextInt();
			} catch (InputMismatchException e) {
				// TODO: handle exception
				return -100;
			}
			if(itemSelection == 0) {
				String[] weapon = {"0", "0", "0", "0", "0"};
				if(player.getBag(heroNumber).getEquippedWeapon() != null) {
					weapon = player.getBag(heroNumber).getEquippedWeapon();
				} else {
					System.out.println("Hero has no weapon and will therefore use 'unarmed strikes'.");
				}
				hero.setWeapon(weapon);
				int damageFrom = hero.damageDone(myTurn);
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.printf("%n'%s' attacked '%s' dealing %d damage. %n", myTurn[0], target[0], damageFrom);
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				target = enemy.damageTaken(target, damageFrom);
			} else if(itemSelection == 1) {
				if (player.getBag(heroNumber).spellLen() != 0) {
					String type;
					System.out.println("Which spell would you like to use?");
					player.getBag(heroNumber).printSpell();
					try {
						itemSelection = scan.nextInt()-1;
					} catch (InputMismatchException e) {
						// TODO: handle exception
						
						return -100;
					}
					String[] spell = player.getBag(heroNumber).getSpell(itemSelection);
					int currentMana = Integer.parseInt(myTurn[1]);
					int manaCost = Integer.parseInt(spell[4]);
					if (currentMana < manaCost) {
						System.out.println("You do not have enough mana to cast this spell");
						return -100;
					}
					type = spell[5];
					int damageFrom = hero.spellDamage(myTurn, spell);
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.printf("%n%s attacked %s dealing %d damage. %n", myTurn[0], target[0], damageFrom);
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					target = enemy.spellDamageTaken(target, damageFrom, type);
				} else {
					System.out.println("You have no spells");
					return -100;
				}
			} else {
				System.out.println("Not a valid input");
				return -100;
			}
		} else if (userInput == 1) {
			System.out.println("Let's go to inventory");
			return 1;
		} else {
			System.out.println("Not a valid input");
			return -100;
		}
		
		
		return 0;
	}
	
	private int enemyTurn(String[] enemyTurn, String[] target, int enemyIndex) {
		
		System.out.printf("%n%s will attack. %n", enemyTurn[0]);
		
		int damageFrom = enemy.damageDone(enemyTurn);
		if(player.getBag(enemyIndex).getEquippedArmor() != null) {
			String[] armor = player.getBag(enemyIndex).getEquippedArmor();
			hero.setArmor(armor);
		}
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("%n'%s' attacked '%s' dealing %d damage. %n", enemyTurn[0], target[0], damageFrom);
		target = hero.damageTaken(target, damageFrom);
		return 0;
	}
	//checks if all are dead
	public boolean allEnemyDead() {
		boolean res = true;
		for (int i = 0; i < enemyList.size(); i++) {
			if(!enemy.isDead(enemyList.get(i))) {
				res = false;
			}
		}
		return res;
	}
	
	public boolean allAllyDead() {
		boolean res = true;
		for (int i = 0; i < player.totalHeroes(); i++) {
			if(!hero.isDead(player.getHero(i))) {
				res = false;
			} else {
				if(!deadHeroList.contains(i))
				{
					deadHeroList.add(i);
				}
			}
		}
		return res;
	}
	
	public void endOfFightReward() {
		for (int i = 0; i < player.totalHeroes(); i++) {
			int hp = Integer.parseInt(player.getHero(i)[7]);
			String[] survivor = player.getHero(i);
			if (hp > 0) {
				System.out.printf("%n%s survived the encounter %n", survivor[0]);
				hero.addEXP(survivor, 5);
				System.out.printf("%n%s gained %d XP%n", survivor[0], 5);
				hero.addMoney(survivor, 150);
				System.out.printf("%n%s gained %d coins%n", survivor[0], 150);
			}
		}
	}
	
	
}
