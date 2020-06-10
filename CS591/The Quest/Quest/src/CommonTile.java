
public class CommonTile extends TileSpace {
	boolean canAccess;
	
	CommonTile(boolean CanAccess) {
		canAccess = CanAccess;
	}
	
	public boolean canAccess() {
		return canAccess;
	}
	
	public void makeItAccessible() {
		canAccess = true;
	}
	
}
