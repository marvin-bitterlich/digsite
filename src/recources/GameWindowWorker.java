package recources;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import network.NetworkHandlerThread;
import network.ServerConnection;

import utilities.ImageUtil;

import gameController.GameControllerThread;
import gameData.GameData;
import gameData.GameProperties;
import gameData.ImageCache;
import gameData.MyOwnFocusTraversalPolicy;
import gameData.TransparentButton;
import gameView.GamePanel;
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

	public static void initMenu(){
		GameWindow gw = SingletonWorker.gameWindow();
		int width = SingletonWorker.gameData().width();
		int height = SingletonWorker.gameData().height();
		gw.activeMainPanel = new JPanel();
		gw.activeMainPanel.setBounds(0, 0, width, height);
		gw.activeMainPanel.setLayout(null);

		BufferedImage menu = ImageCache.getRecource(SingletonWorker.gameProperties().backgroundPath());
		ImageIcon menuBackground = new ImageIcon(ImageUtil.resizeImage(menu, width, height));
		JLabel menuPicture = new JLabel(menuBackground);
		menuPicture.setBounds(0, 0, width, height);

		Font f = SingletonWorker.gameProperties().gameFont();


		gw.start = new TransparentButton(GameProperties.START);
		gw.start.setBounds(50, width / 25 * 1, 300, 50);
		gw.start.setFont(f);
		gw.start.setForeground(Color.WHITE);
		gw.start.addActionListener(gw.buttonListener);


		gw.loginfield = new JTextField("Marvin");
		gw.loginfield.setBounds(width/2-150,height /2-250, 300, 50);
		gw.loginfield.setFont(f);
		gw.loginfield.setForeground(Color.BLACK);
		gw.loginfield.setVisible(false);
		gw.loginfield.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SingletonWorker.gameWindow().login.doClick();
			}
		});
		gw.loginfield.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {}

			@Override
			public void focusGained(FocusEvent arg0) {
				SingletonWorker.gameWindow().loginfield.selectAll();
			}
		});

		gw.usernamefield = new JTextField(GameProperties.USERNAME);
		gw.usernamefield.setBounds(width/2-150,height /2-250, 300, 50);
		gw.usernamefield.setFont(f);
		gw.usernamefield.setForeground(Color.BLACK);
		gw.usernamefield.setVisible(false);
		gw.usernamefield.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SingletonWorker.gameWindow().register.doClick();
			}
		});
		gw.usernamefield.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {}

			@Override
			public void focusGained(FocusEvent arg0) {
				SingletonWorker.gameWindow().usernamefield.selectAll();
			}
		});

		gw.usernamecheck = new TransparentButton(GameProperties.CHECKUSERNAME);
		gw.usernamecheck.setBounds(width/2+100,height /2-250, 300, 50);
		gw.usernamecheck.setFont(f);
		gw.usernamecheck.setForeground(Color.WHITE);
		gw.usernamecheck.addActionListener(gw.buttonListener);
		gw.usernamecheck.setVisible(false);

		gw.emailfield = new JTextField(GameProperties.EMAIL);
		gw.emailfield.setBounds(width/2-150,height /2-150, 300, 50);
		gw.emailfield.setFont(f.deriveFont((float) 22));
		gw.emailfield.setForeground(Color.BLACK);
		gw.emailfield.setVisible(false);
		gw.emailfield.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SingletonWorker.gameWindow().register.doClick();
			}
		});
		gw.emailfield.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {}

			@Override
			public void focusGained(FocusEvent arg0) {
				SingletonWorker.gameWindow().emailfield.selectAll();
			}
		});

		gw.emailcheck = new TransparentButton(GameProperties.CHECKEMAIL);
		gw.emailcheck.setBounds(width/2+100,height /2-150, 300, 50);
		gw.emailcheck.setFont(f);
		gw.emailcheck.setForeground(Color.WHITE);
		gw.emailcheck.addActionListener(gw.buttonListener);
		gw.emailcheck.setVisible(false);

		gw.passwortfield = new JTextField(GameProperties.PASSWORT);
		gw.passwortfield.setBounds(width/2-150,height /2-50, 300, 50);
		gw.passwortfield.setFont(f);
		gw.passwortfield.setForeground(Color.BLACK);
		gw.passwortfield.setVisible(false);
		gw.passwortfield.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SingletonWorker.gameWindow().register.doClick();
			}
		});
		gw.passwortfield.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {}

			@Override
			public void focusGained(FocusEvent arg0) {
				SingletonWorker.gameWindow().passwortfield.selectAll();
			}
		});

		gw.passwordfield = new JPasswordField(50);
		gw.passwordfield.setBounds(width/2-150,height /2-150, 300, 50);
		gw.passwordfield.setVisible(false);
		gw.passwordfield.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SingletonWorker.gameWindow().login.doClick();
			}
		});
		gw.passwordfield.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {}

			@Override
			public void focusGained(FocusEvent arg0) {
				SingletonWorker.gameWindow().passwordfield.selectAll();
			}
		});

		gw.register = new TransparentButton(GameProperties.REGISTER);
		gw.register.setBounds(width/2-150,height /2+50, 300, 50);
		gw.register.setFont(f);
		gw.register.addActionListener(gw.buttonListener);
		gw.register.setForeground(Color.WHITE);
		gw.register.setVisible(false);

		gw.registerfeedback = new JLabel(GameProperties.REGISTER);
		gw.registerfeedback.setBounds(width/2-150,height /2+100, 300, 50);
		gw.registerfeedback.setFont(f);
		gw.registerfeedback.setForeground(Color.WHITE);
		gw.registerfeedback.setVisible(false);

		gw.login = new TransparentButton(GameProperties.LOGIN);
		gw.login.setBounds(width/2-150,height /2-50, 300, 50);
		gw.login.setFont(f);
		gw.login.addActionListener(gw.buttonListener);
		gw.login.setForeground(Color.BLACK);
		gw.login.setVisible(false);

		gw.loginfeedback = new JLabel();
		gw.loginfeedback.setBounds(width/2-150,height/2, 300, 50);
		gw.loginfeedback.setFont(f);
		gw.loginfeedback.setForeground(Color.WHITE);
		gw.loginfeedback.setHorizontalAlignment( SwingConstants.CENTER );
		gw.loginfeedback.setVisible(false);

		gw.registerset = new TransparentButton(GameProperties.REGISTERSET);
		gw.registerset.setBounds(50, width / 25 * 2, 300, 50);
		gw.registerset.setFont(f);
		gw.registerset.addActionListener(gw.buttonListener);
		gw.registerset.setForeground(Color.WHITE);

		gw.video = new TransparentButton(GameProperties.VIDEO);
		gw.video.setBounds(50, width / 25 * 3, 300, 50);
		gw.video.setFont(f);
		gw.video.addActionListener(gw.buttonListener);
		gw.video.setForeground(Color.WHITE);

		gw.exit = new TransparentButton(GameProperties.EXIT);
		gw.exit.setBounds(50, width / 25 * 4, 300, 50);
		gw.exit.setFont(f);
		gw.exit.addActionListener(gw.buttonListener);
		gw.exit.setForeground(Color.WHITE);


		Vector<Component> registerorder = new Vector<Component>(4);
		registerorder.add(gw.usernamefield);
		registerorder.add(gw.emailfield);
		registerorder.add(gw.passwortfield);
