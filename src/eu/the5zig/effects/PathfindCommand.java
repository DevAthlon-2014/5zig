package eu.the5zig.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

import eu.the5zig.effects.config.PlatformManager;

public class PathfindCommand implements CommandExecutor, TabCompleter {

	private Main plugin;
	private final List<String> ARGS = Arrays.asList("setborder", "start");

	public PathfindCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("pathfind")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("setborder")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (p.hasPermission("pathfind.setborder")) {
							PlatformManager m = plugin.getConfigManager().getPlatformManager();
							if (m.isSelecting(p)) {
								m.removeFromSelectList(p);
								p.sendMessage(Formatting.PREFIX + "You are no longer selecting the platform!");
							} else {
								m.addToSelectList(p);
								p.sendMessage(Formatting.PREFIX + "Left- or rightclick a block to set the platform");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "You don't have access to that command!");
						}
					}
				} else if (args[0].equalsIgnoreCase("start")) {
					if (sender.hasPermission("pathfind.start")) {
						if (!plugin.getGameManager().isInGame()) {
							plugin.getGameTask().setSeconds(1);
						} else {
							sender.sendMessage(Formatting.PREFIX + "Game already in progress!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You don't have access to that command!");
					}
				} else {
					showHelp(sender);
				}
			} else {
				showHelp(sender);
			}
		}
		return true;
	}

	private void showHelp(CommandSender sender) {
		sender.sendMessage(Formatting.PREFIX + "Help");
		sender.sendMessage(Formatting.PREFIX + "/pathfind help - Show this help");
		if (sender.hasPermission("pathfind.start")) sender.sendMessage(Formatting.PREFIX + "/pathfind start - Start the game immediately");
		if (sender.hasPermission("pathfind.setborder")) sender.sendMessage(Formatting.PREFIX + "/pathfind setborder - Sets the platform of the game");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length == 1) return (List<String>) StringUtil.copyPartialMatches(args[0], ARGS, new ArrayList<String>(ARGS.size()));
		return ImmutableList.of();
	}

}
