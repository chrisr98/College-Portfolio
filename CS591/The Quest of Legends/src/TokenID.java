import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Keeps a list of players 

public class TokenID {

	private static ArrayList<Integer> playerTokens = new ArrayList<Integer>();
	
	public static ArrayList<Inventory> heroBags = new ArrayList<Inventory>();
	
	private static ArrayList<String[]> playerStats = new ArrayList<String[]>();
	
	private static Inventory hero1 = new Inventory();
	private static Inventory hero2 = new Inventory();
	private static Inventory hero3 = new Inventory();
	
	@SuppressWarnings("rawtypes")
	private static List<ArrayList> listOfMixedTypes = new ArrayList<ArrayList>();

	

	
	public TokenID() { 
		// TODO Auto-generated constructor stub
		heroBags.add(hero1);
		heroBags.add(hero2);
		heroBags.add(hero3);
		
	}
	
	// Add number players to the list
	public void addHero(int heroNum, String[] heroInfo) {
		playerStats.add(heroInfo);
		playerTokens.add(heroNum);
	}
	
	// Returns player number/ID
	public String[] getHero(int heroNumber) {
		return playerStats.get(heroNumber);
	}
	
	//Get array list of heroes for boolean checking
	public ArrayList<String[]> getHeroList(){
		return playerStats;
	}
	
	//Get how many heroes player controls
	public int totalHeroes() {
		return playerStats.size();
	}
	
	//set a hero
	public void setHero(int index, String[] hero) {
		playerStats.set(index, hero);
	}
	
	//set a hero
	public void setHeroList(ArrayList<String[]> hero) {
		playerStats = hero;
	}

	// Print list of heroes
	public void printHeroList() {
		for (int i = 0; i < playerStats.size(); i++) {
			System.out.printf("Hero %d --> %s: | %s | %s | %s | %s | %s | %s | %s %n", i+1, playerStats.get(i)[0], playerStats.get(i)[1], playerStats.get(i)[2], playerStats.get(i)[3], playerStats.get(i)[4], playerStats.get(i)[5], playerStats.get(i)[6], playerStats.get(i)[7]);
		}
		
	}
	
	//Get hero inventory
	public Inventory getBag(int heroNum) {
		return heroBags.get(heroNum);		
	}
	
	//Add item to hero bag
	public void addToBag(int heroNum, String[] item, String itemType) {
		if(itemType.equals("armor")) {
			getBag(heroNum).addArmor(item);
		} else if(itemType.equals("potion")) {
			getBag(heroNum).addPotion(item);
		} else if(itemType.equals("spell")) {
			getBag(heroNum).addSpell(item);
		} else if(itemType.equals("weapon")) {
			getBag(heroNum).addWeapon(item);
		} else {
			System.out.printf("Unidentified items cannot be added");
		}
		
	}
	//Remove item to hero bag
	public void remToBag(int heroNum, int item, String itemType) {
		if(itemType.equals("armor") && getBag(heroNum).armorLen() > item) {
			getBag(heroNum).remArmor(item);
		} else if(itemType.equals("potion") && getBag(heroNum).potionLen() > item) {
			getBag(heroNum).remPotion(item);
		} else if(itemType.equals("spell") && getBag(heroNum).spellLen() > item) {
			getBag(heroNum).remSpell(item);
		} else if(itemType.equals("weapon") && getBag(heroNum).weaponLen() > item) {
			getBag(heroNum).remWeapon(item);
		} else {
			System.out.printf("There is no such item remove");
		}
		
	}
	
}