//		registerorder.add(gw.register);
		gw.registerPolicy = new MyOwnFocusTraversalPolicy(registerorder);

		Vector<Component> loginorder = new Vector<Component>(3);
		loginorder.add(gw.loginfield);
		loginorder.add(gw.passwordfield);
//		loginorder.add(gw.login);
		gw.loginPolicy = new MyOwnFocusTraversalPolicy(loginorder);


		gw.activeMainPanel.add(gw.start);
		gw.activeMainPanel.add(gw.loginfield);
		gw.activeMainPanel.add(gw.passwordfield);
		gw.activeMainPanel.add(gw.login);
		gw.activeMainPanel.add(gw.usernamefield);
		gw.activeMainPanel.add(gw.usernamecheck);
		gw.activeMainPanel.add(gw.emailfield);
		gw.activeMainPanel.add(gw.emailcheck);
		gw.activeMainPanel.add(gw.passwortfield);
		gw.activeMainPanel.add(gw.register);
		gw.activeMainPanel.add(gw.registerset);
		gw.activeMainPanel.add(gw.video);
		gw.activeMainPanel.add(gw.exit);
		gw.activeMainPanel.add(gw.loginfeedback);
		gw.activeMainPanel.add(gw.registerfeedback);

		gw.activeMainPanel.add(menuPicture);

		gw.activeMainPanel.setVisible(true);
		gw.add(gw.activeMainPanel);
	}

	public static void initGame(ServerConnection sc) {
		GameWindow gw = SingletonWorker.gameWindow();
		gw.removeAll();
		SingletonWorker.gameData().setGameState(GameData.INGAME);
		gw.setIgnoreRepaint(true);
		GamePanel gp = new GamePanel(SingletonWorker.gameData());
		gw.activeMainPanel = gp;
		gw.activeMainPanel.setBounds(0, 0, SingletonWorker.gameData().width(), SingletonWorker.gameData().height());
		gw.activeMainPanel.setLayout(null);
		gw.activeMainPanel.setVisible(true);
		gw.add(gw.activeMainPanel);
		gw.validate();
		gw.update(gw.getGraphics());
		gw.pack();
		gw.createBufferStrategy(2);
		SingletonWorker.gameData().setBufferstrategy(gw.getBufferStrategy());

		SingletonWorker.setGameControllerThread(new GameControllerThread());
		SingletonWorker.gameData().setGameControllerThread(SingletonWorker.gameControllerThread()); //TODO becoming obsolete!
		Thread t = new Thread(SingletonWorker.gameControllerThread());
		t.start();
		String pw = new String(gw.passwordfield.getPassword());
		gw.passwordfield.setText("");
		SingletonWorker.setNetworkHandlerThread(new NetworkHandlerThread(gw.loginfield.getText(),pw,sc));
		SingletonWorker.gameData().setNetworkHandlerThread(SingletonWorker.networkHandlerThread()); //TODO becoming obsolete!
		Thread tn = new Thread(SingletonWorker.networkHandlerThread());
		tn.start();

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(SingletonWorker.gameControllerThread());
		gw.addMouseListener(SingletonWorker.gameControllerThread());
		gw.addMouseMotionListener(SingletonWorker.gameControllerThread());

	}
}
