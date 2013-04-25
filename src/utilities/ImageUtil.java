package utilities;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static BufferedImage[] splitImage(BufferedImage img, int cols,
			int rows) {
//		System.out.println("cols: " + cols);
//		System.out.println("rows: " + rows);
//		System.out.println("width: " + img.getWidth());
//		System.out.println("width/cols: "
//				+ ((double) img.getWidth() / (double) cols));
		int w = (int) ((double) img.getWidth() / (double) cols);
		int h = (int) ((double) img.getHeight() / (double) rows);
		int num = 0;
		BufferedImage imgs[] = new BufferedImage[rows * cols];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				int type = img.getType();
				if(type == 0){
					type = 5;
				}
				imgs[num] = new BufferedImage(w, h, type);
				// Tell the graphics to draw only one block of the image
				Graphics2D g = imgs[num].createGraphics();
				g.drawImage(img, 0, 0, w, h, w * x, h * y, w * x + w,
						h * y + h, null);
				g.dispose();
				num++;
			}
		}
		return imgs;
	}

	public static BufferedImage horizontalflip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();
		return dimg;
	}

	public static BufferedImage verticalflip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getColorModel()
				.getTransparency());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
		g.dispose();
		return dimg;
	}

	public static BufferedImage rotate(BufferedImage img, int angle) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.rotate(Math.toRadians(angle), w / 2, h / 2);
		g.drawImage(img, null, 0, 0);

		return dimg;
	}

	// public static BufferedImage makeColorTransparent(String ref, Color color)
	// {
	// BufferedImage image = loadImage(ref);
	// BufferedImage dimg = new BufferedImage(image.getWidth(),
	// image.getHeight(), BufferedImage.TYPE_INT_ARGB);
	// Graphics2D g = dimg.createGraphics();
	// g.setComposite(AlphaComposite.Src);
	// g.drawImage(image, null, 0, 0);
	// g.dispose();
	// for (int i = 0; i < dimg.getHeight(); i++) {
	// for (int j = 0; j < dimg.getWidth(); j++) {
	// if (dimg.getRGB(j, i) == color.getRGB()) {
	// dimg.setRGB(j, i, 0x8F1C1C);
	// }
	// }
	// }
	// return dimg;
	// }

	public static BufferedImage loadImage(String url) {
		BufferedImage bimg = null;
		try {

			bimg = ImageIO.read(new File(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bimg;
	}

	public static Color getColorAtPoint(BufferedImage image, int x, int y) {
		int colorValue = image.getRGB(x, y);
		Color color = new Color(colorValue);
		return color;
	}

	public static BufferedImage resizeImage(BufferedImage originalImage,
			int width, int height) {

		int w = originalImage.getWidth();
		int h = originalImage.getHeight();
		BufferedImage dimg = new BufferedImage(width, height,
				originalImage.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(originalImage, 0, 0, width, height, 0, 0, w, h, null);
		g.dispose();
		return dimg;

		// BufferedImage resizedImage = new BufferedImage(width, height, type);
		// Graphics2D g = resizedImage.createGraphics();
		// g.drawImage(originalImage, 0, 0, width, height, null);
		// g.dispose();
		//
		// return resizedImage;
	}

}
