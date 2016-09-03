package EvPrison;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import EvPrison.commands.*;
import EvPrison.listeners.*;

public final class Prison extends JavaPlugin{
	private static Prison plugin; public static Prison getPlugin(){return plugin;}
	private FileConfiguration config; @Override public FileConfiguration getConfig(){return config;}
	
	private YamlConfiguration jails;
	private Map<String, Set<UUID>> prisonerMap;
	private String defaultJail = "Prison";
	
	@Override public void onEnable(){
//		getLogger().info("Loading " + getDescription().getFullName());
		plugin = this;
		config = FileIO.loadConfig(this, "config-prison.yml", getClass().getResourceAsStream("/config.yml"));
		prisonerMap = new HashMap<String, Set<UUID>>();
		
		loadJails();
		new VaultHook(this);
		registerListeners();
		registerCommands();
	}
	
	public void loadJails(){
		File jailsFile = new File("./plugins/EvFolder/jails.yml");
		jails = YamlConfiguration.loadConfiguration(jailsFile);
		if(jails == null){
			//Create Directory and file
			File dir = new File("./plugins/EvFolder");
			if(!dir.exists()) dir.mkdir();
			try{jailsFile.createNewFile();}
			catch(IOException e){e.printStackTrace();}
			jails = YamlConfiguration.loadConfiguration(jailsFile);
		}
		else for(String jailName : jails.getKeys(false)){
			ConfigurationSection data = jails.getConfigurationSection(jailName);
			
			if(data.getBoolean("default")) defaultJail = jailName;
			
			Set<UUID> inmates = new HashSet<UUID>();
			for(String prisoner : data.getStringList("inmates")) inmates.add(UUID.fromString(prisoner));
			prisonerMap.put(jailName, inmates);
		}
	}
	public void saveJails(){
		try{jails.save(new File("./plugins/EvFolder/jails.yml"));}
		catch(IOException e){e.printStackTrace();}
	}
	
	private void registerListeners(){
		if(config.getBoolean("prevent-teleport-out")){
			getServer().getPluginManager().registerEvents(new TeleportListener(), this);
		}
		if(config.getBoolean("respawn-in-jail")){
			getServer().getPluginManager().registerEvents(new RespawnListener(), this);
		}
		if(config.getBoolean("can-receive-damage-in-jail") || config.getBoolean("can-deal-damage-in-jail")){
			getServer().getPluginManager().registerEvents(new DamageListener(), this);
		}
	}
	
	private void registerCommands(){
		new CommandJail();
		new CommandUnjail();
		new CommandPrisoners();
		if(plugin.getConfig().getBoolean("enable-bails")) new CommandBail();
	}
	
	//--------------- Member functions ------------------------------------------------------
	//TODO: Move this into a native library
	public Set<UUID> getAllPrisoners(){
		Set<UUID> prisonerNames = new HashSet<UUID>();
		for(Set<UUID> inmates : prisonerMap.values()) prisonerNames.addAll(inmates);
		return prisonerNames;
	}
	
	public boolean isPrisoner(UUID playerUUID){
		for(String jail : prisonerMap.keySet()){
			if(prisonerMap.get(jail).contains(playerUUID)) return true;
		}
		return false;
	}
	
	public boolean isValidJail(String jailName){
		return prisonerMap.keySet().contains(jailName);
	}
	
	public boolean isInJail(String jailName, UUID playerUUID){
		return prisonerMap.get(jailName).contains(playerUUID);
	}
	
	public Set<UUID> getPrisoners(String jailName){
		return prisonerMap.get(jailName);
	}
	
	public boolean addPrisoner(String jailName, UUID playerUUID){
		// Commented out because it might be okay to be jailed in multiple jails
//		if(isPrisoner(playerUUID) || !prisonerMap.containsKey(jailName)) return false;
		
		prisonerMap.get(jailName).add(playerUUID);
		jails.getStringList(jailName+".inmates").add(playerUUID.toString());
		saveJails();
		return true;
	}
	
	public boolean addPrisoner(UUID playerUUID){
		// Commented out because it might be okay to be jailed in multiple jails
//		if(isPrisoner(playerUUID)) return false;
		
		prisonerMap.get(defaultJail).add(playerUUID);
		jails.getStringList(defaultJail+".inmates").add(playerUUID.toString());
		saveJails();
		return true;
	}
	
	public boolean removePrisoner(String jailName, UUID playerUUID){
		if(!prisonerMap.containsKey(jailName) || !prisonerMap.get(jailName).remove(playerUUID)) return false;
		
		jails.getStringList(jailName+".inmates").remove(playerUUID.toString());
		saveJails();
		return true;
	}
	
	public boolean removePrisoner(UUID playerUUID){
		boolean contained = false;
		for(String jailName : prisonerMap.keySet()){
			if(prisonerMap.get(jailName).remove(playerUUID)){
				jails.getStringList(jailName+".inmates").remove(playerUUID.toString());
				contained = true;
			}
		}
		if(contained) saveJails();
		return contained;
	}
	
	@Override public void saveConfig(){
		try{config.save(new File("./plugins/EvFolder/config-prison.yml"));}
		catch(IOException ex){ex.printStackTrace();}
	}
}
