package EvPrison.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandUnjail extends BasePrisonCommand{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		//cmd:	/prison unjail [player]
		
		if(args.length == 0){
			sender.sendMessage("§cPlease specify a player to unjail");
			return true;
		}
		
		@SuppressWarnings("deprecation")
		OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
		if(target == null){
			sender.sendMessage("§cUnable to find player '§e"+args[0]+"§c'");
			return true;
		}
		
		plugin.removePrisoner(target.getUniqueId());
		
		if(target.isOnline()){
			target.getPlayer().sendMessage("§aYou have been unjailed!");
			//Alternate version: "You have been released by PerikiyoXD"
			//Alternate version: "Your jail time has ended!"
		}
		
		return true;
	}
}
