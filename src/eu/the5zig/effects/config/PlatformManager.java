package eu.the5zig.effects.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.util.com.google.common.collect.Maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import eu.the5zig.effects.Main;

public class PlatformManager {

	private Main plugin;

	private HashMap<UUID, Selection> selecting = Maps.newHashMap();

	private FileConfiguration borderConfig;

	public final int MIN = 0, MAX = 1;

	public PlatformManager(Main plugin) {
		this.plugin = plugin;
		borderConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "borders.yml"));
	}

	public void addToSelectList(Player p) {
		selecting.put(p.getUniqueId(), new Selection());
	}

	public void removeFromSelectList(Player p) {
		selecting.remove(p.getUniqueId());
	}

	public boolean isSelecting(Player p) {
		return selecting.containsKey(p.getUniqueId());
	}

	public Selection getSelection(Player p) {
		return selecting.get(p.getUniqueId());
	}

	public void onSelect(Player p, Location block, int type) {
		if (isSelecting(p)) {
			Selection s = getSelection(p);
			if (type == MIN) {
				s.selectMin(block);
				p.sendMessage("Location #1 at " + block.getBlockX() + ", " + block.getBlockY() + ", " + block.getBlockZ());
			} else if (type == MAX) {
				s.selectMax(block);
				p.sendMessage("Location #2 at " + block.getBlockX() + ", " + block.getBlockY() + ", " + block.getBlockZ());
			}
			if (s.hasMinAndMax()) {
				p.sendMessage("Region Selected!");
				borderConfig.set("min.x", s.getMin().getBlockX());
				borderConfig.set("min.y", s.getMin().getBlockY());
				borderConfig.set("min.z", s.getMin().getBlockZ());
				borderConfig.set("min.world", s.getMin().getWorld().getName());

				borderConfig.set("max.x", s.getMax().getBlockX());
				borderConfig.set("max.y", s.getMax().getBlockY());
				borderConfig.set("max.z", s.getMax().getBlockZ());
				borderConfig.set("max.world", s.getMax().getWorld().getName());

				try {
					borderConfig.save(new File(plugin.getDataFolder(), "borders.yml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				plugin.getPlatformGenerator().generate();
				plugin.getResetPlatformGenerator().generate();
				
				removeFromSelectList(p);
			}
		}
	}

	public List<Location> getBorderBlocks() {
		if (!borderConfig.contains("min") || !borderConfig.contains("max")) return Lists.newArrayList();

		Location min = new Location(Bukkit.getWorld(borderConfig.getString("min.world")), borderConfig.getInt("min.x"), borderConfig.getInt("min.y"), borderConfig.getInt("min.z"));
		Location max = new Location(Bukkit.getWorld(borderConfig.getString("max.world")), borderConfig.getInt("max.x"), borderConfig.getInt("max.y"), borderConfig.getInt("max.z"));

		List<Location> blocks = Lists.newArrayList();
		for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
				blocks.add(new Location(min.getWorld(), x, min.getY(), z));
			}
		}
		return blocks;
	}

	public Location getMin() {
		if (!borderConfig.contains("min")) return null;

		Location min = new Location(Bukkit.getWorld(borderConfig.getString("min.world")), borderConfig.getInt("min.x"), borderConfig.getInt("min.y"), borderConfig.getInt("min.z"));
		return min;
	}

	public Location getMax() {
		if (!borderConfig.contains("max")) return null;

		Location min = new Location(Bukkit.getWorld(borderConfig.getString("max.world")), borderConfig.getInt("max.x"), borderConfig.getInt("max.y"), borderConfig.getInt("max.z"));
		return min;
	}

}
