package gameView.ingame.inventory;

import gameData.ItemStack;

public class ConcreteItemStack implements ItemStack {

	private int id;
	private int amount;
	private int stacksize;

	public ConcreteItemStack(int id, int amount) {
		this.id = id;
		this.amount = amount;
		this.stacksize = 64;
	}

	public ConcreteItemStack(int id, int amount, int maxstacksize) {
		this.id = id;
		this.amount = amount;
		this.stacksize = maxstacksize;
	}

	@Override
	public int getItemId() {
		return id;
	}

	@Override
	public void setItemId(int itemId) {
		this.id = itemId;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public boolean isEmpty() {
		return amount > 0;
	}

	@Override
	public int getMaxStackSize() {
		return stacksize;
	}

}
