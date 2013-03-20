package gameData;

import java.awt.image.BufferStrategy;

import network.NetworkHandlerThread;
import gameController.GameControllerThread;
import gameView.GamePanel;

public class GameData {
	public static boolean gameLoaded = false;

	private GameSessionData gsd;
	private GameControllerThread gct;
	private NetworkHandlerThread nht;
	private GamePanel gp;


	public void startNewSession() {
		this.gsd = new GameSessionData(); //TODO becoming obsolete!
	}

	public GameSessionData getGameSessionData() {
		return gsd;
	}

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

}