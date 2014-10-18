package eu.the5zig.effects.config;

import org.bukkit.Location;

public class Selection {

	private Location min, max;

	public Selection() {
	}

	public void selectMin(Location loc) {
		this.min = loc;
	}

	public void selectMax(Location loc) {
		this.max = loc;
	}

	public Location getMin() {
		if (min == null && max == null) return null;
		if (min != null && max == null) return min;
		if (min == null && max != null) return max;

		int x = min.getBlockX() < max.getBlockX() ? min.getBlockX() : max.getBlockX();
		int y = min.getBlockY() < max.getBlockY() ? min.getBlockY() : max.getBlockY();
		int z = min.getBlockZ() < max.getBlockZ() ? min.getBlockZ() : max.getBlockZ();
		return new Location(min.getWorld(), x, y, z);
	}

	public Location getMax() {
		if (min == null && max == null) return null;
		if (min != null && max == null) return min;
		if (min == null && max != null) return max;

		int x = min.getBlockX() > max.getBlockX() ? min.getBlockX() : max.getBlockX();
		int y = min.getBlockY() > max.getBlockY() ? min.getBlockY() : max.getBlockY();
		int z = min.getBlockZ() > max.getBlockZ() ? min.getBlockZ() : max.getBlockZ();
		return new Location(min.getWorld(), x, y, z);
	}

	public boolean hasMinAndMax() {
		return min != null && max != null;
	}

}
