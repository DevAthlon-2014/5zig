package eu.the5zig.effects.game.generators;

import org.bukkit.Location;
import org.bukkit.Material;

import eu.the5zig.effects.Main;

public class ResetPlatformGenerator extends Generator {

	public ResetPlatformGenerator(Main plugin) {
		super(plugin);
	}

	@Override
	public void generate() {
		Location min = plugin.getConfigManager().getPlatformManager().getMin();
		Location max = plugin.getConfigManager().getPlatformManager().getMax();

		if (min == null || max == null) return;

		for (int x = min.getBlockX() + 1; x < max.getBlockX(); x++) {
			for (int z = min.getBlockZ() + 1; z < max.getBlockZ(); z++) {
				new Location(min.getWorld(), x, min.getBlockY(), z).getBlock().setType(Material.SNOW_BLOCK);
			}
		}
	}
}
