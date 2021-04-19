package fr.hugovalcraft.deathswap.utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import fr.hugovalcraft.deathswap.Main;

public class WorldCreator {
	
	static int task = 1000;
	
	public static World world;
	
	public static World nworld;
	
	public static boolean NetherGenerated = false;
	
	static org.bukkit.WorldCreator wc;
	
	static org.bukkit.WorldCreator n;

	private static Random r = new Random();
		
	private static int bseed = -2147483648 + r.nextInt(2147483647);
	
	public static int seed = bseed;
	
	public static int border = 20000;

	public static void createWorld(){
		
		wc = new org.bukkit.WorldCreator("" + seed);
		
		wc.type(WorldType.NORMAL);
		
		wc.seed(seed);
		
		world = wc.createWorld();
						
		world.getWorldBorder().setSize(border);
		
		world.setDifficulty(Difficulty.NORMAL);
		
		world.setGameRuleValue("doDaylightCycle", "false");
		
		WorldCreator.world.setGameRuleValue("doMobSpawning", "false");
		
		int y = 180;
		
		world.setSpawnLocation(0, y, 0);
		

		
				
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			p.teleport(world.getSpawnLocation());
			
			PlayerReset.ResetJoin(p);		
			
			
		}
		
		
	}
	
	public static void NetherCreate() {
				
		n = new org.bukkit.WorldCreator("" + seed + "_nether");
		
		n.type(WorldType.NORMAL);
		
		n.environment(Environment.NETHER);
		
		n.seed(seed);
		
		nworld = n.createWorld();
						
		nworld.getWorldBorder().setSize(border/8);
		
		NetherGenerated = true;
		
	}
	
	public static void UnLoadWorld(World world) {
		
		if(!world.equals(null)) {
						
			Bukkit.getServer().unloadWorld(world, true);
			
			DeleteWorld(world);
			
		}else {
			
			Main.getInstance().console.sendMessage("§cError");
			
		}
		
	}
	
	public static void DeleteWorld(World world) {
		
		File path = world.getWorldFolder();
		
		if(path.isDirectory()) {
			
			try {
				FileUtils.deleteDirectory(path);
			} catch (IOException e) {
				
				Main.getInstance().console.sendMessage("§cUn erreur est survenu lors de la supression du monde " + world.getName());
				
			}
			
		}
		
	}

	
}
