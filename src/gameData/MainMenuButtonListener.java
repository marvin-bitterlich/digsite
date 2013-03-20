package gameData;

import gameView.GameWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import network.ServerConnection;
import recources.GameWindowWorker;
import recources.SingletonWorker;

public class MainMenuButtonListener implements ActionListener {
	private ServerConnection sc;
	private boolean registering = false;

	@Override
	public void actionPerformed(ActionEvent ae) {
		JButton l = (JButton) ae.getSource();
		GameWindow gw = SingletonWorker.gameWindow();
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
				gw.initGame(sc);
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
