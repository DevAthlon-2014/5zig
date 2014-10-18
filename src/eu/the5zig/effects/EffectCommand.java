package eu.the5zig.effects;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.the5zig.effects.config.PlatformManager;

public class EffectCommand implements CommandExecutor {

	private Main plugin;

	public EffectCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("effect")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("setborder")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						PlatformManager m = plugin.getConfigManager().getPlatformManager();

						if (m.isSelecting(p)) {
							m.removeFromSelectList(p);
							p.sendMessage("No longer Selecting");
						} else {
							m.addToSelectList(p);
							p.sendMessage("Left- or Rightclick a block to select the border");
						}
					}
				}
			}
		}
		return false;
	}

	private List<String> getHelp() {
		return Arrays.asList("/effect setborder - Sets the border");
	}

}
