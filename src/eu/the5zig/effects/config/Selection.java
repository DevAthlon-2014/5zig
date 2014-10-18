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

	/**
	 * Tries to get the location with smallest x and z values
	 * 
	 * @return the smallest Location
	 */
	public Location getMin() {
		if (min == null && max == null) return null;
		if (min != null && max == null) return min;
		if (min == null && max != null) return max;

		int x = min.getBlockX() < max.getBlockX() ? min.getBlockX() : max.getBlockX();
		int y = min.getBlockY() < max.getBlockY() ? min.getBlockY() : max.getBlockY();
		int z = min.getBlockZ() < max.getBlockZ() ? min.getBlockZ() : max.getBlockZ();
		return new Location(min.getWorld(), x, y, z);
	}

	/**
	 * Tries to get the location with biggest x and z values
	 * 
	 * @return the biggest Location
	 */
	public Location getMax() {
		if (min == null && max == null) return null;
		if (min != null && max == null) return min;
		if (min == null && max != null) return max;

		int x = min.getBlockX() > max.getBlockX() ? min.getBlockX() : max.getBlockX();
		int y = min.getBlockY() > max.getBlockY() ? min.getBlockY() : max.getBlockY();
		int z = min.getBlockZ() > max.getBlockZ() ? min.getBlockZ() : max.getBlockZ();
		return new Location(min.getWorld(), x, y, z);
	}

	/**
	 * Returns if both locations have been set
	 * 
	 * @return
	 */
	public boolean hasMinAndMax() {
		return min != null && max != null;
	}

}
