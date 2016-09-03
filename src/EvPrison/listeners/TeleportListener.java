package EvPrison.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onTp(PlayerTeleportEvent evt){
		//TODO: implement
	}
}
