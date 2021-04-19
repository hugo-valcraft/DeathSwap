package fr.hugovalcraft.deathswap.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.listeners.JoinServer;
import fr.hugovalcraft.deathswap.stats.DeathSwapStats;

public class StartCommand implements CommandExecutor {
	
	public static boolean instart = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(msg.equalsIgnoreCase("start") && sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(Main.getInstance().online.size() < JoinServer.PlayerMin && DeathSwapStats.getState().equals(DeathSwapStats.WAIT)) {
				
				p.sendMessage("§cIl n'y a pas assez de joueur");
				
			}else if(Main.getInstance().online.size() >= JoinServer.PlayerMin && DeathSwapStats.getState().equals(DeathSwapStats.WAIT) && instart == false) {					
					
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 100, 1);
				
				instart = true;
				
				JoinServer.StartGame();
				
			}else if(instart == true) {
				
				p.sendMessage("§cTu ne peux pas éxécuter cette commande en cours de partie !");
				
				return true;
				
			}else if(!DeathSwapStats.getState().equals(DeathSwapStats.WAIT)) {
				
				p.sendMessage("§cTu ne peux pas éxécuter cette commande en cours de partie !");
				
				return true;
				
			}
			
		}
		
		return false;
	}

}
