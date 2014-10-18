package eu.the5zig.effects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.the5zig.effects.config.PlatformManager;

public class PathfindCommand implements CommandExecutor {

	private Main plugin;

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
						PlatformManager m = plugin.getConfigManager().getPlatformManager();
						if (m.isSelecting(p)) {
							m.removeFromSelectList(p);
							p.sendMessage(Formatting.PREFIX + "You are no longer selecting the platform!");
						} else {
							m.addToSelectList(p);
							p.sendMessage(Formatting.PREFIX + "Left- or rightclick a block to set the platform");
						}
					}
				} else if (args[0].equalsIgnoreCase("start")) {
					plugin.getGameTask().setSeconds(1);
				} else {
					showHelp(sender);
				}
			} else {
				showHelp(sender);
			}
		}
		return false;
	}

	private void showHelp(CommandSender sender) {
		sender.sendMessage(Formatting.PREFIX + "Help");
		sender.sendMessage(Formatting.PREFIX + "/pathfind help - Show this help");
		sender.sendMessage(Formatting.PREFIX + "/pathfind start - Start the game immediately");
		sender.sendMessage(Formatting.PREFIX + "/pathfind setborder - Sets the platform of the game");
	}

}
