package fr.hugovalcraft.deathswap.listeners;

import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.game.DeathGame;
import fr.hugovalcraft.deathswap.stats.DeathSwapStats;
import fr.hugovalcraft.deathswap.utils.TitleManager;

public class DamageListeners implements Listener {
	
	Random r = new Random();
	
	@EventHandler
	public void FoodChange(FoodLevelChangeEvent e) {
		
		if(DeathSwapStats.isState(DeathSwapStats.WAIT) || DeathSwapStats.isState(DeathSwapStats.CONFIG)) {
			
			e.setCancelled(true);
			
		}
		
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
				
		if(DeathSwapStats.isState(DeathSwapStats.WAIT) || DeathSwapStats.isState(DeathSwapStats.CONFIG)) {
			
			e.setCancelled(true);
			
		}else if(DeathSwapStats.isState(DeathSwapStats.INVULNERABLE) && e.getEntity() instanceof Player) {
			
			e.setCancelled(true);
			
		}else if(e.getEntity() instanceof Player) {
			
			Player p = (Player) e.getEntity();
			
			if(p.getHealth() <= e.getDamage() && Main.getInstance().online.contains(p.getUniqueId())) {
				
				e.setCancelled(true);
				
				if(!DeathSwapStats.getState().equals(DeathSwapStats.WAIT) && Main.getInstance().online.contains(p.getUniqueId())) {
					
					Main.getInstance().online.remove(p.getUniqueId());	
					
					if(DeathGame.couplePlayer.containsKey(p)) {
						
						for(Entry<Player, Player> map : DeathGame.couplePlayer.entrySet()) {
							
							if(map.getKey().equals(p)) {
								
								Bukkit.broadcastMessage("§cNotre cher ami " + p.getDisplayName() + " §cest mort du switch avec " + map.getValue().getDisplayName());
								
								break;
								
							}
							
						}
						
					}else {
						
						Bukkit.broadcastMessage("§cNotre cher ami " + p.getDisplayName() + " §cest mort...");
						
					}
					
					p.getWorld().spawnEntity(p.getLocation(), EntityType.LIGHTNING);
					
					Main.getInstance().spectator.add(p);
					
					p.setGameMode(GameMode.SPECTATOR);
					
					TitleManager.sendTitle(p, "§cVous êtes mort...", "§7gg", 20*2);
										
				}
				
				for(int slot = 0; slot < p.getInventory().getSize(); slot++) {
					
					if(p.getInventory().getItem(slot) != null) {
						
						p.getWorld().dropItem(p.getLocation(), p.getInventory().getItem(slot));
						
						p.getInventory().remove(p.getInventory().getItem(slot));
						
					}
					
				}
				
				for(ItemStack it : p.getInventory().getArmorContents()) {
					
					if(!it.getType().equals(Material.AIR)) {
						
						p.getWorld().dropItem(p.getLocation(), it);
						
					}
					
				}
								
				p.updateInventory();
				
				p.teleport(Bukkit.getPlayer(Main.getInstance().online.get(r.nextInt(Main.getInstance().online.size()))));
				
			}
			
		}
		
	}
	
}
