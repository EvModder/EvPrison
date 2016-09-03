package EvPrison.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBail extends BasePrisonCommand{
	
	@Override @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		//cmd:	/prison bail [-player]
		
		OfflinePlayer target;
		
		if(args.length == 0){
			if(sender instanceof Player) target = (Player)sender;
			else{
				sender.sendMessage("§cPlease specify the player for whom you wish to pay bail");
				return true;
			}
		}
		else{
			target = plugin.getServer().getOfflinePlayer(args[0]);
			if(target == null){
				sender.sendMessage("§cUnable to find player '§e"+args[0]+"§c'");
				return true;
			}
		}
		boolean unpaid = (sender instanceof Player);
		if(unpaid){
			//TODO: charge player, or return if unable to afford bail
		}
		
		//TODO: release prisoner
		
		return true;
	}
}
