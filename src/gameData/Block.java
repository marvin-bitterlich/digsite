package gameData;

import gameView.GameWindow;

import java.awt.Graphics;

import recources.ImageCache;
import singleton.GameProperties;
import singleton.SingletonWorker;

/***********************************************************************************
 * @author:	Marvin Hofmann	Klasse: DQI10	*
 * Prog.Name: Block.java	Beschreibung	*
 * Version:	1.0	Datum: 01.01.2013	*
 * Compiler:	Oracle Java OS: Microsoft Windows 7	*
 * **********************************************************************************/
public class Block extends Texture{

	public static int relativeplayerx;
	public static int relativeplayery;
	private int id;
	private boolean breakable;
	public int xPos;
	public int yPos;
	private int health = 0;
	public boolean draw = true;
	public boolean shadow = true;
	public boolean massive;

	public Block(int id, int xPos, int yPos, boolean shadow, boolean massive, boolean breakable, int health){
		super(
				(xPos*GameProperties.GRAPHICS_SIZE_BLOCK)
				+(GameWindow.getWindowWidth() / 2)-32-GameProperties.playerx
				,
				(yPos*GameProperties.GRAPHICS_SIZE_BLOCK)
				+(GameWindow.getWindowHeight() / 2)-32-GameProperties.playery
				, 
				false);

		this.massive = massive;
		this.shadow = shadow;
		this.id = id;
		this.xPos = xPos;
		this.yPos = yPos;
		if(shadow){
			this.setImage(ImageCache.getSprite(0));
		}else{
			this.setImage(ImageCache.getSprite(id));
		}
		this.breakable = breakable;
		this.health = health;
	}

	public void hit(ActivePlayer activePlayer){
		if(isBreakable()){
			if(--health <= 0){
				SingletonWorker.gameData().getNetworkHandlerThread().breakBlock(this);
				
//				activePlayer.addItem(id,1);
//				this.id = 1;
//				this.setMassive(false);
//				this.setSprite(ImageCache.getSprite(1));
			}
		}
	}

	public void updatePosition(){
		int xp = (xPos*GameProperties.GRAPHICS_SIZE_BLOCK)
				+ relativeplayerx;

		int yp = (yPos*GameProperties.GRAPHICS_SIZE_BLOCK)
				+ relativeplayery;

		if(xp < 0-GameProperties.GRAPHICS_SIZE_BLOCK || yp < 0-GameProperties.GRAPHICS_SIZE_BLOCK || xp > GameWindow.getWindowWidth() || yp > GameWindow.getWindowHeight()){
			draw = false;
		}else{
			draw = true;
			super.setXPos(xp);
			super.setYPos(yp);
			if(shadow){
				if(
						Math.abs(
								xp+GameProperties.GRAPHICS_SIZE_BLOCK/2  
								- (GameWindow.getWindowWidth() / 2))
								< GameProperties.GRAPHICS_SIZE_BLOCK*2.5 
								&& 
								Math.abs(
										yp+GameProperties.GRAPHICS_SIZE_BLOCK/2 
										- (GameWindow.getWindowHeight() / 2))
										< GameProperties.GRAPHICS_SIZE_BLOCK*2.5  )
				{
					SingletonWorker.gameData().getNetworkHandlerThread().deshadowBlock(this);
					shadow = false;
				}
			}
		}
	}

	public void draw(Graphics g) {
		updatePosition();
		if(draw){
			if (this.getImage() != null){
				g.drawImage(this.getImage(), (int) this.getXPos(),
						(int) this.getYPos(), null);
				//				super.draw(g);
				if(this.isMassive() && !shadow && this.id != 3){
					g.drawString(Integer.toString(health), (int)this.getXPos()+GameProperties.GRAPHICS_SIZE_BLOCK/4, (int)this.getYPos()+GameProperties.GRAPHICS_SIZE_BLOCK/2+36);
				}

			}
			//			try {
			//				throw new Exception("test");
			//			} catch (Exception e) {
			//				e.printStackTrace();
			//				System.exit(0);
			//			}
		}
	}

	@Override
	public void process(long duration) {
		//TODO Fill processing
	}

	public boolean isBreakable() {
		return breakable;
	}

	public void setBreakable(boolean isBreakable) {
		this.breakable = isBreakable;
	}

	public int getID() {
		return id;
	}

	@Override
	public boolean isMassive() {
		return massive;
	}
	@Override
	public void setMassive(boolean isMassive) {
		this.massive = isMassive;
	}

}
