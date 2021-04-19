package fr.hugovalcraft.deathswap.listeners;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.hugovalcraft.deathswap.Main;
import fr.hugovalcraft.deathswap.game.DeathGame;
import fr.hugovalcraft.deathswap.utils.Scenario;
import fr.hugovalcraft.deathswap.utils.WorldCreator;

public class ClickInv implements Listener {
	
	@EventHandler
	public void Click(InventoryClickEvent e) {
		
		if(e.getWhoClicked() instanceof Player) {
			
			Player p = (Player) e.getWhoClicked();
			
			if(e.getCurrentItem() != null) {
				
				ItemStack it = e.getCurrentItem();
				
				ItemStack menu = new ItemStack(Material.WOOD_DOOR);
				
				ItemMeta menuMeta = menu.getItemMeta();
				
				menuMeta.setDisplayName("§cMenu");
				
				menu.setItemMeta(menuMeta);
								
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§dNombre de joueur")) {
					
					e.setCancelled(true);
					
					Inventory inv = Bukkit.createInventory(null, 27, "§dConfig : Joueur");
					
					p.openInventory(inv);
					
					ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					
					SkullMeta headM = (SkullMeta) head.getItemMeta();
					
					headM.setDisplayName("§6" + JoinServer.PlayerMin + "§d joueurs");
					
					head.setItemMeta(headM);
					
					
					ItemStack add = new ItemStack(Material.EMERALD_BLOCK);
					
					ItemMeta Meta = add.getItemMeta();
					
					Meta.setDisplayName("§a+1");
					
					add.setItemMeta(Meta);
					
					
					ItemStack remove = new ItemStack(Material.REDSTONE_BLOCK);
					
					ItemMeta removeM = remove.getItemMeta();
					
					removeM.setDisplayName("§c-1");
					
					remove.setItemMeta(removeM);
					
					inv.setItem(13, head);
					inv.setItem(15, add);
					inv.setItem(11, remove);
					inv.setItem(26, menu);
					
					
				}
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§dTemps de chaque manche")){
					
					e.setCancelled(true);
					
					Inventory inv = Bukkit.createInventory(null, 27, "§dConfig : Manche");
					
					p.openInventory(inv);
					
					ItemStack clock = new ItemStack(Material.WATCH);
					
					ItemMeta clockM = clock.getItemMeta();
					
					clockM.setDisplayName("§dTemps d'une manche : §6" + DeathGame.duration/60 + " min");
					
					clock.setItemMeta(clockM);
					
					ItemStack add = new ItemStack(Material.EMERALD_BLOCK);
					
					ItemMeta Meta = add.getItemMeta();
					
					Meta.setDisplayName("§a+1");
					
					add.setItemMeta(Meta);
					
					
					ItemStack remove = new ItemStack(Material.REDSTONE_BLOCK);
					
					ItemMeta removeM = remove.getItemMeta();
					
					removeM.setDisplayName("§c-1");
					
					remove.setItemMeta(removeM);
					
					inv.setItem(13, clock);
					inv.setItem(15, add);
					inv.setItem(11, remove);
					inv.setItem(26, menu);
					
				}
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§dTaille de la bordure")) {
					
					e.setCancelled(true);
					
					Inventory inv = Bukkit.createInventory(null, 27, "§dConfig : Bordure");
					
					p.openInventory(inv);
					
					ItemStack border = new ItemStack(Material.BARRIER);
					
					ItemMeta borderM = border.getItemMeta();
					
					borderM.setDisplayName("§dTaille de la bordure : §6-" + WorldCreator.border/2 + "§d/§6" + WorldCreator.border/2);
					
					border.setItemMeta(borderM);
					
					ItemStack add = new ItemStack(Material.EMERALD_BLOCK);
					
					ItemMeta Meta = add.getItemMeta();
					
					Meta.setDisplayName("§a+1000");
					
					add.setItemMeta(Meta);
					
					
					ItemStack remove = new ItemStack(Material.REDSTONE_BLOCK);
					
					ItemMeta removeM = remove.getItemMeta();
					
					removeM.setDisplayName("§c-1000");
					
					remove.setItemMeta(removeM);
					
					inv.setItem(13, border);
					inv.setItem(15, add);
					inv.setItem(11, remove);
					inv.setItem(26, menu);
					
				}
				
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§dScenario")) {
					
