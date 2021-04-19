package fr.hugovalcraft.deathswap.game;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.stats.DeathSwapStats;
import fr.hugovalcraft.deathswap.utils.End;
import fr.hugovalcraft.deathswap.utils.Scenario;
import fr.hugovalcraft.deathswap.utils.TitleManager;
import fr.hugovalcraft.deathswap.utils.WorldCreator;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class DeathGame {
	
	public static int task;
	
	static int tptask;
	
	static int chtask;
	
	static int chtimer = 10;
	
	static int timer = 0;
	
	static int invincibilityTimer = 20;
	
	static int timertp = 10;
	
	public static int duration = 600;
	
	static int sec = 59;
	
	static int min;
	
	public static int skin;
	
	public static int manche = 0;
	
	public static int TimesUpTP;
	
	static Random r = new Random();
	
	static ArrayList<Location> TPloc = new ArrayList<>();
	
	static ArrayList<Player> TPp = new ArrayList<>();
	
	public static HashMap<Player, Player> couplePlayer = new HashMap<>();
	
	static HashMap<Player, Location> coupleLoc = new HashMap<>();

	public static void start() {
		
		DeathSwapStats.setState(DeathSwapStats.INVULNERABLE);
		
		for(UUID p : Main.getInstance().online) {
			
			if(Bukkit.getPlayer(p).getAllowFlight()) {
				
				Bukkit.getPlayer(p).setAllowFlight(false);
				
			}
			
			TitleManager.sendTime(Bukkit.getPlayer(p), 20*5);
			
			TitleManager.sendActionBar(Bukkit.getPlayer(p), "§bChargement du terrain...");
			
			WorldCreator.world.setGameRuleValue("doDaylightCycle", "true");
			
			WorldCreator.world.setGameRuleValue("doMobSpawning", "true");
			

			
			RandomTP(p);
			
		}
		
		if(Scenario.Anonymous.isActive()) {
			
			skin = r.nextInt(4);
			
			for(Player player : Bukkit.getOnlinePlayers()) {
				
				int caracter = r.nextInt(7)+1;
				
				StringBuilder strB = new StringBuilder();
				
				String str = null;
				
				for(int i = 0 ; i < caracter ; i++){
				  char c = (char)(r.nextInt(26) + 97);
				  str += c;
				  
				  strB.append(c);
				  
				}
				
				player.setDisplayName("§k" + str + "§f");
				
				player.setPlayerListName("§k" + str + "§f");
				
				player.setCustomName("§k" + str + "§f");
				
				HeadName(player, str);
				
				player.setCustomNameVisible(true);
				
				setSkin(player);
				
				for(Player players : Bukkit.getOnlinePlayers()) {
					
					players.hidePlayer(player);
					
				}
				
				for(Player players : Bukkit.getOnlinePlayers()) {
					
					players.showPlayer(player);
					
				}
				
			}
			
		}
		
		if(Scenario.UHC.isActive()) {
			
			WorldCreator.world.setGameRuleValue("naturalRegeneration", "false");
			
		}
		
		if(Scenario.Times_UP.isActive()) {
			
			TimesUpTP = 60 * (r.nextInt((duration-60)/60) + 1);
			
		}
		

		
		if(!Scenario.Times_UP.isActive()) {
			
			min  = (duration/60)-1;
			
			task =  Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					
					timer++;
					

					
					if(sec == 0) {
						
						sec = 60;
						
						if(!(min <= 0)) {
							
							min--;
							
						}
						
						
					}
					
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						
						TitleManager.sendActionBar(p, "§aTéléportation dans : " + String.format("%02d", min) + ":" + String.format("%02d", sec));
						
					}
					
									
					if(DeathSwapStats.getState().equals(DeathSwapStats.END) && Main.getInstance().online.size() <= 1) {
						
						Bukkit.getScheduler().cancelTask(task);
						
						for(UUID p : Main.getInstance().online) {
							
							End.EndGame(Bukkit.getPlayer(p));
							
						}
						
						
					}
					
					if(invincibilityTimer != 0) {
						
						invincibilityTimer--;
						
					}else{
						
						Bukkit.broadcastMessage("§cInvulnérabilité Désactivée");
						
						DeathSwapStats.setState(DeathSwapStats.INGAME);
						
						invincibilityTimer = -1;
					}
					
					if(timer >= duration-10 && Main.getInstance().online.size() >= 2) {
						
						if(timertp == 0) {
							
							SwitchTP();
							
							min = (duration/60)-1;
							
						}else {
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							
							p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
							
						}
						
						timertp--;
						
						}
						
					}
					
					if(Main.getInstance().online.size() == 1) {
						
						Bukkit.getScheduler().cancelTask(task);
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							
							TitleManager.sendActionBar(p, "Nombre de manche joué : " + DeathGame.manche);
							
						}
						
						for(UUID uuid : Main.getInstance().online) {
							
							End.EndGame(Bukkit.getPlayer(uuid));
							
						}
						
					}
					
					sec--;
				}




				
			}, 20, 20);
			
		}else {
			
			min  = (TimesUpTP/60)-1;
			
			task =  Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					
					timer++;
					

					
					if(sec == 0) {
						
						sec = 60;
						
						if(!(min <= 0)) {
							
							min--;
							
						}
						
					}
					
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						
						if(timer < TimesUpTP-10) {
							
							TitleManager.sendActionBar(p, "§aTéléportation dans : §a§khh§a:§a§khh");
							
						}else{
							
							TitleManager.sendActionBar(p, "§aTéléportation dans : §a§khh§a:" + String.format("%02d", sec));
							
						}
						
						TitleManager.setPlayerList(p, "§dDeathSwap", "§7Plugin par Hugovalcraft");
						
					}
					
									
					if(DeathSwapStats.getState().equals(DeathSwapStats.END) && Main.getInstance().online.size() <= 1) {
						
						Bukkit.getScheduler().cancelTask(task);
						
						for(UUID p : Main.getInstance().online) {
							
							End.EndGame(Bukkit.getPlayer(p));
							
						}
						
						
					}
					
					if(invincibilityTimer != 0) {
						
						invincibilityTimer--;
						
					}else{
						
						Bukkit.broadcastMessage("§cInvulnérabilité Désactivée");
						
						DeathSwapStats.setState(DeathSwapStats.INGAME);
						
						invincibilityTimer = -1;
					}
					
					if(timer >= TimesUpTP-10 && Main.getInstance().online.size() >= 2) {
						
						if(timertp == 0) {
							
							SwitchTP();
							
							TimesUpTP = 60 * (r.nextInt((duration-60)/60) + 1);
							
							min  = (TimesUpTP/60)-1;
							
						}else {
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							
							p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
							
						}
						
						timertp--;
						
						}
						
					}
					
					if(Main.getInstance().online.size() == 1) {
						
						Bukkit.getScheduler().cancelTask(task);
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							
							TitleManager.sendActionBar(p, "Nombre de manche joué : " + DeathGame.manche);
							
						}
						
						for(UUID uuid : Main.getInstance().online) {
							
							End.EndGame(Bukkit.getPlayer(uuid));
							
						}
						
					}
					
					sec--;
				}




				
			}, 20, 20);
			
		}
		
	}
	
	private static void HeadName(Player players, String str) {
		
		try {
            Method getHandle = players.getClass().getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(players);
            boolean gameProfileExists = false;
            try {
                Class.forName("net.minecraft.util.com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {
            }
            try {
                Class.forName("com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {
            }
            if (!gameProfileExists) {
                Field nameField = entityPlayer.getClass().getSuperclass().getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(entityPlayer, "§k" + str + "§f");
            } else {
                Object profile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
                Field ff = profile.getClass().getDeclaredField("name");
                ff.setAccessible(true);
                ff.set(profile, "§k" + str + "§f");
            }
            if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
                @SuppressWarnings("unchecked")
                Collection<? extends Player> players2 = (Collection<? extends Player>) Bukkit.class.getMethod("getOnlinePlayers").invoke(null);
                for (Player p : players2) {
                    p.hidePlayer(players);
                    p.showPlayer(players);
                }
            } else {
                Player[] players3 = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null));
                for (Player p : players3) {
                    p.hidePlayer(players);
                    p.showPlayer(players);
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
		
	}

	private static void SwitchTP() {
		
		
		// Here bitch
		
		if(!couplePlayer.isEmpty()) {
			
			couplePlayer.clear();
										
		}
		
		manche++;
		
		for(UUID uuid : Main.getInstance().online) {
			
			AddList(uuid);
			
		}
		
		for(UUID uuid : Main.getInstance().online) {
			
			RandomTpPlayer(Bukkit.getPlayer(uuid));
			
		}
		
		for(Entry<Player, Player> map : couplePlayer.entrySet()) {
			
			map.getKey().sendMessage("§aTu as été switch avec §6" + map.getValue().getDisplayName());
			
			System.out.println(map.getKey().getName() + " --------> " + map.getValue().getName());
			
		}
		
		for(Entry<Player, Location> map : coupleLoc.entrySet()) {
			
			map.getKey().teleport(map.getValue());
			
		}
		
		System.out.println("|----------------------|" + manche + "|------------------------|");
		
		coupleLoc.clear();
		
		TPloc.clear();
		
		TPp.clear();
		
		timertp = 10;
		
		timer = 0;
		
		for(UUID uuid : Main.getInstance().online) {
			
			Bukkit.getPlayer(uuid).playSound(Bukkit.getPlayer(uuid).getLocation(), Sound.LEVEL_UP, 100, 1);
			
		}
		
		
	}
	
	
	private static void RandomTpPlayer(Player p) {
		
		//Tester le nombre de joueur
		
		if(Main.getInstance().online.size() == 2) {
			
			//Nombre de joueur == 2
			
			int Index = r.nextInt(TPp.size());
			
			Player player = TPp.get(Index);
			
			Location loc = TPloc.get(Index);
			
			if(!p.equals(player) && !couplePlayer.containsValue(player)) {
				
				couplePlayer.put(p, player);
				
				coupleLoc.put(p, loc);
				
			}else {
				
				//System.out.println("Hii");
				
				PlayerFinder(p);
				
			}
			
		}else if(Main.getInstance().online.size() > 2){
			
			//Nombre de joueur > 2
			
			//Créer un couple
			
			if(couplePlayer.isEmpty()) {
				
				int Index = 0;
				
				if(TPp.size() > 1) {
					
					Index = r.nextInt(TPp.size());
					
				}
				
				Player player = TPp.get(Index);
				
				Location loc = TPloc.get(Index);
				
				if(!p.equals(player)) {
					
					couplePlayer.put(p, player);
					
					coupleLoc.put(p, loc);
					
					TPloc.remove(loc);
					TPp.remove(player);
					
					
				}else {
					

					
					PlayerFinder(p);
					
				}
				
				
			}else {
				
				int Index = 0;
				
				if(TPp.size() > 1) {
					
					Index = r.nextInt(TPp.size());
					
				}
				
				Player player = TPp.get(Index);
				
				Location loc = TPloc.get(Index);
				
				

				
				if(!p.equals(player) && !couplePlayer.containsValue(player)) {
					
					couplePlayer.put(p, player);
					
					coupleLoc.put(p, loc);
					
					TPloc.remove(loc);
					TPp.remove(player);
										

					
				}else {
					

					
					PlayerFinder(p);
					
				}
				
			}
			
		}
		
	}
	

	public static void PlayerFinder(Player p) {
		
		//System.out.println(TPp.size() + "");
		
		if(TPp.size() > 1) {
			
			//System.out.println("Hi !");
			
			RandomTpPlayer(p);
			
		}else {
			
			//System.out.println("Hi");
			
			int Index = r.nextInt(Main.getInstance().online.size());
			
			Player player = Bukkit.getPlayer(Main.getInstance().online.get(Index));
			
			if(!p.equals(player)) {
				
				for(Entry<Player, Player> map : couplePlayer.entrySet()) {
					
					if(map.getValue().equals(player)) {
						
						couplePlayer.remove(map.getKey());
						coupleLoc.remove(map.getKey());
						
						couplePlayer.put(p, player);
						coupleLoc.put(p, player.getLocation());
						
						
						RandomTpPlayer(map.getKey());
						
						break;
						
					}
					
				}
				
			}else {
				
				PlayerFinder(p);
				
			}
			
		}
		

		
	}
	
	
	private static void AddList(UUID uuid) {
		
		Player p = Bukkit.getPlayer(uuid);
		
		TPp.add(p);
		
		TPloc.add(p.getLocation());
		
	}


	public static void RandomTP(UUID uuid) {
		
		Player p = Bukkit.getPlayer(uuid);
		
		int border = WorldCreator.border/2;
		
		int x = -border + r.nextInt(border);
		
		int y = 180;
		
		int z = -border + r.nextInt(border);
		
		Location loc = new Location(WorldCreator.world, x, y, z);
		
		isSafe(loc, p);
		
		
		
	}



	private static void isSafe(Location loc, Player p) {
		
		if(loc.getBlock().getBiome().equals(Biome.DEEP_OCEAN)
				|| loc.getBlock().getBiome().equals(Biome.FROZEN_OCEAN) 
				|| loc.getBlock().getBiome().equals(Biome.OCEAN) 
				|| loc.getBlock().getBiome().equals(Biome.RIVER) 
				|| loc.getBlock().getBiome().equals(Biome.FROZEN_RIVER)
				|| loc.getBlock().getBiome().equals(Biome.BEACH)
				|| loc.getBlock().getBiome().equals(Biome.COLD_BEACH)
				|| loc.getBlock().getBiome().equals(Biome.STONE_BEACH)) {
			
			RandomTP(p.getUniqueId());
			
			
		}else {
			
			p.teleport(loc);
			
		}
		
		
		
	}
	
	public static void setSkin(Player p) {
		
		GameProfile profile = ((CraftPlayer)p).getHandle().getProfile();
		
		PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
		
		connection.sendPacket(new PacketPlayOutPlayerInfo(
				PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, 
				((CraftPlayer)p).getHandle()));
		
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", getSkin());
		
		connection.sendPacket(new PacketPlayOutPlayerInfo(
				PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, 
				((CraftPlayer)p).getHandle()));
		
	}
	
	public static Property getSkin() {
		
		switch (skin) {
		
		case 0: return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzY1MTc5MjkwNSwKICAicHJvZmlsZUlkIiA6ICI2NGJhOTkyYzBmYjU0ZDYzYjliODFmZWUyNWVkZDc0MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJOYWxGdW5fIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzY4NThmZjA5NTk4NGVkYTFjNzdkZGVlMDZhMDMxODk4ZTIzYjAwNzMzYjRiNWJhZTNhNTg4MDA5NDJjOTE2ZmUiCiAgICB9CiAgfQp9", "EmfPUu5M0++r4mCPdvkpCeR5evLCAJYzNurjqPJ8gTKwquD/ITsNCKNgHpreZKRTXkb9bml631Ck0fX9IBLKkYJXitskKmUGtvZqRJXQ8pf7W3GLsnDkpUDd4LSE5ZR22K+ql8r1GJEgCkLA22xfMTlMSdY+ZBnBibv4Pgt3GCj4JWTkqot7vjI4CsnRoxySTfx2Eixa4DT9LkqxdsuHDfzDKEwXTqGMypfzneJGKWk4kqqArOH69mQF/WAFWR/yc+P1zrSCea23qpFp8OYz7Ple+ClRzUzFmWuFLzr+K/+HdHc/k2gNwKz3thFpDP1AvTfYS25Vcb7wXrosXgSAdmTRWWqawIdVjBy1WwMf25MFpq9w1hJ/anVkJ/xicnj5Ne3/xAbS8gyN3vO9eKnUDSETC5BWAxzF2MHWhtSgVx4S5yLJLW4E2+xH03OOy35Eek7mYiUZw77JTy2sDRVOPff0M2NogRcb8P8vtFVVYGWEaKjghRS5WrD/8DbKuV23vNxcr56kIK5wshAgO2CRYraFr8sc5rKDdaV/UYLTCNiGQF/diB7yzVg0qex/Uv1rDzuXRdpG20v88dInGRS6vsMLD8RSlU5T2ef9oKOx37fJseUvNOgyo5XwQKkx6TghTDN9XJskDvWx84PXeK/JTTxEj7E1Lsqa9kYigcJ0So4=");
		case 1: return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzY1MTk0NTY2MiwKICAicHJvZmlsZUlkIiA6ICIzMmI3MjBmZWRlNmE0ZWVmODRlNjI3ZThhMjgyODgyZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGFya29zX2Z1biIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yNjVjMWQwNjkxMjZhZjJkODQ1MThkNzgzMzM0NTEyNDA3MzEwNmU1YjQ0ZGE2NzQzOTVmMDY4NmZlNjA0NDUwIgogICAgfQogIH0KfQ==", "Sg84LIO0ZW8JtyOeVnCS3sq6/PbuhNNXi7CjoE2LI9miMocZwmHh27PeKspvcHIN3LYEjwXs94WGB7Xy4XLAzvRI0vTZbNLv2r18P2FGYc0GLJfgMdVSoazs1giXVgWe9B0maZLoePI+fjZb0W+NBPFUTv+b7VHG9AjgPBqAkHjmoFk1gpHPYM0T2b04T7JQ0SJbeHJXdJlXYmLNqBesitZW2bX29h9cUphT0c7QLlQN0II2vF1HHQFGoMuM+Gov7qflzdPItCYz7NbwkJfuEHsheqPY2cvhdtUkSBGAGppJwQ9oUAYFjXLP+Aev9vGU5c6Mq+Y6XuiIp1tRykUgB6xEV2AqECGpqNdY8jZXzt4iCxxJPhOECyXhmJgg7o2O27SGF6+trOnCauudZpUQbPpu9RBzv/SJfzRpcS14BKCWvzK8Al0dPZqkTBlPjikDkK9FZBcdb8liy+Yg5JswdGLvwtape9HqOjwSOtiaRjnV+3wKEMuAOBfeykbEQShXo3t7m2MqRzo/jwN/SH7BXhaJ+B0lDxUlAh20YJ8EtYvCeBLx5NnMitYtsPXaK7HWk7pCPPSo5SCmuZQCR40S7r0d2GRJ8YZLdvSO6m8dqkakCS5nHhCBEi1KKbfk2/qT2rrbkGHqtdDowq85Sg9rU+HeV1Heg8QK8KKsyD4fkU4=");
		case 4: return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzY1MzQzNjk2NCwKICAicHJvZmlsZUlkIiA6ICIwNzMyMmNkOGFmNDc0ZjdiODc1OTFjNzViYzIxZTQ1YyIsCiAgInByb2ZpbGVOYW1lIiA6ICJFd2FuRXh0cmEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2UwNDQ0ODI5YjY4MTI0MThlMWEzYzQxZjllZjJkNDBhMTliZDhiNWIwNDdhMTc3ZDdiODYxN2ExZmJjYjJjMCIKICAgIH0KICB9Cn0=", "YtIeVXTf45z+C52VIID7FhcRFpekaf0Uor5DOZ6YsAKkHIuG2YMsFkepmE+Rqn0FGFZh2UNnDF3gt1bl88nSuFNRn9ikKiSYRUbmf9eof63KTOWrvxgMVl1L1Ie1SYdubLq3/icO9BaJpPZacqtluMCnkuKZvKapDRBbuD7sWIzHSVYwE6HMbSP+C4BhZY4qN7+Xd2ydvvscUp5CEIDH4gTJungCw8hwc6Hji42dPBjYIAn09GyWO7c9ICHbEPDeZnQsl71GW0urxo7vCZCyIXWhu+J1MO9gvHng1BzhVGNUxqJUoT8VvoDVDzPP4YuCQFs2TngtcXBDehkyzYMIXs4rp5YUKkoLFaBit4FmlFTVO5CrRhFI8f1YxDeuu1bxUVC5Eyzmt59E7wR1AVF7f3YYupQCHT2bCuu+UISfQ/sozbuRqf6lrUiLe/5GL23efnjKj2tZUK/UNpJBrC3RceRCrpy0QEP9S8qjG0I513SGWPhQnbWrJc6goJHsYgx4TBkPre36MeGb0SIpNN2RXARAySQBeq/3wM6qDyV6Pe7v/4LKRFH2w2EOYxezQOgs5rmWsTb94BZm/MLR/wMsKY1HbJCIasoCMk0/qxfthAUU0dq3WQFiBR6lGKLXCVRDXDvxQPIY4A183Y8E86PyoC69W2pMsv8RlQ9gEhovD74=");
		case 3: return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzY1MzQ3NTI4NCwKICAicHJvZmlsZUlkIiA6ICI1NjkwYTE4OWRhMzc0OTMyYjhjODJiYmFhOGFkNzM3ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvY3JhZnRzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzVhNzc4MzdkZjlhNzExYTcwZDU2YTM0OWY4MDdiZjE4YWMyOWY0NGI2NjBhMWRhNjRhNTdiYzI0YWI2NTc2ZWIiCiAgICB9CiAgfQp9", "ZlxUUBzIJKBdkeeylBSHTYvNyAfWVHuyWFaRbQMBXhzz0r9q8zoTccx6U/x0WjVqiL5dCRrkbQ7VMc0Fhea9yUVAPr1MUPtJlW+5VnMG7rmNX40PgsfMU8Tz8oVO2kxcaB8ifxZuogEze0FtyJCQJrK2MuCH0LsV3xBjnIXgJCKFaijvG6w5B02HB2YKLTtKnkR2xLr2+O4FUK/VsM0kemyQwHuKGwj9E1zX46SIEP2PNn8aLbS7Wtw5J6IAxzoNoaUp/6jj6Qq49plNXRwXiVu9rK9NU26DaqOmG3rqDYd5A6gDxp3t7g3627ChbtvTX9v7kZndg7oc6m4gFnZBocgk8kVuOMfyJUzrvpU2sBQBRA4VTpneSRPhC9zknYLMyaFcfqj7nWXXP31z1ke5QIVii6h7RJEW3TuIETKvGsoBmMEfIZxAg8MiY8KuiMR2bIMKWP2j5HG71ZFnewSb70BfT4NUqQk5Gw050IofDVuDUz1yCvnd2K9a9knbT2gPK1wmGkP98ZH/ndfSFBczZd/5MDR0iNjil9ayuQaSCqi6z25cTh7WjVAMo6q6x/NQ+KKYR1tc4y4Lnhu42TNHK1fk9++jU/dPEerwZDEybfINDsuoHIJEO+v2BQQfii/gWhOL4uPbxrV+kvk1jywx16kOpXNEbrJm4oIjVXYGmfg=");
		case 2: return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzY1MzUyMDk3OCwKICAicHJvZmlsZUlkIiA6ICJkOTU4NWUyNzJkMTQ0YTQzYmU1ZjhmZjQwMjQ0ZWJjMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJIdWdvdmFsY3JhZnQiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTI5MGQwNGQxYzI5NjY5ZTM0ZWQxOGJhZDMzNmJjZTU0OTVkOTIxOGNlZmM5NWY4NDhiNGMzZjE1OGRlZjVlZSIKICAgIH0KICB9Cn0=", "TrQ2MKkPtGJMGxWi0DmpoL5Adbvzpbq/0Kk0qxcppv0q8TNMl3eFaOKYwT6NrkPHPYfmUmlHlMdu0gqY2ewlnxXKCUaaqAgwiHyc5jbdWxam8nLXhGRrnAWTFYCIKADFXQyMkZ6ZVlnNwYbQmCsNmuZM3pmh3j+qVkPv8UKua+eqtwXodFIKr7vmtH0v8qdvQlqSfHSdGIYO104VWcx09v7IKtkf8osR1gBTr4oMS0u1+KFEi3PPtngenbJ1SicGFF5MGTds1RE/mYfAtd4qx6+tKkm3G7KftmjuABkJx2cTVUEf8jbAm5U3VLy+h5DxGK4/x4stOt1e3MlYrEwBTQUJkWeEIeYj/w/hM3Akx/Bj7X2XNg+F3LebDXD2dpegwB1OBKHFx+S5yS/agMn+7SfezaX/cO5SDjXpgAwErZRvgTXmb/K4/XPsgkw4OLmHLDswbGd/ZgIUIQNNG00BJC6LeNJu6hqPKTtQZXfk2ZTZveM5Yx2aaOFBLLPhx2N3ihD9Q2kv8rcAOGr7DNaLqWkF/Im3fiiSvSpYoHZdsp/88PQj43lZvbPvXoe+lYF+iskEsKGuVdmp5xgWrROzw25pwLz0c/21yFmfShqt0jbhVj7ze9mJgG7YIpLxn3xCZ4JYTFayitgmZ3D87bCUI2FBoZ9AwEFUrsZq/t4UR3E=");
		default: return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzY1MzUyMDk3OCwKICAicHJvZmlsZUlkIiA6ICJkOTU4NWUyNzJkMTQ0YTQzYmU1ZjhmZjQwMjQ0ZWJjMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJIdWdvdmFsY3JhZnQiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTI5MGQwNGQxYzI5NjY5ZTM0ZWQxOGJhZDMzNmJjZTU0OTVkOTIxOGNlZmM5NWY4NDhiNGMzZjE1OGRlZjVlZSIKICAgIH0KICB9Cn0=", "TrQ2MKkPtGJMGxWi0DmpoL5Adbvzpbq/0Kk0qxcppv0q8TNMl3eFaOKYwT6NrkPHPYfmUmlHlMdu0gqY2ewlnxXKCUaaqAgwiHyc5jbdWxam8nLXhGRrnAWTFYCIKADFXQyMkZ6ZVlnNwYbQmCsNmuZM3pmh3j+qVkPv8UKua+eqtwXodFIKr7vmtH0v8qdvQlqSfHSdGIYO104VWcx09v7IKtkf8osR1gBTr4oMS0u1+KFEi3PPtngenbJ1SicGFF5MGTds1RE/mYfAtd4qx6+tKkm3G7KftmjuABkJx2cTVUEf8jbAm5U3VLy+h5DxGK4/x4stOt1e3MlYrEwBTQUJkWeEIeYj/w/hM3Akx/Bj7X2XNg+F3LebDXD2dpegwB1OBKHFx+S5yS/agMn+7SfezaX/cO5SDjXpgAwErZRvgTXmb/K4/XPsgkw4OLmHLDswbGd/ZgIUIQNNG00BJC6LeNJu6hqPKTtQZXfk2ZTZveM5Yx2aaOFBLLPhx2N3ihD9Q2kv8rcAOGr7DNaLqWkF/Im3fiiSvSpYoHZdsp/88PQj43lZvbPvXoe+lYF+iskEsKGuVdmp5xgWrROzw25pwLz0c/21yFmfShqt0jbhVj7ze9mJgG7YIpLxn3xCZ4JYTFayitgmZ3D87bCUI2FBoZ9AwEFUrsZq/t4UR3E=");
		}
		
	}

}
