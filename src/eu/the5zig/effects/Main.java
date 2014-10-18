package eu.the5zig.effects;

import org.bukkit.plugin.java.JavaPlugin;

import eu.the5zig.effects.config.ConfigManager;
import eu.the5zig.effects.game.GameTask;
import eu.the5zig.effects.game.generators.BorderGenerator;
import eu.the5zig.effects.game.generators.ResetPlatformGenerator;

public class Main extends JavaPlugin {

	private GameTask gameTask;

	private ConfigManager configManager;

	private BorderGenerator borderGenerator;
	private ResetPlatformGenerator resetPlatformGenerator;

	@Override
	public void onEnable() {
		configManager = new ConfigManager(this);
		borderGenerator = new BorderGenerator(this);
		resetPlatformGenerator = new ResetPlatformGenerator(this);
		gameTask = new GameTask(this);

		getCommand("effect").setExecutor(new EffectCommand(this));

		getServer().getPluginManager().registerEvents(new BorderSelectionListener(this), this);
	}

	@Override
	public void onDisable() {

	}

	/**
	 * Returns the ConfigManager class
	 * 
	 * @return the ConfigManager class
	 */
	public ConfigManager getConfigManager() {
		return configManager;
	}

	/**
	 * Returns the BorderGenerator class that generates the border of the plattform
	 * 
	 * @return the BorderGenerator class
	 */
	public BorderGenerator getPlatformGenerator() {
		return borderGenerator;
	}

	/**
	 * Returns the ResetPlatformGenerator class that resets the platform and fills it with snow again
	 * 
	 * @return the ResetPlatformGenerator class
	 */
	public ResetPlatformGenerator getResetPlatformGenerator() {
		return resetPlatformGenerator;
	}

}
