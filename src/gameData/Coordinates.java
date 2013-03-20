package gameData;
/***********************************************************************************
 * @author:	Marvin Hofmann	Klasse: DQI10	*
 * Prog.Name: Coordinates.java	Beschreibung	*
 * Version:	1.0	Datum: 01.01.2013	*
 * Compiler:	Oracle Java OS: Microsoft Windows 7	*
 * **********************************************************************************/
public class Coordinates {

	int x;
	int y;
	
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Coordinates){
			Coordinates co = (Coordinates)o;
			if(this.x == co.x && this.y == co.y){
				return true;
			}
		}
		return false;
	}
	
}
