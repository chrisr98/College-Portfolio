import java.util.*;
public class aiTicTacToe {

	public int player; //1 for player 1 and 2 for player 2
	private static List<positionTicTacToe> board = new ArrayList<>();

	//Initialize infinity boundary
	static double javaMax = Double.POSITIVE_INFINITY;
	static double javaMin = Double.NEGATIVE_INFINITY;
	//Turn counter create only for test purposes
	private static int turnCounter = 0;
	

	private int getStateOfPositionFromBoard(positionTicTacToe position, List<positionTicTacToe> board){
		//a helper function to get state of a certain position in the Tic-Tac-Toe board by given position TicTacToe
		int index = position.x*16+position.y*4+position.z;
		return board.get(index).state;
	}

	public positionTicTacToe myAIAlgorithm(List<positionTicTacToe> board, int player){
		
		double minValue = javaMin;
		double alpha = javaMin;
		double beta = javaMin;
		//TODO: this is where you are going to implement your AI algorithm to win the game. The default is an AI randomly choose any available move.
		positionTicTacToe myNextMove = new positionTicTacToe(0,0,0);

		//Starting from the last index of the board 
		for (int i = board.size() - 1; i > 0; i--) {
			//Gets the state of the board
			int currentSpot = board.get(i).state;
			//Checks to see if any else has played there
			if (currentSpot == 0) {
				//Sets the spot on the board to the currently playing player.
				currentSpot = player;
				//Runs minimax to minimize the possible loss for worst case scenario
				double movementValue = miniMaxABP(false, player, 3, javaMin, beta, board);
				alpha = Double.min(alpha, movementValue);
				if (movementValue > minValue) {
					//Set new minimum value 
					minValue = movementValue;
					//Assign the XYZ value to the next move
					myNextMove.x = board.get(i).x;
					myNextMove.y = board.get(i).y;
					myNextMove.z = board.get(i).z;
				}
				if (beta <= alpha)
					break;
			}
			
		
		}
		
		//Test cases where we try to block the path of our AI on certain turns
		turnCounter += 1;
		
		if (turnCounter == 6)
			myNextMove = new positionTicTacToe(2, 2, 2);
		if (turnCounter == 4)
			myNextMove = new positionTicTacToe(0, 1, 0);
		if (turnCounter == 5)
			myNextMove = new positionTicTacToe(0, 2, 1);
		if (turnCounter == 5)
			myNextMove = new positionTicTacToe(1, 2, 0);
		if (turnCounter == 7)
			myNextMove = new positionTicTacToe(0, 1, 3);
		if (turnCounter == 4)
			myNextMove = new positionTicTacToe(3, 1, 3);

//		if (turnCounter == 1)
//			myNextMove = new positionTicTacToe(0, 0, 0);
//		if (turnCounter == 3)
//			myNextMove = new positionTicTacToe(0, 1, 0);
//		if (turnCounter == 5)
//			myNextMove = new positionTicTacToe(0, 2, 0);
//		if (turnCounter == 7)
//			myNextMove = new positionTicTacToe(3, 3, 1);
		
		
		return myNextMove;


	}


    private int evalHeuristicValue (List<positionTicTacToe> board) {

    	//Initialize score to return and a list of all possible winning lines 
		int heuristicScore = 0;
		List<List<positionTicTacToe>> winningLines = initializeWinningLines();

		//Runs a loop on all possible winning positions
		for(int i = 0; i < winningLines.size(); i++){

			ArrayList<Integer> states = new ArrayList<>();
			
			//Gets the winning line
			positionTicTacToe p0 = winningLines.get(i).get(0);
			positionTicTacToe p1 = winningLines.get(i).get(1);
			positionTicTacToe p2 = winningLines.get(i).get(2);
			positionTicTacToe p3 = winningLines.get(i).get(3);
			//Checks who the winning line belongs to, player 1 or 2
			int state0 = getStateOfPositionFromBoard(p0,board);
			int state1 = getStateOfPositionFromBoard(p1,board);
			int state2 = getStateOfPositionFromBoard(p2,board);
			int state3 = getStateOfPositionFromBoard(p3,board);
			
			//Add states to a list
			states.add(state0);
			states.add(state1);
			states.add(state2);
			states.add(state3);

			int maxScore = 0;
			int minScore = 0;

			//Runs through the state list and assign max/min score points accordingly
			for (int j : states){
				//Checks to see who is player and who is opponent to assign maxScore point to player and minScore to opponent
				if (j == player) maxScore += 1;
				if(j == (3-player)) maxScore += 1;
			}

			//Tally heuristicScore value based on how many points max/min score has. If positive it favors the player else it favors the opponent
			if (maxScore <= 0 || minScore <= 0){
				if(maxScore == 0){
					if(minScore == 1) heuristicScore -= 10;
					else if(minScore == 2) heuristicScore -= 50;
					else if(minScore == 3) heuristicScore -= 200;
					else if(minScore == 4) heuristicScore -= 200;
				}
				else if(minScore == 0){
					if(maxScore == 1) heuristicScore += 10;
					else if(maxScore == 2) heuristicScore += 50;
					else if(maxScore == 3) heuristicScore += 100;
					else if(maxScore == 4) heuristicScore += 200;
				}
			}
		}
		return heuristicScore;
	}


