package eu.the5zig.effects.game.generators;

import org.bukkit.Location;
import org.bukkit.Material;

public class ResetPlatformGenerator implements Generator {

	private GeneratorManager manager;

	public ResetPlatformGenerator(GeneratorManager manager) {
		this.manager = manager;
	}

	@Override
	public void generate() {
		Location min = manager.getPlugin().getConfigManager().getPlatformManager().getMin();
		Location max = manager.getPlugin().getConfigManager().getPlatformManager().getMax();

		if (min == null || max == null) return;

		for (int x = min.getBlockX() + 1; x < max.getBlockX(); x++) {
			for (int z = min.getBlockZ() + 1; z < max.getBlockZ(); z++) {
				new Location(min.getWorld(), x, min.getBlockY(), z).getBlock().setType(Material.HARD_CLAY);
			}
		}
	}
}
