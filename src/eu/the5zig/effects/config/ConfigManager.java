package eu.the5zig.effects.config;

import eu.the5zig.effects.Main;

public class ConfigManager {

	protected Main plugin;

	private PlatformManager borderManager;

	public ConfigManager(Main plugin) {
		this.plugin = plugin;
		if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();

		borderManager = new PlatformManager(plugin);
	}

	public PlatformManager getPlatformManager() {
		return borderManager;
	}
	
}
