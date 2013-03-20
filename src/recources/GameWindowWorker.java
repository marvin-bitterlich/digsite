package recources;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import gameData.ImageCache;
import gameView.GameWindow;

public class GameWindowWorker {

	public static void doStartPressed() {
		GameWindow gw = SingletonWorker.gameWindow();
		gw.loginfield.setVisible(true);
		gw.login.setVisible(true);
		gw.passwordfield.setVisible(true);
		gw.usernamefield.setVisible(false);
		gw.usernamecheck.setVisible(false);
		gw.emailfield.setVisible(false);
		gw.emailcheck.setVisible(false);
		gw.passwortfield.setVisible(false);
		gw.register.setVisible(false);
		gw.registerfeedback.setVisible(false);
		gw.loginfeedback.setVisible(false);
		gw.setFocusTraversalPolicy(gw.loginPolicy);
		gw.loginfield.requestFocus();
		gw.loginfield.setSelectionStart(0);
		gw.loginfield.setSelectionEnd(gw.loginfield.getText().length());
	}
	public static void doRegistersetPressed() {
		GameWindow gw = SingletonWorker.gameWindow();
		gw.loginfield.setVisible(false);
		gw.login.setVisible(false);
		gw.passwordfield.setVisible(false);
		gw.usernamefield.setVisible(true);
		gw.usernamecheck.setVisible(true);
		gw.emailfield.setVisible(true);
		gw.emailcheck.setVisible(true);
		gw.passwortfield.setVisible(true);
		gw.register.setVisible(true);
		gw.registerfeedback.setVisible(false);
		gw.loginfeedback.setVisible(false);
		gw.setFocusTraversalPolicy(gw.registerPolicy);
		gw.usernamefield.requestFocus();
	}
	
	public static void initGui() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		SingletonWorker.gameData().setWidth((int) screen.getWidth());
		SingletonWorker.gameData().setHeight((int) screen.getHeight());
		GameWindow.width = SingletonWorker.gameData().width(); //TODO becoming obsolete!
		GameWindow.height = SingletonWorker.gameData().height(); //TODO becoming obsolete!

		BufferedImage image = ImageCache.getRecource(SingletonWorker.gameProperties().logoPath());
		GameWindow gw = SingletonWorker.gameWindow();
		gw.setIconImage(image);


		gw.setMinimumSize(new Dimension(SingletonWorker.gameData().width(), SingletonWorker.gameData().height()));
		gw.setExtendedState(Frame.MAXIMIZED_BOTH);
		gw.setLocationRelativeTo(null);
		gw.setLayout(null);
		gw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gw.setUndecorated(true);
		gw.setCursor(SingletonWorker.gameProperties().cursorPath());

		gw.setVisible(true);

	}
	

}
