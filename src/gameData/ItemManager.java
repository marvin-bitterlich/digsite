package gameData;

import java.util.HashMap;

public class ItemManager {
	private static HashMap<Integer, Item> itemList = new HashMap<Integer, Item>();
	
	public static Item getItem(int id){
		return itemList.get(id);
	}
	
	public static void setItemList(HashMap<Integer, Item> list){
		itemList = list;
	}

	public static Sprite getSpriteForItem(int itemId) {
		// TODO BILDER FÜR ITEMSTACK ZURÜCKGEBEN
		return null;
	}
	
}
