package eu.the5zig.effects;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import eu.the5zig.effects.config.ConfigManager;
import eu.the5zig.effects.game.GameManager;
import eu.the5zig.effects.game.GameTask;
import eu.the5zig.effects.game.generators.GeneratorManager;
import eu.the5zig.effects.listener.FreezeListener;
import eu.the5zig.effects.listener.ItemListener;
import eu.the5zig.effects.listener.JoinQuitListener;
import eu.the5zig.effects.listener.MoveListener;
import eu.the5zig.effects.listener.PlayerListener;

public class Main extends JavaPlugin {

	private GameTask gameTask;
	private GameManager gameManager;

	private ConfigManager configManager;
	private GeneratorManager generatorManager;

	@Override
	public void onEnable() {
		configManager = new ConfigManager(this);
		generatorManager = new GeneratorManager(this);
		gameManager = new GameManager(this);
		gameTask = new GameTask(this);

		getCommand("pathfind").setExecutor(new PathfindCommand(this));

		PluginManager plManager = getServer().getPluginManager();
		plManager.registerEvents(new BorderSelectionListener(this), this);
		plManager.registerEvents(new FreezeListener(this), this);
		plManager.registerEvents(new ItemListener(this), this);
		plManager.registerEvents(new MoveListener(this), this);
		plManager.registerEvents(new JoinQuitListener(this), this);
		plManager.registerEvents(new PlayerListener(), this);
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
	 * Returns the GeneratorManager class that manages all Generators
	 * 
	 * @return the GeneratorManager class
	 */
	public GeneratorManager getGeneratorManager() {
		return generatorManager;
	}

	/**
	 * Returns the GameTask class that manages the countdown
	 * 
	 * @return
	 */
	public GameTask getGameTask() {
		return gameTask;
	}

	/**
	 * 
	 * @return
	 */
	public GameManager getGameManager() {
		return gameManager;
	}
	
	public void startGameTask() {
		gameTask = new GameTask(this);
	}

}
