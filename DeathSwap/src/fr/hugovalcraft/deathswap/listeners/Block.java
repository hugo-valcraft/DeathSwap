package fr.hugovalcraft.deathswap.listeners;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import fr.hugovalcraft.deathswap.stats.DeathSwapStats;
import fr.hugovalcraft.deathswap.utils.Scenario;

public class Block implements Listener {
	
	@EventHandler
	public void onInteractBlock(PlayerInteractEvent e) {
		
		if(e.getClickedBlock() != null && e.getClickedBlock().getType() != Material.AIR) {
			
			if(DeathSwapStats.isState(DeathSwapStats.CONFIG) || DeathSwapStats.isState(DeathSwapStats.WAIT)) {
				
				e.setCancelled(true);
				
			}
			
		}
		
	}
		
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		
		if(DeathSwapStats.isState(DeathSwapStats.WAIT) || DeathSwapStats.isState(DeathSwapStats.CONFIG)) {
			
			e.setCancelled(true);
			
		}else if(Scenario.CutClean.isActive() && e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
			
			if(e.getBlock().getType().equals(Material.IRON_ORE)) {
				
				e.setCancelled(true);
				
				for(int i = 0; i < e.getBlock().getDrops().size(); i ++) {
					
					CutCleanBlock(e.getBlock().getType(), e.getBlock());
					
				}
				
			}
			if(e.getBlock().getType().equals(Material.GOLD_ORE)) {
				
				e.setCancelled(true);
				
				for(int i = 0; i < e.getBlock().getDrops().size(); i ++) {
					
					CutCleanBlock(e.getBlock().getType(), e.getBlock());
					
				}
				
			}
			if(e.getBlock().getType().equals(Material.SAND)) {
				
				e.setCancelled(true);
				
				for(int i = 0; i < e.getBlock().getDrops().size(); i ++) {
					
					CutCleanBlock(e.getBlock().getType(), e.getBlock());
					
				}
				
			}
			
		}
		
	}

	private void CutCleanBlock(Material type, org.bukkit.block.Block block) {
		
		//https://www.youtube.com/watch?v=LbfmUHf9W_s
		
		ItemStack resultat = null;
		
		final ItemStack baseItemStack = new ItemStack(type);
		
		final Iterator<Recipe> i = Bukkit.recipeIterator();
		
		while(i.hasNext()) {
			
			Recipe r = i.next();
			
			if(!(r instanceof FurnaceRecipe))continue;
			
			FurnaceRecipe fr = (FurnaceRecipe)r;
			
			if(fr.getInput().getType() != baseItemStack.getType())continue;
			
			resultat = fr.getResult();
			
			break;
			
		}
		
		if(resultat != null) {
			
			block.setType(Material.AIR);
			
			block.getWorld().dropItem(block.getLocation(), resultat);
			
			block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(3);
			
		}
		
	}

	
}
