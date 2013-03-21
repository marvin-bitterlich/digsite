package recources;

import gameData.ItemStack;
import gameView.ingame.inventory.DrawableItemStack;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ItemStackConverter implements Converter {
	public static final String SEPERATOR = ";";
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class clazz) {
		if(
				clazz.equals(ItemStack.class) ||
				clazz.equals(DrawableItemStack.class)
				){
			return true;
		}else{
			return false;
		}
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ItemStack is = (ItemStack) value;
		String output = 
				is.getItemId() + SEPERATOR
				+ is.getAmount() + SEPERATOR
				+ is.getMaxStackSize();
		writer.setValue(output);
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String input = reader.getValue();
		String[] split = input.split(SEPERATOR);
		return new DrawableItemStack(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
	}

}
