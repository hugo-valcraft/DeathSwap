package fr.hugovalcraft.deathswap.utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class PlayerReset {
	
	public static void ResetJoin(Player p) {
				
		p.setGameMode(GameMode.SURVIVAL);
		
		p.setExp(0);
		
		p.setLevel(0);
		
		p.setTotalExperience(0);
		
		p.setFoodLevel(40);
		
		p.setHealth(20);
		
		p.getInventory().clear();
		
		p.setMaxHealth(20);
		
		ItemStack air = new ItemStack(Material.AIR);
		
		p.getInventory().setHelmet(air);
		p.getInventory().setChestplate(air);
		p.getInventory().setLeggings(air);
		p.getInventory().setBoots(air);
		
		p.updateInventory();
		
	}
	
}
