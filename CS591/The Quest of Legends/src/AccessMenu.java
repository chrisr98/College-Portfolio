import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

//A way to access inventory from any other class.
public class AccessMenu {
	
	private static QuestInfo questInf;
	private static TokenID player;
	private static Hero hero;
	private static int inFight = -100;

	public AccessMenu() {
		// TODO Auto-generated constructor stub
	}
	
	public AccessMenu(TokenID playerInfo, QuestInfo data) {
		// TODO Auto-generated constructor stub
		this.player = playerInfo;
		this.questInf = data;
	}
	
	//If accessing from fight window always use this constructor
	public AccessMenu(TokenID playerInfo, QuestInfo data, int fighting) {
		// TODO Auto-generated constructor stub
		this.player = playerInfo;
		this.questInf = data;
		inFight = fighting;
	}
	
	public int accessInventory() {
		infoMenu();
		
		if(inFight != -100) {
			return fightInventory();
		}
		Scanner scan = new Scanner(System.in);
		
		int itemSelection;
		
		// Ask which hero to access
		System.out.println("Which hero would you like to access?");
		System.out.println("*Or enter 0 to exit*");
		player.printHeroList();
		
		int heroIndex = 0;
		try {
			heroIndex = scan.nextInt() - 1;
		} catch (InputMismatchException e) {
			// TODO: handle exception
			System.out.println("Wrong input");
			return 0;
		}
		if (heroIndex == -1) {
			return 0;
		}
		
		System.out.println("Which item type would you like to access?");
		
		String[] heroInventory = player.getHero(heroIndex);
		
		System.out.printf("This is what they are carrying:%n");

		Inventory bag = player.getBag(heroIndex);

		bag.printBagItems();

		// Name/mana/strength/agility/dexterity/starting money/starting experience
		int heroInventoryMana = Integer.parseInt(heroInventory[1]);
		int heroInventorySTR = Integer.parseInt(heroInventory[2]);
		int heroInventoryAGL = Integer.parseInt(heroInventory[3]);
		int heroInventoryDEX = Integer.parseInt(heroInventory[4]);
		int heroInventoryIncome = Integer.parseInt(heroInventory[5]);
		int heroInventoryEXP = Integer.parseInt(heroInventory[8]);
		int heroInventoryHP = Integer.parseInt(heroInventory[7]);
		
		//Select item from to sell from inventory through prompts
		System.out.println("Potions(p/P), Armor(a/A), Spells(s/S), Weapons(w/W)");
		System.out.println("Enter (e/E) to exit");
		System.out.println("To view Map press m/M.");

		scan.hasNext();
		String userInput;
		String type;
		try {
			userInput = scan.next();
		} catch (InputMismatchException e) {
			// TODO: handle exception
			System.out.println("Wrong input");
			return 0;
		}

		if (userInput.equals("e") || userInput.equals("E")) {
			System.out.println("OK!");
			return 0;
		} else if (userInput.equals("m") || userInput.equals("M")) {
			return 3;
		}

		System.out.println("Select a number");
		scan.hasNext();

		String[] item;

		try {
			itemSelection = scan.nextInt() - 1;
		} catch (InputMismatchException e) {
			// TODO: handle exception
			System.out.println("Wrong input");
			return 0;
		}

		// checks if hero has item to sell and gets the item to remove
		if (userInput.equals("p") || userInput.equals("P")) {
			type = "potion";
			System.out.println("OK!");
			if (player.getBag(heroIndex).potionLen() == 0 || player.getBag(heroIndex).potionLen() <= itemSelection) {
				System.out.println("Hero does not own that item.");
				return 0;
			}
			item = player.getBag(heroIndex).getPotion(itemSelection);
			// Increase stat accordingly
			if (item[0].equals("Healing_Potion")) {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventoryHP;
				heroInventoryHP += (potion);
				System.out.printf("HP increased from %d to %d", temp, heroInventoryHP);
			} else if (item[0].equals("Strength_Potion")) {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventorySTR;
				heroInventorySTR += (potion);
				System.out.printf("Strength increased from %d to %d", temp, heroInventorySTR);
			} else if (item[0].equals("Magic_Potion")) {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventoryMana;
				heroInventoryMana += (potion);
				System.out.printf("Mana increased from %d to %d", temp, heroInventoryMana);
			} else if (item[0].equals("Luck_Elixir")) {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventoryAGL;
				heroInventoryAGL += (potion);
				System.out.printf("Agility increased from %d to %d", temp, heroInventoryAGL);
			} else if (item[0].equals("Mermaid_Tears")) {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventoryDEX;
				heroInventoryDEX += (potion);
				System.out.printf("Dextirity increased from %d to %d", temp, heroInventoryDEX);
			} else {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventoryEXP;
				hero.addEXP(heroInventory, potion);
				hero.levelUp(heroInventory);
				System.out.printf("Experience increased from %d to %d", temp, heroInventoryEXP);
			}
			player.remToBag(heroIndex, itemSelection, type);
		} else if (userInput.equals("a") || userInput.equals("A")) {
			type = "armor";
			System.out.println("OK!");
			if (player.getBag(heroIndex).armorLen() == 0 || player.getBag(heroIndex).armorLen() <= itemSelection) {
				System.out.println("Hero does not own that item.");
				return 0;
			}
			item = player.getBag(heroIndex).getArmor(itemSelection);
			player.getBag(heroIndex).equipArmor(itemSelection);
			System.out.printf("You have equipped %s.", Arrays.toString(item));
		} else if (userInput.equals("s") || userInput.equals("S")) {
			type = "spell";
			System.out.println("OK!");
			if (player.getBag(heroIndex).spellLen() == 0 || player.getBag(heroIndex).spellLen() <= itemSelection) {
				System.out.println("Hero does not own that item.");
				return 0;
			}
			item = player.getBag(heroIndex).getSpell(itemSelection);
			System.out.println("Can only be used as an attack not consumable");
			return 0;
		} else if (userInput.equals("w") || userInput.equals("W")) {
			type = "weapon";
			System.out.println("OK!");
			if (player.getBag(heroIndex).weaponLen() == 0 || player.getBag(heroIndex).weaponLen() <= itemSelection) {
				System.out.println("Hero does not own that item.");
				return 0;
			}
			item = player.getBag(heroIndex).getWeapon(itemSelection);
			System.out.printf("You have equipped %s.", Arrays.toString(item));
			player.getBag(heroIndex).equipWeapon(itemSelection);
		} else {
			System.out.println("Hero does not own that item.");
			return 0;
		}

		// Set new value
		String newMana = "" + heroInventoryMana;
		heroInventory[1] = newMana;

		String newSTR = "" + heroInventorySTR;
		heroInventory[2] = newSTR;

		String newAGL = "" + heroInventoryAGL;
		heroInventory[3] = newAGL;

		String newDEX = "" + heroInventoryDEX;
		heroInventory[4] = newDEX;

		String newIncome = "" + heroInventoryIncome;
		heroInventory[5] = newIncome;

		String newEXP = "" + heroInventoryEXP;
		heroInventory[6] = newEXP;

		String newHP = "" + heroInventoryHP;
		heroInventory[7] = newHP;

		return 1;
	}
	
