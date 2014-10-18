package eu.the5zig.effects.game.generators;

import org.bukkit.Location;
import org.bukkit.Material;

import eu.the5zig.effects.Main;

public class BorderGenerator extends Generator {

	public BorderGenerator(Main plugin) {
		super(plugin);
	}

	@Override
	public void generate() {
		Location min = plugin.getConfigManager().getPlatformManager().getMin();
		Location max = plugin.getConfigManager().getPlatformManager().getMax();

		if (min == null || max == null) return;

		for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			new Location(min.getWorld(), x, min.getBlockY(), min.getBlockZ()).getBlock().setTypeIdAndData(Material.WOOL.getId(), (byte) 15, true);
			new Location(min.getWorld(), x, min.getBlockY(), max.getBlockZ()).getBlock().setTypeIdAndData(Material.WOOL.getId(), (byte) 15, true);
		}
		for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
			new Location(min.getWorld(), min.getBlockX(), min.getBlockY(), z).getBlock().setTypeIdAndData(Material.WOOL.getId(), (byte) 15, true);
			new Location(min.getWorld(), max.getBlockX(), min.getBlockY(), z).getBlock().setTypeIdAndData(Material.WOOL.getId(), (byte) 15, true);
		}
	}

}
