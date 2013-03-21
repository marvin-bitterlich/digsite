package recources;

import gameView.ingame.Block;
import gameView.ingame.Chunk;
import gameView.ingame.Mine;
import gameView.ingame.inventory.DrawableItemStack;
import gameView.ingame.inventory.Inventory;

import java.io.StringWriter;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

/***********************************************************************************
 * @author:	Marvin Hofmann	Klasse: DQI10	*
 * Prog.Name: XStreamWorker.java	Beschreibung	*
 * Version:	1.0	Datum: 10.01.2013	*
 * Compiler:	Oracle Java OS: Microsoft Windows 7	*
 * **********************************************************************************/
public class XStreamWorker {
	
	public static XStream xstream = null;
	
	private static void init(){
		if(xstream == null){
			xstream = new XStream();
			xstream.alias("Inventory", Inventory.class);
			xstream.alias("I", DrawableItemStack.class);
			xstream.alias("Mine", Mine.class);
			xstream.alias("C", Chunk.class);
			xstream.alias("B", Block.class);
			xstream.registerConverter(new BlockConverter());
			xstream.registerConverter(new ItemStackConverter());
		}
	}

	
	/**
	 * Serialises an object to XML and strips all whitespaces to save storage-space.
	 * 
	 * @param o The object to be serialized.
	 * @return The serialised String without whitespaces
	 */
	public static String toXML(Object o){
		init();
		StringWriter sw = new StringWriter();
		xstream.marshal(o,  new CompactWriter(sw));
		String xml = sw.toString();
		return xml;
//		return xstream.toXML(o);
	}
	
	/**
	 * Deserialises an Object out of a String.
	 * 
	 * @param s The String to be deserialised.
	 * @return The deserialized Object.
	 */
	public static Object fromXML(String s){
		init();
		return xstream.fromXML(s);
	}
	
}
