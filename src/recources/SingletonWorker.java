package recources;

import network.NetworkHandlerThread;
import gameController.GameControllerThread;
import gameData.GameData;
import gameData.GameProperties;
import gameView.GameWindow;

public class SingletonWorker {
	
	private static SingletonWorker singletonWorker;
	private static SingletonWorker getSingletonWorker(){
		if(singletonWorker == null){
			singletonWorker = new SingletonWorker();
		}
		return singletonWorker;
	}

	/*
	 * GameWindow is the overall window of the game.
	 * Contains the Login-Screen and the GameView.
	 * Starting the Game works with creating an instance of it!
	 * */
	private GameWindow gameWindow;
	public static void setGameWindow(GameWindow _gameWindow) {
		getSingletonWorker().gameWindow = _gameWindow; 
	}
	public static GameWindow gameWindow() {
		return getSingletonWorker().gameWindow; 
	}
	
	/*
	 * Contains all Data of the game
	 * not belonging anywhere else.
	 * */
	private GameData gameData;
	public static GameData gameData() {
		if(getSingletonWorker().gameData == null){
			getSingletonWorker().gameData = new GameData();
		}
		return getSingletonWorker().gameData;
	}
	
	/*
	 * Contains all data that is final.
	 * */
	private GameProperties gameProperties;
	public static void setGameProperties(GameProperties _gameProperties) {
		getSingletonWorker().gameProperties = _gameProperties;
	}
	public static GameProperties gameProperties() {
		return getSingletonWorker().gameProperties;
	}
	
	/*
	 * The GameControllerThread is the heartbeat-thread of the game.
	 * It contains all calculation and drawing.
	 * */
	private GameControllerThread gameControllerThread;
	public static void setGameControllerThread(GameControllerThread _gameControllerThread) {
		getSingletonWorker().gameControllerThread = _gameControllerThread; 
	}
	public static GameControllerThread gameControllerThread() {
		return getSingletonWorker().gameControllerThread; 
	}
	
	/*
	 * The GameControllerThread is the heartbeat-thread of the game.
	 * It contains all calculation and drawing.
	 * */
	private NetworkHandlerThread networkHandlerThread;
	public static void setNetworkHandlerThread(NetworkHandlerThread _networkHandlerThread) {
		getSingletonWorker().networkHandlerThread = _networkHandlerThread; 
	}
	public static NetworkHandlerThread networkHandlerThread() {
		return getSingletonWorker().networkHandlerThread; 
	}
	
}
