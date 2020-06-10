
public class Market extends TileSpace{
	private Item[] commodities;
	
	Market(Item[] toSell) {
		commodities = toSell;
	}
	
	public String toString() {
		String itemsInfo = "";
		for (int i = 0; i < commodities.length; i++) {
			itemsInfo += (i+1) + ". " + commodities[i].toString() + '\n';
		}
		return itemsInfo;
	}
	
	public Item[] getItems() {
		return commodities;
	}
	
	public Item getItem(int index) {
		return commodities[index];
	}
	
	public int numItems() {
		return commodities.length;
	}
}
