package gameData;

import java.awt.Image;
import java.awt.image.BufferedImage;

import utilities.ImageUtil;

public class SpriteWithRandomImage extends Sprite {

	private Image[] imageParts;

	public SpriteWithRandomImage(Image img, int cols, int rows) {
		super(img);

		imageParts = ImageUtil.splitImage((BufferedImage) super.getImage(),
				cols, rows);

		// System.out.println("cols!!: " + cols);
		// System.out.println("rows!!: " + rows);
	}

	public Image getImage(int randomImageIndex) {
		// System.out.println("IN SpriteWithRandomImage getImage");
		// System.out.println("ImageHeight: " + super.getHeight());
		// System.out.println("ImageWidth: " + super.getWidth());
		// System.out.println("cols!!: " + cols);
		// System.out.println("rows!!: " + rows);
		return imageParts[randomImageIndex];
	}

	@Override
	public int getHeight() {
		return imageParts[0].getHeight(null);
	}
	
	@Override
	public int getWidth() {
		return imageParts[0].getWidth(null);
	}
	
}
