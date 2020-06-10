import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 
 */

/**
 * @author chrisr98
 *
 */
public class CreateBoard 
{
	List<positionTTT> boardTicTacToe = new ArrayList<positionTTT>();
	
	private int row;
	private int column;
	
	public CreateBoard() {
		// TODO Auto-generated constructor stub
	}
	
	public List<positionTTT> createTicTacToeBoard(int desiredRow, int desiredColumn)
	{
		//create a TicTacToe board and store it in a list
		this.row = desiredRow;
		this.column = desiredColumn;
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				boardTicTacToe.add(new positionTTT(i, j));
			}
		}
		
		return boardTicTacToe;
	}

	//Not needed for all games
	public List<List<positionTTT>> initializeWinningLines(int boardSize, boolean OaO)
	{
		//create a list of winning line so that the game will "brute-force" check 
		//if a player satisfied any winning condition(s).
		List<List<positionTTT>> winningLines = new ArrayList<List<positionTTT>>();
				
		//Win on x-axis
		List<positionTTT> oneWinCondtion = new ArrayList<positionTTT>();
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				oneWinCondtion.add(new positionTTT(i, j, 1));
			}
			winningLines.add(oneWinCondtion);
			oneWinCondtion = new ArrayList<positionTTT>();
		}
		

		// Win on y-axis
		oneWinCondtion = new ArrayList<positionTTT>();
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				oneWinCondtion.add(new positionTTT(j, i, 1));
			}
			winningLines.add(oneWinCondtion);
			oneWinCondtion = new ArrayList<positionTTT>();
		}
		
		
		// Win diagonally
		oneWinCondtion = new ArrayList<positionTTT>();
		for (int i = 0; i < boardSize; i++) {
			oneWinCondtion.add(new positionTTT(i, i, 1));
		}
		winningLines.add(oneWinCondtion);
		
		
		oneWinCondtion = new ArrayList<positionTTT>();
		for (int i = 0, j = boardSize - 1; i < boardSize; i++, j--) {
			oneWinCondtion.add(new positionTTT(j, i, 1));
		}
		winningLines.add(oneWinCondtion);
		
		
		
		if(OaO == true)
		{
//			Figure out how to handle the other 2 diagonal winning condition (The non centered ones)
//			This is not scalable, or at least I don't think
			oneWinCondtion = new ArrayList<positionTTT>();
			for (int i = 0, j = 1; i < boardSize-1; i++, j++) {
				oneWinCondtion.add(new positionTTT(i, j, 1));
			}
			winningLines.add(oneWinCondtion);
			
			oneWinCondtion = new ArrayList<positionTTT>();
			for (int i = 0, j = 1; i < boardSize-1; i++, j++) {
				oneWinCondtion.add(new positionTTT(j, i, 1));
			}
			winningLines.add(oneWinCondtion);
			
			oneWinCondtion = new ArrayList<positionTTT>();
			for (int i = 1, j = boardSize-1; i < boardSize; i++, j--) {
				oneWinCondtion.add(new positionTTT(j, i, 1));
			}
			winningLines.add(oneWinCondtion);
			
			oneWinCondtion = new ArrayList<positionTTT>();
			for (int i = 0, j = boardSize-2; i < boardSize-1; i++, j--) {
				oneWinCondtion.add(new positionTTT(j, i, 1));
			}
			winningLines.add(oneWinCondtion);
			
			
		}

		
		return winningLines;
	}
	
	public int getStateOfPositionFromBoard(positionTTT position, int size)
	{
		//a helper function to get state of a certain position in the board by given position 
		int state = 0;
		for (int i = 0; i < this.column*this.column; i++) 
		{
			if(this.boardTicTacToe.get(i).y == position.y && this.boardTicTacToe.get(i).x == position.x) {
				state = this.boardTicTacToe.get(i).state;
			}
		}
		return state;
	}
	
	public void printBoard()
	{
		
		//print in "graphical" display
		for (int i = 0; i < row; i++) 
		{	
			System.out.print("["); // boundary
			for (int j = 0; j < column; j++) 
			{
				if (this.getStateOfPositionFromBoard(new positionTTT(i,j), column)==1)
				{
					System.out.print(" X "); //print cross "X" for position marked by player 1
				}
				else if(this.getStateOfPositionFromBoard(new positionTTT(i,j), column)==2)
				{
//					System.out.print(" O "); //print cross "O" for position marked by player 2
					System.out.print(" M "); //print cross "M" for position marked by Market
				}
				else if(this.getStateOfPositionFromBoard(new positionTTT(i,j), column)==0)
				{
					System.out.print(" _ "); //print "_" if the position is not marked
				}
				else if(this.getStateOfPositionFromBoard(new positionTTT(i,j), column)== 3)
				{
					System.out.print(" & "); //print "&" if the position is not accessible
				}
				if(j==column-1)
				{
					System.out.print("]"); // boundary
					System.out.println();
				}
			}
			System.out.println();
		}
	} 
		
	
	
	public void setTileStates()	{
		Random rand = new Random();
		int randPlacement = rand.nextInt(10 - 1 + 1) + 1;
		
		//set tiles at the beginning of the game to decide their function
		for (int i = 0; i < this.column*this.column; i++) 
		{	
			randPlacement = rand.nextInt(10 - 1 + 1) + 1;
			//first tile is always player
			if (this.boardTicTacToe.get(i).y == 0 && this.boardTicTacToe.get(i).x == 0) {
				this.boardTicTacToe.get(i).state = 1;
			} else {
				//20% of all tiles are non accessible
				if (randPlacement < 3) {
					this.boardTicTacToe.get(i).state = 3;
				} else if (randPlacement >= 3 && randPlacement <= 5) { //30% of all tiles are market
					this.boardTicTacToe.get(i).state = 2;
				} else { //50% of all tiles are common
					this.boardTicTacToe.get(i).state = 0;
				}
			}
		}
	} 
	
	//Update tile states once player makes a move.
	public void updateTiles(positionTTT newLoc, positionTTT oldLoc, int previousState) {
		for (int i = 0; i < this.column*this.column; i++) 
		{
			if(this.boardTicTacToe.get(i).y == newLoc.y && this.boardTicTacToe.get(i).x == newLoc.x) {
				this.boardTicTacToe.get(i).state = newLoc.state;
			}
			if(this.boardTicTacToe.get(i).y == oldLoc.y && this.boardTicTacToe.get(i).x == oldLoc.x) {
				this.boardTicTacToe.get(i).state = previousState;
			}
		}
	}
	
}
