package EvPrison.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import EvPrison.Prison;

public class CommandPrison implements TabExecutor{
	private Prison plugin;
	
	private Map<String, List<String>> pluginCommands;

	public CommandPrison(){
		plugin = Prison.getPlugin();
		
		// Load a list of this plugin's commands
		pluginCommands = new HashMap<String, List<String>>();
		for(String cmdName : plugin.getDescription().getCommands().keySet()){
			pluginCommands.put(cmdName, plugin.getCommand(cmdName).getAliases());
		}
		
		plugin.getCommand("prison").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		String thisCmd="";
		
		if(args.length != 0){
			thisCmd = args[0].toLowerCase().replace("prison", "");
			String[] oldArgs = args;
			args = new String[args.length-1];
			for(int i=0; i<args.length; ++i) args[i] = oldArgs[i+1];
		}
		
		//"hm ?" command (help):
		if(thisCmd.isEmpty() || thisCmd.equals("?") || thisCmd.equals("help") || thisCmd.equals("info")){
			sender.sendMessage("§8==================================");
			// Send help/info/commands
			for(String name : plugin.getDescription().getCommands().keySet()){
				Command cmd = plugin.getCommand(name);
				if(sender.hasPermission(cmd.getPermission())){
					sender.sendMessage("§6"+cmd.getUsage()+"§8§l - §7"+cmd.getDescription());
				}
			}
			sender.sendMessage("§8==================================");
			return true;
			
		}
		for(String cmdName : pluginCommands.keySet()){
			
			if(cmdName.equals(thisCmd) || pluginCommands.get(cmdName).contains(thisCmd)
									   || pluginCommands.get(cmdName).contains("prison"+thisCmd))
			{
				PluginCommand cmd = plugin.getCommand(cmdName);
				
				if(!sender.hasPermission(cmd.getPermission())){
					sender.sendMessage(cmd.getPermissionMessage());
					return true;
				}
				cmd.getExecutor().onCommand(sender, cmd, thisCmd, args);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length == 1){
			List<String> completions = new ArrayList<String>();
			
			for(String cmdName : pluginCommands.keySet()){
				completions.add(cmdName);
				completions.addAll(pluginCommands.get(cmdName));
			}
			
			List<String> possibleCompletions = TabCompletionHelper.getPossibleCompletionsForGivenArgs(args, completions);
			
			return possibleCompletions;
		}
		return null;
	}
}
