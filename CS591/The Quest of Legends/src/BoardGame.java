import java.util.List;

//This is an interface for what methods all board games should include.

public interface BoardGame {
	
	// A method to check if the game is over
	boolean isEnded();
	// A method to move player
	boolean playerMoves(String move, List<positionTTT> board);
	// A method to validate the plater move 
	boolean validate(int player, List<positionTTT> board);
	// A method to run the game
	int run();
}
