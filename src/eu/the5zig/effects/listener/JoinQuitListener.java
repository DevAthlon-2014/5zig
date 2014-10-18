package eu.the5zig.effects.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.the5zig.effects.Main;

public class JoinQuitListener implements Listener {

	private Main plugin;

	public JoinQuitListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (plugin.getGameManager().isInGame()) {
			plugin.getGameManager().unfreeze(p);
			plugin.getGameManager().removeFromPlaying(p);
			plugin.getGameManager().removeFromScore(p);
			if (Bukkit.getOnlinePlayers().length <= 2) {
				plugin.getGameManager().endGame(Bukkit.getOnlinePlayers()[0].getUniqueId().equals(p.getUniqueId()) ? Bukkit.getOnlinePlayers()[1] : Bukkit.getOnlinePlayers()[0], true);
			}
		}
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		if (plugin.getGameManager().isInGame()) e.disallow(Result.KICK_OTHER, "Game in progress!");
	}
}
