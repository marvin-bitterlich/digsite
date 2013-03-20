package gameData;

import java.awt.Font;
import java.io.File;
import java.util.logging.Level;

import recources.SingletonWorker;

public class GameProperties {
	public static final String SEPERATOR = System.lineSeparator();
	public static final String START = "Starten";
	public static final String VIDEO = "Video";
	public static final String REGISTER = "Registrieren";
	public static final String EXIT = "Beenden";
	public static final int MAP_SIZE_CHUNK = 8;
	public static final int FILE_SIZE_BLOCK = 128;
	public static final int GRAPHICS_SIZE_BLOCK = 128;
	public static final int GRAPHICS_SIZE_CHAR_HEIGHT = 64;
	public static final int GRAPHICS_SIZE_CHAR_WIDTH = 32;

	public static final String savePath = (System.getenv("APPDATA") + SEPERATOR + "BlueBrickGames" + SEPERATOR + "Arr - Die Piratenbucht" + SEPERATOR);
	public static final int MENU_ID_INVENTORY = 1;
	public static final int MENU_ID_MAIN = 0;
	public static final int MENU_ID_SKILL = 2;
	public static final int MENU_ID_VIDEO = 3;
	public static final int MENU_ID_AUDIO = 4;
	public static final String MENU_PIC_MAIN = "mainmenu.png";
	public static final String MENU_PIC_SKILL = "skillmenu.png";
	public static final String MENU_PIC_VIDEO = "videomenu.png";
	public static final String MENU_PIC_AUDIO = "audiomenu.png";
	public static final String MENU_PIC_INV = "inventory.png";
	public static final String SPLASH_PIC_BACKGROUND = "background.jpg";
	public static final int INV_CELLS = 4;
	public static final int INV_ROWS = 5;
	public static final double INV_PIC_HEIGHT = 900d;
	public static final double INV_PIC_WIDTH = 1600d;
	public static final RelativeBoxPosition INV_RELATIVE_FIRST_BOX = new RelativeBoxPosition(
			getRelativeValueToWidth(940), getRelativeValueToHeight(194),
			getRelativeValueToWidth(1034), getRelativeValueToHeight(288));
	public static final double INV_RELATIVE_DISTANCE_X = 6d / INV_PIC_WIDTH;
	public static final double INV_RELATIVE_DISTANCE_Y = 6d / INV_PIC_HEIGHT;
	public static final RelativeBoxPosition INV_RELATIVE_BACK_BTN = new RelativeBoxPosition(
			getRelativeValueToWidth(855), getRelativeValueToHeight(466),
			getRelativeValueToWidth(912), getRelativeValueToHeight(512));
	public static final RelativeBoxPosition INV_RELATIVE_FWD_BTN = new RelativeBoxPosition(
			getRelativeValueToWidth(1458), getRelativeValueToHeight(467),
			getRelativeValueToWidth(1513), getRelativeValueToHeight(512));
	public static final RelativeBoxPosition INV_RELATIVE_HAT_BOX = new RelativeBoxPosition(
			getRelativeValueToWidth(447), getRelativeValueToHeight(45),
			getRelativeValueToWidth(542), getRelativeValueToHeight(130));
	public static final RelativeBoxPosition INV_RELATIVE_PARROT_BOX = new RelativeBoxPosition(
			getRelativeValueToWidth(341), getRelativeValueToHeight(159),
			getRelativeValueToWidth(447), getRelativeValueToHeight(254));
	public static final RelativeBoxPosition INV_RELATIVE_WOODEN_LEG_BOX = new RelativeBoxPosition(
			getRelativeValueToWidth(498), getRelativeValueToHeight(641),
			getRelativeValueToWidth(593), getRelativeValueToHeight(747));
	public static final RelativeBoxPosition INV_RELATIVE_EYE_PATCH_BOX = new RelativeBoxPosition(
			getRelativeValueToWidth(472), getRelativeValueToHeight(132),
			getRelativeValueToWidth(567), getRelativeValueToHeight(228));
	public static final RelativeBoxPosition INV_RELATIVE_SHOE_BOX = new RelativeBoxPosition(
			getRelativeValueToWidth(397), getRelativeValueToHeight(641),
			getRelativeValueToWidth(497), getRelativeValueToHeight(737));
	public static final RelativeBoxPosition INV_RELATIVE_PANTS_BOX = new RelativeBoxPosition(
			getRelativeValueToWidth(447), getRelativeValueToHeight(465),
			getRelativeValueToWidth(543), getRelativeValueToHeight(561));
	public static final RelativeBoxPosition INV_RELATIVE_WAEPON_BOX = new RelativeBoxPosition(
			getRelativeValueToWidth(330), getRelativeValueToHeight(421),
			getRelativeValueToWidth(425), getRelativeValueToHeight(517));
	public static final RelativeBoxPosition INV_RELATIVE_SECOND_HAND_BOX = new RelativeBoxPosition(
			getRelativeValueToWidth(565), getRelativeValueToHeight(421),
			getRelativeValueToWidth(661), getRelativeValueToHeight(517));
	public static final RelativeBoxPosition INV_RELATIVE_CHEST_BOX = new RelativeBoxPosition(
			getRelativeValueToWidth(448), getRelativeValueToHeight(286),
			getRelativeValueToWidth(543), getRelativeValueToHeight(382));
	public static final String LOGIN = "Login";
	public static final String CHECKUSERNAME = "Name testen";
	public static final String EMAIL = "Email";
	public static final String USERNAME = "Benutzername";
	public static final String PASSWORT = "Passwort";
	public static final String REGISTERSET = "Registrierung";
	public static final String CHECKEMAIL = "Email testen";

	public String gameTitle(){
		return "Digsite";
	}


	public static int playerx = 0, playery = 0;

	private static double getRelativeValueToWidth(int pos) {
		return ((double) pos / (double) INV_PIC_WIDTH);
	}

	private static double getRelativeValueToHeight(int pos) {
		return ((double) pos / (double) INV_PIC_HEIGHT);
	}

	public static String getGamePath() {
		return SingletonWorker.gameProperties().gamePath();
	}

	private String gamePath;
	public String gamePath(){
		if(gamePath == null){
			gamePath = System.getProperty("user.home")+File.separator+".digsite";
		}
		return gamePath;
	}

	private Font gameFont;
	public Font gameFont(){
		if(gameFont == null){
			try{
				gameFont = Font.createFont(Font.TRUETYPE_FONT,	new File(gamePath() + File.separator + "rec" + File.separator + "digsite.ttf"));
			} catch (Exception e){
				SingletonWorker.logger().log(Level.WARNING,e.getLocalizedMessage(),e);
				gameFont = new Font(Font.MONOSPACED, Font.BOLD, 40);
			}
			gameFont = gameFont.deriveFont(60f);
		}

		return gameFont;
	}

	public static int getPlayerBlockX() {
		int x = (GameProperties.playerx+62)/GameProperties.GRAPHICS_SIZE_BLOCK;
		if(GameProperties.playerx+62<0){
			x--;
		}
		return x;
	}

	public static int getPlayerBlockY() {
		int y = (GameProperties.playery-31)	/GameProperties.GRAPHICS_SIZE_BLOCK;
		return y;
	}

	public String logoPath() {
		return "logo.png";
	}
	public String cursorPath() {
		return File.separator + "rec" + File.separator + "cursor.png";
	}
	public String backgroundPath(){
		return "menu.jpg";
	}
	public String splashPath(){
		return "loading.jpg";
	}
	public String playerPath() {
		return "player.png";
	}
}
