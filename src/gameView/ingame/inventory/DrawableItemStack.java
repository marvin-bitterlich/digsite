package gameView.ingame.inventory;

import gameView.ingame.datatypes.ItemStack;
import gameView.ingame.datatypes.Texture;
import recources.ImageCache;

public class DrawableItemStack extends Texture implements ItemStack{
	private int itemId;
	private int amount;

	public DrawableItemStack(ItemStack is, int xPos, int yPos) {
		super(xPos, yPos);
		this.amount = is.getAmount();
		this.itemId = is.getItemId();
		this.setImage(ImageCache.getIcon(this.itemId));
	}
	
	public DrawableItemStack(int id, int amount) {
		super(0,0);
		this.amount = amount;
		this.itemId = id;
		this.setImage(ImageCache.getIcon(this.itemId));
	}

	@Override
	public void process(long duration) {
		
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public boolean isEmpty() {
		return getItemId() <= 0;
	}

	@Override
	public int getMaxStackSize() {
		if(getItemId() <= 0){
			return 0;
		}
		return 64;
	}
}