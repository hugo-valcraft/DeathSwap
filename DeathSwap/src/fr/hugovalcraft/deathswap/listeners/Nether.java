package fr.hugovalcraft.deathswap.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.utils.WorldCreator;

public class Nether implements Listener {
	
	
	
	@EventHandler
	public void NetherJoin(PlayerPortalEvent e) {
		
		Player p = e.getPlayer();
		
		if(e.getCause() == TeleportCause.NETHER_PORTAL) {
			
			e.useTravelAgent(true);
			
			e.getPortalTravelAgent().setCanCreatePortal(true);
			
			Location location = null;
			
			if(p.getWorld() == WorldCreator.world) {
				
				location = new Location(WorldCreator.nworld, e.getFrom().getX()/8,e.getFrom().getY(), e.getFrom().getZ()/8);
				
			}else if(p.getWorld() == WorldCreator.nworld){
				
				location = new Location(WorldCreator.world, e.getFrom().getX()*8,e.getFrom().getY(), e.getFrom().getZ()*8);
				
			}else {
				
				Main.getInstance().console.sendMessage("§cError with nether portal !");
				
				return;
				
			}
			
			e.setTo(e.getPortalTravelAgent().findOrCreate(location));
			
		}
		
	}
	
	@EventHandler
	public void NetherPortalCreation(PortalCreateEvent e) {
		
		if(WorldCreator.NetherGenerated == false) {
			
			Bukkit.broadcastMessage("§cGénération du nether...");
			
		}
		
		if(e.getReason() == CreateReason.FIRE) {
			
			
			WorldCreator.NetherCreate();
			
		}
		
	}
	
}
