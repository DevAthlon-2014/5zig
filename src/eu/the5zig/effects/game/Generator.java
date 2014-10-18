package eu.the5zig.effects.game;

import eu.the5zig.effects.Main;

public abstract class Generator {

	protected Main plugin;

	public Generator(Main plugin) {
		this.plugin = plugin;
	}

	public abstract void generate();

}