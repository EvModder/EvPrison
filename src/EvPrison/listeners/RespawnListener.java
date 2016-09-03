package EvPrison.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(PlayerRespawnEvent evt){
		//TODO: teleport to jail on respawn
	}
}
