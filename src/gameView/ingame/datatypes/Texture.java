package gameView.ingame.datatypes;

import gameData.Player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import singleton.GameProperties;

public abstract class Texture {
	private boolean massive;
	private double xPos, yPos;
	private BufferedImage image;

	public Texture(double xPos, double yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public Texture(int xPos, int yPos, boolean isMassive) {
		this.massive = isMassive;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void draw(Graphics g) {
		g.drawImage(image, (int) xPos, (int) yPos, null);
	}

	public abstract void process(long duration);

	public boolean intercects(Texture t) {

		Rectangle r;
		if (this instanceof Player) {
			r = new Rectangle((int) this.getXPos(),
					(int)this.getYPos(),
					GameProperties.GRAPHICS_SIZE_CHAR_WIDTH,
					GameProperties.GRAPHICS_SIZE_CHAR_HEIGHT);
		} else {
			r = new Rectangle((int) this.getXPos(), (int) this.getYPos(), this
					.getImage().getWidth(), this.getImage().getHeight());
		}

		Rectangle r2;
		if (t instanceof Player) {
			r2 = new Rectangle((int) this.getXPos(),
					(int)this.getYPos(),
					GameProperties.GRAPHICS_SIZE_CHAR_WIDTH,
					GameProperties.GRAPHICS_SIZE_CHAR_HEIGHT);
		} else {
			r2 = new Rectangle((int) t.getXPos(), (int) t.getYPos(), t
					.getImage().getWidth(), t.getImage().getHeight());
		}

		return r.intersects(r2);
	}
	
	public boolean intercectsOffset(Texture t,int xoffset,int yoffset) {

		Rectangle r;
		if (this instanceof Player) {
			r = new Rectangle((int) this.getXPos()+xoffset,
					(int)this.getYPos()+yoffset,
					GameProperties.GRAPHICS_SIZE_CHAR_WIDTH,
					GameProperties.GRAPHICS_SIZE_CHAR_HEIGHT);
		} else {
			r = new Rectangle((int) this.getXPos()+xoffset, (int) this.getYPos()+yoffset, this
					.getImage().getWidth(), this.getImage().getHeight());
		}

		Rectangle r2;
		if (t instanceof Player) {
			r2 = new Rectangle((int) this.getXPos(),
					(int)this.getYPos(),
					GameProperties.GRAPHICS_SIZE_CHAR_WIDTH,
					GameProperties.GRAPHICS_SIZE_CHAR_HEIGHT);
		} else {
			r2 = new Rectangle((int) t.getXPos(), (int) t.getYPos(), t
					.getImage().getWidth(), t.getImage().getHeight());
		}

		return r.intersects(r2);
	}

	
	
	public boolean hits(Texture t) {
		Rectangle r = new Rectangle((int) this.getXPos(), (int) this.getYPos(),
				this.getImage().getWidth(), this.getImage().getHeight());

		Rectangle r2 = new Rectangle((int) t.getXPos(), (int) t.getYPos(), t
				.getImage().getWidth(), t.getImage().getHeight());

		return r.intersects(r2);
	}

	public double getXPos() {
		return xPos;
	}

	public void setXPos(double xPos) {
		this.xPos = xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public void setYPos(double yPos) {
		this.yPos = yPos;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public boolean isMassive() {
		return massive;
	}

	public void setMassive(boolean isMassive) {
		this.massive = isMassive;
	}

}
