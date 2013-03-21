package gameView.ingame.datatypes;

public class RelativeBoxPosition {
	private double relativeStartX, relativeStartY, relativeEndX, relativeEndY;

	public RelativeBoxPosition(double relativeStartX, double relativeStartY,
			double relativeEndX, double relativeEndY) {
		super();
		this.relativeStartX = relativeStartX;
		this.relativeStartY = relativeStartY;
		this.relativeEndX = relativeEndX;
		this.relativeEndY = relativeEndY;
	}

	public double getRelativeStartX() {
		return relativeStartX;
	}

	public void setRelativeStartX(double relativeStartX) {
		this.relativeStartX = relativeStartX;
	}

	public double getRelativeStartY() {
		return relativeStartY;
	}

	public void setRelativeStartY(double relativeStartY) {
		this.relativeStartY = relativeStartY;
	}

	public double getRelativeEndX() {
		return relativeEndX;
	}

	public void setRelativeEndX(double relativeEndX) {
		this.relativeEndX = relativeEndX;
	}

	public double getRelativeEndY() {
		return relativeEndY;
	}

	public void setRelativeEndY(double relativeEndY) {
		this.relativeEndY = relativeEndY;
	}

}
