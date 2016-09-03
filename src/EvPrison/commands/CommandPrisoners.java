package EvPrison.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandPrisoners extends BasePrisonCommand{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		//cmd:	/prison list [-prison]
		
		if(args.length == 0 || args[0].equalsIgnoreCase("all") || args[0].equals("@a")){
			if(!sender.hasPermission("evp.prison.list.all")){
				sender.sendMessage("§cYou do not have permission to view that prisoner list");
			}
			else{
				//TODO: show all prisoners
			}
		}
		else{
			//TODO: find prison based on args[] and show prisoners for that prison
		}
		return true;
	}
}
