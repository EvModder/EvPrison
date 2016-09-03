package EvPrison.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandJail extends BasePrisonCommand{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		//cmd:	/prison jail [player] [-time] [-jail]
		
		if(args.length == 0){
			sender.sendMessage("§cPlease specify a player to jail");
			return true;
		}
		
		@SuppressWarnings("deprecation")
		OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
		if(target == null){
			sender.sendMessage("§cUnable to find player '§e"+args[0]+"§c'");
			return true;
		}
//		long time;
		if(args.length > 1){
			//TODO: calculate time to jail
		}
		
		if(args.length > 2){
			String jailName = args[1];
			if(!plugin.isValidJail(jailName)){
				sender.sendMessage("§cInvalid jail '§e"+jailName+"§c'");
				return true;
			}
//			if(plugin.isInJail(jailName, target.getUniqueId())){
//				sender.sendMessage("§cSelected player is already in that jail");
//				return true;
//			}
			else plugin.addPrisoner(jailName, target.getUniqueId()/*, time */);
		}
		else plugin.addPrisoner(target.getUniqueId()/*, time */);
		
		if(target.isOnline()){
			target.getPlayer().sendMessage("§4You have been jailed"+/* for [time]*/"!");
			//More complete version: "You have been jailed for 2h 15m by PerikiyoXD for hacking."
		}
		
		return true;
	}
}
