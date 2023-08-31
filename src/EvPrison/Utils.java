package EvPrison;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.bukkit.entity.Player;

public class Utils{
	public static long getTimeInSeconds(String time){
		int index = time.indexOf('y');
		int years = Integer.parseInt(time.substring(0, index));
		time = time.substring(index + 1);

		index = time.indexOf('w');
		int weeks = Integer.parseInt(time.substring(0, index));
		time = time.substring(index + 1);

		index = time.indexOf('d');
		int days = Integer.parseInt(time.substring(0, index));
		time = time.substring(index + 1);

		index = time.indexOf('h');
		int hours = Integer.parseInt(time.substring(0, index));
		time = time.substring(index + 1);

		index = time.indexOf('m');
		int minutes = Integer.parseInt(time.substring(0, index));
		time = time.substring(index + 1);

		index = time.indexOf('s');
		long seconds = Integer.parseInt(time.substring(0, index));
		time = time.substring(index + 1);

		return seconds + 60 * (minutes + 60 * ((long)(hours + 24 * (days + 7 * (weeks + 52.1775 * years)))));
	}

	public static boolean isTimeString(String time){
		String cleaned = time.toLowerCase().replaceAll("[ywdhms]", "");
		return !cleaned.isEmpty() && cleaned.matches("^[0-9.]+$");
	}

	public static long timeToDate(long time){
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.SECOND, (int)time);
		return date.getTimeInMillis() / 1000 + time;
	}

	public static long dateToTime(long date){
		return date - new GregorianCalendar().getTimeInMillis() / 1000;
	}

	public static void sendHyperTextCommand(String preMsg, String hyperMsg, String command, String postMsg, Player... recipients){
		//TODO: call EvLib
	}
}
