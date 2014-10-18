package eu.the5zig.effects.game.generators;

import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;

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

			int i = r.nextInt(100);
			if (i >= 0 && i < 40) {
				// increment z
				zp++;
			} else if (i >= 40 && i < 60) {
				// increment x
				xp++;
			} else if (i >= 60 && i < 100) {
				// decrease z
				zp--;
			}

			boolean xb = true;
			for (Location loc : blocks)
				if (loc.getBlockX() == xp - 1 && loc.getBlockZ() == zp) xb = false;

			if (lastz != zp && zp < max.getBlockZ() && zp > min.getBlockZ() && xb) {
				z = zp;
				lastz = z;
			} else if (lastx != xp && xp < max.getBlockX() && xp > min.getBlockX()) {
				x = xp;
				lastx = x;
			} else {
				zp = z;
				xp = x;
				continue;
			}

			Location loc = new Location(min.getWorld(), x, y, z);

			if (!blocks.contains(loc)) {
				blocks.add(loc);
			}
		}

		runTaskTimer(manager.getPlugin(), 0, 4);
	}

	private int index = 0;

	@Override
	public void run() {
//		if (index >= blocks.size()) {
//			cancel();
//			// manager.getPlugin().getGameManager().
//			return;
//		}

		Location loc = blocks.get(index++ % blocks.size());
//		loc.getBlock().setTypeIdAndData(Material.WOOL.getId(), (byte) 5, true);
		for (double x = loc.getX(); x <= loc.getX() + 1; x += 0.5) {
			for (double z = loc.getZ(); z <= loc.getZ() + 1; z += 0.5) {
				Effect.animate(ParticleEffect.INSTANT_SPELL, new Location(loc.getWorld(), x, loc.getY() + 1, z));
			}
		}
	}
}
