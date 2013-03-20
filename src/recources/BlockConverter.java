package recources;


import gameData.Block;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class BlockConverter implements Converter {
		public static final String SEPERATOR = ";";
        @SuppressWarnings("rawtypes")
		public boolean canConvert(Class clazz) {
                return clazz.equals(Block.class);
        }
        public int convertboolean(boolean b){
        	if(b){
        		return 1;
        	}else{
        		return 0;
        	}
        }
        
        public boolean parseboolean(int i){
        	if(i == 1){
        		return true;
        	}else if (i == 0){
        		return false;
        	}
        	System.out.println("Fehler beim convertieren von i in " + BlockConverter.class);
        	return false;
        }
//<Block id="100" breakable="true" xPos="0" yPos="0" health="100" massive="false" shadow="true"/>
        public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
//                Block block = (Block) value;
//                String output = 
//                		Integer.toString(block.getID()) + SEPERATOR
//                		+ block.getxPos() + SEPERATOR
//                		+ block.getyPos() + SEPERATOR
//                		+ convertboolean(block.isBreakable()) + SEPERATOR
//                		+ convertboolean(block.isMassive()) + SEPERATOR
//                		+ convertboolean(block.isShadow()) + SEPERATOR
//                		+ Integer.toString(block.getHealth());
//                writer.setValue(output);
        		//do nothing, no client-marshaling of blocks!
        }

        public Object unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context) {
                String input = reader.getValue();
                
                String[] split = input.split(SEPERATOR);
                Block block = new Block(
                		Integer.parseInt(split[0]),
                		Integer.parseInt(split[1]),
                		Integer.parseInt(split[2]),
                		parseboolean(Integer.parseInt(split[5])),
                		parseboolean(Integer.parseInt(split[4])),
                		parseboolean(Integer.parseInt(split[3])),
                		Integer.parseInt(split[6])
                		);
                return block;
        }

}
