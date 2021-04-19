package fr.hugovalcraft.deathswap.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.hugovalcraft.deathswap.Main;

public class ChatEvent implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		if(Main.getInstance().spectator.contains(e.getPlayer())) {
			
			String msg = e.getMessage();
			
			e.setCancelled(true);
			
			for(Player player : Main.getInstance().spectator) {
				
				player.sendMessage("§8[SPECTATEUR]" + e.getPlayer().getName() + " : §7§o"+ msg);
				
			}
			
		}
		
	}
	
}
