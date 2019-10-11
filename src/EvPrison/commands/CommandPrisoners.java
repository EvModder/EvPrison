package EvPrison.commands;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import EvPrison.Prison;
import net.evmodder.EvLib.EvCommand;

public class CommandPrisoners extends EvCommand{
	public CommandPrisoners(Prison p){super(p);}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		//cmd:	/prison list [-prison]
		
		if(args.length == 0 || args[0].equalsIgnoreCase("all") || args[0].equals("@a")){
			if(!sender.hasPermission("evp.prison.list.all")){
				sender.sendMessage("�cYou do not have permission to view that prisoner list");
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

	@Override public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3){
		// TODO Auto-generated method stub
		return null;
	}
}
