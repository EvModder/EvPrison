package EvPrison;

public class Utils {
	public static long getTimeInSeconds(String time){
		int index = time.indexOf('y');
		int years = Integer.parseInt(time.substring(0, index));
		time = time.substring(index+1);
		
		index = time.indexOf('w');
		int weeks = Integer.parseInt(time.substring(0, index));
		time = time.substring(index+1);
		
		index = time.indexOf('d');
		int days = Integer.parseInt(time.substring(0, index));
		time = time.substring(index+1);
		
		index = time.indexOf('h');
		int hours = Integer.parseInt(time.substring(0, index));
		time = time.substring(index+1);
		
		index = time.indexOf('m');
		int minutes = Integer.parseInt(time.substring(0, index));
		time = time.substring(index+1);
		
		index = time.indexOf('s');
		long seconds = Integer.parseInt(time.substring(0, index));
		time = time.substring(index+1);
		
		return seconds + 60*minutes + 60*60*hours + 60*60*24*days + 60*60*24*7*weeks + (int)(60*60*24*365.25*years);
	}
}
