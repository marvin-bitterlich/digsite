package gameData;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Structure extends Texture {
	public static final byte LAYER_BUILDING = 1;
	public static final byte LAYER_FLOOR = 2;
	public static final byte LAYER_UNDERGROUND = 3;
	public static final byte LAYER_UNDER_UNDERGROUND = 4;
	public static final byte LAYER_LOWEST = 5;

	private byte layer;
	private boolean breakable;
	private int randomImageIndex;

	public Structure(int xPos, int yPos, byte layer, String src,
			boolean isMassive, boolean isBreakable) {
		super(xPos, yPos, isMassive);
		this.setSprite(SpriteManager.getSprite(src, true));
		this.layer = layer;
		this.breakable = isBreakable;
	}

	public Structure(int xPos, int yPos, byte layer, String src,
			boolean isMassive, boolean isBreakable,
			boolean isStructureWithRandomImage) {
		super(xPos, yPos, isMassive);
		this.setSprite(SpriteManager.getSprite(src, false));
		this.layer = layer;
		this.breakable = isBreakable;

		if (isStructureWithRandomImage) {
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File(GameProperties.getGamePath()
						+ "/rec/" + src));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.setSprite(SpriteManager.getSpriteWithRandomImage(src,
					image.getWidth() / GameProperties.GRAPHICS_SIZE_BLOCK,
					image.getHeight() / GameProperties.GRAPHICS_SIZE_BLOCK));
			this.randomImageIndex = (int) (Math.random() * (double) (image
					.getHeight()
					/ GameProperties.GRAPHICS_SIZE_BLOCK
					* image.getWidth() / GameProperties.GRAPHICS_SIZE_BLOCK));
		}

	}

	public void draw(Graphics g) {
		if (this.getSprite().getImage() != null)
			if (this.getSprite() instanceof SpriteWithRandomImage) {
				g.drawImage(((SpriteWithRandomImage) (this.getSprite()))
						.getImage(this.randomImageIndex), (int) this.getXPos(),
						(int) this.getYPos(), null);
			} else {
				super.draw(g);
			}

	}

	@Override
	public void process(long duration) {
		// TODO Auto-generated method stub
		
	}

	public byte getLayer() {
		return layer;
	}

	public void setLayer(byte layer) {
		this.layer = layer;
	}

	public boolean isBreakable() {
		return breakable;
	}

	public void setBreakable(boolean isBreakable) {
		this.breakable = isBreakable;
	}

}
