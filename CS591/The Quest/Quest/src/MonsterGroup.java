import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class MonsterGroup { 
	private String[] monsterFiles = {"Exoskeletons.txt", "Dragons.txt", "Spirits.txt"}; //make this an input in constructor or sth
	int maxMonsters;
	int monsterCounter = 0;
	private ArrayList<Monster> monstersInGroup = new ArrayList<Monster>(); 
	
	public static Monster deepCopy(Monster m) {
		Monster ret = new Monster(m.getHP(), m.getName(), m.getLevel(), m.getAttackDamage(), m.getDef(), m.getDodge());
		return ret;
	}
	
	MonsterGroup(ArrayList<Monster> monsters) {
		monstersInGroup = monsters;
	}
	
	MonsterGroup(int level, int number) { //initializes specified amount of monsters, for QoL
		ArrayList<Monster> allMonsters = getMonsters(level);
		for (int i = 0; i < number; i++) {
			Monster toAdd = deepCopy(allMonsters.get(i));
			monstersInGroup.add(toAdd);
		}
	}
	
	MonsterGroup(int level) { //initializes random amount of monsters, with max value as maxMonsters
		ArrayList<Monster> allMonsters = getMonsters(level);
		Random r = new Random();
		int monsterNum = r.nextInt((3 - 1) + 1) + 1;
		maxMonsters = monsterNum;
		
		for (int i = 0; i < monsterNum; i++) {
			int randIndex = r.nextInt((allMonsters.size()));
			Monster toAdd = deepCopy(allMonsters.get(randIndex));
			monstersInGroup.add(toAdd);
		}
	}
	
	public ArrayList<Monster> getMonsters(int level) {
		Entity[] entityList;
		Monster[] monsterList;
		ArrayList<Monster> allMonsters = new ArrayList<Monster>();
		for (int i = 0; i < monsterFiles.length; i++) {
			entityList = Gamelogic.getData(monsterFiles[i], 4, 0);
			if (entityList instanceof Monster[]) {
				monsterList = (Monster[]) Gamelogic.getData(monsterFiles[i], 4, 0);
				for (Monster m : monsterList) {
					//int tempMonsterLevel = m.getLevel();
					if (m.getLevel() == level) {
						allMonsters.add(m);
					}
				}
			} else {
				System.out.println("Something is wrong with parsing monsters");
			}
		}
		return allMonsters;
	}
	
	public boolean removeMonster(Monster m) {
		return monstersInGroup.remove(m);
	}
	
	public void addMonster(Monster m) {
		monstersInGroup.add(m);
	}
	
	public ArrayList<Monster> getMonsterList() {
		return monstersInGroup;
	}
	
	public boolean allDead() {
		for (Monster m : monstersInGroup) {
			if (!m.isDead()) return false;
		}
		return true;
	}
	
	public String toString() {
		String ret = "";
		for (int i = 0; i < monstersInGroup.size(); i++) {
			ret += (i + 1) + ". " + monstersInGroup.get(i).toString() + '\n';
		}
		return ret;
	}
	
	public Monster getMonster(int i) {
		return monstersInGroup.get(i);
	}
	
	public int getMonsterNum() {
		return maxMonsters;
	}
}
