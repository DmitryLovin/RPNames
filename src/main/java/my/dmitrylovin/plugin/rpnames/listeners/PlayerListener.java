package my.dmitrylovin.plugin.rpnames.listeners;

import my.dmitrylovin.plugin.rpnames.RPNames;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import my.dmitrylovin.plugin.rpnames.utils.RPNamesUtils;

public class PlayerListener implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		RPNamesUtils.loadName(event.getPlayer().getUniqueId().toString());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		RPNamesUtils.unloadName(event.getPlayer().getUniqueId().toString());
	}
}
