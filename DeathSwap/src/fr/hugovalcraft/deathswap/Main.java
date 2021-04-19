package fr.hugovalcraft.deathswap;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.hugovalcraft.deathswap.command.ConfigCommand;
import fr.hugovalcraft.deathswap.command.ScenarioCommand;
import fr.hugovalcraft.deathswap.command.StartCommand;
import fr.hugovalcraft.deathswap.listeners.Block;
import fr.hugovalcraft.deathswap.listeners.ChatEvent;
import fr.hugovalcraft.deathswap.listeners.ClickInv;
import fr.hugovalcraft.deathswap.listeners.DamageListeners;
import fr.hugovalcraft.deathswap.listeners.DeathListeners;
import fr.hugovalcraft.deathswap.listeners.FurnanceEvent;
import fr.hugovalcraft.deathswap.listeners.JoinServer;
import fr.hugovalcraft.deathswap.listeners.Nether;
import fr.hugovalcraft.deathswap.listeners.UpdateList;
import fr.hugovalcraft.deathswap.stats.DeathSwapStats;
import fr.hugovalcraft.deathswap.utils.WorldCreator;

public class Main extends JavaPlugin {
	
	public ArrayList<UUID> online = new ArrayList<UUID>();
	
	public ArrayList<Player> teleportable = new ArrayList<Player>();
	
	public ArrayList<Player> winner = new ArrayList<Player>();
	
	public ArrayList<Player> spectator = new ArrayList<Player>();
	
	public ArrayList<String> scenario = new ArrayList<String>();
	
	private static Main instance;
	
	
	public ConsoleCommandSender console = Bukkit.getConsoleSender();
	
	
	@Override
	public void onEnable() {
		
		getServer().getPluginManager().registerEvents(new UpdateList(), this);
		
		DeathSwapStats.setState(DeathSwapStats.GENERATION);

		getServer().getPluginManager().registerEvents(new JoinServer(), this);
		
		instance = this;
		
		getServer().getPluginManager().registerEvents(new DamageListeners(), this);
		
		getServer().getPluginManager().registerEvents(new Block(), this);
		
		getServer().getPluginManager().registerEvents(new DeathListeners(), this);
		
		getServer().getPluginManager().registerEvents(new Nether(), this);
		
		getServer().getPluginManager().registerEvents(new ClickInv(), this);
		
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		
		getServer().getPluginManager().registerEvents(new FurnanceEvent(), this);
		
		getCommand("start").setExecutor(new StartCommand());
		
		getCommand("config").setExecutor(new ConfigCommand());
		
		getCommand("scenario").setExecutor(new ScenarioCommand());
		
		WorldCreator.createWorld();
		
		if(WorldCreator.world.getSpawnLocation().getBlock().getBiome().equals(Biome.DEEP_OCEAN)
		|| WorldCreator.world.getSpawnLocation().getBlock().getBiome().equals(Biome.FROZEN_OCEAN) 
		|| WorldCreator.world.getSpawnLocation().getBlock().getBiome().equals(Biome.OCEAN) 
		|| WorldCreator.world.getSpawnLocation().getBlock().getBiome().equals(Biome.RIVER) 
		|| WorldCreator.world.getSpawnLocation().getBlock().getBiome().equals(Biome.FROZEN_RIVER)
		|| WorldCreator.world.getSpawnLocation().getBlock().getBiome().equals(Biome.BEACH)
		|| WorldCreator.world.getSpawnLocation().getBlock().getBiome().equals(Biome.COLD_BEACH)
		|| WorldCreator.world.getSpawnLocation().getBlock().getBiome().equals(Biome.STONE_BEACH)) {
			
			Bukkit.reload();
			
		}
		
		console.sendMessage("§aLe monde a bien était generer");
		
		DeathSwapStats.setState(DeathSwapStats.CONFIG);
		
	}
	
	@Override
	public void onDisable() {
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			p.kickPlayer("§aRestart du serveur !");
			
		}
		
		WorldCreator.UnLoadWorld(WorldCreator.world);
		
		if(WorldCreator.NetherGenerated) {
			
			WorldCreator.UnLoadWorld(WorldCreator.nworld);
			
		}
		
		
	}

	public static Main getInstance() {
		
		return instance;
		
	}
	
}
