package gameController;

import gameData.ActivePlayer;
import gameData.Block;
import gameData.Entity;
import gameData.GameData;
import gameData.GameSessionData;
import gameData.GameProperties;
import gameData.DrawableInventory;
import gameData.ImageCache;
import gameData.Inventory;
import gameData.Item;
import gameData.ItemManager;
import gameData.MainMenu;
import gameData.Mine;
import gameData.Player;
import gameData.RelativeBoxPosition;
import gameData.SkillMenu;
import gameData.Texture;
import gameView.GameWindow;

import java.awt.Font;
import java.awt.FontFormatException;
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
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import utilities.ImageUtil;

public class GameControllerThread implements Runnable, MouseListener, MouseMotionListener,
KeyEventDispatcher {

	private GameSessionData gsd;
	private static final int REFRESHS_PER_SECOND = 60;
	private boolean forwardPressed, backwardPressed, leftPressed, rightPressed;
	public static boolean rendering;
	private static final int GRAVITY_Y = 5;

	private File bg = new File(GameProperties.getGamePath()
			+ "/rec/" + GameProperties.SPLASH_PIC_BACKGROUND);
	private File splash = new File(GameProperties.getGamePath()
			+ "/rec/" + GameProperties.SPLASH_PIC_START);
	private BufferedImage splashimage;
	private BufferedImage background = null;
	private BufferedImage basic = null;
	private static final Font FONT = new Font(Font.MONOSPACED, Font.BOLD, 40);
	public Font f;
	public int frames = 0;
	public int framecount = 0;
	public long frametime = 0;
	private long timeout;
	private long time;


	public GameControllerThread(GameSessionData gsd) {
		this.gsd = gsd;
	}

	private void fillItemsAndInventoryTest() {
		HashMap<Integer, Item> itemList = new HashMap<Integer, Item>();
		for (int i = 0; i < 11; i++) {
			itemList.put(i, new Item(i));
		}
		ItemManager.setItemList(itemList);
		gsd.getActivePlayer().setInventory(new Inventory(100));

		int[] equipedItems = new int[9];
		for (int i = 0; i < 9; i++) {
			equipedItems[i] = Integer.MAX_VALUE;
		}
		gsd.getActivePlayer().setEquiped(equipedItems);
	}

	private void fillMap() {
		gsd.setMine(new Mine());
	}

	@Override
	public void run() {
		
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
		Graphics2D g = (Graphics2D) GameData.bufferstrategy.getDrawGraphics();
		try {
			if(splashimage == null){
				splashimage = ImageIO.read(splash);
			}
			g.drawImage(ImageUtil.resizeImage(
					splashimage,
					GameWindow.getWindowWidth(),
					GameWindow.getWindowHeight()), 0, 0, null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		g.dispose();
		GameData.bufferstrategy.show();
		this.initGameValues();

		GameData.gameLoaded = true;

		long duration;
		long cycleStartTime = System.currentTimeMillis();
		frametime = System.currentTimeMillis();
		while (!gsd.isReturnToMenu()) {
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
			int screenXMovement = (int) gsd.getActivePlayer()
					.calculateScreenXMovement(duration);
			int screenYMovement = +GRAVITY_Y;


			gsd.getActivePlayer().updateImageDirection(duration);
			LinkedList<Texture> textures = gsd.getMine().getTextures();
			this.updateMenus(duration);
			boolean digging = false;
			//move y direction
			boolean outofbounds = true;
			if(GameProperties.playery < 15){
				outofbounds = false;
			}

			for(Texture curTexture : textures){
				if(curTexture instanceof Block){
					if(gsd.getActivePlayer().intercectsOffset(curTexture,0,+5)){
						Block current = ((Block) curTexture);
						if(current.getID() == 11 || current.getID() == 12){
							screenYMovement = (int) gsd.getActivePlayer().calculateScreenYMovement(duration);
						}
					}else if(gsd.getActivePlayer().intercectsOffset(curTexture,0,+10)){
						Block current = ((Block) curTexture);
						Block ontop = gsd.getMine().getBlock(current.xPos, current.yPos-1);
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

					if (currentTexture instanceof Block && gsd.getActivePlayer().intercectsOffset(currentTexture,0,screenYMovement*5)) {
						outofbounds = false;
						Block current = ((Block) currentTexture);
						if (current.isMassive()){
							movementPossible = false;
							if(!gsd.getActivePlayer().intercectsOffset(currentTexture,0,5)){
								GameProperties.playery += 1;
							}
							if((current.yPos > GameProperties.getPlayerBlockY() && backwardPressed) || (current.yPos < GameProperties.getPlayerBlockY() && forwardPressed)){
								current.hit(gsd.getActivePlayer());
								digging = true;
							}
						}

					} else if (gsd.getActivePlayer().hits(currentTexture)) {

						gsd.getActivePlayer().collide(currentTexture);

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
							gsd.getActivePlayer().intercectsOffset(currentTexture,screenXMovement*3,0)) {
						Block current = ((Block) currentTexture);
						if (current.isMassive()){
							movementPossible = false;

							if(((current.xPos < GameProperties.getPlayerBlockX() && leftPressed) || (current.xPos > GameProperties.getPlayerBlockX() && rightPressed)) && !digging){
								current.hit(gsd.getActivePlayer());
							}
						}

					} else if (gsd.getActivePlayer().hits(currentTexture)) {

						gsd.getActivePlayer().collide(currentTexture);

					}
				}

			}

			if (movementPossible) {
				GameProperties.playerx += screenXMovement;
			}

			GameWindow.getGameData().getNetworkHandlerThread().sendMovement(GameProperties.playerx,GameProperties.playery);
			GameData gamedata = GameWindow.getGameData();
			g = (Graphics2D) GameData.bufferstrategy.getDrawGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(f);
			if(basic == null){
				basic = ImageUtil.resizeImage(
						(BufferedImage) ImageCache.getIcon(0).getImage(),
						GameWindow.getWindowWidth(),
						GameWindow.getWindowHeight());
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

			gamedata.getGameSessionData().getMine().draw(g);

			gamedata.getGameSessionData().getActivePlayer().draw(g);
			for(Entity e : gamedata.getGameSessionData().getEntityMap().values()){
				e.draw(g);
			}
			if (gamedata.getGameSessionData().getActiveMenu() != Integer.MAX_VALUE) {
				gamedata.getGameSessionData().getUiItemMap()
				.get(gamedata.getGameSessionData().getActiveMenu()).draw(g);
			}
			g.drawString("Frames: " + frames + " Miliseconds:" + time + "/" + timeout, 50, 50);
//			System.out.println("Frames: " + frames + " Miliseconds:" + time + "/" + timeout);
			g.dispose();
			GameData.bufferstrategy.show();
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
		this.gsd.setActivePlayer(new ActivePlayer(20, 20, 20,20, 20, 20, 20,
				10, Player.CLASS_BRUTE, 200));

		// this.gsd.getTextureList().add(gsd.getActivePlayer());

		this.fillItemsAndInventoryTest();
		this.fillMap();


		// gsd.setStructureList(MapBuilder.buildMap(new
		// File("Current Mapname")));

		this.addMenus();
	}

	private void updateMenus(long duration) {
		for (int uiKey : gsd.getUiItemMap().keySet())
			gsd.getUiItemMap().get(uiKey).process(duration);
	}

	private void addMenus() {
		gsd.getUiItemMap().put(
				GameProperties.MENU_ID_MAIN,
				new MainMenu(0, 0, GameWindow.getWindowWidth(), GameWindow
						.getWindowHeight(), GameProperties.MENU_PIC_MAIN));
		gsd.getUiItemMap().put(GameProperties.MENU_ID_INVENTORY,
				this.createInventory());
		gsd.getUiItemMap().put(
				GameProperties.MENU_ID_SKILL,
				new SkillMenu(0, 0, GameWindow.getWindowWidth(), GameWindow
						.getWindowHeight(), GameProperties.MENU_PIC_SKILL));
		gsd.getUiItemMap().put(
				GameProperties.MENU_ID_VIDEO,
				new SkillMenu(0, 0, GameWindow.getWindowWidth(), GameWindow
						.getWindowHeight(), GameProperties.MENU_PIC_VIDEO));
		gsd.getUiItemMap().put(
				GameProperties.MENU_ID_AUDIO,
				new SkillMenu(0, 0, GameWindow.getWindowWidth(), GameWindow
						.getWindowHeight(), GameProperties.MENU_PIC_AUDIO));
	}

	private void updateMenuActiveStates() {
		for (int i = 0; i <= 4; i++) {
			if (gsd.getActiveMenu() == i) {
				gsd.getUiItemMap().get(i).setActive(true);
			} else {
				gsd.getUiItemMap().get(i).setActive(false);
			}
		}
	}

	private void hideOrShowMenu(int menu) {
		if (gsd.getActiveMenu() == menu) {
			gsd.setActiveMenu(Integer.MAX_VALUE);
		} else {
			gsd.setActiveMenu(menu);
		}
	}

	private void calculateMovements() {

		if (forwardPressed && backwardPressed) {
			gsd.getActivePlayer().setYPerSec(0);

		} else if (forwardPressed) {
			gsd.getActivePlayer().setYPerSec(
					-gsd.getActivePlayer().getSpeedPxlsPerSec());

		} else if (backwardPressed) {
			gsd.getActivePlayer().setYPerSec(
					gsd.getActivePlayer().getSpeedPxlsPerSec());

		} else {
			gsd.getActivePlayer().setYPerSec(0);
		}

		if (leftPressed && rightPressed) {
			gsd.getActivePlayer().setXPerSec(0);

		} else if (leftPressed) {
			gsd.getActivePlayer().setXPerSec(
					-gsd.getActivePlayer().getSpeedPxlsPerSec());

		} else if (rightPressed) {
			gsd.getActivePlayer().setXPerSec(
					gsd.getActivePlayer().getSpeedPxlsPerSec());

		} else {
			gsd.getActivePlayer().setXPerSec(0);
		}

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (!GameData.gameLoaded)
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
				GameWindow.getGameData().getNetworkHandlerThread().placeLadder(x,y,11);
				GameWindow.getGameData().getNetworkHandlerThread().placeLadder(x,y,12);
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
		if (!GameData.gameLoaded)
			return;

		if (gsd.getActiveMenu() != Integer.MAX_VALUE) {
			gsd.getUiItemMap().get(gsd.getActiveMenu()).mouseKlicked(e);
		} else {
			gsd.getActivePlayer().attack(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (!GameData.gameLoaded)
			return;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (!GameData.gameLoaded)
			return;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!GameData.gameLoaded)
			return;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!GameData.gameLoaded)
			return;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!GameData.gameLoaded)
			return;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!GameData.gameLoaded)
			return;

		gsd.getActivePlayer().setMouseX(e.getX());
		gsd.getActivePlayer().setMouseY(e.getY());
	}

	private DrawableInventory createInventory() {
		RelativeBoxPosition[] equipmentBoxes = new RelativeBoxPosition[9];
		equipmentBoxes[Item.ITEM_CATEGORY_HAT] = GameProperties.INV_RELATIVE_HAT_BOX;
		equipmentBoxes[Item.ITEM_CATEGORY_EYE_PATCH] = GameProperties.INV_RELATIVE_EYE_PATCH_BOX;
		equipmentBoxes[Item.ITEM_CATEGORY_PARROT] = GameProperties.INV_RELATIVE_PARROT_BOX;
		equipmentBoxes[Item.ITEM_CATEGORY_CHEST] = GameProperties.INV_RELATIVE_CHEST_BOX;
		equipmentBoxes[Item.ITEM_CATEGORY_WAEPON] = GameProperties.INV_RELATIVE_WAEPON_BOX;
		equipmentBoxes[Item.ITEM_CATEGORY_SECOND_HAND] = GameProperties.INV_RELATIVE_SECOND_HAND_BOX;
		equipmentBoxes[Item.ITEM_CATEGORY_PANTS] = GameProperties.INV_RELATIVE_PANTS_BOX;
		equipmentBoxes[Item.ITEM_CATEGORY_SHOE] = GameProperties.INV_RELATIVE_SHOE_BOX;
		equipmentBoxes[Item.ITEM_CATEGORY_WOODEN_LEG] = GameProperties.INV_RELATIVE_WOODEN_LEG_BOX;

		return new DrawableInventory(0, 0, GameWindow.getWindowWidth(),
				GameWindow.getWindowHeight(), GameProperties.MENU_PIC_INV,
				GameProperties.INV_ROWS, GameProperties.INV_CELLS,
				GameProperties.INV_RELATIVE_FIRST_BOX,
				GameProperties.INV_RELATIVE_DISTANCE_X,
				GameProperties.INV_RELATIVE_DISTANCE_Y,
				GameProperties.INV_RELATIVE_BACK_BTN,
				GameProperties.INV_RELATIVE_FWD_BTN, equipmentBoxes);
	}

	public GameSessionData getGsd() {
		return gsd;
	}
}
