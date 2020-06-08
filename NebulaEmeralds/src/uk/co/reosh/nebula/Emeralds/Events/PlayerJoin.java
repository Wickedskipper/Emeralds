package uk.co.reosh.nebula.Emeralds.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import uk.co.reosh.nebula.Emeralds.Emeralds;

public class PlayerJoin implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if(!Emeralds.em.userExists(player.getName())) {
			Emeralds.registerPlayer(player);
		}
	}
}
