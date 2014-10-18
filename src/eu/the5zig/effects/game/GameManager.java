package eu.the5zig.effects.game;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Lists;

import eu.the5zig.effects.Main;
import eu.the5zig.effects.util.ItemStackUtils;

public class GameManager {

	private Main plugin;

	private List<UUID> dontMove = Lists.newArrayList();
	private List<UUID> playing = Lists.newArrayList();

	private List<Location> blocks;

	public GameManager(Main plugin) {
		this.plugin = plugin;
	}

	public boolean isFrozen(Player p) {
		return dontMove.contains(p.getUniqueId());
	}

	public boolean isPlaying(Player p) {
		return playing.contains(p.getUniqueId());
	}

	public void setBlocks(List<Location> blocks) {
		this.blocks = blocks;
	}

	public List<Location> getBlocks() {
		return blocks;
	}
	
	public List<UUID> getPlaying() {
		return playing;
	}
	
	public List<UUID> getDontMove() {
		return dontMove;
	}

	/**
	 * Start the game!
	 */
	public void startGame() {
		if (Bukkit.getOnlinePlayers().length < 1) {
			Bukkit.broadcastMessage("Not enough players!");
			plugin.startGameTask();
			return;
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			playing.add(player.getUniqueId());
			changeMode(player);
		}
		plugin.getGeneratorManager().getResetPlatformGenerator().generate();

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				plugin.getGeneratorManager().getPathGenerator().generate();
			}
		}, 10);
	}

	public Location getSpectateLocation() {
		Location min = plugin.getConfigManager().getPlatformManager().getMin();
		Location max = plugin.getConfigManager().getPlatformManager().getMax();
		int x = min.getBlockX() + (max.getBlockX() - min.getBlockX()) / 2;
		int z = min.getBlockZ() + (max.getBlockZ() - min.getBlockZ()) / 2;
		Location loc = new Location(min.getWorld(), x, min.getY() + 5, z, 0, 90);
		return loc;
	}

	public Location getGameLocation() {
		Location min = plugin.getConfigManager().getPlatformManager().getMin();
		Location max = plugin.getConfigManager().getPlatformManager().getMax();
		int x = min.getBlockX();
		int z = min.getBlockZ() + (max.getBlockZ() - min.getBlockZ()) / 2;
		Location loc = new Location(min.getWorld(), x, min.getY() + 1, z, 270, 0);
		return loc;
	}

	public void changeMode(Player p) {
		if (isPlaying(p)) {
			playing.remove(p.getUniqueId());
			dontMove.add(p.getUniqueId());
			p.getInventory().clear();
			p.getInventory().setItem(4, ItemStackUtils.newItemStack(Material.REDSTONE).name("§b§lJoin").getItemStack());
			p.setAllowFlight(true);
			p.setFlying(true);
			p.teleport(getSpectateLocation());
			p.removePotionEffect(PotionEffectType.JUMP);
		} else {
			playing.add(p.getUniqueId());
			dontMove.remove(p.getUniqueId());
			p.getInventory().clear();
			p.getInventory().setItem(4, ItemStackUtils.newItemStack(Material.SUGAR).name("§b§lSpectate").getItemStack());
			p.teleport(getGameLocation());
			p.setFlying(false);
			p.setAllowFlight(false);
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -100));
		}
	}

	public void endGame(Player p) {
		
	}

}
