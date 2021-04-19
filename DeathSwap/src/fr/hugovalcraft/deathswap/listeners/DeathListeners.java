package fr.hugovalcraft.deathswap.listeners;

import java.util.Iterator;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.utils.Scenario;
import fr.hugovalcraft.deathswap.utils.WorldCreator;

public class DeathListeners implements Listener {
	
	Random r = new Random();
	
	@EventHandler
	public void DeathByEntity(EntityDeathEvent e) {
		
		if(!(e.getEntity() instanceof Player) && Scenario.CutClean.isActive()) {
			
			for(Object drop : e.getDrops().toArray()) {
				
				//https://www.youtube.com/watch?v=LbfmUHf9W_s
				
				ItemStack resultat = null;
				
				final ItemStack baseItemStack = (ItemStack) drop;
				
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
					
					e.getDrops().add(resultat);
					
					e.getDrops().remove(drop);
					
				}
				
			}
			
		}
		
	}
	
	
	@EventHandler
	public void RespawnEvent(PlayerRespawnEvent e){
		
		e.getPlayer().setGameMode(GameMode.SPECTATOR);
		
		e.getPlayer().teleport(WorldCreator.world.getSpawnLocation());
		
		if(Main.getInstance().online.size() == 1) {
			
			Player p = Bukkit.getPlayer(Main.getInstance().online.get(0));
			
			e.getPlayer().teleport(p);
			
		}
		
	}
	

	
	
}
