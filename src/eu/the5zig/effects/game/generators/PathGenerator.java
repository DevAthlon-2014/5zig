package eu.the5zig.effects.game.generators;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;

public class PathGenerator extends BukkitRunnable implements Generator {

	private GeneratorManager manager;
	private final Random r = new Random();

	private List<Location> blocks = Lists.newArrayList();

	public PathGenerator(GeneratorManager manager) {
		this.manager = manager;
	}

	/**
	 * Generates a random path
	 */
	@Override
	public void generate() {
		Location min = manager.getPlugin().getConfigManager().getPlatformManager().getMin();
		Location max = manager.getPlugin().getConfigManager().getPlatformManager().getMax();
		int x = min.getBlockX();
		int y = min.getBlockY();
		int z = min.getBlockZ() + (max.getBlockZ() - min.getBlockZ()) / 2;

		while (x < max.getBlockX()) {
			int zp = z;
			int xp = x;

			int i = r.nextInt(3);
			switch (i) {
			case 0:
				// incement x
				xp++;
				break;
			case 1:
				// incement z
				zp++;
				break;
			case 2:
				// decrease z
				zp--;
				break;
			default:
				break;
			}

			if (zp != z && zp < max.getBlockZ() && zp > min.getBlockZ()) {
				z = zp;
			}
			if (xp != x) {
				x = xp;
			}

			if (xp != x || zp != z) blocks.add(new Location(min.getWorld(), x, y, z));
		}

		runTaskTimer(manager.getPlugin(), 0, 20);
	}

	private int index = 0;

	@Override
	public void run() {
		if (index >= blocks.size()) {
			cancel();
			return;
		}

		Location loc = blocks.get(index++);
		loc.getBlock().setTypeIdAndData(Material.WOOL.getId(), (byte) 5, true);
	}
}
