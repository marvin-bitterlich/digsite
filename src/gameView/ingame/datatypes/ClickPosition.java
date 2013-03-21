package gameView.ingame.datatypes;

import singleton.SingletonWorker;

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
		startX = (int) (relativeBox.getRelativeStartX() * (double)SingletonWorker.gameData().width());
		startY = (int) (relativeBox.getRelativeStartY() * (double)SingletonWorker.gameData().height());
		endX = (int) (relativeBox.getRelativeEndX() * (double)SingletonWorker.gameData().width());
		endY = (int) (relativeBox.getRelativeEndY() * (double)SingletonWorker.gameData().height());
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
