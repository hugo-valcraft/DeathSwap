package fr.hugovalcraft.deathswap.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockVector;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.utils.Scenario;

public class FurnanceEvent implements Listener {
	
	private static List<BlockVector> boosted = new ArrayList<>();
	
	@EventHandler
	public void onFurnace(FurnaceBurnEvent e) {
		
		if(Scenario.Fast_Smelting.isActive()) {
			
			if(!(boosted.contains(e.getBlock().getLocation().toVector().toBlockVector()))) {
				
				boosted.add(e.getBlock().getLocation().toVector().toBlockVector());
				
				new BukkitRunnable() {
					
					private short speedUp = 2;
					
					@Override
					public void run() {
						
						Furnace furnace = (Furnace) e.getBlock().getState();
						
						if(furnace.getCookTime() > 0 || furnace.getBurnTime() > 0) {
							
							furnace.setCookTime((short) (furnace.getCookTime() + speedUp));
							
							short burnTime = (short) (furnace.getBurnTime() - speedUp + speedUp / 10);
							
							furnace.setBurnTime(burnTime);
							
							furnace.update();
							
						}else {
							
							boosted.remove(e.getBlock().getLocation().toVector().toBlockVector());
							
							cancel();
							
						}
						
					}
				}.runTaskTimer(Main.getInstance(), 0, 1);
				
			}
			
		}
		

		
	}
		
}
