package eu.the5zig.effects.game;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import eu.the5zig.effects.Main;

public class GameManager {

	private Main plugin;

	private List<UUID> dontMove = Lists.newArrayList();

	public GameManager(Main plugin) {
		this.plugin = plugin;
	}

	public void freezePlayer(Player p) {
		dontMove.add(p.getUniqueId());
	}

	public boolean isFrozen(Player p) {
		return dontMove.contains(p.getUniqueId());
	}

	public void unFreezePlayer(Player p) {
		dontMove.remove(p.getUniqueId());
	}

	/**
	 * Start the game!
	 */
	public void startGame() {
		if (Bukkit.getOnlinePlayers().length < 1) {
			Bukkit.broadcastMessage("Not enough players!");
			plugin.startGameTask();
			return;
		}
		Location min = plugin.getConfigManager().getPlatformManager().getMin();
		Location max = plugin.getConfigManager().getPlatformManager().getMax();
		int x = min.getBlockX() + (max.getBlockX() - min.getBlockX()) / 2;
		int z = min.getBlockZ() + (max.getBlockZ() - min.getBlockZ()) / 2;
		Location loc = new Location(min.getWorld(), x, min.getY() + 5, z, 0, 90);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.teleport(loc);
			freezePlayer(player);
		}
		plugin.getGeneratorManager().getResetPlatformGenerator().generate();

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				plugin.getGeneratorManager().getPathGenerator().generate();
			}
		}, 10);
	}

}
