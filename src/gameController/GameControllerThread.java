package gameController;

import gameData.ActivePlayer;
import gameData.Player;
import gameView.GameWindow;
import gameView.ingame.Block;
import gameView.ingame.datatypes.Direction;
import gameView.ingame.datatypes.Entity;
import gameView.ingame.datatypes.RelativeBoxPosition;
import gameView.ingame.datatypes.Texture;
import gameView.ingame.menu.DrawableInventory;
import gameView.ingame.menu.MainMenu;
import gameView.ingame.menu.SkillMenu;
import gameView.ingame.menu.UIItem;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import recources.ImageCache;
import singleton.GameData;
import singleton.GameProperties;
import singleton.SingletonWorker;

import utilities.ImageUtil;

public class GameControllerThread implements Runnable, MouseListener, MouseMotionListener,
KeyEventDispatcher {

	private static final int REFRESHS_PER_SECOND = 60;
	private boolean forwardPressed, backwardPressed, leftPressed, rightPressed;
	public static boolean rendering;
	private static final int GRAVITY_Y = 5;

	private File bg = new File(GameProperties.getGamePath()
			+ "/rec/" + GameProperties.SPLASH_PIC_BACKGROUND);
	private BufferedImage background = null;
	private BufferedImage basic = null;
	public Font f;
	public int frames = 0;
	public int framecount = 0;
	public long frametime = 0;
	private long timeout;
	private long time;

	private void fillItemsAndInventoryTest() {
		int[] equipedItems = new int[9];
		for (int i = 0; i < 9; i++) {
			equipedItems[i] = Integer.MAX_VALUE;
		}
		SingletonWorker.gameData().activePlayer().setEquiped(equipedItems);
	}

	@Override
	public void run() {
		GameData gamedata = SingletonWorker.gameData();
		f = SingletonWorker.gameProperties().gameFont();
		Graphics2D g = (Graphics2D) gamedata.bufferstrategy().getDrawGraphics();
		BufferedImage splashimage = ImageCache.getRecource(SingletonWorker.gameProperties().splashPath());
		g.drawImage(ImageUtil.resizeImage(splashimage,GameWindow.getWindowWidth(),GameWindow.getWindowHeight()), 0, 0, null);
		g.dispose();
		gamedata.bufferstrategy().show();
		this.initGameValues();

		gamedata.gameLoaded = true;
		
		long duration;
		long cycleStartTime = System.currentTimeMillis();
		frametime = System.currentTimeMillis();
		while(true){ //game heartbeat thread running indefinitely, until explicit break
			framecount++;
			if((System.currentTimeMillis() - frametime) >= 1000){
				frames = framecount;
				framecount = 0;
				frametime += 1000;
			}

			duration = System.currentTimeMillis() - cycleStartTime;
			cycleStartTime = System.currentTimeMillis();

			this.calculateMovements();
			this.updateMenuActiveStates();
			//			boolean onLadder = false;
			boolean movementPossible = true;
			int screenXMovement = (int) gamedata.activePlayer()
					.calculateScreenXMovement(duration);
			int screenYMovement = +GRAVITY_Y;

			if(leftPressed){
				gamedata.activePlayer().setDirection(Direction.left);
			}else if (rightPressed){
				gamedata.activePlayer().setDirection(Direction.right);
			}else if (forwardPressed){
				gamedata.activePlayer().setDirection(Direction.up);
			}else if (backwardPressed){
				gamedata.activePlayer().setDirection(Direction.down);
			}
			gamedata.activePlayer().updateImageDirection(duration);
			LinkedList<Texture> textures = gamedata.mine().getTextures();
			this.updateMenus(duration);
			boolean digging = false;
			//move y direction
			boolean outofbounds = true;
			if(GameProperties.playery < 15){
				outofbounds = false;
			}

			for(Texture curTexture : textures){
				if(curTexture instanceof Block){
					if(gamedata.activePlayer().intercectsOffset(curTexture,0,+5)){
						Block current = ((Block) curTexture);
						if(current.getID() == 11 || current.getID() == 12){
							screenYMovement = (int) gamedata.activePlayer().calculateScreenYMovement(duration);
						}
					}else if(gamedata.activePlayer().intercectsOffset(curTexture,0,+10)){
						Block current = ((Block) curTexture);
						Block ontop = gamedata.mine().getBlock(current.xPos, current.yPos-1);
						if((current.getID() == 11 || current.getID() == 12) && !backwardPressed){
							if(ontop == null || (ontop.getID() != 11 && ontop.getID() != 12)){
								screenYMovement = 0;
							}
						}
					}
				}
			}
			for (Texture currentTexture : textures) {
				if(movementPossible){

					if (currentTexture instanceof Block && gamedata.activePlayer().intercectsOffset(currentTexture,0,screenYMovement*5)) {
						outofbounds = false;
						Block current = ((Block) currentTexture);
						if (current.isMassive()){
							movementPossible = false;
							if(!gamedata.activePlayer().intercectsOffset(currentTexture,0,5)){
								GameProperties.playery += 1;
							}
							if((current.yPos > GameProperties.getPlayerBlockY() && backwardPressed) || (current.yPos < GameProperties.getPlayerBlockY() && forwardPressed)){
								current.hit(gamedata.activePlayer());
								digging = true;
							}
						}

					} else if (gamedata.activePlayer().hits(currentTexture)) {

						gamedata.activePlayer().collide(currentTexture);

					}
				}

			}
			if (movementPossible && !outofbounds) {
				GameProperties.playery += screenYMovement;
			}
			movementPossible = true;
			//move x direction
			for (Texture currentTexture : textures) {
				if(movementPossible){
					if (currentTexture instanceof Block &&
							gamedata.activePlayer().intercectsOffset(currentTexture,screenXMovement*3,0)) {
						Block current = ((Block) currentTexture);
						if (current.isMassive()){
							movementPossible = false;

							if(((current.xPos < GameProperties.getPlayerBlockX() && leftPressed) || (current.xPos > GameProperties.getPlayerBlockX() && rightPressed)) && !digging){
								current.hit(gamedata.activePlayer());
							}
						}

					} else if (gamedata.activePlayer().hits(currentTexture)) {

						gamedata.activePlayer().collide(currentTexture);

					}
				}

			}

			if (movementPossible) {
				GameProperties.playerx += screenXMovement;
			}

			gamedata.getNetworkHandlerThread().sendMovement(GameProperties.playerx,GameProperties.playery);
			g = (Graphics2D) gamedata.bufferstrategy().getDrawGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(f);
			if(basic == null){
				basic = ImageUtil.resizeImage(ImageCache.getIcon(0),GameWindow.getWindowWidth(),GameWindow.getWindowHeight());
			}
			g.drawImage(basic, 0, 0, null);

			if(background == null){
				try {
					background = ImageUtil.resizeImage(
							ImageIO.read(bg),
							GameWindow.getWindowWidth()*2,
							GameWindow.getWindowHeight()*2);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			g.drawImage(background, -(GameWindow.getWindowWidth()/2)-(GameProperties.playerx/2), -200-(GameProperties.playery/2) , null);

			gamedata.mine().draw(g);

			gamedata.activePlayer().draw(g);
			for(Entity e : gamedata.entityMap().values()){
				e.draw(g);
			}
			if (gamedata.getActiveMenu() != Integer.MAX_VALUE) {
				gamedata.uiItemMap()
				.get(gamedata.getActiveMenu()).draw(g);
			}
			g.drawString("Frames: " + frames + " Miliseconds:" + time + "/" + timeout, 50, 50);
			g.dispose();
			gamedata.bufferstrategy().show();
			time = System.currentTimeMillis()-cycleStartTime; 

			try {
				timeout = (1000 / REFRESHS_PER_SECOND)-time;
				Thread.sleep((timeout > 0) ? timeout : 5);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
	}

	private void initGameValues() {
		SingletonWorker.gameData().setActivePlayer(new ActivePlayer(20, 20, 20,20, 20, 20, 20,
				10, Player.CLASS_BRUTE, 200));

		// this.gsd.getTextureList().add(gamedata.activePlayer());

		this.fillItemsAndInventoryTest();


		// gsd.setStructureList(MapBuilder.buildMap(new
		// File("Current Mapname")));

		this.addMenus();
	}

	private void updateMenus(long duration) {
		for (UIItem uiitem : SingletonWorker.gameData().uiItemMap().values()){
			uiitem.process(duration);
		}
	}

	private void addMenus() {
		SingletonWorker.gameData().uiItemMap().put(
				GameProperties.MENU_ID_MAIN,
				new MainMenu(0, 0, GameWindow.getWindowWidth(), GameWindow
						.getWindowHeight(), GameProperties.MENU_PIC_MAIN));
		SingletonWorker.gameData().uiItemMap().put(GameProperties.MENU_ID_INVENTORY,
				this.createInventory());
		SingletonWorker.gameData().uiItemMap().put(
				GameProperties.MENU_ID_SKILL,
				new SkillMenu(0, 0, GameWindow.getWindowWidth(), GameWindow
						.getWindowHeight(), GameProperties.MENU_PIC_SKILL));
		SingletonWorker.gameData().uiItemMap().put(
				GameProperties.MENU_ID_VIDEO,
				new SkillMenu(0, 0, GameWindow.getWindowWidth(), GameWindow
						.getWindowHeight(), GameProperties.MENU_PIC_VIDEO));
		SingletonWorker.gameData().uiItemMap().put(
				GameProperties.MENU_ID_AUDIO,
				new SkillMenu(0, 0, GameWindow.getWindowWidth(), GameWindow
						.getWindowHeight(), GameProperties.MENU_PIC_AUDIO));
	}

	private void updateMenuActiveStates() {
		for (int i = 0; i <= 4; i++) {
			if (SingletonWorker.gameData().getActiveMenu() == i) {
				SingletonWorker.gameData().uiItemMap().get(i).setActive(true);
			} else {
				SingletonWorker.gameData().uiItemMap().get(i).setActive(false);
			}
		}
	}

	private void hideOrShowMenu(int menu) {
		if (SingletonWorker.gameData().getActiveMenu() == menu) {
			SingletonWorker.gameData().setActiveMenu(Integer.MAX_VALUE);
		} else {
			SingletonWorker.gameData().setActiveMenu(menu);
		}
	}

	private void calculateMovements() {
		GameData gamedata = SingletonWorker.gameData();
		if (forwardPressed && backwardPressed) {
			gamedata.activePlayer().setYPerSec(0);

		} else if (forwardPressed) {
			gamedata.activePlayer().setYPerSec(
					-gamedata.activePlayer().getSpeedPxlsPerSec());

		} else if (backwardPressed) {
			gamedata.activePlayer().setYPerSec(
					gamedata.activePlayer().getSpeedPxlsPerSec());

		} else {
			gamedata.activePlayer().setYPerSec(0);
		}

		if (leftPressed && rightPressed) {
			gamedata.activePlayer().setXPerSec(0);

		} else if (leftPressed) {
			gamedata.activePlayer().setXPerSec(
					-gamedata.activePlayer().getSpeedPxlsPerSec());

		} else if (rightPressed) {
			gamedata.activePlayer().setXPerSec(
					gamedata.activePlayer().getSpeedPxlsPerSec());

		} else {
			gamedata.activePlayer().setXPerSec(0);
		}

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (!SingletonWorker.gameData().gameLoaded)
			return true;

		if (e.getKeyCode() == KeyEvent.VK_I
				|| e.getKeyCode() == KeyEvent.VK_TAB) {

			if (e.getID() == KeyEvent.KEY_PRESSED)
				hideOrShowMenu(GameProperties.MENU_ID_INVENTORY);

		} else if (e.getKeyCode() == KeyEvent.VK_K) {

			if (e.getID() == KeyEvent.KEY_PRESSED)
				hideOrShowMenu(GameProperties.MENU_ID_SKILL);

		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			if (e.getID() == KeyEvent.KEY_PRESSED)
				hideOrShowMenu(GameProperties.MENU_ID_MAIN);

		} else if (e.getKeyCode() == KeyEvent.VK_E) {

			if (e.getID() == KeyEvent.KEY_PRESSED){
				int x = GameProperties.getPlayerBlockX();
				int y = GameProperties.getPlayerBlockY();
				SingletonWorker.gameData().getNetworkHandlerThread().placeLadder(x,y,11);
				SingletonWorker.gameData().getNetworkHandlerThread().placeLadder(x,y,12);
			}

		} else if (e.getKeyCode() == KeyEvent.VK_W) {

			if (e.getID() == KeyEvent.KEY_PRESSED) {
				this.forwardPressed = true;
			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
				this.forwardPressed = false;
			}

		} else if (e.getKeyCode() == KeyEvent.VK_S) {

			if (e.getID() == KeyEvent.KEY_PRESSED) {
				this.backwardPressed = true;
			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
				this.backwardPressed = false;
			}

		} else if (e.getKeyCode() == KeyEvent.VK_A) {

			if (e.getID() == KeyEvent.KEY_PRESSED) {
				this.leftPressed = true;
			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
				this.leftPressed = false;
			}

		} else if (e.getKeyCode() == KeyEvent.VK_D) {

			if (e.getID() == KeyEvent.KEY_PRESSED) {
				this.rightPressed = true;
			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
				this.rightPressed = false;
			}

		}

		// Allow the event to be redispatched
		return false;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!SingletonWorker.gameData().gameLoaded)
			return;

		if (SingletonWorker.gameData().getActiveMenu() != Integer.MAX_VALUE) {
			SingletonWorker.gameData().uiItemMap().get(SingletonWorker.gameData().getActiveMenu()).mouseKlicked(e);
		} else {
			SingletonWorker.gameData().activePlayer().attack(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (!SingletonWorker.gameData().gameLoaded)
			return;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (!SingletonWorker.gameData().gameLoaded)
			return;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!SingletonWorker.gameData().gameLoaded)
			return;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!SingletonWorker.gameData().gameLoaded)
			return;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!SingletonWorker.gameData().gameLoaded)
			return;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!SingletonWorker.gameData().gameLoaded)
			return;

		SingletonWorker.gameData().activePlayer().setMouseX(e.getX());
		SingletonWorker.gameData().activePlayer().setMouseY(e.getY());
	}

	private DrawableInventory createInventory() {
		RelativeBoxPosition[] equipmentBoxes = new RelativeBoxPosition[9];
		equipmentBoxes[0] = GameProperties.INV_RELATIVE_HAT_BOX;
		equipmentBoxes[1] = GameProperties.INV_RELATIVE_EYE_PATCH_BOX;
		equipmentBoxes[2] = GameProperties.INV_RELATIVE_PARROT_BOX;
		equipmentBoxes[3] = GameProperties.INV_RELATIVE_CHEST_BOX;
		equipmentBoxes[4] = GameProperties.INV_RELATIVE_WAEPON_BOX;
		equipmentBoxes[5] = GameProperties.INV_RELATIVE_SECOND_HAND_BOX;
		equipmentBoxes[6] = GameProperties.INV_RELATIVE_PANTS_BOX;
		equipmentBoxes[7] = GameProperties.INV_RELATIVE_SHOE_BOX;
		equipmentBoxes[8] = GameProperties.INV_RELATIVE_WOODEN_LEG_BOX;

		return new DrawableInventory(0, 0, GameWindow.getWindowWidth(),
				GameWindow.getWindowHeight(), GameProperties.MENU_PIC_INV,
				GameProperties.INV_ROWS, GameProperties.INV_CELLS,
				GameProperties.INV_RELATIVE_FIRST_BOX,
				GameProperties.INV_RELATIVE_DISTANCE_X,
				GameProperties.INV_RELATIVE_DISTANCE_Y,
				GameProperties.INV_RELATIVE_BACK_BTN,
				GameProperties.INV_RELATIVE_FWD_BTN, equipmentBoxes);
	}

}
