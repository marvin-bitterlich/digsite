package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

/***********************************************************************************
 * @author:	Marvin Hofmann	Klasse: DQI10	*
 * Prog.Name: ServerConnection.java	Beschreibung	*
 * Version:	1.0	Datum: 23.11.2012	*
 * Compiler:	Oracle Java OS: Microsoft Windows 7	*
 * **********************************************************************************/
public class ServerConnection {

//	private final String GAMESERVER_URL = "digsite.de";
	private final String GAMESERVER_URL = "178.254.8.51";
	private final int GAMESERVER_PORT = 4444;
	private String username;
	public int userid;
	private Socket connection;
	private PrintWriter out;
	private BufferedReader in;
	private boolean connected = false;
	private String selected = "none";

	public ServerConnection(){

	}

	public void disconnectFromServer(){
		if(connected == true){
			log("Trenne Verbindung!", Level.INFO);
			if(out != null){
				out.close();
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					// supress
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (IOException e) {
					// supress
				}
			}
			connected = false;
			selected = "none";
		}
	}

	public boolean connectToServer(){
		if(connected == true){
			log("Du bist schon verbunden!", Level.WARNING);
			return false;
		}
		try {
			connection = new Socket(GAMESERVER_URL, GAMESERVER_PORT);
			log("Connection from Port " + connection.getLocalPort() 
					+ " to Port " + connection.getPort() 
					+ " is established!", Level.INFO);
			out = new PrintWriter(connection.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			connected = true;
			selected = "chose";
		} catch (IOException e) {
			log("Connection failed! " + e.getLocalizedMessage(), Level.WARNING);
		}
		return true;
	}

	//Blockiert den aufrufenden Thread!
	public boolean loginPlayer(String Username, String Password){
		if(connected == false){
			log("Du bist nicht verbunden!", Level.WARNING);
			return false;
		}
		if(selected == "login"){
			log("logincheck", Level.INFO);
			return checkCredentials(Username, Password);
		}
		if(selected != "chose"){
			log("Server erwartet " + selected + ", reconnecting!", Level.INFO);
			disconnectFromServer();
			connectToServer();
		}
		selected = "login";
		this.username = Username;
		sendLine("login");
		String response = getLine();
		if(response.equals("ready")){
			log("Server is ready for login, sending credentials!",Level.INFO);
			checkCredentials(Username, Password);
		}else{
			log("Login got denied! " + response, Level.SEVERE);
			return false;
		}
		return true;
	}

	private boolean checkCredentials(String Username, String Password){
		log("login2", Level.INFO);
		sendLine(Username + " " + Password);
		log("login3", Level.INFO);
		String response = getLine();
		log("login4 >> " + response, Level.INFO);
		if(response.equals("You logged in successfull")){
			log("Server accepted credentials, proceding!",Level.INFO);
			userid = Integer.parseInt(getLine());
			return true;
		}else{
			log("Credentials were denied! " + response, Level.INFO);
			return false;
		}
	}

	public boolean enableRegister(){
		if(connected == false){
			log("Du bist nicht verbunden!", Level.WARNING);
			return false;
		}
		if(selected != "chose"){
			log("Server erwartet " + selected + ", reconnecting!", Level.INFO);
			disconnectFromServer();
			connectToServer();
		}
		selected = "register";
		sendLine("register");
		String response = getLine();
		if(response.equals("ready")){
			log("Server is ready for registration!",Level.INFO);
			return true;
		}else{
			log("Registration got denied! " + response, Level.SEVERE);
			return false;
		}
	} 

	public boolean checkUsername(String Username){
		if(connected == false){
			log("Du bist nicht verbunden!", Level.WARNING);
			return false;
		}
		if(selected != "register"){
			log("Server erwartet " + selected + ", stopping!", Level.INFO);
			return false;
		}
		sendLine(Username);
		String response = getLine();
		if(response.equals("true")){
			log("Server accepted Username, you can proceed!",Level.INFO);
			return true;
		}else{
			log("Username already taken! " + response, Level.INFO);
			return false;
		}
	}

	public boolean checkEmail(String Email){
		if(connected == false){
			log("Du bist nicht verbunden!", Level.WARNING);
			return false;
		}
		if(selected != "register"){
			log("Server erwartet " + selected + ", stopping!", Level.INFO);
			return false;
		}
		sendLine("email " + Email);
		String response = getLine();
		if(response.equals("true")){
			log("Server accepted Email, you can proceed!",Level.INFO);
			return true;
		}else{
			log("Email already taken! " + response, Level.INFO);
			return false;
		}
	}

	public boolean register(String Username, String Password, String Email){
		if(connected == false){
			log("Du bist nicht verbunden!", Level.WARNING);
			return false;
		}
		if(selected != "register"){
			log("Server erwartet " + selected + ", stopping!", Level.INFO);
			return false;
		}
		if(!checkUsername(Username) || !checkEmail(Email)){
			return false;
		}
		sendLine(Username + " " + Password + " " + Email);
		String response = getLine();
		if(response.equals("Register done!")){
			log("Server accepted Register, you can login!",Level.INFO);
			return true;
		}else{
			log("Register failed! " + response, Level.INFO);
			return false;
		}
	}

	private void log(String message, Level loglevel){
		System.out.println("[" + loglevel.toString() + "] " + message);
	}

	void sendLine(String line){
		if(out != null){
			out.println(line);
		}
	}

	public String getLine(){
		if(in != null){
			try {
				return in.readLine();
			} catch (IOException e) {
				log("Reading failed! " + e.getLocalizedMessage(), Level.WARNING);
			}
		}
		return "";
	}

	public String getUsername() {
		return username;
	}

}
