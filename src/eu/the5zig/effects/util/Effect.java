package eu.the5zig.effects.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.comphenix.packetwrapper.WrapperPlayServerWorldParticles;
import com.comphenix.packetwrapper.WrapperPlayServerWorldParticles.ParticleEffect;

public class Effect {
	
	public static void animate(ParticleEffect effect, Location loc) {
		WrapperPlayServerWorldParticles packet = new WrapperPlayServerWorldParticles(effect, 15, loc, new Vector());
        
		for (Player p : Bukkit.getOnlinePlayers()) {
			packet.sendPacket(p);
		}
	}
	
}