					e.setCancelled(true);
					
					Inventory inv = Bukkit.createInventory(null, 36, "§dConfig : Scenario");
					
					p.openInventory(inv);
					
					inv.setItem(0, CutCleanItem());
					
					inv.setItem(1, UHCItem());
					
					inv.setItem(2, FastSItem());
					
					inv.setItem(3, TimesItem());
					
					inv.setItem(4, AnonymousItem());
					
					inv.setItem(26, menu);
					
				}
				
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§a+1")) {
					
					e.setCancelled(true);
					
					if(e.getInventory().getName().equals("§dConfig : Joueur")) {
						
						if(JoinServer.PlayerMin == 20) {
							
							p.sendMessage("§cTu as atteint le nombre maximum de joueur");
							
							p.playSound(p.getLocation(), Sound.VILLAGER_NO, 100, 1);
							
							return;
							
						}
						
						JoinServer.PlayerMin++;
						
						ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
						
						SkullMeta headM = (SkullMeta) head.getItemMeta();
						
						headM.setDisplayName("§6" + JoinServer.PlayerMin + "§d joueurs");
						
						head.setItemMeta(headM);
						
						e.getInventory().setItem(13, head);
						
					}else if(e.getInventory().getName().equals("§dConfig : Manche")) {
						
						DeathGame.duration = DeathGame.duration+60;
						
						ItemStack clock = new ItemStack(Material.WATCH);
						
						ItemMeta clockM = clock.getItemMeta();
						
						clockM.setDisplayName("§dTemps d'une manche : §6" + DeathGame.duration/60 + " min");
						
						clock.setItemMeta(clockM);
						
						e.getInventory().setItem(13, clock);
						
					}

					

					
				}
				
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§c-1")) {
					
					e.setCancelled(true);
					
					if(e.getInventory().getName().equals("§dConfig : Joueur")) {
						
						if(JoinServer.PlayerMin == 2) {
							
							p.sendMessage("§c2 joueurs est le nombre minimum");
							
							p.playSound(p.getLocation(), Sound.VILLAGER_NO, 100, 1);
							
							return;
							
						}
						
						JoinServer.PlayerMin--;
						
						ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
						
						SkullMeta headM = (SkullMeta) head.getItemMeta();
						
						headM.setDisplayName("§6" + JoinServer.PlayerMin + "§d joueurs");
						
						head.setItemMeta(headM);
						
						e.getInventory().setItem(13, head);
						
					}else if(e.getInventory().getName().equals("§dConfig : Manche")) {
						
						if(DeathGame.duration == 60) {
							
							p.sendMessage("§c1 min est le minimum de minute pour chaque manche");
							
							p.playSound(p.getLocation(), Sound.VILLAGER_NO, 100, 1);
							
							return;
							
						}
						
						DeathGame.duration = DeathGame.duration-60;
						
						ItemStack clock = new ItemStack(Material.WATCH);
						
						ItemMeta clockM = clock.getItemMeta();
						
						clockM.setDisplayName("§dTemps d'une manche : §6" + DeathGame.duration/60 + " min");
						
						clock.setItemMeta(clockM);
						
						e.getInventory().setItem(13, clock);
						
					}
					
				}
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§a+1000")) {
					
					e.setCancelled(true);
					
					if(e.getInventory().getName().equals("§dConfig : Bordure")) {
						
						WorldCreator.border = WorldCreator.border+1000*2;
						
						WorldCreator.world.getWorldBorder().setSize(WorldCreator.border);
						
						ItemStack border = new ItemStack(Material.BARRIER);
						
						ItemMeta borderM = border.getItemMeta();
						
						borderM.setDisplayName("§dTaille de la bordure : §6-" + WorldCreator.border/2 + "§d/§6" + WorldCreator.border/2);
						
						border.setItemMeta(borderM);
						
						e.getInventory().setItem(13, border);
						
					}
				}
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§c-1000")) {
					
					e.setCancelled(true);
					
					if(e.getInventory().getName().equals("§dConfig : Bordure")) {
						
						if(WorldCreator.border <= 2000*2) {
							
							WorldCreator.border = 2000*2;
							
							p.sendMessage("§6-" + WorldCreator.border/2 + "/" + WorldCreator.border/2 + " §cest la bordure minimum");
							
							p.playSound(p.getLocation(), Sound.VILLAGER_NO, 100, 1);
							
							return;
							
						}
						
						WorldCreator.border = WorldCreator.border-1000*2;
						
						WorldCreator.world.getWorldBorder().setSize(WorldCreator.border);
						
						ItemStack border = new ItemStack(Material.BARRIER);
						
						ItemMeta borderM = border.getItemMeta();
						
						borderM.setDisplayName("§dTaille de la bordure : §6-" + WorldCreator.border/2 + "§d/§6" + WorldCreator.border/2);
						
						border.setItemMeta(borderM);
						
						e.getInventory().setItem(13, border);
						
					}
					
				}
				
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§dCutClean")) {
					
					Inventory inv = e.getInventory();
					
					if(Scenario.CutClean.isActive()) {
						
						Scenario.CutClean.setActive(false);
						
						Main.getInstance().scenario.remove(Scenario.CutClean.getName());
						
					}else {
						
						Scenario.CutClean.setActive(true);
						
						Main.getInstance().scenario.add(Scenario.CutClean.getName());
						
					}
					
					inv.setItem(0, CutCleanItem());
					
				}
				
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§dUltra Hardcore")) {
					
					Inventory inv = e.getInventory();
					
					if(Scenario.UHC.isActive()) {
						
						Scenario.UHC.setActive(false);
						
						Main.getInstance().scenario.remove(Scenario.UHC.getName());
						
					}else {
						
						Scenario.UHC.setActive(true);
						
						Main.getInstance().scenario.add(Scenario.UHC.getName());
						
					}
					
					inv.setItem(1, UHCItem());
					
				}
				
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§dFast Smelting")) {
					
					Inventory inv = e.getInventory();
					
					if(Scenario.Fast_Smelting.isActive()) {
						
						Scenario.Fast_Smelting.setActive(false);
						
						Main.getInstance().scenario.remove(Scenario.Fast_Smelting.getName());
						
					}else {
						
						Scenario.Fast_Smelting.setActive(true);
						
						Main.getInstance().scenario.add(Scenario.Fast_Smelting.getName());
						
					}
					
					inv.setItem(2, FastSItem());
					
				}
				
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§dTimes-UP")) {
					
					Inventory inv = e.getInventory();
					
					if(Scenario.Times_UP.isActive()) {
						
						Scenario.Times_UP.setActive(false);
						
						Main.getInstance().scenario.remove(Scenario.Times_UP.getName());
						
					}else {
						
						Scenario.Times_UP.setActive(true);
						
						Main.getInstance().scenario.add(Scenario.Times_UP.getName());
						
					}
					
					inv.setItem(3, TimesItem());
					
				}
				
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§dAnonymous")) {
					
					Inventory inv = e.getInventory();
					
					if(Scenario.Anonymous.isActive()) {
						
						Scenario.Anonymous.setActive(false);
						
						Main.getInstance().scenario.remove(Scenario.Anonymous.getName());
						
					}else {
						
						Scenario.Anonymous.setActive(true);
						
						Main.getInstance().scenario.add(Scenario.Anonymous.getName());
						
					}
					
					inv.setItem(4, AnonymousItem());
					
				}
				
				if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equals("§cMenu")) {
					
					p.closeInventory();
					
					Inventory inv = Bukkit.createInventory(null, 45, "§dConfig : Menu");
					
					p.openInventory(inv);
					
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					
					SkullMeta skullM = (SkullMeta) skull.getItemMeta();
					
					skullM.setDisplayName("§dNombre de joueur");
					
					skull.setItemMeta(skullM);
					
					
					ItemStack clock = new ItemStack(Material.WATCH);
					
					ItemMeta clockM = clock.getItemMeta();
					
					clockM.setDisplayName("§dTemps de chaque manche");
					
					clock.setItemMeta(clockM);
					
					
					
					ItemStack border = new ItemStack(Material.BARRIER);
					
					ItemMeta borderM = border.getItemMeta();
					
					borderM.setDisplayName("§dTaille de la bordure");
					
					border.setItemMeta(borderM);
					
					
					ItemStack scenario = new ItemStack(Material.BOOK);
					
					ItemMeta scenarioM = scenario.getItemMeta();
					
					scenarioM.setDisplayName("§dScenario");
					
					scenario.setItemMeta(scenarioM);
					
					
					
					inv.setItem(11, skull);
					
					inv.setItem(13, clock);
					
					inv.setItem(15, border);
					
					inv.setItem(31, scenario);
					
				}
				
				if(e.getInventory().getName().startsWith("§dConfig")) {
					
					e.setCancelled(true);
					
				}
				
			}
			
		}
		
	}
	
	public ItemStack CutCleanItem() {
		
		ItemStack CutClean = new ItemStack(Material.COAL);
		
		ItemMeta Meta = CutClean.getItemMeta();
		
		Meta.setDisplayName("§dCutClean");
		
		if(Scenario.CutClean.isActive()) {
			
			Meta.setLore(Arrays.asList("§aON", "§8tous les drops sont", "§8déjà cuits"));
			
		}else {
			
			Meta.setLore(Arrays.asList("§cOFF", "§8tous les drops sont", "§8déjà cuits"));
			
		}
		
		CutClean.setItemMeta(Meta);
		
		return CutClean;
		
	}
	
	public ItemStack UHCItem() {
		
		ItemStack UHC = new ItemStack(Material.GOLDEN_APPLE);
		
		ItemMeta Meta = UHC.getItemMeta();
		
		Meta.setDisplayName("§dUltra Hardcore");
		
		if(Scenario.UHC.isActive()) {
			
			Meta.setLore(Arrays.asList("§aON", "§8la régénération auto est", "§8désactivée"));
			
		}else {
			
			Meta.setLore(Arrays.asList("§cOFF", "§8la régénération auto est", "§8désactivée"));
			
		}
		
		UHC.setItemMeta(Meta);
		
		return UHC;
		
	}
	
	public ItemStack FastSItem() {
		
		ItemStack Fast = new ItemStack(Material.FURNACE);
		
		ItemMeta Meta = Fast.getItemMeta();
		
		Meta.setDisplayName("§dFast Smelting");
		
		if(Scenario.Fast_Smelting.isActive()) {
			
			Meta.setLore(Arrays.asList("§aON", "§8le temps de cuisson", "§8est accéléré"));
			
		}else {
			
			Meta.setLore(Arrays.asList("§cOFF", "§8le temps de cuisson", "§8est accéléré"));
			
		}
		
		Fast.setItemMeta(Meta);
		
		return Fast;
		
	}
	
	public ItemStack TimesItem() {
		
		ItemStack Times = new ItemStack(Material.WATCH);
		
		ItemMeta Meta = Times.getItemMeta();
		
		Meta.setDisplayName("§dTimes-UP");
		
		if(Scenario.Times_UP.isActive()) {
			
			Meta.setLore(Arrays.asList("§aON", "§8le temps entre", "§8chaque téléportation", "§8est inconnu et aléatoire"));
			
		}else {
			
			Meta.setLore(Arrays.asList("§cOFF", "§8le temps entre", "§8chaque téléportation", "§8est inconnu et aléatoire"));
			
		}
		
		Times.setItemMeta(Meta);
		
		return Times;
		
	}
	
	public ItemStack AnonymousItem() {
		
		ItemStack Anonymous = new ItemStack(Material.EYE_OF_ENDER);
		
		ItemMeta Meta = Anonymous.getItemMeta();
		
		Meta.setDisplayName("§dAnonymous");
		
		if(Scenario.Anonymous.isActive()) {
			
			Meta.setLore(Arrays.asList("§aON", "§8l'identité de chaque joueur", "§8est masquée"));
			
		}else {
			
			Meta.setLore(Arrays.asList("§cOFF", "§8l'identité de chaque joueur", "§8est masquée"));
			
		}
		
		Anonymous.setItemMeta(Meta);
		
		return Anonymous;
		
	}
}
