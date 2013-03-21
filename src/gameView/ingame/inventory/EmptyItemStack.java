package gameView.ingame.inventory;

import gameData.ItemStack;

public class EmptyItemStack implements ItemStack {

	@Override
	public int getItemId() {
		return 0;
	}

	@Override
	public void setItemId(int itemId) {
	}

	@Override
	public int getAmount() {
		return 0;
	}

	@Override
	public void setAmount(int amount) {
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public int getMaxStackSize() {
		return 0;
	}

}
