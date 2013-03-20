package gameView;

import gameController.GameControllerThread;
import gameData.GameData;
import gameData.MainMenuButtonListener;
import gameData.MyOwnFocusTraversalPolicy;
import gameData.TransparentButton;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import recources.GameWindowWorker;
import recources.SingletonWorker;

import network.NetworkHandlerThread;
import network.ServerConnection;

public class GameWindow extends JFrame {
	private static final long serialVersionUID = 8144111541455084091L;
	public final Font FONT = new Font(Font.MONOSPACED, Font.BOLD, 40);
	public static int width;
	public static int height;

	public JPanel activeMainPanel;
	public GameWindow gw;
	public static GameData gameData;
	public JButton start, video, registerset, exit;
	public ActionListener buttonListener = new MainMenuButtonListener();
	public JTextField loginfield;
	public JPasswordField passwordfield;
	public TransparentButton login;
	public TransparentButton usernamecheck;
	public JTextField emailfield;
	public TransparentButton emailcheck;
	public JTextField usernamefield;
	public JTextField passwortfield;
	public TransparentButton register;
	public MyOwnFocusTraversalPolicy registerPolicy;
	public MyOwnFocusTraversalPolicy loginPolicy;
	public TransparentButton loginfeedback;
	public TransparentButton registerfeedback;
	public GameWindow() {
		super(SingletonWorker.gameProperties().gameTitle());
		SingletonWorker.setGameWindow(this);
		gw = this; //TODO becoming obsolete!
		GameWindow.gameData = SingletonWorker.gameData(); //TODO becoming obsolete!
		GameWindowWorker.initGui();
		GameWindowWorker.initMenu();
		validate();
		update(getGraphics());
		pack();
	}



	public void initGame(ServerConnection sc) {
		GameWindow gw = SingletonWorker.gameWindow();
		gw.removeAll();
		SingletonWorker.gameData().setGameState(GameData.INGAME);
		SingletonWorker.gameData().startNewSession(); //TODO becoming obsolete!
		gw.setIgnoreRepaint(true);
		GamePanel gp = new GamePanel(gameData);
		gw.activeMainPanel = gp;
		gw.activeMainPanel.setBounds(0, 0, width, height);
		gw.activeMainPanel.setLayout(null);
		gw.activeMainPanel.setVisible(true);
		gw.add(activeMainPanel);
		gw.validate();
		gw.update(gw.getGraphics());
		gw.pack();
		gw.createBufferStrategy(2);
		SingletonWorker.gameData().setBufferstrategy(gw.getBufferStrategy());

		SingletonWorker.setGameControllerThread(new GameControllerThread(SingletonWorker.gameData().getGameSessionData()));
		SingletonWorker.gameData().setGameControllerThread(SingletonWorker.gameControllerThread()); //TODO becoming obsolete!
		Thread t = new Thread(SingletonWorker.gameControllerThread());
		t.start();
		String pw = new String(gw.passwordfield.getPassword());
		gw.passwordfield.setText("");
		SingletonWorker.setNetworkHandlerThread(new NetworkHandlerThread(gameData.getGameSessionData(),gw.loginfield.getText(),pw,sc));
		getGameData().setNetworkHandlerThread(SingletonWorker.networkHandlerThread()); //TODO becoming obsolete!
		Thread tn = new Thread(gameData.getNetworkHandlerThread());
		tn.start();

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(SingletonWorker.gameControllerThread());
		gw.addMouseListener(SingletonWorker.gameControllerThread());
		gw.addMouseMotionListener(SingletonWorker.gameControllerThread());

	}

	public static int getWindowWidth() {
		return width; //TODO becoming obsolete!
	}

	public static int getWindowHeight() {
		return height; //TODO becoming obsolete!
	}

	public static GameData getGameData() {
		return gameData;
	}

	public static void setGameData(GameData gd) {
		GameWindow.gameData = gd;
	}

	public void setCursor(String src){
		Cursor c = getToolkit()
				.createCustomCursor(
						new ImageIcon(SingletonWorker.gameProperties().gamePath()
								+ src).getImage(),
								new Point(0, 0), "Cursor");
		super.setCursor(c);
	}
	
}