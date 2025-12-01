package EvPrison.commands;

import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import EvPrison.Prison;
import net.evmodder.EvLib.bukkit.EvCommand;

public class CommandUnjail extends EvCommand{
	public CommandUnjail(Prison p){super(p);}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		//cmd:	/prison unjail [player] [-jail]
		
		if(args.length == 0){
			sender.sendMessage("�cPlease specify a player to unjail");
			return true;
		}
		
		@SuppressWarnings("deprecation")
		OfflinePlayer target = Prison.getPlugin().getServer().getOfflinePlayer(args[0]);
		if(target == null || !target.hasPlayedBefore()){
			sender.sendMessage("�cUnable to find player '�e"+args[0]+"�c'");
			return true;
		}
		if(args.length > 1){
			if(!Prison.getPlugin().isJail(args[1])){
				sender.sendMessage("�cInvalid jail '�e"+args[1]+"�c'");
				return true;
			}
			else Prison.getPlugin().unjail(target.getUniqueId(), args[1]);
		}
		//if no jail specified, unjail from all prisons.
		else Prison.getPlugin().unjail(target.getUniqueId());
		
		if(target.isOnline()){
			target.getPlayer().sendMessage("�aYou have been unjailed!");
			//Alternate version: "You have been released by PerikiyoXD"
			//Alternate version: "Your jail time has ended!"
		}
		
		return true;
	}

	@Override public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3){
		// TODO Auto-generated method stub
		return null;
	}
}
