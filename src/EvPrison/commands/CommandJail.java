package EvPrison.commands;

import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import EvPrison.Prison;
import EvPrison.Utils;
import net.evmodder.EvLib.bukkit.EvCommand;

public class CommandJail extends EvCommand{
	private long DEFAULT_SENTANCE;
	
	public CommandJail(Prison p){
		super(p);
		DEFAULT_SENTANCE = Utils.getTimeInSeconds(p.getConfig().getString("default-sentance-length"));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		//cmd:	/prison jail [player] [-jail] [-time]
		
		if(args.length == 0){
			sender.sendMessage("�cPlease specify a player to jail");
			return true;
		}
		
		@SuppressWarnings("deprecation")
		OfflinePlayer target = Prison.getPlugin().getServer().getOfflinePlayer(args[0]);
		if(target == null || !target.hasPlayedBefore()){
			sender.sendMessage("�cUnable to find player '�e"+args[0]+"�c'");
			return true;
		}
		
		long time = DEFAULT_SENTANCE;
		Prison pl = Prison.getPlugin();
		String jailName = pl.getDefaultJail();
		
		if(args.length == 1){
			//nothin
		}
		else if(args.length == 2){
			if(Utils.isTimeString(args[1])) time = Utils.getTimeInSeconds(args[1]);
			else jailName = args[1];
		}
		else if(args.length == 3){
			if(Utils.isTimeString(args[1])){
				time = Utils.getTimeInSeconds(args[1]);
				jailName = args[2];
			}
			else{
				jailName = args[1];
				if(Utils.isTimeString(args[2])) time = Utils.getTimeInSeconds(args[2]);
				else{
					sender.sendMessage("�cInvalid jail sentance, must be a time value");
					return false;
				}
			}
		}
		else{
			sender.sendMessage("�cInvalid number of arguments");
			return false;
		}
		
		if(!pl.isJail(jailName)){
			sender.sendMessage("�cInvalid jail '�e"+jailName+"�c'");
			return true;
		}
		if(pl.isInJail(target.getUniqueId(), jailName)){
			sender.sendMessage("�c'�7"+target.getName()+"�c' is already in that jail");
			if(time != 0) sender.sendMessage("�cChanging sentance to: �6"+args[2]);
			else return true;
		}
		
		pl.jail(target.getUniqueId(), jailName, time);
		
		if(target.isOnline()){
			target.getPlayer().sendMessage("�4You have been jailed for "+time+'!');
			//More complete version: "You have been jailed for 2h 15m by PerikiyoXD for hacking."
		}
		//TODO: teleport player to jail
		return true;
	}

	@Override public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3){
		// TODO Auto-generated method stub
		return null;
	}
}
