package fr.hugovalcraft.deathswap.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hugovalcraft.deathswap.Main;

public class ScenarioCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(msg.equalsIgnoreCase("scenario") && sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(Main.getInstance().scenario.isEmpty()) {
				
				p.sendMessage("§eIl n'y a aucun scenario actif...");
				
			}else {
				
				if(Main.getInstance().scenario.size() != 1) {
					
					p.sendMessage("§eLes scenarios actif sont : ");
					
					for(int i = 0; i < Main.getInstance().scenario.size(); i++) {
						
						p.sendMessage("§6-" + Main.getInstance().scenario.get(i));
						
					}
					
				}else {
					
					p.sendMessage("§eLe scenario actif est : ");
					
					for(int i = 0; i < Main.getInstance().scenario.size(); i++) {
						
						p.sendMessage("§6-" + Main.getInstance().scenario.get(i));
						
					}
					
				}
				

				
			}
			
		}
		
		return false;
	}

}
