package gameData;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import recources.SingletonWorker;

import utilities.ImageUtil;

/***********************************************************************************
 * @author:	Marvin Hofmann	Klasse: DQI10	*
 * Prog.Name: ImageCache.java	Beschreibung	*
 * Version:	1.0	Datum: 01.01.2013	*
 * Compiler:	Oracle Java OS: Microsoft Windows 7	*
 * **********************************************************************************/
public class ImageCache {

	private static HashMap<Integer, Sprite[]> map = new HashMap<Integer, Sprite[]>();

	public static Sprite getSprite(int id) {
		if(map.containsKey(id)){
			Sprite[] spritearray = map.get(id);
			return spritearray[(int)(Math.random()*spritearray.length)];
		}

		BufferedImage image = null;
		try {
			String path = GameProperties.getGamePath()
					+ "/texture/hi_" + id + ".png";
			System.out.println("loaded image: " + path);
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth()/GameProperties.FILE_SIZE_BLOCK;
		int h = image.getHeight()/GameProperties.FILE_SIZE_BLOCK;

		BufferedImage[] imageParts = ImageUtil.splitImage(image,w,h);

		Sprite[] sprites = new Sprite[imageParts.length];

		for (int i = 0; i < imageParts.length; i++) {
			BufferedImage bi = imageParts[i];
			Sprite sp = new Sprite(
					ImageUtil.resizeImage(bi, GameProperties.GRAPHICS_SIZE_BLOCK, GameProperties.GRAPHICS_SIZE_BLOCK)
					);
			sprites[i] = sp;
		}

		map.put(id, sprites);
		return sprites[(int)(Math.random()*sprites.length)];
	}

	public static Sprite getIcon(int id) {
		if(map.containsKey(id)){
			Sprite[] spritearray = map.get(id);
			return spritearray[0];
		}

		BufferedImage image = null;
		try {
			String path = GameProperties.getGamePath()
					+ "/texture/hi_" + id + ".png";
			System.out.println("loaded image: " + path);
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth()/GameProperties.FILE_SIZE_BLOCK;
		int h = image.getHeight()/GameProperties.FILE_SIZE_BLOCK;

		BufferedImage[] imageParts = ImageUtil.splitImage(image,w,h);

		Sprite[] sprites = new Sprite[imageParts.length];

		for (int i = 0; i < imageParts.length; i++) {
			BufferedImage bi = imageParts[i];
			Sprite sp = new Sprite(
					ImageUtil.resizeImage(bi, GameProperties.GRAPHICS_SIZE_BLOCK, GameProperties.GRAPHICS_SIZE_BLOCK)
					);
			sprites[i] = sp;
		}

		map.put(id, sprites);
		return sprites[0];
	}
	
	public static BufferedImage getRecource(String filename){
		File f = new File(SingletonWorker.gameProperties().gamePath() + File.separator + "rec" + File.separator + filename);
		try {
			return ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("Failed to read the Recource " + filename + "! The System is inconsistent and has to exit!");
		System.exit(-1);
		return null;
	}
	
}
