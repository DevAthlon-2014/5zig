package eu.the5zig.effects.game.generators;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.packetwrapper.WrapperPlayServerWorldParticles.ParticleEffect;
import com.google.common.collect.Lists;

import eu.the5zig.effects.util.Effect;

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
		int x = min.getBlockX() + 1;
		int y = min.getBlockY();
		int z = min.getBlockZ() + (max.getBlockZ() - min.getBlockZ()) / 2;

		int lastx = x;
		int lastz = z;
		
		while (x < max.getBlockX() - 1) {
			int zp = z;
			int xp = x;

			int i = r.nextInt(3);
			switch (i) {
			case 0:
				// increment x
				xp++;
				break;
			case 1:
				// increment z
				zp++;
				break;
			case 2:
				// decrease z
				zp--;
				break;
			default:
				break;
			}

			boolean add = false;
			
			boolean xb = true;
			for (Location loc : blocks) if (loc.getBlockX() == xp - 1 && loc.getBlockZ() == zp) xb = false;
			
			if (lastz != zp && zp < max.getBlockZ() && zp > min.getBlockZ() && xb) {
				z = zp;
				lastz = z;
				add = true;
			} else if (lastx != xp && xp < max.getBlockX() && xp > min.getBlockX()) {
				x = xp;
				lastx = x;
				add = true;
			}

			Location loc = new Location(min.getWorld(), x, y, z);

			if (add && !blocks.contains(loc)) {
				blocks.add(loc);
			}
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
		Effect.animate(ParticleEffect.FLAME, loc);
	}
}
