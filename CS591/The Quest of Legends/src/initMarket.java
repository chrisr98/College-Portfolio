import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class initMarket {
	private static QuestInfo questInf;
	private static TokenID playerInMarket;

	public initMarket() {
		// TODO Auto-generated constructor stub
	}
	
	public int runMarket(QuestInfo data, TokenID player) {
		//initiate market and prompt player
		System.out.println("\n__________________________________________________________________");
		System.out.println("Khajiit has wares if you have coins!!");
		System.out.println("Are we buying(b/B) or selling(s/S) today?");
		System.out.println("e/E to exit the market");
		System.out.println("To view Info press i/I.");
		System.out.println("To view Map press m/M.");
		System.out.println("__________________________________________________________________");
		Scanner scan = new Scanner(System.in);
		int validBuy = 0;
		String userInput;
		try {
			userInput = scan.next();
		} catch (InputMismatchException e) {
			// TODO: handle exception
			System.out.println("Enter the designated letters");
			return 0;
		}
						
		questInf = data;
		playerInMarket = player;
		//Checks user input for valid response
		if(userInput.equals("b") || userInput.equals("B")) {
			System.out.println("OK!");
			validBuy = buyItem();
		} else if(userInput.equals("s") || userInput.equals("S")) {
			System.out.println("OK!");
			validBuy = sellItem();
		} else if(userInput.equals("e") || userInput.equals("E")) {
			System.out.println("OK!");
			return 1;
		} else if(userInput.equals("i") || userInput.equals("I")) {
			System.out.println("OK!");
			return 2;
		} else if(userInput.equals("m") || userInput.equals("M")) {
			return 3;
		} else {
			System.out.println("Enter the designated letters");
			return 0;
		}

		/*
		System.out.println("\n________________");
		System.out.println("Armor List");
		System.out.println("________________");
		questInf.printArmory();
		
		System.out.println("\n________________");
		System.out.println("Spell List");
		System.out.println("________________");
		questInf.printFireSpells();
		questInf.printIceSpells();
		questInf.printLightningSpells();
		
		System.out.println("\n________________");
		System.out.println("Potion List");
		System.out.println("________________");
		questInf.printPotions();
		
		System.out.println("\n________________");
		System.out.println("Weapon List");
		System.out.println("________________");
		questInf.printWeaponry();
		
		System.out.println("\n________________");
		System.out.println("Hero List");
		System.out.println("________________");
		questInf.printWarriors();
		questInf.printPaladins();
		questInf.printSorcerers();
		*/
		
		return validBuy;
		
		
	}
	
	private int sellItem() {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		int itemSelection;
		
		// Ask which hero will make the sale
		System.out.println("Which hero will make the sale?");
		System.out.println("*Or enter 0 to exit market*");
		playerInMarket.printHeroList();
		int heroIndex = scan.nextInt() - 1;

		if (heroIndex == -1) {
			return 0;
		}

		//Get hero's inventory and display it
		String[] seller = playerInMarket.getHero(heroIndex);
		System.out.printf("%n%s is selling.%n", Arrays.toString(seller));

		System.out.printf("This is what they are carrying:%n");

		Inventory bag = playerInMarket.getBag(heroIndex);

		bag.printBagItems();

		// Name/mana/strength/agility/dexterity/starting money/starting experience
		int sellerIncome = Integer.parseInt(seller[5]);
		
		//Select item from to sell from inventory through prompts
		System.out.println("Which item type would you like to sell?");
		System.out.println("Potions(p/P), Armor(a/A), Spells(s/S), Weapons(w/W)");
		System.out.println("Enter (e/E) to exit");
		System.out.println("To view Info press i/I.");
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
		
		if(userInput.equals("e") || userInput.equals("E")) {
			System.out.println("OK!");
			return 0;
		} else if(userInput.equals("i") || userInput.equals("I")) {
			System.out.println("OK!");
			return 2;
		} else if(userInput.equals("m") || userInput.equals("M")) {
			return 3;
		}
		
		System.out.println("Select a number");
		scan.hasNext();
		
		String[] item;
		
		try {
			itemSelection = scan.nextInt()-1;
		} catch (InputMismatchException e) {
			// TODO: handle exception
			System.out.println("Wrong input");
			return 0;
		}
		
		//checks if hero has item to sell and gets the item to remove
		if(userInput.equals("p") || userInput.equals("P")) {
			type = "potion";
			System.out.println("OK!");
			if(playerInMarket.getBag(heroIndex).potionLen() == 0 || playerInMarket.getBag(heroIndex).potionLen() <= itemSelection) {
				System.out.println("Hero does not own that item.");
				return 0;
			}
			item = playerInMarket.getBag(heroIndex).getPotion(itemSelection);
			playerInMarket.remToBag(heroIndex, itemSelection, type);
		} else if(userInput.equals("a") || userInput.equals("A")) {
			type = "armor";
			System.out.println("OK!");
			if (playerInMarket.getBag(heroIndex).armorLen() == 0 || playerInMarket.getBag(heroIndex).armorLen() <= itemSelection || playerInMarket.getBag(heroIndex).isArmorEquipped(itemSelection) == false) {
				System.out.println("Hero does not own that item/cannot sell equipped item.");
				return 0;
			}
			item = playerInMarket.getBag(heroIndex).getArmor(itemSelection);
			playerInMarket.remToBag(heroIndex, itemSelection, type);
		} else if(userInput.equals("s") || userInput.equals("S")) {
			type = "spell";
			System.out.println("OK!");
			if (playerInMarket.getBag(heroIndex).spellLen() == 0 || playerInMarket.getBag(heroIndex).spellLen() <= itemSelection) {
				System.out.println("Hero does not own that item.");
				return 0;
			}
			item = playerInMarket.getBag(heroIndex).getSpell(itemSelection);
			playerInMarket.remToBag(heroIndex, itemSelection, type);
		} else if(userInput.equals("w") || userInput.equals("W")) {
			type = "weapon";
			System.out.println("OK!");
			if (playerInMarket.getBag(heroIndex).weaponLen() == 0 || playerInMarket.getBag(heroIndex).weaponLen() <= itemSelection || playerInMarket.getBag(heroIndex).isWeaponEquipped(itemSelection) == false) {
				System.out.println("Hero does not own that item/cannot sell equipped item.");
				return 0;
			}
			item = playerInMarket.getBag(heroIndex).getWeapon(itemSelection);
			playerInMarket.remToBag(heroIndex, itemSelection, type);
		} else {
			System.out.println("Hero does not own that item.");
			return 0;
		}
		
		int price = Integer.parseInt(item[1]);
		sellerIncome += (price/2);
		
		//Set new income value
		String newIncome = "" + sellerIncome;
		seller[5] = newIncome;
		
		return 1;
	}

	
	private int buyItem() {
		
		Scanner scan = new Scanner(System.in);
		
		Random rand = new Random();

		int itemSelection = 0;
		
		int validPurchase = 0;
		//Ask which hero will make the purchase
				System.out.println("Which hero will make the purchase?");
				System.out.println("*Or enter 0 to exit market*");
				System.out.println("To view Info press i/I.");
				System.out.println("To view Map press m/M.");
				playerInMarket.printHeroList();
				String userInput;
				int heroIndex;
				try {
					userInput = scan.next();
					if(userInput.equals("i") || userInput.equals("I")) {
						return 2;
					} else if(userInput.equals("m") || userInput.equals("M")) {
						return 3;
					}else {
						heroIndex = Integer.parseInt(userInput)-1;
					}
					
				} catch (InputMismatchException e) {
					// TODO: handle exception
					System.out.println("Wrong input");
					return 0;
				}

				if(heroIndex == -1) {
					return 1;
				}
				
				String[] buyer = playerInMarket.getHero(heroIndex);
				System.out.printf("%n %s is buying.", Arrays.toString(buyer));
				
				//Name/mana/strength/agility/dexterity/starting money/starting experience
				int buyerLevel = Integer.parseInt(buyer[6]);
				int buyerIncome = Integer.parseInt(buyer[5]);
				
				System.out.println("\n__________________________________________________________________");
				System.out.println("Choose from this list with the coresponding number");
				System.out.println("*Or enter 0 to exit market*");
				System.out.println("__________________________________________________________________");
				
				String[] healingPotion;
				String[] potion2;
				String[] armor;
				String[] fireSpell;
				String[] iceSpell;
				String[] lightningSpell;
				String[] weapon;
//				String[] hero;
				
				//Always show 1 healing poition 
				System.out.println("---Potions---");
				System.out.print(1 + " --> ");
				healingPotion = questInf.getPotion(0);
				//Show a random potion, armor, fire/ice/lightning spell, and weapon 
				int randPotion = rand.nextInt((questInf.getPotionsSize()-1) - 1 + 1) + 1;
				System.out.print(2 + " --> ");
				potion2 = questInf.getPotion(randPotion);
				
				System.out.println("---Armor---");
				int randArmor= rand.nextInt((questInf.getArmorySize()-1) - 0 + 1) + 0;
				System.out.print(3 + " --> ");
				armor = questInf.getArmor(randArmor);
				
				System.out.println("---Spells---");
				int randFireSpell = rand.nextInt((questInf.getFireSpellsSize()-1) - 0 + 1) + 0;
				System.out.print(4 + " --> ");
				fireSpell = questInf.getFireSpell(randFireSpell);
				int randIceSpell = rand.nextInt((questInf.getIceSpellsSize()-1) - 0 + 1) + 0;
				System.out.print(5 + " --> ");
				iceSpell = questInf.getIceSpell(randIceSpell);
				int randLightningSpell = rand.nextInt((questInf.getLightningSpellsSize()-1) - 0 + 1) + 0;
				System.out.print(6 + " --> ");
				lightningSpell = questInf.getLightningSpell(randLightningSpell);
				
				System.out.println("---Weapon---");
				int randWeapon = rand.nextInt((questInf.getWeaponrySize()-1) - 0 + 1) + 0;
				System.out.print(7 + " --> ");
				weapon = questInf.getWeapon(randWeapon);
				
				/*
				//Only show 1 of the 3 hero classes at random.
				int whichHero = rand.nextInt(3 - 1 + 1) + 1;
				
				if (whichHero == 1) {
					System.out.println("---Paladin---");
					int randHero= rand.nextInt((questInf.getPaladinsSize()-1) - 0 + 1) + 0;
					System.out.print(8 + " --> ");
					hero = questInf.getPaladins(randHero);
				} else if (whichHero == 2) {
					System.out.println("---Sorcerer---");
					int randHero= rand.nextInt((questInf.getSorcerersSize()-1) - 0 + 1) + 0;
					System.out.print(8 + " --> ");
					hero = questInf.getSorcerer(randHero);
				} else {
					System.out.println("---Warrior---");
					int randHero= rand.nextInt((questInf.getWarriorsSize()-1) - 0 + 1) + 0;
					System.out.print(8 + " --> ");
					hero = questInf.getWarrior(randHero);
				}
				*/
				scan.hasNext();
				
				try {
					itemSelection = scan.nextInt();
				} catch (InputMismatchException e) {
					// TODO: handle exception
					System.out.println("\nWrong input.");
					return 0;
				}
				
				//Select item from market menu and makes sure hero meets requirement to buy said item
				if(itemSelection == 0) {
					return 1;
				} else if(itemSelection == 1) {
					System.out.print("You chose --> ");
					System.out.print(Arrays.toString(healingPotion));
					if ((buyerLevel < Integer.parseInt(healingPotion[2])) || (buyerIncome < Integer.parseInt(healingPotion[1]))) {
						System.out.print("\nYou do no meet requirement to buy this item.");
						validPurchase = 0;
					} else {
						playerInMarket.addToBag(heroIndex, healingPotion, "potion");
						buyerIncome -= Integer.parseInt(healingPotion[1]);
						validPurchase = 1;
						System.out.println("\nItem bought!");
					}
					
				} else if(itemSelection == 2) {
					System.out.print("You chose --> ");
					System.out.print(Arrays.toString(potion2));
					if ((buyerLevel < Integer.parseInt(potion2[2])) || (buyerIncome < Integer.parseInt(potion2[1]))) {
						System.out.print("\nYou do no meet requirement to buy this item.");
						validPurchase = 0;
					} else {
						playerInMarket.addToBag(heroIndex, potion2, "potion");
						buyerIncome -= Integer.parseInt(potion2[1]);
						validPurchase = 1;
						System.out.println("\nItem bought!");
					}
					
				} else if(itemSelection == 3) {
					System.out.print("You chose --> ");
					System.out.print(Arrays.toString(armor));
					if ((buyerLevel < Integer.parseInt(armor[2])) || (buyerIncome < Integer.parseInt(armor[1]))) {
						System.out.print("\nYou do no meet requirement to buy this item.");
						validPurchase = 0;
					} else {
						playerInMarket.addToBag(heroIndex, armor, "armor");
						buyerIncome -= Integer.parseInt(armor[1]);
						validPurchase = 1;
						System.out.println("\nItem bought!");
					}
					
				} else if(itemSelection == 4) {
					System.out.print("You chose --> ");
					System.out.print(Arrays.toString(fireSpell));
					if ((buyerLevel < Integer.parseInt(fireSpell[2])) || (buyerIncome < Integer.parseInt(fireSpell[1]))) {
						System.out.print("\nYou do no meet requirement to buy this item.");
						validPurchase = 0;
					} else {
						playerInMarket.addToBag(heroIndex, fireSpell, "spell");
						buyerIncome -= Integer.parseInt(fireSpell[1]);
						validPurchase = 1;
						System.out.println("\nItem bought!");
					}
					
				} else if(itemSelection == 5) {
					System.out.print("You chose --> ");
					System.out.print(Arrays.toString(iceSpell));
					if ((buyerLevel < Integer.parseInt(iceSpell[2])) || (buyerIncome < Integer.parseInt(iceSpell[1]))) {
						System.out.print("\nYou do no meet requirement to buy this item.");
						validPurchase = 0;
					} else {
						playerInMarket.addToBag(heroIndex, iceSpell, "spell");
						buyerIncome -= Integer.parseInt(iceSpell[1]);
						validPurchase = 1;
						System.out.println("\nItem bought!");
					}
					
				} else if(itemSelection == 6) {
					System.out.print("You chose --> ");
					System.out.print(Arrays.toString(lightningSpell));
					if ((buyerLevel < Integer.parseInt(lightningSpell[2])) || (buyerIncome < Integer.parseInt(lightningSpell[1]))) {
						System.out.print("\nYou do no meet requirement to buy this item.");
						validPurchase = 0;
					} else {
						playerInMarket.addToBag(heroIndex, lightningSpell, "spell");
						buyerIncome -= Integer.parseInt(lightningSpell[1]);
						validPurchase = 1;
						System.out.println("\nItem bought!");
					}
					
				} else if(itemSelection == 7) {
					System.out.print("You chose --> ");
					System.out.print(Arrays.toString(weapon));
					if ((buyerLevel < Integer.parseInt(weapon[2])) || (buyerIncome < Integer.parseInt(weapon[1]))) {
						System.out.print("\nYou do no meet requirement to buy this item.");
						validPurchase = 0;
					} else {
						playerInMarket.addToBag(heroIndex, weapon, "weapon");
						buyerIncome -= Integer.parseInt(weapon[1]);
						validPurchase = 1;
						System.out.println("\nItem bought!");
					}
					
				} else {
					System.out.print("Invalid Selection!");
					return validPurchase;
				}
				
				/*
				 * If heroes can be purchased this is the code to use
				else if(itemSelection == 8) {
					System.out.print("You chose --> ");
					System.out.print(Arrays.toString(hero));
					if (buyerLevel < 0 && buyerIncome < 0) {
						validPurchase = false;
					} else {
						validPurchase = true;
					}
					
				} 
				*/
				

				// Set new income value
				String newIncome = ""+buyerIncome;
				buyer[5] = newIncome;
				
				return validPurchase;
	}

}
