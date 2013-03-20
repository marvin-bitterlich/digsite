package gameData;

public class Item {
	public static final int ITEM_CATEGORY_HAT = 0;
	public static final int ITEM_CATEGORY_EYE_PATCH = 1;
	public static final int ITEM_CATEGORY_PARROT = 2;
	public static final int ITEM_CATEGORY_CHEST = 3;
	public static final int ITEM_CATEGORY_WAEPON = 4;
	public static final int ITEM_CATEGORY_SECOND_HAND = 5;
	public static final int ITEM_CATEGORY_PANTS = 6;
	public static final int ITEM_CATEGORY_SHOE = 7;
	public static final int ITEM_CATEGORY_WOODEN_LEG = 8;
	public static final int ITEM_CATEGORY_OTHERS = 9;
	
	private Sprite s;
	private int category;

	public Item(int id) {
		this.s = ImageCache.getSprite(id);
		this.category = ITEM_CATEGORY_OTHERS;
	}

	public Sprite getSprite() {
		return s;
	}

	public void setSprite(Sprite s) {
		this.s = s;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}