    //Same isEnded code from runTicTacToe
    private int isEnded(List<positionTicTacToe> board){
		List<List<positionTicTacToe>> winningLines = initializeWinningLines();
		for(int i=0;i<winningLines.size();i++){

			positionTicTacToe p0 = winningLines.get(i).get(0);
			positionTicTacToe p1 = winningLines.get(i).get(1);
			positionTicTacToe p2 = winningLines.get(i).get(2);
			positionTicTacToe p3 = winningLines.get(i).get(3);

			int state0 = getStateOfPositionFromBoard(p0,board);
			int state1 = getStateOfPositionFromBoard(p1,board);
			int state2 = getStateOfPositionFromBoard(p2,board);
			int state3 = getStateOfPositionFromBoard(p3,board);

			//if they have the same state (marked by same player) and they are not all marked.
			if(state0 == state1 && state1 == state2 && state2 == state3 && state0 != 0){
				//Game is over with a winning player/ 1 or 2
				return state0;
			}
		}
		for(int i=0;i<board.size();i++){
			if(board.get(i).state==0){
				//game is not ended, continue
				return 0;
			}
		}
		return -1; //call it a draw
	}
	


    //Our minimax function uses Alpha Beta Pruning (ABP). Basically taken from the pseudo-code given to us.
    public double miniMaxABP(boolean maximizingPlayer, int player, int depth, double alpha, double beta, List<positionTicTacToe> board){
    	//Get the heuristic score go the board
    	int score = evalHeuristicValue(board);
		//Stop minimax and return score
		if(depth == 0 || isEnded(board)!=0){
			if(this.player == player){
				return score;
			}
			else{
				return score;
			}
		}
		
		// if maximizingplayer is true, then value = - infinity
		if(maximizingPlayer) {
			double maxValue = javaMin;
			//recur down
			for(int i = 0; i<64; i++){
				if(board.get(i).state == 0) {
					board.get(i).state = player;
					maxValue = Math.max(maxValue, miniMaxABP(false, 3 - player, depth-1, alpha, beta, board));
					board.get(i).state = 0;
					alpha = Math.max(alpha, maxValue);
					//alpha beta pruning
					if (beta <= alpha){
						break;
					}
				}
			}
			return maxValue;
		}
		else {
			double minValue = javaMax;
			
			//recur down
			for(int i = 0; i<64; i++){
				if(board.get(i).state == 0) {
					if(player == 1){
						board.get(i).state = 2;
					}
					else if(player == 2){
						board.get(i).state = 1;
					}
					minValue = Math.min(minValue, miniMaxABP(true, 3 - player, depth-1, alpha, beta, board));
					board.get(i).state = 0;
					beta = Math.min(beta, minValue);
					
					//alpha beta pruning
					if (beta <= alpha) break;
				}
			}
			return minValue;
		}

	}


	private List<List<positionTicTacToe>> initializeWinningLines()
	{
		//create a list of winning line so that the game will "brute-force" check if a player satisfied any 	winning condition(s).
		List<List<positionTicTacToe>> winningLines = new ArrayList<List<positionTicTacToe>>();

		//48 straight winning lines
		//z axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,j,0,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//y axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,j,-1));
				winningLines.add(oneWinCondtion);
			}
		//x axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,j,-1));
				winningLines.add(oneWinCondtion);
			}

		//12 main diagonal winning lines
		//xz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,0,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,1,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,2,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//yz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,0,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//xy plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,0,i,-1));
				oneWinCondtion.add(new positionTicTacToe(1,1,i,-1));
				oneWinCondtion.add(new positionTicTacToe(2,2,i,-1));
				oneWinCondtion.add(new positionTicTacToe(3,3,i,-1));
				winningLines.add(oneWinCondtion);
			}

		//12 anti diagonal winning lines
		//xz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,3,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,2,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,1,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,0,-1));
				winningLines.add(oneWinCondtion);
			}
		//yz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,3,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,0,-1));
				winningLines.add(oneWinCondtion);
			}
		//xy plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,3,i,-1));
				oneWinCondtion.add(new positionTicTacToe(1,2,i,-1));
				oneWinCondtion.add(new positionTicTacToe(2,1,i,-1));
				oneWinCondtion.add(new positionTicTacToe(3,0,i,-1));
				winningLines.add(oneWinCondtion);
			}

		//4 additional diagonal winning lines
		List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,0,0,-1));
		oneWinCondtion.add(new positionTicTacToe(1,1,1,-1));
		oneWinCondtion.add(new positionTicTacToe(2,2,2,-1));
		oneWinCondtion.add(new positionTicTacToe(3,3,3,-1));
		winningLines.add(oneWinCondtion);

		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,0,3,-1));
		oneWinCondtion.add(new positionTicTacToe(1,1,2,-1));
		oneWinCondtion.add(new positionTicTacToe(2,2,1,-1));
		oneWinCondtion.add(new positionTicTacToe(3,3,0,-1));
		winningLines.add(oneWinCondtion);

		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(3,0,0,-1));
		oneWinCondtion.add(new positionTicTacToe(2,1,1,-1));
		oneWinCondtion.add(new positionTicTacToe(1,2,2,-1));
		oneWinCondtion.add(new positionTicTacToe(0,3,3,-1));
		winningLines.add(oneWinCondtion);

		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,3,0,-1));
		oneWinCondtion.add(new positionTicTacToe(1,2,1,-1));
		oneWinCondtion.add(new positionTicTacToe(2,1,2,-1));
		oneWinCondtion.add(new positionTicTacToe(3,0,3,-1));
		winningLines.add(oneWinCondtion);

		return winningLines;

	}

	public aiTicTacToe(int setPlayer){
		player = setPlayer;
	}
}
	
	
	
