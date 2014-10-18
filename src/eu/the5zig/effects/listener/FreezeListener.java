package eu.the5zig.effects.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import eu.the5zig.effects.Main;

public class FreezeListener implements Listener {

	private Main plugin;

	public FreezeListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (plugin.getGameManager().isFrozen(p)) p.teleport(e.getFrom());
	}

}
