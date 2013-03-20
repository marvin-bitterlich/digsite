package recources;
/***********************************************************************************
 * @author:	Marvin Hofmann	Klasse: DQI10	*
 * Prog.Name: NumberWorker.java	Beschreibung	*
 * Version:	1.0	Datum: 09.01.2013	*
 * Compiler:	Oracle Java OS: Microsoft Windows 7	*
 * **********************************************************************************/
public class NumberWorker {

	/**
	 * Versucht, aus einem String eine Zahl zu parsen und diese zurückzugeben.
	 * 
	 * @param string The String to be converted 
	 * @return eine positive Zahl oder -1, falls ein Fehler auftritt. Bei negativen Zahlen wird -2 zurückgegeben.
	 */
	public static int getPositiveNumber(String string){
		int getNumber = -1;
		try{
			getNumber = Integer.parseInt(string);
			//Falls es keine Zahl ist, bricht das Programm schon hier ab
			if(getNumber < 0){
				return -2;
			}
		}catch(NumberFormatException nfe){/*Macht nix, supress Exception*/}

		return getNumber;
	}
	
	/**
	 * Versucht, aus einem String eine Zahl zu parsen und diese zurückzugeben.
	 * 
	 * @param string The String to be converted 
	 * @return Die geparste Zahl oder 0 bei Fehlern!
	 */
	public static int getNumber(String string){
		int getNumber = 0;
		try{
			getNumber = Integer.parseInt(string);
			//Falls es keine Zahl ist, bricht das Programm schon hier ab
		}catch(NumberFormatException nfe){/*Macht nix, supress Exception*/}

		return getNumber;
	}
	
	/**
	 * Versucht, aus einem String eine Zahl zu parsen und überprüft, ob dies möglich ist.
	 * 
	 * @param string The String to be checked
	 * @return true, falls der String eine Zahl repräsentiert, false, wenn nicht.
	 */
	public static boolean isNumber(String string){
		Boolean isNumber = false;
		try{
			Integer.parseInt(string);
			//Falls es keine Zahl ist, bricht das Programm schon hier ab
			isNumber = true;
		}catch(NumberFormatException nfe){/*Macht nix, supress Exception*/}

		return isNumber;
	}
	
}
