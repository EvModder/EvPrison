package EvPrison.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import evmodder.EvLib.CommandBase;
import EvPrison.Prison;

public class CommandUnjail extends CommandBase{
	public CommandUnjail(Prison p){super(p);}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		//cmd:	/prison unjail [player] [-jail]
		
		if(args.length == 0){
			sender.sendMessage("§cPlease specify a player to unjail");
			return true;
		}
		
		@SuppressWarnings("deprecation")
		OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
		if(target == null || !target.hasPlayedBefore()){
			sender.sendMessage("§cUnable to find player '§e"+args[0]+"§c'");
			return true;
		}
		if(args.length > 1){
			if(!((Prison)plugin).isJail(args[1])){
				sender.sendMessage("§cInvalid jail '§e"+args[1]+"§c'");
				return true;
			}
			else ((Prison)plugin).unjail(target.getUniqueId(), args[1]);
		}
		//if no jail specified, unjail from all prisons.
		else ((Prison)plugin).unjail(target.getUniqueId());
		
		if(target.isOnline()){
			target.getPlayer().sendMessage("§aYou have been unjailed!");
			//Alternate version: "You have been released by PerikiyoXD"
			//Alternate version: "Your jail time has ended!"
		}
		
		return true;
	}
}
