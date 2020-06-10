import java.util.ArrayList;

public class QolTile extends TileSpace {
	private ArrayList<Entity> occupants = new ArrayList<Entity>();
	private int type;
	private Market market;
	//0 = regular cell(plain), 1 = bush(dexterity), 2 = koulon(strength), 3 = cave(agility + 10%), 4 = nexus
	
	QolTile(int Type) {
		type = Type;
	}
	
	public boolean setMarket(Market m) { //returns if successful or not
		if (type == 4) {
			market = m;
			return true;
		} else {
			System.out.println("This tile isn't a nexus type!");
			return false;
		}
	}
	
	public Market getMarket() {
		return market;
	}
	public int getType() {
		return type;
	}
	
	public Entity[] getOccupants() {
		Entity[] result = new Entity[occupants.size()];
		for (int i = 0; i < occupants.size(); i++) {
			result[i] = occupants.get(i);
		}
		return result;
	}
	
	public boolean containsHero() {
		for (Entity e : occupants) {
			if (e instanceof Hero) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsMonster() {
		for (Entity e : occupants) {
			if (e instanceof Monster) {
				return true;
			}
		}
		return false;
	}
	
	public boolean removeEntity(Entity entity) {
		boolean success = occupants.remove(entity);
		if (!success) {
			System.out.println("Cannot remove entity");
		}
		return success;
	}
	
	public boolean addEntity(Entity entity, int heroOrMonster) { //returns whether adding was successful
		//parameters: entity to add, type of entity (0 = hero, 1 = monster)
//		if (type == 5 || type == 1) {
//			System.out.println("Cannot add entity in QolTile - it's a market/inaccessible!");
//			return false;
//		}
		if (occupants.size() >= 2) {
			System.out.println("Cannot add entity in QolTile - there are already two entities!");
			return false;
		}
		for (Entity e: occupants) {
			if (e instanceof Hero && heroOrMonster == 0) {
				System.out.println("Cannot add entity in QolTile - there is already a hero!");
				return false;
			}
			if (e instanceof Monster && heroOrMonster == 1) {
				System.out.println("Cannot add entity in QolTile - there is already a monster!");
				return false;
			}
		}
		if (heroOrMonster == 1) {
			occupants.add((Monster) entity);
			return true;
		} else if (heroOrMonster == 0) {
			occupants.add((Hero) entity);
			return true;
		} else {
			System.out.println("Cannot add entity in QolTile - incorrect heroOrMonster parameter");
			return false;
		}
	}

}
