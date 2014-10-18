package eu.the5zig.effects.game;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.util.com.google.common.collect.Maps;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Lists;

import eu.the5zig.effects.Formatting;
import eu.the5zig.effects.Main;
import eu.the5zig.effects.util.ItemStackUtils;

public class GameManager {

	private Main plugin;

	private boolean inGame;

	private List<UUID> dontMove = Lists.newArrayList();
	private List<UUID> playing = Lists.newArrayList();

	private HashMap<UUID, Integer> scores = Maps.newHashMap();

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

	public boolean isInGame() {
		return inGame;
	}

	/**
	 * Start the game!
	 */
	public void startGame() {
		if (Bukkit.getOnlinePlayers().length < 1) {
			Bukkit.broadcastMessage(Formatting.PREFIX + "Not enough players online! Resetting countdown");
			plugin.startGameTask();
			return;
		}
		inGame = true;
		Bukkit.broadcastMessage(Formatting.PREFIX + "The game has begun!");
		Bukkit.broadcastMessage(Formatting.PREFIX + "Remember the path! If you are ready click on the redstone item in your inventory!");
		Bukkit.broadcastMessage(Formatting.PREFIX + "First player that reaches the end wins!");
		for (Player player : Bukkit.getOnlinePlayers()) {
			playing.add(player.getUniqueId());
			changeMode(player);

			for (Player p : Bukkit.getOnlinePlayers()) {
				p.hidePlayer(player);
			}
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
		return loc.add(0.5, 0, 0.5);
	}

	/**
	 * Either teleports the player to the platform or sets it to spectator mode again
	 * 
	 * @param Player
	 *            player
	 */
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

	/**
	 * Called when a player reaches the last block.
	 * 
	 * @param Player
	 *            player
	 */
	public void endGame(Player p) {
		Bukkit.broadcastMessage(Formatting.PREFIX + p.getName() + " won this round!");
		Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.AQUA).withFade(Color.GREEN).with(Type.BALL).trail(true).build();
		fwm.addEffect(effect);
		fwm.setPower(1);
		fw.setFireworkMeta(fwm);

		scores.put(p.getUniqueId(), scores.containsKey(p.getUniqueId()) ? scores.get(p.getUniqueId()) + 1 : 1);
		if (scores.get(p.getUniqueId()) > 3) {
			inGame = false;
			Bukkit.broadcastMessage(Formatting.PREFIX + p.getName() + " won the game!");

			for (Player player : Bukkit.getOnlinePlayers()) {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					pl.showPlayer(player);
				}
			}
		}
		Bukkit.broadcastMessage(Formatting.PREFIX + "Top " + (scores.size() < 3 ? scores.size() : 3) + ":");
		List<UUID> players = Lists.newArrayList(scores.keySet());
		Collections.sort(players, new Comparator<UUID>() {
			@Override
			public int compare(UUID u1, UUID u2) {
				return scores.get(u2) - scores.get(u1);
			}
		});
		for (int i = 0; i < players.size(); i++) {
			if (i > 3) break;
			Bukkit.broadcastMessage(Formatting.PREFIX + (i + 1) + ": " + Bukkit.getPlayer(players.get(i)).getName() + " - " + scores.get(players.get(i)));
		}

		playing.clear();
		dontMove.clear();
		for (Player player : Bukkit.getOnlinePlayers()) {
			playing.add(player.getUniqueId());
			changeMode(player);
		}
		plugin.getGeneratorManager().getResetPlatformGenerator().generate();

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				plugin.getGeneratorManager().resetPathGenerator();
				plugin.getGeneratorManager().getPathGenerator().generate();
			}
		}, 10);
	}
}
