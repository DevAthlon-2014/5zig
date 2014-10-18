package eu.the5zig.effects.game.generators;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ResetPlatformGenerator implements Generator {

	private GeneratorManager manager;

	public ResetPlatformGenerator(GeneratorManager manager) {
		this.manager = manager;
	}

	/**
	 * Resets the platform
	 */
	@Override
	public void generate() {
		Location min = manager.getPlugin().getConfigManager().getPlatformManager().getMin();
		Location max = manager.getPlugin().getConfigManager().getPlatformManager().getMax();

		if (min == null || max == null) return;

		for (int x = min.getBlockX() + 1; x < max.getBlockX(); x++) {
			for (int z = min.getBlockZ() + 1; z < max.getBlockZ(); z++) {
				Location l = new Location(min.getWorld(), x, min.getBlockY(), z);
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.sendBlockChange(l, Material.HARD_CLAY, (byte) 0);
				}
				l.getBlock().setType(Material.HARD_CLAY);
			}
		}
	}
}
