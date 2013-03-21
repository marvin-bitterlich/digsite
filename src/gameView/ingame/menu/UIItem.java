package gameView.ingame.menu;

import gameData.Texture;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import recources.ImageCache;
import utilities.ImageUtil;

public abstract class UIItem extends Texture {

	private boolean active = false;

	public UIItem(int startX, int startY, int endX, int endY, String src) {
		super(startX, startY);
		this.setImage(ImageCache.getRecource(src));
		this.setImage(ImageUtil.resizeImage((BufferedImage) this.getImage(), endX - startX, endY - startY));
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
