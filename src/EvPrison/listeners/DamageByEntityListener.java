package EvPrison.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import EvPrison.Prison;

public class DamageByEntityListener implements Listener{
	private Prison plugin;

	public DamageByEntityListener(){
		plugin = Prison.getPlugin();
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent evt){
		if(evt.getDamager().getType() == EntityType.PLAYER && plugin.isPrisoner(evt.getDamager().getUniqueId())){
			evt.setCancelled(true);
		}
	}
}
