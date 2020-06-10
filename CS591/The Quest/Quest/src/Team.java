import java.util.ArrayList;

public class Team {
	private Hero activeHero = null;
	private Hero[] heroList;
	private int maxHeroes;
	
	Team(int maxH) {
		if (maxH < 1 || maxH > 3) {
			throw new IllegalArgumentException("max heroes not valid");
		}
		maxHeroes = maxH;
		heroList = new Hero[maxH];
	}
	
	public int getHeroIndex(Hero hero) {
		for (int i = 0; i < heroList.length; i++) {
			if (heroList[i] != null) {
				if (heroList[i] == hero) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public Hero getHero(int index) {
		if (index < heroList.length && index >= 0)
			return heroList[index];
		else {
			System.out.println("Bad Index!");
			return null;
		}
	}
	
	public void reviveTheDead() {
		for (int i = 0; i < heroList.length; i++) {
			if (heroList[i] != null) {
				if (heroList[i].isDead()) {
					heroList[i].revive();
				}
			}
		}
	}
	
	public boolean allDead() {
		for (int i = 0; i < heroList.length; i++) {
			if (heroList[i] == null) {
				continue;
			} else {
				if (!heroList[i].isDead()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void addHero(Hero a) {
		for (int i = 0; i < heroList.length; i++) {
			if (heroList[i] == null) {
				heroList[i] = a;
				return;
			} else if (i == heroList.length && heroList[i] != null) {
				System.out.println("You can't add any more heroes!");
			}
		}
	}
	
	public int numHeroes() {
		int ret = 0;
		for (int i = 0; i < heroList.length; i++) {
			if (heroList[i] != null) {
				ret++;
			}
		}
		return ret;
	}
	
	public String toString() {
		String ret = "";
		for (int i = 0; i < heroList.length; i++) {
			if (heroList[i] != null) {
				ret += (i + 1) + ". " + heroList[i].toString() + '\n';
			} else {
				ret += (i + 1) + ". \n";
			}
		}
		return ret;
	}

	public Hero getActiveHero() {
		return activeHero;
	}
	
	public boolean changeActiveHero(int toChange) {
		if (toChange < 0 || toChange > maxHeroes - 1) {
			return false;
		} else {
			activeHero = heroList[toChange];
			System.out.println("\nYour active hero is now " + activeHero);
			return true;
		}
	}
	
	public boolean removeHero(int index) {
		if (heroList[index] == null) {
			System.out.println("This hero doesn't exist already.");
			return false;
		} else {
			heroList[index] = null;
			return true;
		}
	}

}
