package gameData;



import java.util.ArrayList;



/***********************************************************************************
 * @author:	Marvin Hofmann	Klasse: DQI10	*
 * Prog.Name: Inventory.java	Beschreibung	*
 * Version:	1.0	Datum: 28.11.2012	*
 * Compiler:	Oracle Java OS: Microsoft Windows 7	*
 * **********************************************************************************/
public class Inventory {
	int size;
	ArrayList<DrawableItemStack> itemstacks;
	boolean changed = true;

	public Inventory(int inventorysize){
		this.size = inventorysize;
		itemstacks = new ArrayList<DrawableItemStack>();
		for (int i = 0; i < size; i++) {
			itemstacks.add(new DrawableItemStack(0, 0));
		}
		System.out.println(itemstacks.size());
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if(this.size < size){
			int stacks = size-this.size;
			for (int i = 0; i < stacks; i++) {
				itemstacks.add(new DrawableItemStack(0, 0));
			}
		}
		if(this.size > size){
			int stacks = this.size-size;
			int i = getEmptySlots();
			while(i != -1 && stacks > 0){
				removeItemStack(i);
				stacks--;
			}
		}
		this.size = size;
	}

	public ItemStack getItemStack(int id) {
		if(id >= size){
			return itemstacks.get(id);
		}
		return null;
	}

	public int getEmptySlots(){
		int i = -1;
		for(DrawableItemStack s : itemstacks){
			if(s.getItemId() == 0){
				i++;
			}
		}
		return i;
	}

	public int getNextEmptySlot(){
		for (int j = 0; j < size; j++) {
			if(itemstacks.get(j).getItemId()==0){
				return j;
			}
		}
		return -1;
	}

	public void addItemStack(DrawableItemStack is){

		int id = is.getItemId();
		int amount = is.getAmount();
		for (int j = 0; j < size; j++) {
			if(amount > 0){
				ItemStack s = itemstacks.get(j);
				if(s.getAmount() < s.getMaxStackSize()){
					if(s.getItemId() == id){
						while(s.getAmount() < s.getMaxStackSize() && amount > 0){
							s.setAmount(s.getAmount()+1);
							amount--;
						}
					}
				}
			}
		}
		if(amount > 0){
			is.setAmount(amount);
			int empty = getNextEmptySlot();
			if(empty >= 0){
				itemstacks.set(empty, is);
			}
		}
	}

	public DrawableItemStack removeItemStack(int id){
		if(id <= size){
			DrawableItemStack is = itemstacks.get(id);
			itemstacks.set(id,new DrawableItemStack(0, 0));
			return is;
		}
		return null;
	}
	
	public ArrayList<DrawableItemStack> getInventoryList(){
		return itemstacks;
	}
	
	public boolean hasChanged(){
		boolean b = changed;
		changed = false;
		return b;
	}

}
