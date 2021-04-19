package fr.hugovalcraft.deathswap.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.listeners.JoinServer;
import fr.hugovalcraft.deathswap.stats.DeathSwapStats;

public class ConfigCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(msg.equalsIgnoreCase("config") && sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(args.length == 0) {
				
				if(!DeathSwapStats.isState(DeathSwapStats.CONFIG)) {
					
					p.sendMessage("§cTu ne peux pas modifier la config pendant la partie !");
					
					return false;
					
				}
				
				Inventory inv = Bukkit.createInventory(null, 45, "§dConfig : Menu");
				
				p.openInventory(inv);
				
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				
				SkullMeta skullM = (SkullMeta) skull.getItemMeta();
				
				skullM.setDisplayName("§dNombre de joueur");
				
				skull.setItemMeta(skullM);
				
				
				ItemStack clock = new ItemStack(Material.WATCH);
				
				ItemMeta clockM = clock.getItemMeta();
				
				clockM.setDisplayName("§dTemps de chaque manche");
				
				clock.setItemMeta(clockM);
				
				
				
				ItemStack border = new ItemStack(Material.BARRIER);
				
				ItemMeta borderM = border.getItemMeta();
				
				borderM.setDisplayName("§dTaille de la bordure");
				
				border.setItemMeta(borderM);
				
				
				ItemStack scenario = new ItemStack(Material.BOOK);
				
				ItemMeta scenarioM = scenario.getItemMeta();
				
				scenarioM.setDisplayName("§dScenario");
				
				scenario.setItemMeta(scenarioM);
				
				
				inv.setItem(11, skull);
				
				inv.setItem(13, clock);
				
				inv.setItem(15, border);
				
				inv.setItem(31, scenario);
				
			}else if(args.length == 1 && args[0].equalsIgnoreCase("done")) {
				
				p.sendMessage("§dl'host est ouvert...");
				
				if(Bukkit.getOnlinePlayers().size() < JoinServer.PlayerMin) {
					
					for(Player players : Bukkit.getOnlinePlayers()) {
						
						Main.getInstance().online.add(players.getUniqueId());
						
						players.setAllowFlight(false);
						
					}
					
				}
				
				DeathSwapStats.setState(DeathSwapStats.WAIT);
				
			}
			
		}
		
		return false;
	}

}