	private int fightInventory() {
		Scanner scan = new Scanner(System.in);
		
		int itemSelection;
		int heroIndex = inFight;
		
		System.out.println("\n");
		System.out.println("\nShowing info for Hero " + heroIndex+1);
		System.out.println("Which item type would you like to access?");
		
		String[] heroInventory = player.getHero(heroIndex);
		
		System.out.printf("This is what they are carrying:%n");

		Inventory bag = player.getBag(heroIndex);

		bag.printBagItems();

		// Name/mana/strength/agility/dexterity/starting money/starting experience
		int heroInventoryMana = Integer.parseInt(heroInventory[1]);
		int heroInventorySTR = Integer.parseInt(heroInventory[2]);
		int heroInventoryAGL = Integer.parseInt(heroInventory[3]);
		int heroInventoryDEX = Integer.parseInt(heroInventory[4]);
		int heroInventoryIncome = Integer.parseInt(heroInventory[5]);
		int heroInventoryEXP = Integer.parseInt(heroInventory[8]);
		int heroInventoryHP = Integer.parseInt(heroInventory[7]);
		
		//Select item from to sell from inventory through prompts
		System.out.println("Potions(p/P), Armor(a/A), Spells(s/S), Weapons(w/W)");
		System.out.println("Enter (e/E) to exit");
		System.out.println("To view Map press m/M.");

		scan.hasNext();
		String userInput;
		String type;
		try {
			userInput = scan.next();
		} catch (InputMismatchException e) {
			// TODO: handle exception
			System.out.println("Wrong input");
			return 0;
		}

		if (userInput.equals("e") || userInput.equals("E")) {
			System.out.println("OK!");
			return 0;
		} else if (userInput.equals("m") || userInput.equals("M")) {
			return 3;
		}

		System.out.println("Select a number");
		scan.hasNext();

		String[] item;

		try {
			itemSelection = scan.nextInt() - 1;
		} catch (InputMismatchException e) {
			// TODO: handle exception
			System.out.println("Wrong input");
			return 0;
		}

		// checks if hero has item to sell and gets the item to remove
		if (userInput.equals("p") || userInput.equals("P")) {
			type = "potion";
			System.out.println("OK!");
			if (player.getBag(heroIndex).potionLen() == 0 || player.getBag(heroIndex).potionLen() <= itemSelection) {
				System.out.println("Hero does not own that item.");
				return 0;
			}
			item = player.getBag(heroIndex).getPotion(itemSelection);
			// Increase stat accordingly
			if (item[0].equals("Healing_Potion")) {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventoryHP;
				heroInventoryHP += (potion);
				System.out.printf("HP increased from %d to %d", temp, heroInventoryHP);
			} else if (item[0].equals("Strength_Potion")) {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventorySTR;
				heroInventorySTR += (potion);
				System.out.printf("Strength increased from %d to %d", temp, heroInventorySTR);
			} else if (item[0].equals("Magic_Potion")) {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventoryMana;
				heroInventoryMana += (potion);
				System.out.printf("Mana increased from %d to %d", temp, heroInventoryMana);
			} else if (item[0].equals("Luck_Elixir")) {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventoryAGL;
				heroInventoryAGL += (potion);
				System.out.printf("Agility increased from %d to %d", temp, heroInventoryAGL);
			} else if (item[0].equals("Mermaid_Tears")) {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventoryDEX;
				heroInventoryDEX += (potion);
				System.out.printf("Dextirity increased from %d to %d", temp, heroInventoryDEX);
			} else {
				int potion = Integer.parseInt(item[3]);
				int temp = heroInventoryEXP;
				hero.addEXP(heroInventory, potion);
				hero.levelUp(heroInventory);
				System.out.printf("Experience increased from %d to %d", temp, heroInventoryEXP);
			}
			player.remToBag(heroIndex, itemSelection, type);
		} else if (userInput.equals("a") || userInput.equals("A")) {
			type = "armor";
			System.out.println("OK!");
			if (player.getBag(heroIndex).armorLen() == 0 || player.getBag(heroIndex).armorLen() <= itemSelection) {
				System.out.println("Hero does not own that item.");
				return 0;
			}
			item = player.getBag(heroIndex).getArmor(itemSelection);
			player.getBag(heroIndex).equipArmor(itemSelection);
			System.out.printf("You have equipped %s.", Arrays.toString(item));
		} else if (userInput.equals("s") || userInput.equals("S")) {
			type = "spell";
			System.out.println("OK!");
			if (player.getBag(heroIndex).spellLen() == 0 || player.getBag(heroIndex).spellLen() <= itemSelection) {
				System.out.println("Hero does not own that item.");
				return 0;
			}
			item = player.getBag(heroIndex).getSpell(itemSelection);
			System.out.println("Can only be used as an attack not consumable");
			return 0;
		} else if (userInput.equals("w") || userInput.equals("W")) {
			type = "weapon";
			System.out.println("OK!");
			if (player.getBag(heroIndex).weaponLen() == 0 || player.getBag(heroIndex).weaponLen() <= itemSelection) {
				System.out.println("Hero does not own that item.");
				return 0;
			}
			item = player.getBag(heroIndex).getWeapon(itemSelection);
			System.out.printf("You have equipped %s.", Arrays.toString(item));
			player.getBag(heroIndex).equipWeapon(itemSelection);
		} else {
			System.out.println("Hero does not own that item.");
			return 0;
		}

		// Set new value
		String newMana = "" + heroInventoryMana;
		heroInventory[1] = newMana;

		String newSTR = "" + heroInventorySTR;
		heroInventory[2] = newSTR;

		String newAGL = "" + heroInventoryAGL;
		heroInventory[3] = newAGL;

		String newDEX = "" + heroInventoryDEX;
		heroInventory[4] = newDEX;

		String newIncome = "" + heroInventoryIncome;
		heroInventory[5] = newIncome;

		String newEXP = "" + heroInventoryEXP;
		heroInventory[6] = newEXP;

		String newHP = "" + heroInventoryHP;
		heroInventory[7] = newHP;

		
		return 1;
	}

	public int infoMenu() {

		System.out.println("\nINFORMATION MENU");
		System.out.println("\nHero # -->_______Name________: | Mana | Strength | Agility | Dexterity | Money | LV | HP");
		System.out.println("_______________________________________________________________");
		player.printHeroList();
		System.out.println("_______________________________________________________________");

		System.out.println("THEIR INVENTORY");

		for (int i = 0; i < player.totalHeroes(); i++) {
			String[] hero = player.getHero(i);
			System.out.printf("Hero %d --> %s: | %s | %s | %s | %s | %s | %s | %s %n", i+1, hero[0], hero[1], hero[2], hero[3], hero[4], hero[5], hero[6], hero[7]);

			System.out.printf("This is what they are carrying:%n");

			Inventory bag = player.getBag(i);

			bag.printBagItems();

			System.out.printf("Equipped armor: %s %n", Arrays.toString(bag.getEquippedArmor()));
			System.out.printf("Equipped weapon: %s %n", Arrays.toString(bag.getEquippedWeapon()));
		}

		return 1;
	}

}
