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

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public BorderGenerator getPlatformGenerator() {
		return borderGenerator;
	}

	public ResetPlatformGenerator getResetPlatformGenerator() {
		return resetPlatformGenerator;
	}

}
