package gameView;

import gameController.GameControllerThread;
import gameData.GameData;
import gameData.GameProperties;
import gameData.ImageCache;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import recources.GameWindowWorker;
import recources.SingletonWorker;

import network.NetworkHandlerThread;
import network.ServerConnection;

import utilities.ImageUtil;

public class GameWindow extends JFrame {
	private static final long serialVersionUID = 8144111541455084091L;
	private static final Font FONT = new Font(Font.MONOSPACED, Font.BOLD, 40);
	public static int width;
	public static int height;

	private JPanel activeMainPanel;
	private GameWindow gw;
	private static GameData gameData;
	private JButton start, video, registerset, exit;
	private ActionListener buttonListener = new MainMenuButtonListener();
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
		gw.initMenu();
		gw.validate();
		gw.update(gw.getGraphics());
		gw.pack();
	}

	private void initMenu(){
		GameWindow gw = SingletonWorker.gameWindow();
		gw.activeMainPanel = new JPanel();
		gw.activeMainPanel.setBounds(0, 0, SingletonWorker.gameData().width(), SingletonWorker.gameData().height());
		gw.activeMainPanel.setLayout(null);

		BufferedImage menu = ImageCache.getRecource(SingletonWorker.gameProperties().backgroundPath());
		ImageIcon menuBackground = new ImageIcon(ImageUtil.resizeImage(menu,
				width, height));
		JLabel menuPicture = new JLabel(menuBackground);
		menuPicture.setBounds(0, 0, width, height);

		Font f;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT,
					new File(GameProperties.getGamePath() + "/rec/"
							+ GameProperties.FONT_NAME));
		} catch (FontFormatException e){
			System.out.println(e.getLocalizedMessage());
			f = FONT;
		}catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			f = FONT;
		}
		f = f.deriveFont(60f);

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

		gw.passwordfield = new JPasswordField(50);
		gw.passwordfield.setBounds(width/2-150,height /2-150, 300, 50);
		gw.passwordfield.setVisible(false);
		gw.passwordfield.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SingletonWorker.gameWindow().login.doClick();
			}
		});

		gw.register = new TransparentButton(GameProperties.REGISTER);
		gw.register.setBounds(width/2-150,height /2+50, 300, 50);
		gw.register.setFont(f);
		gw.register.addActionListener(gw.buttonListener);
		gw.register.setForeground(Color.WHITE);
		gw.register.setVisible(false);

		gw.registerfeedback = new TransparentButton(GameProperties.REGISTER);
		gw.registerfeedback.setBounds(width/2-150,height /2+100, 300, 50);
		gw.registerfeedback.setFont(f);
		gw.registerfeedback.addActionListener(gw.buttonListener);
		gw.registerfeedback.setForeground(Color.WHITE);
		gw.registerfeedback.setVisible(false);

		gw.login = new TransparentButton(GameProperties.LOGIN);
		gw.login.setBounds(width/2-150,height /2-50, 300, 50);
		gw.login.setFont(f);
		gw.login.addActionListener(gw.buttonListener);
		gw.login.setForeground(Color.BLACK);
		gw.login.setVisible(false);

		gw.loginfeedback = new TransparentButton(GameProperties.LOGIN);
		gw.loginfeedback.setBounds(width/2-150,height /2, 300, 50);
		gw.loginfeedback.setFont(f);
		gw.loginfeedback.addActionListener(gw.buttonListener);
		gw.loginfeedback.setForeground(Color.BLACK);
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
		registerorder.add(gw.register);
		registerPolicy = new MyOwnFocusTraversalPolicy(registerorder);

		Vector<Component> loginorder = new Vector<Component>(3);
		loginorder.add(gw.loginfield);
		loginorder.add(gw.passwordfield);
		loginorder.add(gw.login);
		loginPolicy = new MyOwnFocusTraversalPolicy(loginorder);


		gw.activeMainPanel.add(start);
		gw.activeMainPanel.add(loginfield);
		gw.activeMainPanel.add(passwordfield);
		gw.activeMainPanel.add(login);
		gw.activeMainPanel.add(usernamefield);
		gw.activeMainPanel.add(usernamecheck);
		gw.activeMainPanel.add(emailfield);
		gw.activeMainPanel.add(emailcheck);
		gw.activeMainPanel.add(passwortfield);
		gw.activeMainPanel.add(register);
		gw.activeMainPanel.add(registerset);
		gw.activeMainPanel.add(video);
		gw.activeMainPanel.add(exit);

		gw.activeMainPanel.add(menuPicture);

		gw.activeMainPanel.setVisible(true);
		gw.add(activeMainPanel);
	}

	private void initGame(ServerConnection sc) {

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
		return width;
	}

	public static int getWindowHeight() {
		return height;
	}

	public class MainMenuButtonListener implements ActionListener {
		private ServerConnection sc;
		private boolean registering = false;

		@Override
		public void actionPerformed(ActionEvent ae) {
			JButton l = (JButton) ae.getSource();

			if (l.equals(gw.start)) {
				GameWindowWorker.doStartPressed();
			}

			if (l.equals(gw.registerset)) {
				GameWindowWorker.doRegistersetPressed();
			}

			if (l.equals(gw.login)) {
				gw.loginfeedback.setVisible(true);
				if(sc == null){
					sc = new ServerConnection();
					if(!sc.connectToServer()){
						gw.loginfeedback.setText("No connection to the server!");
						return;
					}
				}
				String pw = new String(gw.passwordfield.getPassword());
				gw.loginfeedback.setText("logging in, please wait...");

				if(sc.loginPlayer(gw.loginfield.getText(),pw)){
					gw.loginfeedback.setText("logged in!");
					gw.loginfeedback.setVisible(false);
					initGame(sc);
				}else{
					gw.loginfeedback.setText("Login failed!");
				}
			}

			if (l.equals(gw.register)) {
				gw.registerfeedback.setVisible(true);
				if(sc == null){
					sc = new ServerConnection();
					if(!sc.connectToServer()){
						gw.registerfeedback.setText("No connection to the server!");
						return;
					}
				}
				if(!registering){
					if(!sc.enableRegister()){
						gw.registerfeedback.setText("Register not allowed!");
						return;
					}
					registering = true;
				}
				if(sc.register(gw.usernamefield.getText(), gw.passwortfield.getText(), gw.emailfield.getText())){
					gw.register.setText("Erfolgreich!");
				}else{
					System.out.println("Failed");
				}
				sc = null;
			}

			if (l.equals(gw.usernamecheck)) {
				gw.registerfeedback.setVisible(true);
				if(sc == null){
					sc = new ServerConnection();
					if(!sc.connectToServer()){
						gw.registerfeedback.setText("No connection to the server!");
						return;
					}
				}
				if(!registering){
					if(!sc.enableRegister()){
						gw.registerfeedback.setText("Register not allowed!");
						return;
					}
					registering = true;
				}

				if(sc.checkUsername(gw.usernamefield.getText())){
					gw.usernamecheck.setText("Frei!");
				}else{
					gw.usernamecheck.setText("Belegt!");
				}

			}

			if (l.equals(gw.emailcheck)) {
				gw.registerfeedback.setVisible(true);
				if(sc == null){
					sc = new ServerConnection();
					if(!sc.connectToServer()){
						gw.registerfeedback.setText("No connection to the server!");
						return;
					}
				}
				if(!registering){
					if(!sc.enableRegister()){
						gw.registerfeedback.setText("Register not allowed!");
						return;
					}
					registering = true;
				}

				if(sc.checkEmail(gw.emailfield.getText())){
					gw.emailcheck.setText("Frei!");
				}else{
					gw.emailcheck.setText("Belegt!");
				}
			}


		}

	}

	public class TransparentButton extends JButton {
		private static final long serialVersionUID = 8225611310676742450L;

		public TransparentButton(String text) {
			super(text);
			setBorder(null);
			setBorderPainted(false);
			setContentAreaFilled(false);
			setOpaque(false);
		}
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