package eu.the5zig.effects.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayServerWorldParticles;
import com.comphenix.packetwrapper.WrapperPlayServerWorldParticles.ParticleEffect;

public class Effect {
	
	public static void animate(ParticleEffect effect, Location loc) {
		WrapperPlayServerWorldParticles packet = new WrapperPlayServerWorldParticles();
        
        packet.setNumberOfParticles(5);
        packet.setOffsetX(0);
        packet.setOffsetZ(0);
        packet.setOffsetY(0);
        packet.setLocation(loc);
        packet.setParticleEffect(effect);
        packet.setParticleName(effect.getParticleName());
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			packet.sendPacket(p);
		}
	}
	
}
