package recources;

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

}
