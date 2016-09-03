package EvPrison.commands;

import org.bukkit.command.CommandExecutor;

import EvPrison.Prison;

abstract class BasePrisonCommand implements CommandExecutor{
	Prison plugin;
	
	BasePrisonCommand(){
		plugin = Prison.getPlugin();
		String commandName = getClass().getSimpleName().substring(7);
		plugin.getCommand(commandName).setExecutor(this);
	}
}
