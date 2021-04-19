package fr.hugovalcraft.deathswap.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.stats.DeathSwapStats;
import fr.hugovalcraft.deathswap.utils.UUIDtoName;

public class UpdateList implements Listener {
	
	@EventHandler
	public void onUpdateList(ServerListPingEvent e) {
		
		e.setMaxPlayers(JoinServer.PlayerMin);
		
		if(DeathSwapStats.isState(DeathSwapStats.WAIT)) {
			
			e.setMotd("§aEn attente de joueur...");
			
		}else if(DeathSwapStats.isState(DeathSwapStats.INVULNERABLE)) {
			
			e.setMotd("§cLa partie vient de débuter");
			
		}else if(DeathSwapStats.isState(DeathSwapStats.INGAME)) {
			
			e.setMotd("§cPartie en cours...");
			
		}else if(DeathSwapStats.isState(DeathSwapStats.END) && !Main.getInstance().online.isEmpty()) {
			
			e.setMotd("§bBien joué à " + UUIDtoName.getName(Main.getInstance().online.get(0).toString()));
			
		}else if(DeathSwapStats.isState(DeathSwapStats.GENERATION)) {
			
			e.setMotd("§bGénération du terrain...");
			
		}else if(DeathSwapStats.isState(DeathSwapStats.CONFIG)) {
			
			e.setMotd("§dUn host doit configurer la partie");
			
		}
		
		
	}
	
}
