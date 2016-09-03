package EvPrison.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import EvPrison.Prison;

public class DamageListener implements Listener{
	boolean canDealDamage, canTakeDamage;

	public DamageListener(){
		Prison plugin = Prison.getPlugin();
		canDealDamage = plugin.getConfig().getBoolean("can-deal-damage-in-jail");
		canTakeDamage = plugin.getConfig().getBoolean("can-take-damage-in-jail");
	}
	
	//Reduce fall damage
	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt){
		//TODO: implement
	}
}
