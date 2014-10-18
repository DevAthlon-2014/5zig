package eu.the5zig.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.the5zig.effects.config.PlatformManager;

public class BorderSelectionListener implements Listener {

	private Main plugin;

	public BorderSelectionListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			PlatformManager m = plugin.getConfigManager().getPlatformManager();
			if (m.isSelecting(p)) {
				e.setCancelled(true);
				m.onSelect(p, e.getClickedBlock().getLocation(), e.getAction() == Action.LEFT_CLICK_BLOCK ? m.MIN : m.MAX);
			}
		}
	}

}
