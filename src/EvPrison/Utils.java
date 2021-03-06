package EvPrison;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import net.minecraft.server.v1_16_R1.ChatMessageType;
import net.minecraft.server.v1_16_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R1.IChatMutableComponent;
import net.minecraft.server.v1_16_R1.PacketPlayOutChat;

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
		return !cleaned.isEmpty() && StringUtils.isNumeric(cleaned);
	}

	public static long timeToDate(long time){
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.SECOND, (int)time);
		return date.getTimeInMillis() / 1000 + time;
	}

	public static long dateToTime(long date){
		return date - new GregorianCalendar().getTimeInMillis() / 1000;
	}

	public static void sendHyperTextCommand(String preMsg, String hyperMsg, String command, String postMsg,
			Player... recipients){
		IChatMutableComponent comp;
		if(preMsg != null && !preMsg.isEmpty()) {
			comp = ChatSerializer.a("{\"text\":\"" + preMsg + "\",\"extra\":[{\"text\":\"" + hyperMsg
					+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + command + "\"}}]}");
		}
		else{
			comp = ChatSerializer.a("{\"extra\":[{\"text\":\"�2" + hyperMsg
					+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + command + "\"}}]}");
		}
		if(postMsg != null && !postMsg.isEmpty()) {
			comp.addSibling(ChatSerializer.a("{\"text\":\"" + postMsg + "\"}"));
		}
		PacketPlayOutChat packet = new PacketPlayOutChat(comp, ChatMessageType.GAME_INFO, UUID.randomUUID());
		for(Player p : recipients)
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}
}
