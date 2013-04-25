package network;

import recources.NumberWorker;
import recources.XStreamWorker;
import singleton.GameProperties;
import singleton.SingletonWorker;
import gameData.Player;
import gameView.ingame.Block;
import gameView.ingame.Chunk;
import gameView.ingame.datatypes.Entity;
import gameView.ingame.inventory.Inventory;

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

	public NetworkHandlerThread(String username,String password, ServerConnection sc) {
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
							Entity e = SingletonWorker.gameData().entityMap().get(entity);
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
						if(cut.length >= 5){
							int entity = NumberWorker.getNumber(cut[1]);
							int x = NumberWorker.getNumber(cut[2]);
							int y = NumberWorker.getNumber(cut[3]);
							String name = cut[4];
							if(entity != userID){
								SingletonWorker.gameData().entityMap().put(entity,
										new Player(20, 20, 20,20, 20, 20, 20,
												10, Player.CLASS_BRUTE, 10, x, y, name)
										);
							}
						}
					}
					
					if(cmd == NetworkConstants.SERVER_MOVEMENT_PLAYER_LEAVE){
						cut = line.split(NetworkConstants.SEPERATOR);
						if(cut.length >= 2){
							int entity = NumberWorker.getNumber(cut[1]);
							if(entity != userID){
								SingletonWorker.gameData().entityMap().remove(entity);
							}
						}
					}

					if(cmd == NetworkConstants.SERVER_COMMUNICATION_SENDCHUNK){
						Chunk c = (Chunk) XStreamWorker.fromXML(cut[1]);
						SingletonWorker.logger().info(cut[1]);
						SingletonWorker.gameData().mine().addChunk(c);
					}

					if(cmd == NetworkConstants.SERVER_COMMUNICATION_SENDBLOCK){
						Block b = (Block) XStreamWorker.fromXML(cut[1]);
						SingletonWorker.gameData().mine().updateBlock(b);
					}

					if(cmd == NetworkConstants.SERVER_COMMUNICATION_SENDINVENTORY){
						System.out.println(cut[0] + " " + cut[1]);
						Inventory i = (Inventory) XStreamWorker.fromXML(cut[1]);
						//TODO bad fix!
						while(SingletonWorker.gameData().activePlayer() == null){
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						SingletonWorker.gameData().activePlayer().setInventory(i);
					}
				}
			}
		}
	}

	public void requestChunk(int x, int y) {
		connection.sendLine(NetworkConstants.SERVER_COMMUNICATION + NetworkConstants.SEPERATOR + NetworkConstants.SERVER_COMMUNICATION_GETCHUNK + NetworkConstants.SEPERATOR + 
				x + NetworkConstants.SEPERATOR + y);
		SingletonWorker.logger().info(NetworkConstants.SERVER_COMMUNICATION + NetworkConstants.SEPERATOR + NetworkConstants.SERVER_COMMUNICATION_GETCHUNK + NetworkConstants.SEPERATOR + 
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
		connection.sendLine(NetworkConstants.SERVER_CRAFTING + NetworkConstants.SEPERATOR + i);
	}



}
