package gameView;

import gameController.GameControllerThread;
import gameData.GameData;
import gameData.MainMenuButtonListener;
import gameData.TransparentButton;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.Vector;

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
		SingletonWorker.setGameData(new GameData());
		GameWindow.gameData = SingletonWorker.gameData(); //TODO becoming obsolete!
		GameWindowWorker.initGui();
		GameWindowWorker.initMenu();
		validate();
		update(getGraphics());
		pack();
	}



	public void initGame(ServerConnection sc) {

		gw.removeAll();
		GameWindow.gameData.setGameState(GameData.INGAME);
		GameWindow.gameData.startNewSession();
		setIgnoreRepaint(true);
		GamePanel gp = new GamePanel(gameData);
		gw.activeMainPanel = gp;
		gw.activeMainPanel.setBounds(0, 0, width, height);
		gw.activeMainPanel.setLayout(null);
		gw.activeMainPanel.setVisible(true);
		gw.add(activeMainPanel);
		gw.validate();
		gw.update(getGraphics());
		gw.pack();
		createBufferStrategy(2);
		GameData.bufferstrategy = getBufferStrategy();

		gameData.setGameControllerThread(new GameControllerThread(gameData
				.getGameSessionData()));
		Thread t = new Thread(gameData.getGameControllerThread());
		t.start();
		String pw = new String(gw.passwordfield.getPassword());
		gw.passwordfield.setText("");
		getGameData().setNetworkHandlerThread(new NetworkHandlerThread(gameData
				.getGameSessionData(),gw.loginfield.getText(),pw,sc));
		Thread tn = new Thread(gameData.getNetworkHandlerThread());
		tn.start();

		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		.addKeyEventDispatcher(gameData.getGameControllerThread());
		gw.addMouseListener(gameData.getGameControllerThread());
		gw.addMouseMotionListener(gameData.getGameControllerThread());

	}

	public static int getWindowWidth() {
		return width; //TODO becoming obsolete!
	}

	public static int getWindowHeight() {
		return height; //TODO becoming obsolete!
	}

	

	public static class MyOwnFocusTraversalPolicy extends FocusTraversalPolicy{
		Vector<Component> order;

		public MyOwnFocusTraversalPolicy(Vector<Component> order) {
			this.order = new Vector<Component>(order.size());
			this.order.addAll(order);
		}
		public Component getComponentAfter(Container focusCycleRoot,
				Component aComponent)
		{
			int idx = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(idx);
		}

		public Component getComponentBefore(Container focusCycleRoot,
				Component aComponent)
		{
			int idx = order.indexOf(aComponent) - 1;
			if (idx < 0) {
				idx = order.size() - 1;
			}
			return order.get(idx);
		}

		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}

		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}
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