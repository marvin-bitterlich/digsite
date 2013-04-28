package singleton;

import java.awt.image.BufferStrategy;
import java.util.HashMap;

import network.NetworkHandlerThread;
import gameController.GameControllerThread;
import gameData.ActivePlayer;
import gameView.GamePanel;
import gameView.ingame.Mine;
import gameView.ingame.datatypes.Entity;
import gameView.ingame.menu.UIItem;

public class GameData {
	public boolean gameLoaded = false;

	private GameControllerThread gct;
	private NetworkHandlerThread nht;
	private GamePanel gp;

	public static final int MENU = 0;
	public static final int INGAME = 1;
	private int gameState = 0;
	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
	public int getGameState() {
		return gameState;
	}

	public GameControllerThread getGameControllerThread() {
		return gct;
	}

	public void setGameControllerThread(GameControllerThread gct) {
		this.gct = gct;
	}

	public void setNetworkHandlerThread(
			NetworkHandlerThread networkHandlerThread) {
		this.nht = networkHandlerThread;
	}

	public NetworkHandlerThread getNetworkHandlerThread() {
		return nht;
	}

	public GamePanel getGamePanel() {
		return gp;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gp = gamePanel;
	}

	/*
	 * Integer of the width of the GameWindow.
	 * Equals the users Screenwidth!
	 * */
	private int width;
	public void setWidth(int _width) {
		width = _width;
	}
	public int width() {
		return width;
	}

	/*
	 * Integer of the height of the GameWindow.
	 * Equals the users Screenheight!
	 * */
	private int height;
	public void setHeight(int _height) {
		height = _height;
	}
	public int height() {
		return height;
	}

	private BufferStrategy bufferstrategy;
	public void setBufferstrategy(BufferStrategy _bufferStrategy) {
		bufferstrategy = _bufferStrategy;
	}
	public BufferStrategy bufferstrategy(){
		return bufferstrategy;
	}
	
	private HashMap<Integer,Entity> entitymap;
	public HashMap<Integer,Entity> entityMap() {
		if(entitymap == null){
			entitymap = new HashMap<Integer,Entity>();
		}
		return entitymap;
	}
	
	private HashMap<Integer, UIItem> uiItemMap;
	public HashMap<Integer, UIItem> uiItemMap() {
		if(uiItemMap == null){
			uiItemMap = new HashMap<Integer, UIItem>();
		}
		return uiItemMap;
	}
	
	private ActivePlayer activePlayer;
	public void setActivePlayer(ActivePlayer p) {
		this.activePlayer = p;
	}
	public ActivePlayer activePlayer() {
		return activePlayer;
	}
	
	private int activeMenu = Integer.MAX_VALUE;
	public void setActiveMenu(int activeMenu) {
		this.activeMenu = activeMenu;
	}
	public int getActiveMenu() {
		return activeMenu;
	}
	
	private Mine mine;
	public Mine mine() {
		if(mine == null){
			mine = new Mine();
		}
		return mine;
	}
	private int money = 0;
	public void setMoney(int _money) {
		money = _money;
	}
	public int money() {
		return money;
	}


}