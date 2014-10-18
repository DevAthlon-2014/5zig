package eu.the5zig.effects.game.generators;

import eu.the5zig.effects.Main;

public class GeneratorManager {

	private Main plugin;

	private BorderGenerator borderGenerator;
	private ResetPlatformGenerator resetPlatformGenerator;
	private PathGenerator pathGenerator;

	public GeneratorManager(Main plugin) {
		this.plugin = plugin;

		borderGenerator = new BorderGenerator(this);
		resetPlatformGenerator = new ResetPlatformGenerator(this);
		pathGenerator = new PathGenerator(this);
	}

	public BorderGenerator getBorderGenerator() {
		return borderGenerator;
	}

	public ResetPlatformGenerator getResetPlatformGenerator() {
		return resetPlatformGenerator;
	}

	public PathGenerator getPathGenerator() {
		return pathGenerator;
	}

	public Main getPlugin() {
		return plugin;
	}
	
}
