package eu.the5zig.effects.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import eu.the5zig.effects.Main;

public class MoveListener implements Listener {

	private Main plugin;

	public MoveListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (!plugin.getGameManager().isInGame()) return;
		if (plugin.getGameManager().isPlaying(p)) {
			if (e.getTo().getBlockX() != e.getFrom().getBlockX() || e.getTo().getBlockZ() != e.getFrom().getBlockZ()) {
				Location min = plugin.getConfigManager().getPlatformManager().getMin();
				Location max = plugin.getConfigManager().getPlatformManager().getMax();
				if (e.getTo().getBlockX() > min.getBlockX() && e.getTo().getBlockX() < max.getBlockX() && e.getTo().getBlockZ() > min.getBlockZ()
						&& e.getTo().getBlockZ() < max.getBlockZ()) {
					List<Location> blocks = plugin.getGameManager().getBlocks();
					Location l = null;
					for (Location loc : blocks) {
						if (loc.clone().add(0, 1, 0).getBlockX() == e.getTo().getBlockX() && loc.clone().add(0, 1, 0).getBlockZ() == e.getTo().getBlockZ()) {
							l = loc;
							break;
						}
					}
					if (l != null) {
						p.sendBlockChange(l, Material.WOOL, (byte) 5);
					} else {
						l = e.getTo().add(0, -1, 0);
						p.sendBlockChange(l, Material.WOOL, (byte) 14);
						p.setVelocity(new Vector());
						p.teleport(plugin.getGameManager().getGameLocation());
					}
					final Location ll = l;
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						@Override
						public void run() {
							p.sendBlockChange(ll, Material.HARD_CLAY, (byte) 0);
						}
					}, 40);
				} else if (e.getTo().getBlockX() >= max.getBlockX() && (e.getTo().getBlockZ() >= min.getBlockZ() || e.getTo().getBlockZ() <= max.getBlockZ())) {
					plugin.getGameManager().endGame(p, false);
				} else if (e.getTo().getBlockX() == min.getBlockX()) {
				} else {
					p.teleport(e.getFrom());
				}
			}
		}
	}
}
