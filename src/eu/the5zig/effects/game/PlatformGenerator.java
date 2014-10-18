package eu.the5zig.effects.game;

import org.bukkit.Location;
import org.bukkit.Material;

import eu.the5zig.effects.Main;

public class PlatformGenerator extends Generator {

	public PlatformGenerator(Main plugin) {
		super(plugin);
	}

	@Override
	public void generate() {
		Location min = plugin.getConfigManager().getPlatformManager().getMin();
		Location max = plugin.getConfigManager().getPlatformManager().getMax();

		if (min == null || max == null) return;

		for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			new Location(min.getWorld(), x, min.getBlockY(), min.getBlockZ()).getBlock().setType(Material.SNOW_BLOCK);
			new Location(min.getWorld(), x, min.getBlockY(), max.getBlockZ()).getBlock().setType(Material.SNOW_BLOCK);
		}
		for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
			new Location(min.getWorld(), min.getBlockX(), min.getBlockY(), z).getBlock().setType(Material.SNOW_BLOCK);
			new Location(min.getWorld(), max.getBlockX(), min.getBlockY(), z).getBlock().setType(Material.SNOW_BLOCK);
		}
	}

}
