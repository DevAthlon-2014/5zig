package eu.the5zig.effects.game;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import eu.the5zig.effects.Main;
import eu.the5zig.effects.util.ItemStackUtils;

public class GameManager {

	private Main plugin;

	private List<UUID> dontMove = Lists.newArrayList();

	private List<UUID> spectating = Lists.newArrayList();

	public GameManager(Main plugin) {
		this.plugin = plugin;
	}

	public boolean isFrozen(Player p) {
		return dontMove.contains(p.getUniqueId());
	}

	public boolean isSpectating(Player p) {
		return spectating.contains(p.getUniqueId());
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
			dontMove.add(player.getUniqueId());
		}
		plugin.getGeneratorManager().getResetPlatformGenerator().generate();

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				plugin.getGeneratorManager().getPathGenerator().generate();
			}
		}, 10);
	}

	private Location getSpectateLocation() {
		Location min = plugin.getConfigManager().getPlatformManager().getMin();
		Location max = plugin.getConfigManager().getPlatformManager().getMax();
		int x = min.getBlockX() + (max.getBlockX() - min.getBlockX()) / 2;
		int z = min.getBlockZ() + (max.getBlockZ() - min.getBlockZ()) / 2;
		Location loc = new Location(min.getWorld(), x, min.getY() + 5, z, 0, 90);
		return loc;
	}

	private Location getGameLocation() {
		Location min = plugin.getConfigManager().getPlatformManager().getMin();
		Location max = plugin.getConfigManager().getPlatformManager().getMax();
		int x = min.getBlockX() + (max.getBlockX() - min.getBlockX()) / 2;
		int z = min.getBlockZ() + (max.getBlockZ() - min.getBlockZ()) / 2;
		Location loc = new Location(min.getWorld(), x, min.getY() + 5, z, 0, 90);
		return loc;
	}

	public void changeMode(Player p) {
		if (isSpectating(p)) {
			spectating.remove(p.getUniqueId());
			dontMove.add(p.getUniqueId());
			p.getInventory().clear();
			p.getInventory().setItem(4, ItemStackUtils.newItemStack(Material.REDSTONE).name("§b§lJoin").getItemStack());
			p.teleport(getSpectateLocation());
		} else {
			spectating.add(p.getUniqueId());
			dontMove.remove(p.getUniqueId());
			p.getInventory().clear();
			p.getInventory().setItem(4, ItemStackUtils.newItemStack(Material.SUGAR).name("§b§lSpectate").getItemStack());
			p.teleport(getGameLocation());
		}
	}

}
