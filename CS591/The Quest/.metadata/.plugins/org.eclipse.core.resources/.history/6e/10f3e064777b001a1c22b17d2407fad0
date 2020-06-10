import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
	private TileSpace[][] curboard;
	private int rowNum, colNum;
	private int[] playerPos = {0,0};
	Random r = new Random();
	double random;
	Hero player;
	private String[] marketNames = {"FireSpells.txt", "IceSpells.txt", "LightningSpells.txt", "Armory.txt", "Weaponry.txt", "Potions.txt"};
	
	Board() { //empty constructor for QolBoard
		
	}
	Board(int rows, int cols) {
		int marketCounter = 0;
		if (rows <= 0 || cols <= 0) throw new IllegalArgumentException("Row/Col number has to be bigger than 0");
		if (rows >= 100 || cols >= 100) throw new IllegalArgumentException("Row/Col number has to be smaller than 100");
		rowNum = rows;
		colNum = cols;
		curboard = new TileSpace[rowNum][colNum];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				random = r.nextDouble();
				if (random < 0.2) { //generate inaccessible tiles
					curboard[i][j] = new CommonTile(false);
				} else if (random < 0.5) { //generate markets
					curboard[i][j] = new Market(readFile(marketNames[marketCounter % marketNames.length], marketCounter % marketNames.length));
					marketCounter++;
				} else { //generate accessible tiles
					curboard[i][j] = new CommonTile(true);
				}
			}
		}
		playerPos[0] = rows;
		playerPos[1] = cols;
		if (rows > 2 && cols > 2) {
			if (getTile(playerPos[0] - 1, playerPos[1]) instanceof CommonTile) {
				CommonTile upTile = (CommonTile) getTile(playerPos[0] - 1, playerPos[1]);
				if (!upTile.canAccess()) {
					if (getTile(playerPos[0], playerPos[1] - 1) instanceof CommonTile) {
						CommonTile leftTile = (CommonTile) getTile(playerPos[0], playerPos[1] - 1);
						if (!leftTile.canAccess()) {
							upTile.makeItAccessible();
						}
					}
				}
			}
		}
	}
	
	
	
	public String printLegendHelper(int heroState, int monsterState) {
		//assuming 0 = no hero, 1 = hero 1, 2 = hero 2, 3 = hero 3
		//monsters: 0 = no monster, 1 = monster 1, etc.
		String toReturn = "| ";
		if (heroState == 0) {
			toReturn += "  ";
		} else {
			toReturn += "H" + Integer.toString(heroState);
		}
		toReturn += " ";
		if (monsterState == 0) {
			toReturn += "  ";
		} else {
			toReturn += "M" + Integer.toString(monsterState);
		}
		toReturn += " |  ";
		return toReturn;
	}
	
	
	
	             
	
	public TileSpace getTile(int row, int col) {
		if (row >= 0 && row < rowNum && col >= 0 && col < colNum) {
			return curboard[row][col];
		}
		System.out.println("Board.getTile(): wrong rowcol num!!");
		return null;
	}
	
	Board(int squareSize) {
		int marketCounter = 0;
		rowNum = squareSize;
		colNum = squareSize;
		curboard = new TileSpace[squareSize][colNum];
		for (int i = 0; i < squareSize; i++) {
			for (int j = 0; j < squareSize; j++) {
				random = r.nextDouble();
				if (random < 0.2) { //generate inaccessible tiles
					curboard[i][j] = new CommonTile(false);
				} else if (random < 0.5) { //generate markets
					curboard[i][j] = new Market(readFile(marketNames[marketCounter % marketNames.length], marketCounter % marketNames.length));
					marketCounter++;
				} else { //generate accessible tiles
					curboard[i][j] = new CommonTile(true);
				}
			}
		}
		
		curboard[squareSize - 1][squareSize - 1] = new CommonTile(true);
		playerPos[0] = squareSize - 1;
		playerPos[1] = squareSize - 1;
	}
	
	/*for (int i = 0; i < squareSize; i++) {
		for (int j = 0; j < squareSize; j++) {
			curboard[i][j] = ' ';
		}
	}*/
	
	public void printBoard() {
		//make it flexible
		String horline = "+--";
		for (int i = 0; i < colNum - 1; i++) {
			horline += "+--";
		}
		horline += "+";
		//at the end horline should look something like "+--+--+--+"
		
		//print out each row, separated by horline
		for (int i = 0; i < rowNum; i++) {
			String contentRow = "|";
			System.out.println(horline);
			for (int j = 0; j < colNum; j++) {
				contentRow += tileToChar(curboard[i][j]);
				if (playerPos[0] == i && playerPos[1] == j) {
					contentRow += "H|";
				} else
				contentRow += " |";
			}
			System.out.println(contentRow);
			//System.out.println("|" + curboard[0][0] + " |" + curboard[0][1] + " |" + curboard[0][2] + " |");
		}
		System.out.println(horline);
	}
	
	public TileSpace makeMove(char wasd) {
		if (wasd == 'W' || wasd == 'w') {
			if (checkAvailability(playerPos[0] - 1, playerPos[1])) {
				playerPos[0] = playerPos[0] - 1;
			} else return null;
		} else if (wasd == 'S' || wasd == 's') {
			if (checkAvailability(playerPos[0] + 1, playerPos[1])) {
				playerPos[0] = playerPos[0] + 1;
			} else return null;
		} else if (wasd == 'A' || wasd == 'a') {
			if (checkAvailability(playerPos[0], playerPos[1] - 1)) {
				playerPos[1] = playerPos[1] - 1;
			} else return null;
		} else if (wasd == 'D' || wasd == 'd') {
			if (checkAvailability(playerPos[0], playerPos[1] + 1)) {
				playerPos[1] = playerPos[1] + 1;
			} else return null;
		}
		return curboard[playerPos[0]][playerPos[1]];
	}
	
	public boolean checkAvailability(int row, int col) {
		if (row > 0 && col > 0) {
			if (row < rowNum && col < colNum) {
				if (curboard[row][col] instanceof CommonTile && ((CommonTile) curboard[row][col]).canAccess() ) {
					return true;
				}
				if (curboard[row][col] instanceof Market) {
					return true;
				}
			}
		}
		return false;
	}
	
	public char tileToChar(TileSpace a) {
		if (a instanceof Market) {
			return 'M';
		} else if (a instanceof CommonTile && ((CommonTile) a).canAccess()) {
			return ' ';
		} else if (a instanceof Hero){ //hero
			return 'P';
		} else { //inaccessible
			return 'X';
		}
	}
	
	public TileSpace getCurrentMarket() {
		return curboard[playerPos[0]][playerPos[1]];
	}
	
	public Item[] readFile(String filename, int type) { //type 0-2 are spells, 3 = armor, 4 = weapon, 5 = potions
		String[] spellTypes = {"IceSpells.txt", "FireSpells.txt", "LightningSpells.txt"};
		ArrayList<Item> items = new ArrayList<Item>(); //armor & potions have 3 attributes. rest have 4. 
		int[] attributes = {0,0,0,0};
		FileReader input = null;
		try {
			input = new FileReader(filename);
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		}
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;
		try {
			bufRead.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			while ( (myLine = bufRead.readLine()) != null)
			{
			    String[] array1 = myLine.split("\\s+");
			    String tempName = array1[0];
			    for (int i = 1; i < array1.length; i++) {
			    	attributes[i-1] = Integer.parseInt(array1[i]);
			    }
			    if (type >= 0 && type <= 2) { //spell
			    	int spellType = Gamelogic.indexxof(spellTypes, filename);
			    	items.add(new Spell(attributes[0], attributes[1], tempName, attributes[2], attributes[3], spellType));
			    } else if (type == 3) { //armor
			    	items.add(new Armor(attributes[0], attributes[1], tempName, attributes[2]));
			    } else if (type == 4) { //weapon
			    	items.add(new Weapon(attributes[0], attributes[1], tempName, attributes[2], (attributes[3] == 2)));
			    } else if (type == 5) { //potion
			    	items.add(new Potion(attributes[0], attributes[1], tempName, attributes[2], 0)); //TODO: do something about potion type
			    } else {
			    	System.out.println("There might be a problem somewhere");
			    }
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("number format problem");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("io exception");
			e.printStackTrace();
		}
		Item[] itemsInTextFile = items.toArray(new Item[items.size()]);
		return itemsInTextFile;
	}
	
}
