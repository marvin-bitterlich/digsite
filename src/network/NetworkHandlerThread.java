package network;

import recources.NumberWorker;
import recources.XStreamWorker;
import gameData.Block;
import gameData.Chunk;
import gameData.Entity;
import gameData.GameProperties;
import gameData.GameSessionData;
import gameData.Inventory;
import gameData.Player;
import gameView.GameWindow;

/***********************************************************************************
 * @author:	Marvin Hofmann	Klasse: DQI10	*
 * Prog.Name: NetworkHandlerThread.java	Beschreibung	*
 * Version:	1.0	Datum: 10.01.2013	*
 * Compiler:	Oracle Java OS: Microsoft Windows 7	*
 * **********************************************************************************/
public class NetworkHandlerThread implements Runnable {

	String username;
	String password;
	ServerConnection connection = null;
	private GameSessionData gsd;

	public NetworkHandlerThread(GameSessionData gameSessionData,String username,String password, ServerConnection sc) {
		this.gsd = gameSessionData;
		connection = sc;
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		int userID = connection.userid;

		while(true){
			String line = connection.getLine();
			if(line != null){
				String[] cut = line.split(NetworkConstants.SEPERATOR,2);
				int cmd = NumberWorker.getPositiveNumber(cut[0]);
				if(cmd >= 0){

					if(cmd == NetworkConstants.SERVER_MOVEMENT_MOVE_ENTITY){
						cut = line.split(NetworkConstants.SEPERATOR);
						if(cut.length == 4){
							int entity = NumberWorker.getNumber(cut[1]);
							int x = NumberWorker.getNumber(cut[2]);
							int y = NumberWorker.getNumber(cut[3]);
							Entity e = GameWindow.getGameData().getGameSessionData().getEntityMap().get(entity);
							if(entity != userID){
								if(e != null){
									e.setXPos(x);
									e.setYPos(y);
								}
							}else{
								GameProperties.playerx = x;
								GameProperties.playery = y;
							}
						}
					}

					if(cmd == NetworkConstants.SERVER_MOVEMENT_PLAYER_ENTER){
						cut = line.split(NetworkConstants.SEPERATOR);
						System.out.println(line);
						if(cut.length >= 4){
							int entity = NumberWorker.getNumber(cut[1]);
							int x = NumberWorker.getNumber(cut[2]);
							int y = NumberWorker.getNumber(cut[3]);
							if(entity != userID){
								GameWindow.getGameData().getGameSessionData().getEntityMap().put(entity,
										new Player(20, 20, 20,20, 20, 20, 20,
												10, Player.CLASS_BRUTE, 10, x, y)
										);
							}
						}
					}
					
					if(cmd == NetworkConstants.SERVER_MOVEMENT_PLAYER_LEAVE){
						cut = line.split(NetworkConstants.SEPERATOR);
						System.out.println(line);
						if(cut.length >= 2){
							int entity = NumberWorker.getNumber(cut[1]);
							if(entity != userID){
								GameWindow.getGameData().getGameSessionData().getEntityMap().remove(entity);
							}
						}
					}

					if(cmd == NetworkConstants.SERVER_COMMUNICATION_SENDCHUNK){
						Chunk c = (Chunk) XStreamWorker.fromXML(cut[1]);
						gsd.getMine().addChunk(c);
					}

					if(cmd == NetworkConstants.SERVER_COMMUNICATION_SENDBLOCK){
						Block b = (Block) XStreamWorker.fromXML(cut[1]);
						gsd.getMine().updateBlock(b);
					}

					if(cmd == NetworkConstants.SERVER_COMMUNICATION_SENDINVENTORY){
						Inventory i = (Inventory) XStreamWorker.fromXML(cut[1]);
						//TODO bad fix!
						if(gsd.getActivePlayer() == null){
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						gsd.getActivePlayer().setInventory(i);
					}
				}
			}
		}
	}

	public void requestChunk(int x, int y) {
		connection.sendLine(NetworkConstants.SERVER_COMMUNICATION + NetworkConstants.SEPERATOR + NetworkConstants.SERVER_COMMUNICATION_GETCHUNK + NetworkConstants.SEPERATOR + 
				x + NetworkConstants.SEPERATOR + y);
	}

	public void breakBlock(Block block) {
		connection.sendLine(NetworkConstants.SERVER_COMMUNICATION + NetworkConstants.SEPERATOR + NetworkConstants.SERVER_COMMUNICATION_BREAKBLOCK + NetworkConstants.SEPERATOR + 
				block.xPos + NetworkConstants.SEPERATOR + block.yPos);
	}

	public void deshadowBlock(Block block) {
		connection.sendLine(NetworkConstants.SERVER_COMMUNICATION + NetworkConstants.SEPERATOR + NetworkConstants.SERVER_COMMUNICATION_DESHADOWBLOCK + NetworkConstants.SEPERATOR + 
				block.xPos + NetworkConstants.SEPERATOR + block.yPos);
	}

	public void changeItemsInInventory(int item1, int item2) {
		connection.sendLine(NetworkConstants.SERVER_COMMUNICATION + NetworkConstants.SEPERATOR + NetworkConstants.SERVER_COMMUNICATION_CHANGEITEMININVENTORY + NetworkConstants.SEPERATOR + 
				item1 + NetworkConstants.SEPERATOR + item2);
	}

	public void sendMovement(int playerx, int playery) {
		connection.sendLine(NetworkConstants.SERVER_MOVEMENT + NetworkConstants.SEPERATOR + NetworkConstants.SERVER_MOVEMENT_MOVE + NetworkConstants.SEPERATOR + 
				playerx + NetworkConstants.SEPERATOR + playery);
	}

	public void placeLadder(int xpos, int ypos, int itemid) {
		connection.sendLine(NetworkConstants.SERVER_INTERACTION + NetworkConstants.SEPERATOR + NetworkConstants.SERVER_INTERACTION_PLACE + NetworkConstants.SEPERATOR + 
				NetworkConstants.SERVER_INTERACTION_PLACE_LADDER + NetworkConstants.SEPERATOR +
				xpos + NetworkConstants.SEPERATOR + ypos + NetworkConstants.SEPERATOR + itemid);
	}

	public void craftItem(int i) {
		System.out.println("sendcrafting:" + NetworkConstants.SERVER_CRAFTING + NetworkConstants.SEPERATOR + i);
		connection.sendLine(NetworkConstants.SERVER_CRAFTING + NetworkConstants.SEPERATOR + i);
	}



}
