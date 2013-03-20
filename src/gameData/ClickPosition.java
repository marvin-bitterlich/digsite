package gameData;

import gameView.GameWindow;

public class ClickPosition {
	private int startX, startY, endX, endY;

	public ClickPosition(int startX, int startY, int endX, int endY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	public ClickPosition(RelativeBoxPosition relativeBox) {
		startX = (int) (relativeBox.getRelativeStartX() * (double) GameWindow
				.getWindowWidth());
		startY = (int) (relativeBox.getRelativeStartY() * (double) GameWindow
				.getWindowHeight());
		endX = (int) (relativeBox.getRelativeEndX() * (double) GameWindow
				.getWindowWidth());
		endY = (int) (relativeBox.getRelativeEndY() * (double) GameWindow
				.getWindowHeight());
	}

	public boolean isInRange(int x, int y) {
		if (x >= startX && x <= endX && y >= startY && y <= endY) {
			return true;
		}
		return false;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}
}
