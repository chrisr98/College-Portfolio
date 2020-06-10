public class positionTTT {

	int x;
	int y;
	int stateHero;
	int stateMonster;
	int stateSpecialCell;
	
	//Construct position with all argument 
	public positionTTT(int setX, int setY, int setStateHero, int setStateMonster, int setStateSpecialCell)
	{
		this.x = setX;
		this.y = setY;
		this.stateHero = setStateHero;
		this.stateMonster = setStateMonster;
		this.stateSpecialCell = setStateSpecialCell;
	}
	
	////Construct position with only X and Y argument
	public positionTTT(int setX, int setY)
	{
		this.x = setX;
		this.y = setY;
		this.stateHero = 0;
		this.stateMonster = 0;
		this.stateSpecialCell = 0;
	}
	
	//Help with printing for debugging
	public void printPositionTTT()
	{
		System.out.print("("+x+","+y+")");
		System.out.println("state hero: "+stateHero);
		System.out.println("state monster: "+stateMonster);
		System.out.println("state special cell: "+stateSpecialCell);
	}
	
	public void changeStateHero(int setStateHero) {
		this.stateHero = setStateHero;
	}
	
	public void changeStateMonster(int setStateMonster) {
		this.stateMonster= setStateMonster;
	}
	
	public void changeStateSpecialCell (int setStateSpecialCell) {
		this.stateSpecialCell = setStateSpecialCell;
	}
}
