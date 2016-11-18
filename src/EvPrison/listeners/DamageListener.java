package EvPrison.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import EvPrison.Prison;

public class DamageListener implements Listener{
	private Prison plugin;

	public DamageListener(){
		plugin = Prison.getPlugin();
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt){
		if(evt.getEntityType() == EntityType.PLAYER && plugin.isPrisoner(evt.getEntity().getUniqueId())){
			evt.setCancelled(true);
		}
	}
}
