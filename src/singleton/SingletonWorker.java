package singleton;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import network.NetworkHandlerThread;
import gameController.GameControllerThread;
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
	public static GameProperties gameProperties() {
		if(getSingletonWorker().gameProperties == null){
			getSingletonWorker().gameProperties = new GameProperties();
		}
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

	private Logger logger;
	public static Logger logger(){
		if(getSingletonWorker().logger == null){
			try {
				Logger logger = Logger.getLogger("Digsite");
				File log = new File(SingletonWorker.gameProperties().gamePath() + File.separator + "logs" + File.separator);
				log.mkdirs();
				Handler ch = new ConsoleHandler();
				Handler handler = new FileHandler( log.getAbsolutePath() + File.separator + "log_" + 
				new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis())) + ".txt" );
				Formatter f = new Formatter() {


					private SimpleDateFormat dateFormat;
					String line = System.getProperty("line.separator");

					@Override
					public String format(LogRecord record) {
						StringBuffer buf = new StringBuffer(180);

						if (dateFormat == null)
							dateFormat = new SimpleDateFormat("dd|MM|yyyy HH:mm:ss.SSS");

						buf.append(dateFormat.format(new Date(record.getMillis())));
						buf.append(" [");
						buf.append(record.getLevel());
						buf.append("]: ");
						buf.append(formatMessage(record));
						Throwable throwable = record.getThrown();
						if (throwable != null)
						{
							StringWriter sink = new StringWriter();
							throwable.printStackTrace(new PrintWriter(sink, true));
							buf.append(sink.toString());
						}
						buf.append(line);
						return buf.toString();
					}
				};
				ch.setFormatter(f);
				handler.setFormatter(f);
				logger.addHandler(handler);
				logger.addHandler(ch);
				logger.setUseParentHandlers(false);
				logger.setLevel(Level.ALL);
				getSingletonWorker().logger = logger;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getSingletonWorker().logger;
	}


}
