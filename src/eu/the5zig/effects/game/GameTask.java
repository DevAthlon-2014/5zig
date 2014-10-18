package eu.the5zig.effects.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import eu.the5zig.effects.Main;
import eu.the5zig.effects.config.PlatformManager;

public class GameTask extends BukkitRunnable {

	private Main plugin;

	private int seconds;

	public GameTask(Main plugin) {
		this.plugin = plugin;

		PlatformManager m = plugin.getConfigManager().getPlatformManager();
		if (m.getBorderBlocks().isEmpty()) {
			System.err.println("No Platform Location defined!");
			System.err.println("Please select a Platform ingame with the '/effect setplatform' command and reload the server afterwards!");
			return;
		}

		seconds = 90;
		runTaskTimer(plugin, 10, 20);
	}

	@Override
	public void run() {
		if ((seconds % 20 == 0 || seconds == 10 || seconds <= 5) && seconds != 0) Bukkit.broadcastMessage(seconds + " seconds remaining!");
		seconds--;
		if (seconds < 0) startGame();
	}

	/**
	 * Start the game!
	 */
	private void startGame() {
		if (Bukkit.getOnlinePlayers().length < 2) {
			seconds = 60;
			Bukkit.broadcastMessage("Not enough players!");
			return;
		}
		Location min = plugin.getConfigManager().getPlatformManager().getMin();
		int x = min.getBlockX() / 2;
		int z = min.getBlockZ() / 2;
		int u = x > z ? x : z;
		Location loc = new Location(min.getWorld(), x > z ? x : min.getBlockX(), min.getBlockY(), x > z ? min.getBlockZ() : z);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.teleport(loc);
		}
	}
}
