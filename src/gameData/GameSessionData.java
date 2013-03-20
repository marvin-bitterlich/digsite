package gameData;

import java.util.HashMap;
import java.util.LinkedList;

public class GameSessionData {
	private HashMap<Integer,Entity> entitymap = new HashMap<Integer,Entity>();
	
	private LinkedList<Texture> textureList = new LinkedList<Texture>();
	private Mine mine;
	private HashMap<Integer, UIItem> uiItemMap = new HashMap<Integer, UIItem>();
	private ActivePlayer activePlayer;
	private int activeMenu = Integer.MAX_VALUE;

	private boolean returnToMenu = false;

	public boolean isReturnToMenu() {
		return returnToMenu;
	}

	public void setReturnToMenu(boolean returnToMenu) {
		this.returnToMenu = returnToMenu;
	}

	public HashMap<Integer,Entity> getEntityMap() {
		return entitymap;
	}

//
//	public LinkedList<Structure> getStructureList() {
//		return structureList;
//	}
//
//	public void setStructureList(LinkedList<Structure> structureList) {
//		this.structureList = structureList;
//	}

	public HashMap<Integer, UIItem> getUiItemMap() {
		return uiItemMap;
	}

	public void setUiItemMap(HashMap<Integer, UIItem> uiItemMap) {
		this.uiItemMap = uiItemMap;
	}

	public ActivePlayer getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(ActivePlayer p) {
		this.activePlayer = p;
	}

	public int getActiveMenu() {
		return activeMenu;
	}

	public void setActiveMenu(int activeMenu) {
		this.activeMenu = activeMenu;
	}

	public LinkedList<Texture> getTextureList() {
		return textureList;
	}

	public void setTextureList(LinkedList<Texture> textureList) {
		this.textureList = textureList;
	}

	public Mine getMine() {
		return mine;
	}

	public void setMine(Mine mine) {
		this.mine = mine;
	}
}
