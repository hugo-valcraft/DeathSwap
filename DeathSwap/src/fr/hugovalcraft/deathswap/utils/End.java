package fr.hugovalcraft.deathswap.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.game.DeathGame;
import fr.hugovalcraft.deathswap.stats.DeathSwapStats;

public class End {
	
	static int task;
	
	static int restart = 30;
	
	static boolean ended = false;
	
	public static void EndGame(Player winner) {
		
		if(ended == true)return;
		
		ended = true;
		
		DeathSwapStats.setState(DeathSwapStats.END);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			p.teleport(winner);
			
			p.setGameMode(GameMode.SPECTATOR);
			
			p.playSound(p.getLocation(), Sound.WITHER_DEATH, 100, 1);
			
			TitleManager.sendTitle(p, "§aFélicitation !", "§c" + UUIDtoName.getName(winner.getUniqueId().toString()), restart);
			
			TitleManager.sendActionBar(p, "Nombre de manche joué : " + DeathGame.manche);
			
		}
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				if(restart == 10) {
					
					Bukkit.broadcastMessage("§aredémarrage du serveur dans 10 sec !");
					
				}
				
				if(restart == 0) {
					
					Bukkit.getScheduler().cancelTask(task);
					
					Bukkit.getServer().reload();
					
				}
				
				restart--;
				
			}
		}, 20, 20);
		
	}
	
	
		
}
