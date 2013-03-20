package recources;
/***********************************************************************************
 * @author:	Marvin Hofmann	Klasse: DQI10	*
 * Prog.Name: ConstantsWorker.java	Beschreibung	*
 * Version:	1.0	Datum: 10.01.2013	*
 * Compiler:	Oracle Java OS: Microsoft Windows 7	*
 * **********************************************************************************/
public class ConstantsWorker {

	/**
	 * Defines the right pattern für every Coordinate stored as a String!
	 * 
	 * @param x the given x
	 * @param y the give y
	 * @return The String representing the Coordinate.
	 */
	public static String getCoordinateString(int x, int y){
		return x+"-"+y;
	}
	
}
