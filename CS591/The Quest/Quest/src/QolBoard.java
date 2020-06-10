import java.util.ArrayList;
import java.util.Random;

public class QolBoard extends Board {
	private TileSpace[][] curboard = new TileSpace[8][8];
	private String[] marketNames = {"FireSpells.txt", "IceSpells.txt", "LightningSpells.txt", "Armory.txt", "Weaponry.txt", "Potions.txt"};
	private Team team;
	private MonsterGroup monsters;
	
	QolBoard(Team team) {
		super();
		this.team = team;
		// positionTTT parameters: int setX, int setY, int setStateHero, int
		// setStateMonster, int setStateSpecialCell
		int marketCounter = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (j == 2 || j == 5) {
					curboard[i][j] = new CommonTile(false); // make the column inaccessible
					continue;
				}
				if (i == 0 || i == 7) { // nexus's (make markets in first and last row)
					QolTile nexus = new QolTile(4);
					Market m = new Market(readFile(marketNames[marketCounter % marketNames.length],
							marketCounter % marketNames.length));
					nexus.setMarket(m);
					curboard[i][j] = nexus;
					continue; // which integers mean what for monsterstate, herostate and setStateSpecialCell?
				} // 0 = regular cell(plain), 1 = bush(dexterity), 2 = koulon(strength), 3 =
					// cave(agility + 10%)

				Random r = new Random();
				double random = +r.nextDouble();
				// each empty cell has a 15% chance of being a special cell
				if (random < 0.05) {
					curboard[i][j] = new QolTile(1); // bush tile
				} else if (random < 0.10) {
					curboard[i][j] = new QolTile(2); // koulon tile
				} else if (random < 0.15) {
					curboard[i][j] = new QolTile(3); // cave tile
				} else {
					curboard[i][j] = new QolTile(0); // 85% of being a plain tile
				}
			}
		}
		// put monsters and heroes in cell
		int numOfEnemies = 3; // TODO: make this less hardcoded. put things in for loop. use variables instead
								// of numbers
		MonsterGroup enemies = new MonsterGroup(1, numOfEnemies); // get 3 level 1 enemies
		((QolTile) curboard[0][0]).addEntity(enemies.getMonsterList().get(0), 1);
		((QolTile) curboard[0][3]).addEntity(enemies.getMonsterList().get(1), 1);
		((QolTile) curboard[0][6]).addEntity(enemies.getMonsterList().get(2), 1);
		monsters = enemies;
		
		((QolTile) curboard[7][0]).addEntity(team.getHero(0), 0);
		((QolTile) curboard[7][3]).addEntity(team.getHero(1), 0);
		((QolTile) curboard[7][6]).addEntity(team.getHero(2), 0);

	}
	
	public void spawnNewEnemies(int maxLevel) {
		int numOfEnemies = 3;
		MonsterGroup enemies = new MonsterGroup(maxLevel, numOfEnemies); // get 3 level 1 enemies
		Monster m1 = enemies.getMonsterList().get(0);
		Monster m2 = enemies.getMonsterList().get(1);
		Monster m3 = enemies.getMonsterList().get(2);
		((QolTile) curboard[0][0]).addEntity(m1, 1);
		((QolTile) curboard[0][3]).addEntity(m2, 1);
		((QolTile) curboard[0][6]).addEntity(m3, 1);
		monsters.addMonster(m1);
		monsters.addMonster(m2);
		monsters.addMonster(m3);
	}
	
	public boolean teleport(Hero h, int row, int col) {
		int[] curPos = getEntityPosition(h);
		int currentLane = getLane(curPos[1]);
		int laneToGo = getLane(col);
		try {
			if (!checkQolAvailability(row, col, h)) {
				System.out.println("Invalid tile space - wrong tile!");
				return false;
			} else if (currentLane == laneToGo) {
				System.out.println("Can't teleport to same lane!");
				return false;
			} else {
				
				
				if(validTeleport(laneToGo, row, col, curPos[0], curPos[1])) {
					//remove entity from original position, add to new position
					validSwap(curPos, row, col, h, 0);
					return true;
				} else {
					System.out.println("Invalid tile space - can't teleport behind enemies!");
					return false;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			System.out.println("Invalid tile space - out of bounds");
			return false;
		}
		
	}
	
	public boolean validTeleport(int laneToGo, int goingToRow, int goingToCol, int currentRow, int currentCol) {
		boolean res = true;
		int firstLaneIndex = 0;
		int lastLaneIndex = 0;
		if (laneToGo == 1) {
			firstLaneIndex = 0;
			lastLaneIndex = 1;
		} else if(laneToGo == 2) {
			firstLaneIndex = 3;
			lastLaneIndex = 4;
		} else if(laneToGo == 3) {
			firstLaneIndex = 6;
			lastLaneIndex = 7;
		} else {
			return false;
		}
		
		if(goingToRow == 0) {
			System.out.println("Invalid tile space - out of bounds");
			return false;
		}
		
		for (int i = 0; i < 8; i++) {
			for (int j = firstLaneIndex; j <= lastLaneIndex; j++) {
				//If there is a monster on this tile check to see if they are going past that row.
				if (((QolTile) curboard[i][j]).containsMonster()) {
					if (goingToRow < i) {
						res = false;
					}
				}
			}
		}
		return res;
	}
	
	public int getLane(int col) {
		int res = 0;
		if (col == 0 || col == 1) {
			res = 1;
		} else if (col == 3 || col == 4) {
			res = 2;
		} else if (col == 6 || col == 7) {
			res = 3;
		}
		return res;
	}

	public TileSpace getTile(int row, int col) {
		TileSpace res = null;
		try {
			res = curboard[row][col];
			return res;
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			System.out.println("Invalid tile space!");
			return res;
		}
	}
	public void setTeam(Team t) {
		team = t;
	}
		
	public String printBoardHelper(Entity[] entities) {
		String ret = "| "; //| H1 M  |
		if(entities.length == 0) {
			ret += "   ";
		}
		for (Entity e : entities) {
			if (e instanceof Hero) {
				ret += "H" + team.getHeroIndex((Hero) e);
				ret += " ";
			} else if (e instanceof Monster) {
				ret +=  "M ";
				ret += " ";
			} 
		}
		
		while (ret.length() < 8) {
			ret += " ";
		}
		
		ret += "|  ";
		return ret;
	}
		
	public void printLegendBoard() {
		int counter = 0;
		String topAndBot = "";
		String mid = "";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (curboard[i][j] instanceof QolTile) {
					//0 = regular cell(plain), 1 = bush(dexterity), 2 = koulon(strength), 3 = cave(agility + 10%), 4 = nexus
					int cellState = ((QolTile) curboard[i][j]).getType();
					//"H1" "H2"
					if (cellState == 0) {
						topAndBot += "P - P - P  ";
					} else if (cellState == 1) {
							topAndBot += "B - B - B  ";
					} else if (cellState == 2) {
						topAndBot += "K - K - K  ";
					} else if (cellState == 3) {
						topAndBot += "C - C - C  ";
					} else if (cellState == 4) {
						topAndBot += "N - N - N  ";
					}
					Entity[] occupants = ((QolTile) curboard[i][j]).getOccupants();
					mid += printBoardHelper(occupants);
				} else {
					topAndBot += "X - X - X  ";
					mid += "|       |  ";
				}
			}
			System.out.println(topAndBot);
			System.out.println(mid);
			System.out.println(topAndBot + "\n");
			mid = "";
			topAndBot = "";
		}
	}
	
	public int getTileType(int row, int col) {
		int res = 0;
		if(curboard[row][col] instanceof QolTile) {
			res = ((QolTile)curboard[row][col]).getType();
		}
		
		return res;
	}
	
	public ArrayList<Entity> getAdjacentCreature(int row, int col, int type) {
		//Negative Type is a hero looking for nearby monster
		//Positive Type is a monster looking for nearby hero
		int topRow = row-1;
		int bottomRow = row+1;
		int leftCol = col-1;
		int rightCol = col+1;
		ArrayList<Entity> allAround = new ArrayList<Entity>(); 
		ArrayList<Entity> adjacentMonsterList = new ArrayList<Entity>();
		ArrayList<Entity> adjacentHeroList = new ArrayList<Entity>();
		
		try {
			QolTile Center = (QolTile)curboard[row][col];
			Entity[] heroTile = Center.getOccupants();
			for (int i = 0; i < heroTile.length; i++) {
				allAround.add(heroTile[i]);
			}	
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
//			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO: handle exception
//			e.printStackTrace();
		}
		

		try {
			QolTile TopLeft = (QolTile)curboard[topRow][leftCol];
			Entity[] monster1 = TopLeft.getOccupants();
			for (int i = 0; i < monster1.length; i++) {
				allAround.add(monster1[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
//			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO: handle exception
//			e.printStackTrace();
		}

		try {
			QolTile TopCenter = (QolTile)curboard[topRow][col];
			Entity[] monster2 = TopCenter.getOccupants();
			for (int i = 0; i < monster2.length; i++) {
				allAround.add(monster2[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
//			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO: handle exception
//			e.printStackTrace();
		}

		try {
			QolTile TopRight = (QolTile)curboard[topRow][rightCol];
			Entity[] monster3 = TopRight.getOccupants();
			for (int i = 0; i < monster3.length; i++) {
				allAround.add(monster3[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
//			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO: handle exception
//			e.printStackTrace();
		}

		try {
			QolTile CenterLeft = (QolTile)curboard[row][leftCol];
			Entity[] monster4 = CenterLeft.getOccupants();
			for (int i = 0; i < monster4.length; i++) {
				allAround.add(monster4[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
//			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO: handle exception
//			e.printStackTrace();
		}

		try {
			QolTile CenterRight = (QolTile)curboard[row][col+1];
			Entity[] monster5 = CenterRight.getOccupants();
			for (int i = 0; i < monster5.length; i++) {
				allAround.add(monster5[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
//			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO: handle exception
//			e.printStackTrace();
		}

		try {
			QolTile BottomLeft = (QolTile)curboard[bottomRow][leftCol];
			Entity[] monster6 = BottomLeft.getOccupants();
			for (int i = 0; i < monster6.length; i++) {
				allAround.add(monster6[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
//			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO: handle exception
//			e.printStackTrace();
		}

		try {
			QolTile BottomCenter = (QolTile)curboard[bottomRow][col];
			Entity[] monster7 = BottomCenter.getOccupants();
			for (int i = 0; i < monster7.length; i++) {
				allAround.add(monster7[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
//			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO: handle exception
//			e.printStackTrace();
		}

		try {
			QolTile BottomRight = (QolTile)curboard[bottomRow][rightCol];
			Entity[] monster8 = BottomRight.getOccupants();
			for (int i = 0; i < monster8.length; i++) {
				allAround.add(monster8[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
//			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO: handle exception
//			e.printStackTrace();
		}		
		
		
		for(Entity e : allAround) {
			if (e instanceof Monster) {
				adjacentMonsterList.add(e);
			} else if (e instanceof Hero) {
				adjacentHeroList.add(e);
			}
		}
		
		//Negative Type is a hero looking for nearby monster
		//Positive Type is a monster looking for nearby hero
		//Return respective list
		
		if (type < 0) {
			return adjacentMonsterList;
		} else if (type > 0) {
			return adjacentHeroList;
		} else {
			return null;
		}
		
		
		
		
	}
	
	public MonsterGroup getMonsters() {
		return monsters;
	}
	
	public boolean checkQolAvailability(int row, int col, Entity e) {
		if (row >= 0 && col >= 0 && row < 8 && col < 8) {
			if (curboard[row][col] instanceof QolTile) {
				if (!((QolTile) curboard[row][col]).containsMonster() && (e instanceof Monster)) { //check that target tile doesn't have monster
					return true;
				}
				if (!((QolTile) curboard[row][col]).containsHero() && (e instanceof Hero)) { //check that the target tile doesn't have a hero
					return true;
				}
			}
		}
		return false;
	}
	
	public int[] getEntityPosition(Entity e) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (curboard[row][col] instanceof QolTile) {
					Entity[] occupants = ((QolTile) curboard[row][col]).getOccupants();
					for (Entity curEntity : occupants) {
						if (e == curEntity) {
							return new int[] {row, col};
						}
					}
				}
			}
		}
		System.out.println("Problem with getEntityPosition - entity not found");
		return new int[] {-1, -1};
	}
	
	
	public boolean canAdvance(ArrayList<Entity> nearby, int goingToRow, int goingToCol, int heroOrMonster) { //heroOrMonster 0=hero, 1=monster
		boolean res = true;
		int currentLane = getLane(goingToCol);
		if (heroOrMonster == 0 && nearby != null) {
			for (int i = 0; i < nearby.size(); i++) {
				int enemyRow = (getEntityPosition(nearby.get(i)))[0];
				int enemyCol = (getEntityPosition(nearby.get(i)))[1];
				int enemyLane = getLane(enemyCol);
				if(goingToRow == 0) {
					System.out.println("Can't go into enemy nexus while enemy is nearby!");
					res = false;
				} else if ((goingToRow < enemyRow) && (currentLane == enemyLane)) {
					System.out.println("Hero can't walk past monster");
					res = false;
				} else {
					res = true;
				}
			}
		} else if (heroOrMonster == 1  && nearby != null) {
			for (int i = 0; i < nearby.size(); i++) {
				int enemyRow = (getEntityPosition(nearby.get(i)))[0];
				if (goingToRow > enemyRow) {
					System.out.println("Can't walk past hero"); // can remove this testing
					res = false;
				} else {
					res = true;
				}
			}
		} else {
			System.out.println("Error occured: unknown type of entity, return null");
			return false;
		}
		
		return res;
	}
	public TileSpace makeMove(char wasd, Entity e, int heroOrMonster) { //heroOrMonster 0=hero, 1=monster
		int entityRow = getEntityPosition(e)[0];
		int entityCol = getEntityPosition(e)[1];
		
		ArrayList<Entity> adjacentEnemy = null;
		boolean canMoveForward = true;
		
		if (wasd == 'W' || wasd == 'w') {
			if (checkQolAvailability(entityRow - 1, entityCol, e)) {
				adjacentEnemy = getAdjacentCreature(entityRow, entityCol, -1);
				if(adjacentEnemy != null) {
					canMoveForward = canAdvance(adjacentEnemy, entityRow-1, entityCol, heroOrMonster);
				} 
				if(canMoveForward) {
					//remove entity from original position, add to new position
					validSwap(new int[] {entityRow, entityCol}, entityRow - 1, entityCol, e, heroOrMonster);
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else if (wasd == 'S' || wasd == 's') {
			if (checkQolAvailability(entityRow + 1, entityCol, e)) {
				// remove entity from original position, add to new position
				validSwap(new int[] {entityRow, entityCol}, entityRow + 1, entityCol, e, heroOrMonster);
			} else {
				return null;
			}
		} else if (wasd == 'A' || wasd == 'a') {
			if (checkQolAvailability(entityRow, entityCol - 1, e)) {
				validSwap(new int[] {entityRow, entityCol}, entityRow, entityCol - 1, e, heroOrMonster);
			} else {
				return null;
			}
		} else if (wasd == 'D' || wasd == 'd') {
			if (checkQolAvailability(entityRow, entityCol + 1, e)) {
				validSwap(new int[] {entityRow, entityCol}, entityRow, entityCol + 1, e, heroOrMonster);
			} else {
				return null;
			}
		} else {
			return null;
		}
		
		return curboard[entityRow][entityCol];
	}
	
	public void validSwap(int[] curPos, int row, int col, Entity curEntity, int heroOrMonster) {// 1 for monster and 0 for hero
		Entity[] eList = ((QolTile) curboard[curPos[0]][curPos[1]]).getOccupants();
		Hero heroToMove = null;
		Monster monsterToMove = null;
		for (Entity e : eList) {
			if(e instanceof Hero) {
				heroToMove = (Hero)e;
			} else if (e instanceof Monster) {
				monsterToMove = (Monster)e;
			}
		}
		if(heroOrMonster == 1) {
			((QolTile) curboard[row][col]).addEntity(monsterToMove, 1);
			((QolTile) curboard[curPos[0]][curPos[1]]).removeEntity(curEntity);
		} else  if (heroOrMonster == 0) {
			((QolTile) curboard[row][col]).addEntity(heroToMove, 0);
			((QolTile) curboard[curPos[0]][curPos[1]]).removeEntity(curEntity);
		} else {
			System.out.println("something is wrong - don't know entity type!");
		}
		
	}
		
	public boolean isAdjacent(int heroIndex, int monsterIndex) { //returns whether a hero is adjacent to a monster i.e. attackable
		
		int heroRow = heroIndex / (int) 8;
		int heroColumn = heroIndex % (int) 8;
		int monsterRow = monsterIndex / (int) 8;
		int monsterColumn = monsterIndex % (int) 8;
		
		//error checking
		if (heroRow == 2 || heroRow == 5 || monsterRow == 5 || monsterColumn == 2) {
			System.out.println("something is wrong - either someone is in an inaccessible block, or isAdjacent is implemented wrongly");
		}
		
		if (Math.abs(heroRow - monsterRow) <= 1 && Math.abs(heroColumn - monsterColumn) <= 1) {
			return true;
		}
		return false;
		
	}
}