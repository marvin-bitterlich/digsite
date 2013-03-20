package gameView;

import gameData.MainMenuButtonListener;
import gameData.MyOwnFocusTraversalPolicy;
import gameData.TransparentButton;

import java.awt.Cursor;
import java.awt.Font;
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

public class GameWindow extends JFrame {
	private static final long serialVersionUID = 8144111541455084091L;
	public final Font FONT = new Font(Font.MONOSPACED, Font.BOLD, 40);
	public static int width;
	public static int height;

	public JPanel activeMainPanel;
	public GameWindow gw;
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
		GameWindowWorker.initGui();
		GameWindowWorker.initMenu();
		validate();
		update(getGraphics());
		pack();
	}

	public static int getWindowWidth() {
		return width; //TODO becoming obsolete!
	}

	public static int getWindowHeight() {
		return height; //TODO becoming obsolete!
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