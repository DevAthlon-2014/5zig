package eu.the5zig.effects.game.generators;

import org.bukkit.Location;
import org.bukkit.Material;

public class BorderGenerator implements Generator {

	private GeneratorManager manager;

	public BorderGenerator(GeneratorManager manager) {
		this.manager = manager;
	}

	@Override
	public void generate() {
		Location min = manager.getPlugin().getConfigManager().getPlatformManager().getMin();
		Location max = manager.getPlugin().getConfigManager().getPlatformManager().getMax();

		if (min == null || max == null) return;

		for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			new Location(min.getWorld(), x, min.getBlockY(), min.getBlockZ()).getBlock().setTypeIdAndData(Material.WOOL.getId(), (byte) 13, true);
			new Location(min.getWorld(), x, min.getBlockY(), max.getBlockZ()).getBlock().setTypeIdAndData(Material.WOOL.getId(), (byte) 13, true);
		}
		for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
			new Location(min.getWorld(), min.getBlockX(), min.getBlockY(), z).getBlock().setTypeIdAndData(Material.WOOL.getId(), (byte) 13, true);
			new Location(min.getWorld(), max.getBlockX(), min.getBlockY(), z).getBlock().setTypeIdAndData(Material.WOOL.getId(), (byte) 13, true);
		}
	}

}
