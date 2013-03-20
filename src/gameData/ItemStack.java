package gameData;

public interface ItemStack{

	public int getItemId();

	public void setItemId(int itemId);

	public int getAmount();

	public void setAmount(int amount);
	
	public boolean isEmpty();
	
	public int getMaxStackSize();

}