package gameData;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import utilities.ImageUtil;

public abstract class UIItem extends Texture {

	private boolean active = false;

	public UIItem(int startX, int startY, int endX, int endY, String src) {
		super(startX, startY);
		this.setSprite(new Sprite(ImageCache.getRecource(src)));
		this.getSprite().setImage(
				ImageUtil.resizeImage((BufferedImage) this.getSprite()
						.getImage(), endX - startX, endY - startY));
	}

	public abstract void keyPressed();

	public abstract void mouseKlicked(MouseEvent e);

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
