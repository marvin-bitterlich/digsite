package gameData;



public abstract class Entity extends Texture {

	public Entity(double xPos, double yPos) {
		super(xPos, yPos);
		this.setSprite(new Sprite(ImageCache.getPlayerSprite(getDirection())));
	}

	public static final byte LAYER_FLY = 0;
	public static final byte LAYER_WALK = 1;
	public static final byte LAYER_CROUCH = 2;

	private int speedPxlsPerSec, xPerSec, yPerSec, layer = 1;
	private Direction direction = Direction.down;

	public void process(long duration) {
		this.setXPos(this.getXPos()
				+ ((double) duration * (double) this.getXPerSec()) / 1000d);
		this.setYPos(this.getYPos()
				+ ((double) duration * (double) this.getYPerSec()) / 1000d);

		// if (duration * this.getXPerSec() != 0)
		// SingletonWorker.logger().info("duration: " + duration + " xMovement: "
		// + ((double) ((double) duration * (double) this.getXPerSec()))
		// / 1000d + " xPos: "
		// + this.getXPos());
		// if (duration * this.getYPerSec() != 0)
		// SingletonWorker.logger().info("duration: " + duration + " yMovement: "
		// + ((double) ((double) duration * (double) this.getYPerSec()))
		// / 1000d + " yPos: "
		// + this.getYPos());
	}

	public void collide(Texture t) {
		// TODO Auto-generated method stub
		
	}
	
	public abstract void updateImageDirection(long duration);

	public int getXPerSec() {
		return xPerSec;
	}

	public void setXPerSec(int xPerSec) {
		this.xPerSec = xPerSec;
	}

	public int getYPerSec() {
		return yPerSec;
	}

	public void setYPerSec(int yPerSec) {
		this.yPerSec = yPerSec;
	}

	public int getSpeedPxlsPerSec() {
		return speedPxlsPerSec;
	}

	public void setSpeedPxlsPerSec(int speedPxlsPerSec) {
		this.speedPxlsPerSec = speedPxlsPerSec;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
