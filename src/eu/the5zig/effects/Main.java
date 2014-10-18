package eu.the5zig.effects;

import org.bukkit.plugin.java.JavaPlugin;

import eu.the5zig.effects.config.ConfigManager;
import eu.the5zig.effects.game.PlatformGenerator;

public class Main extends JavaPlugin {

	private ConfigManager configManager;

	private PlatformGenerator platformGenerator;

	@Override
	public void onEnable() {
		configManager = new ConfigManager(this);
		platformGenerator = new PlatformGenerator(this);

		getCommand("effect").setExecutor(new EffectCommand(this));

		getServer().getPluginManager().registerEvents(new BorderSelectionListener(this), this);
	}

	@Override
	public void onDisable() {

	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public PlatformGenerator getPlatformGenerator() {
		return platformGenerator;
	}

}
