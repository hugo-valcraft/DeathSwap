package fr.hugovalcraft.deathswap.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.command.StartCommand;
import fr.hugovalcraft.deathswap.game.DeathGame;
import fr.hugovalcraft.deathswap.stats.DeathSwapStats;
import fr.hugovalcraft.deathswap.utils.End;
import fr.hugovalcraft.deathswap.utils.PlayerReset;
import fr.hugovalcraft.deathswap.utils.TitleManager;
import fr.hugovalcraft.deathswap.utils.WorldCreator;

public class JoinServer implements Listener {
	
	Random r = new Random();
	
	public static int Stask;
	
	public static int timer = 10;
	
	public static int PlayerMin = 2;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		e.setJoinMessage("");
		
		TitleManager.setPlayerList(e.getPlayer(), "§dDeathSwap", "§7Plugin par Hugovalcraft");
		
		if(!Main.getInstance().online.contains(e.getPlayer().getUniqueId()) && DeathSwapStats.getState().equals(DeathSwapStats.WAIT)) {
			
			e.getPlayer().teleport(WorldCreator.world.getSpawnLocation());
			
			
			if(DeathSwapStats.getState().equals(DeathSwapStats.WAIT) && Main.getInstance().online.size() < PlayerMin) {
				
				PlayerReset.ResetJoin(e.getPlayer());
				
				if(!Main.getInstance().online.contains(e.getPlayer().getUniqueId())) {
					
					Main.getInstance().online.add(e.getPlayer().getUniqueId());
					
					Bukkit.broadcastMessage("§a(" + Main.getInstance().online.size() + "/" + PlayerMin + ")");
					
				}
				
				if(Main.getInstance().online.size() >= PlayerMin) {
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						
						if(p.isOp()) {
							
							p.sendMessage("§aLa partie est pleine, si vous voulez commencer faites : /start");
							
						}
						
					}
					
					
				}
				
			}else if(!Main.getInstance().spectator.contains(e.getPlayer())) {
				
				Main.getInstance().spectator.add(e.getPlayer());
				
				e.getPlayer().setGameMode(GameMode.SPECTATOR);
				
			}
				
			
		}else if(DeathSwapStats.isState(DeathSwapStats.CONFIG)){
			
			if(e.getPlayer().isOp()) {
				
				e.getPlayer().teleport(WorldCreator.world.getSpawnLocation());
				
				e.getPlayer().sendMessage("§dTu peux configurer la game avec la commande /config [done]");
				
				PlayerReset.ResetJoin(e.getPlayer());
				
			}else {
				
				e.getPlayer().kickPlayer("§dTu n'est pas host");
				
			}
			
			
		}else {
			
			Main.getInstance().spectator.add(e.getPlayer());
			
			e.getPlayer().setGameMode(GameMode.SPECTATOR);
			
			e.getPlayer().teleport(Bukkit.getPlayer(Main.getInstance().online.get(r.nextInt(Main.getInstance().online.size()))));
			
		}
		
		
	

		
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		
		e.setQuitMessage("");
		
		if(DeathSwapStats.isState(DeathSwapStats.WAIT) && Main.getInstance().online.contains(e.getPlayer().getUniqueId())) {
			
			int i = Main.getInstance().online.size() - 1;
			
			Bukkit.broadcastMessage("§c(" + i + "/" + PlayerMin + ")");
			
			if(Main.getInstance().online.size() < PlayerMin &&  Main.getInstance().spectator.size() > 0) {
				
				Player p = Main.getInstance().spectator.get(0);
				
				Main.getInstance().online.add(p.getUniqueId());
				
				Main.getInstance().spectator.remove(p);
				
				Bukkit.broadcastMessage("§a(" + Main.getInstance().online.size() + "/" + PlayerMin + ")");
				
				p.setGameMode(GameMode.SURVIVAL);
				
			}
			
		}else if(!DeathSwapStats.isState(DeathSwapStats.WAIT) && Main.getInstance().online.contains(e.getPlayer().getUniqueId())){
			
			Bukkit.broadcastMessage("§cLe joueur " + e.getPlayer().getName() + " à quitter la game ");
			
		}
		
		Player p = e.getPlayer();
		
		if(Main.getInstance().online.contains(p.getUniqueId())) {
			
			Main.getInstance().online.remove(p.getUniqueId());
			
			if(Main.getInstance().teleportable.contains(p)) {
				
				Main.getInstance().teleportable.remove(p);
				
			}
			
		}else if(Main.getInstance().spectator.contains(p)) {
			
			Main.getInstance().spectator.remove(p);
			
		}
		
		if(Main.getInstance().online.size() == 1 && !DeathSwapStats.getState().equals(DeathSwapStats.WAIT)) {
			
			End.EndGame(Bukkit.getPlayer(Main.getInstance().online.get(0)));
			
		}
		
	}
	
	public static void StartGame() {
		

		
		Stask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				if(Main.getInstance().online.size() < PlayerMin) {
					
					Bukkit.broadcastMessage("§cIl n'y a pas assez de joueur !");
					
					timer = 11;
					
					StartCommand.instart = false;
					
					Bukkit.getScheduler().cancelTask(Stask);
																	
				}

				
				if(timer >= 1) {
				
				Bukkit.broadcastMessage("§aLancement dans " + timer);
				
				for(Player pp : Bukkit.getOnlinePlayers()) {
					
					pp.playSound(pp.getLocation(), Sound.CLICK, 1, 1);
					
				}
				
				}else if(timer == 0) {
					
					Bukkit.broadcastMessage("§aBonne game à tous !");
					
					Bukkit.getScheduler().cancelTask(Stask);
					
					DeathGame.start();
					
				}
				

				

				
				timer--;
				
				
				
			}
		},20, 20);
		
	}
	
}
