import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Each hero will have an inventory for the items
public class Inventory {
	
	private List<ArrayList<String[]>> heroBag = new ArrayList<ArrayList<String[]>>();
	
	private ArrayList<String[]> spellList = new ArrayList<String[]>();
	
	private ArrayList<String[]> armorList = new ArrayList<String[]>();
	
	private ArrayList<String[]> weaponList = new ArrayList<String[]>();
	
	private ArrayList<String[]> potionList = new ArrayList<String[]>();
	
	private String[] equippedArmor;
	private String[] equippedWeapon;
	
	public Inventory() {
		// TODO Auto-generated constructor stub
		updateInventory();
	}
	
	// Add items to bag
	public void addSpell(String[] spellInfo) {
		spellList.add(spellInfo);
		updateInventory();
	}
	
	public void addArmor(String[] armorInfo) {
		armorList.add(armorInfo);
		updateInventory();
	}
	
	public void addWeapon(String[] weaponInfo) {
		weaponList.add(weaponInfo);
		updateInventory();
	}
	
	public void addPotion(String[] potionInfo) {
		potionList.add(potionInfo);
		updateInventory();
	}
	
	//Remove items from bag
	public void remSpell(int spellIndex) {
		spellList.remove(spellIndex);
		updateInventory();
	}
	
	public void remArmor(int armorIndex) {
		armorList.remove(armorIndex);
		updateInventory();
	}
	
	public void remWeapon(int weaponIndex) {
		weaponList.remove(weaponIndex);
		updateInventory();
	}
	
	public void remPotion(int potionIndex) {
		potionList.remove(potionIndex);
		updateInventory();
	}
	
	//Get item from bag
	public String[] getSpell(int spellIndex) {
		return spellList.get(spellIndex);
	}
	
	public String[] getArmor(int armorIndex) {
		return armorList.get(armorIndex);
	}
	
	public String[] getWeapon(int weaponIndex) {
		return weaponList.get(weaponIndex);
	}
	
	public String[] getPotion(int potionIndex) {
		return potionList.get(potionIndex);
	}
	
	//Equip armor or weapon
	public void equipArmor(int index) {
		equippedArmor = getArmor(index);
	}
	
	public void equipWeapon(int index) {
		equippedWeapon = getWeapon(index);
	}

	//Unequip armor or weapon
	public void remEquipArmor() {
		String[] temp = {"0", "0", "0", "0"};
		equippedArmor = temp;
	}
	
	public void remEquipWeapon() {
		String[] temp = {"0", "0", "0", "0", "0"};
		equippedWeapon = temp;
	}
	
	//Get item that is equipped
	public String[] getEquippedWeapon() {
		return equippedWeapon;
	}
	
	public String[] getEquippedArmor() {
		return equippedArmor;
	}
	
	
	//Check if item is equipped
	public boolean isArmorEquipped(int index) {
		String armor1 = Arrays.toString(equippedArmor);
		String armor2 = Arrays.toString(getArmor(index));
		return armor1 == armor2;
	}
	
	public boolean isWeaponEquipped(int index) {
		String weapon1 = Arrays.toString(equippedWeapon);
		String weapon2 = Arrays.toString(getWeapon(index));
		return weapon1 == weapon2;
	}
	
	
	
	//Get how many items in bag
	public int potionLen() {
		return potionList.size();
	}
	
	public int weaponLen() {
		return weaponList.size();
	}
	
	public int spellLen() {
		return spellList.size();
	}
	
	public int armorLen() {
		return armorList.size();
	}
	
	public void printSpell() {
		for (int i = 0; i < spellLen(); i++) {
			String[] m = spellList.get(i);
			System.out.printf("%d --> %s%n", i+1, Arrays.toString(m));
		}
	}
	//Update bag after every change
	private void updateInventory() {
		List<ArrayList<String[]>> tempList = new ArrayList<ArrayList<String[]>>();
		tempList.add(potionList);
		tempList.add(armorList);
		tempList.add(spellList);
		tempList.add(weaponList);
		
		heroBag = tempList;
		
	}
	
	//Display items in bag
	public void printBagItems() {
		for (int i = 0; i < heroBag.size(); i++) {
			if(i == 0) {
				System.out.println("---Potions---");
			} else if(i == 1) {
				System.out.println("---Armor---");
			} else if(i == 2) {
				System.out.println("---Spells---");
			} else {
				System.out.println("---Weapons---");
			}
			for (int j = 0; j < heroBag.get(i).size(); j++) {
				String[] m = heroBag.get(i).get(j);
				System.out.printf("%d --> %s%n", j+1, Arrays.toString(m));
			}
		}
	}
	

}
