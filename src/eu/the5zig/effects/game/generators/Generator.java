package eu.the5zig.effects.game.generators;

import eu.the5zig.effects.Main;

public abstract class Generator {

	protected Main plugin;

	public Generator(Main plugin) {
		this.plugin = plugin;
	}

	/**
	 * Generate blocks for the platform
	 */
	public abstract void generate();

}
