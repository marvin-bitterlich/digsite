package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

import singleton.SingletonWorker;

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
			SingletonWorker.logger().info("Trenne Verbindung!");
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
			SingletonWorker.logger().warning("Du bist schon verbunden!");
			return false;
		}
		try {
			connection = new Socket(GAMESERVER_URL, GAMESERVER_PORT);
			SingletonWorker.logger().info("Connection from Port " + connection.getLocalPort() 
					+ " to Port " + connection.getPort() 
					+ " is established!");
			out = new PrintWriter(connection.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			connected = true;
			selected = "chose";
		} catch (IOException e) {
			SingletonWorker.logger().warning("Connection failed! " + e.getLocalizedMessage());
		}
		return true;
	}

	//Blockiert den aufrufenden Thread!
	public boolean loginPlayer(String Username, String Password){
		if(connected == false){
			SingletonWorker.logger().warning("Du bist nicht verbunden!");
			return false;
		}
		if(selected != "chose"){
			SingletonWorker.logger().info("Server erwartet " + selected + ", reconnecting!");
			disconnectFromServer();
			connectToServer();
		}
		selected = "login";
		this.username = Username;
		sendLine("login");
		String response = getLine();
		if(response.equals("ready")){
			SingletonWorker.logger().info("Server is ready for login, sending credentials!");
			return checkCredentials(Username, Password);
		}else{
			SingletonWorker.logger().severe("Login got denied! " + response);
			return false;
		}
	}

	private boolean checkCredentials(String Username, String Password){
		sendLine(Username + " " + Password);
		String response = getLine();
		if(response.equals("You logged in successfull")){
			SingletonWorker.logger().info("Server accepted credentials, proceding!");
			userid = Integer.parseInt(getLine());
			return true;
		}else{
			SingletonWorker.logger().info("Credentials were denied! " + response);
			return false;
		}
	}

	public boolean enableRegister(){
		if(connected == false){
			SingletonWorker.logger().warning("Du bist nicht verbunden!");
			return false;
		}
		if(selected != "chose"){
			SingletonWorker.logger().info("Server erwartet " + selected + ", reconnecting!");
			disconnectFromServer();
			connectToServer();
		}
		selected = "register";
		sendLine("register");
		String response = getLine();
		if(response.equals("ready")){
			SingletonWorker.logger().info("Server is ready for registration!");
			return true;
		}else{
			SingletonWorker.logger().severe("Registration got denied! " + response);
			return false;
		}
	} 

	public boolean checkUsername(String Username){
		if(connected == false){
			SingletonWorker.logger().warning("Du bist nicht verbunden!");
			return false;
		}
		if(selected != "register"){
			SingletonWorker.logger().info("Server erwartet " + selected + ", stopping!");
			return false;
		}
		sendLine(Username);
		String response = getLine();
		if(response.equals("true")){
			SingletonWorker.logger().info("Server accepted Username, you can proceed!");
			return true;
		}else{
			SingletonWorker.logger().info("Username already taken! " + response);
			return false;
		}
	}

	public boolean checkEmail(String Email){
		if(connected == false){
			SingletonWorker.logger().warning("Du bist nicht verbunden!");
			return false;
		}
		if(selected != "register"){
			SingletonWorker.logger().info("Server erwartet " + selected + ", stopping!");
			return false;
		}
		sendLine("email " + Email);
		String response = getLine();
		if(response.equals("true")){
			SingletonWorker.logger().info("Server accepted Email, you can proceed!");
			return true;
		}else{
			SingletonWorker.logger().info("Email already taken! " + response);
			return false;
		}
	}

	public boolean register(String Username, String Password, String Email){
		if(connected == false){
			SingletonWorker.logger().warning("Du bist nicht verbunden!");
			return false;
		}
		if(selected != "register"){
			SingletonWorker.logger().info("Server erwartet " + selected + ", stopping!");
			return false;
		}
		if(!checkUsername(Username) || !checkEmail(Email)){
			return false;
		}
		sendLine(Username + " " + Password + " " + Email);
		String response = getLine();
		if(response.equals("Register done!")){
			SingletonWorker.logger().info("Server accepted Register, you can login!");
			return true;
		}else{
			SingletonWorker.logger().info("Register failed! " + response);
			return false;
		}
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
				SingletonWorker.logger().log(Level.WARNING,"Reading failed! " + e.getLocalizedMessage(), e);
			}
		}
		return "";
	}

	public String getUsername() {
		return username;
	}

}
