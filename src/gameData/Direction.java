package gameData;

public enum Direction {
	up, right, down, left;

	public static int getValue(Direction d) {
		switch (d) {
		case up:
			return 0;
		case right:
			return 1;
		case down:
			return 2;
		case left:
			return 3;
		default:
			return Integer.MAX_VALUE;
		}
	}
}
