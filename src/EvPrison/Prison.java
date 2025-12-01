package EvPrison;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import EvPrison.commands.CommandBail;
import EvPrison.commands.CommandJail;
import EvPrison.commands.CommandPrisoners;
import EvPrison.commands.CommandUnjail;
import EvPrison.listeners.DamageByEntityListener;
import EvPrison.listeners.DamageListener;
import EvPrison.listeners.RespawnListener;
import EvPrison.listeners.TeleportListener;
import net.evmodder.EvLib.bukkit.EvPlugin;
import net.evmodder.EvLib.hooks.VaultHook;
import net.evmodder.EvLib.bukkit.Section;

public final class Prison extends EvPlugin{
	private YamlConfiguration jails;
	private Map<String, Jail> jailMap;
	private static Prison plugin; public static Prison getPlugin(){return plugin;}
	private String defaultJail; public String getDefaultJail(){return defaultJail;}
	
	@Override public void onEvEnable(){
		plugin = this;
		jailMap = new HashMap<String, Jail>();
		loadJails();
		new VaultHook(this);
		registerListeners();
		registerCommands();
		new BukkitRunnable(){
			@Override public void run(){
				
			}
		}.runTaskTimer(this, 20, 20);//wait 1 second, than run every second.
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
			
			if(defaultJail == null || data.getBoolean("default")) defaultJail = jailName;
			
			String[] min = data.getString("min").split(","), max = data.getString("max").split(",");
//			String[] warp = data.getString("warp").split(",");

			String worldName = data.getString("world");
//			World world;
			if(worldName.isEmpty()) worldName = (/*world=*/getServer().getWorlds().get(0)).getName();
//			else world = getServer().getWorld(worldName);

			Section bounds;
//			Location teleport;
			try{ bounds = new Section(worldName,
					Integer.parseInt(max[0]), Integer.parseInt(min[0]),
					Integer.parseInt(max[1]), Integer.parseInt(min[1]),
					Integer.parseInt(max[2]), Integer.parseInt(min[2]));

//				teleport = new Location(world, Integer.parseInt(warp[0]), Integer.parseInt(warp[1]), Integer.parseInt(warp[2]));
			}
			catch(NumberFormatException ex){getLogger().severe("Error loading coordinates: " + jailName); continue;}
			
			Set<UUID> inmates = new HashSet<UUID>();
			for(String prisoner : data.getStringList("inmates")) inmates.add(UUID.fromString(prisoner));
			jailMap.put(jailName.toLowerCase(), new Jail(jailName, inmates, bounds));
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
		if(config.getBoolean("can-take-damage-in-jail")){
			getServer().getPluginManager().registerEvents(new DamageListener(), this);
		}
		if(config.getBoolean("can-deal-damage-in-jail")){
			getServer().getPluginManager().registerEvents(new DamageByEntityListener(), this);
		}
	}
	private void registerCommands(){
		new CommandJail(this);
		new CommandUnjail(this);
		new CommandPrisoners(this);
		if(plugin.getConfig().getBoolean("enable-bails")) new CommandBail(this);
	}
	
	//--------------- Member functions ------------------------------------------------------
	//TODO: Move this into a library interface
	public Set<UUID> getAllPrisoners(){
		Set<UUID> prisonerNames = new HashSet<UUID>();
		for(Jail jail : jailMap.values()) prisonerNames.addAll(jail.inmates);
		return prisonerNames;
	}
	
	public boolean isPrisoner(UUID playerUUID){
		for(String jail : jailMap.keySet()){
			if(jailMap.get(jail).inmates.contains(playerUUID)) return true;
		}
		return false;
	}
	
	public boolean isJail(String jailName){
		return jailMap.keySet().contains(jailName.toLowerCase());
	}
	
	public boolean isInJail(UUID playerUUID, String jailName){
		return jailMap.get(jailName).inmates.contains(playerUUID);
	}
	
	public Set<UUID> getPrisoners(String jailName){
		return jailMap.get(jailName).inmates;
	}
	
	public boolean jail(UUID playerUUID, String jailName, long time){
		// Commented out because it might be okay to be jailed in multiple jails
//		if(isPrisoner(playerUUID) || !prisonerMap.containsKey(jailName)) return false;
		
		Jail jail = jailMap.get(jailName.toLowerCase());
		if(jail == null) return false;
		jail.inmates.add(playerUUID);
		jails.getStringList(jail.fullName+".inmates").add(playerUUID.toString());
		saveJails();
		return true;
	}
		
	public boolean unjail(UUID playerUUID, String jailName){
		jailName = jailName.toLowerCase();
		if(!jailMap.containsKey(jailName) || !jailMap.get(jailName).inmates.remove(playerUUID)) return false;
		
		jails.getStringList(jailMap.get(jailName).fullName+".inmates").remove(playerUUID.toString());
		saveJails();
		return true;
	}
	
	public boolean unjail(UUID playerUUID){
		boolean contained = false;
		for(String jailName : jails.getKeys(false)){
			if(jailMap.get(jailName.toLowerCase()).inmates.remove(playerUUID)){
				jails.getStringList(jailName+".inmates").remove(playerUUID.toString());
				contained = true;
			}
		}
		if(contained) saveJails();
		return contained;
	}
	
	public long getJailTimeLeft(UUID playerUUID){
		return 10;//TODO: fill in stub
	}
}