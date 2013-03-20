package gameData;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteManager {

	private static HashMap<String, Sprite> map = new HashMap<String, Sprite>();

	public static Sprite getSprite(String src, boolean staticImage) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(GameProperties.getGamePath()
					+ "/rec/" + src));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (staticImage){
			return new Sprite(image);
		}

		if (isSpriteExistent(image, src)) {
			return map.get(src);
		}

		Sprite s = new Sprite(image);
		map.put(src, s);
		return s;
	}

	public static SpriteWithRandomImage getSpriteWithRandomImage(String src, int cols, int rows) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(GameProperties.getGamePath()
					+ "/rec/" + src));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isSpriteExistent(image, src) && map.get(src) instanceof SpriteWithRandomImage) {
			return (SpriteWithRandomImage) map.get(src);
		}
		System.out.println(src);
		SpriteWithRandomImage s = new SpriteWithRandomImage(image, cols, rows);
		map.put(src, s);
		return s;
	}

	private static boolean isSpriteExistent(BufferedImage image, String src) {
		if (map.containsKey(src)) {
			if (map.get(src).getImage().getWidth(null) 
					== image.getWidth()
					&& map.get(src).getImage().getHeight(null) 
					== image.getHeight()) {
				return true;
			}
		}
		return false;
	}
}