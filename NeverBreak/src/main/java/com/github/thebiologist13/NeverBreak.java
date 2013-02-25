package com.github.thebiologist13;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.thebiologist13.listeners.BowFireListener;
import com.github.thebiologist13.listeners.EntityDamageListener;
import com.github.thebiologist13.listeners.FishListener;
import com.github.thebiologist13.listeners.LoginListener;
import com.github.thebiologist13.listeners.LogoutListener;
import com.github.thebiologist13.listeners.ToolUseEvent;
import com.github.thebiologist13.listeners.WorldChangeListener;

public class NeverBreak extends JavaPlugin {
	
	//Map of modes
	public static ConcurrentHashMap<Player, Boolean> mode = new ConcurrentHashMap<Player, Boolean>();
	
	//YAML variable
	private FileConfiguration config;
	
	//YAML file variable
	private File configFile;
	
	//Map of durability
	private HashMap<Integer, Integer> configuredDurabilities = new HashMap<Integer, Integer>();
	
	//Logger
	public Logger log = Logger.getLogger("Minecraft");
	
	public void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			log.severe("Could not copy config from jar!");
			e.printStackTrace();
		}
	}
	
	public short getConfiguredDurability(ItemStack stack) {
		int id = stack.getTypeId();
		if(this.configuredDurabilities.containsKey(id)) {
			return this.configuredDurabilities.get(id).shortValue();
		} else {
			return stack.getType().getMaxDurability();
		}
	}
	
	public FileConfiguration getCustomConfig() {
		if (config == null) {
	        reloadCustomConfig();
	    }
	    return config;
	}
	
	public HashMap<Integer, Integer> getItems() {
		List<?> items = config.getList("items");
		HashMap<Integer, Integer> map = null;

		if(items != null) {
			
			map = new HashMap<Integer, Integer>();
			
			//Loop for all items from config
			for(Object o : items) {

				int itemId = -1;
				int damageInt = -1;

				String input = String.valueOf(o);
				
				int dashIndex = input.indexOf("-");
				
				try {
					if(dashIndex == -1) {
						itemId = Integer.parseInt(input);
						
						Material m = Material.getMaterial(itemId);
						
						if(m != null) {
							damageInt = m.getMaxDurability();
						} else {
							log.info("Invalid config value " + input + ". Make sure it is in the format <item id>-<item damage>");
							continue;
						}
							
						
					} else {
						String itemStr = input.substring(0, dashIndex);
						String damageStr = input.substring(dashIndex + 1, input.length());
						
						itemId = Integer.parseInt(itemStr);
						damageInt = Integer.parseInt(damageStr);
					}
				} catch(NumberFormatException e) {
					log.info("Invalid config value " + input + ". Make sure it is in the format <item id>-<item damage>");
					continue;
				}
				
				if(damageInt <= -1 || itemId <= 0) {
					log.info("Invalid config value " + input + ". Make sure it is in the format <item id>-<item damage>");
					continue;
				} else {
					map.put(itemId, damageInt);
				}

			}
			
		}
		
		return map;
		
	}
	
	public boolean getMode(Player p) {
		if(mode.containsKey(p))
			return mode.get(p);
		else
			return false;
	}
	
	public void onDisable() {
		//Disable message
		log.info("[NeverBreak] NeverBreak v" + this.getDescription().getVersion() + " by thebiologist13 has been disabled!");
	}
	
	public void onEnable() {
		
		//Config
		config = this.getCustomConfig();
		
		//Listeners
		getServer().getPluginManager().registerEvents(new ToolUseEvent(this), this);
		getServer().getPluginManager().registerEvents(new LoginListener(this), this);
		getServer().getPluginManager().registerEvents(new LogoutListener(), this);
		getServer().getPluginManager().registerEvents(new FishListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
		getServer().getPluginManager().registerEvents(new WorldChangeListener(this), this);
		getServer().getPluginManager().registerEvents(new BowFireListener(this), this);
		
		//Toggle command setup
		getCommand("neverbreak").setExecutor(new ToggleCommand(this));
		
		//Durability command setup
		getCommand("setdurability").setExecutor(new DurabilityCommand(this));
		
		//Durability map setting
		putDamages(config);
		
		//Just removes players from the map that are not logged in anymore
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				
				for(Player p : mode.keySet()) {
					
					if(!p.isOnline()) {
						mode.remove(p);
					}
					
				}
				
			}
			
		}, 20, 1200);
		
		//Enable message
		log.info("[NeverBreak] NeverBreak v" + this.getDescription().getVersion() + " by thebiologist13 has been enabled!");
	}
	
	//Config stuff
	//Credit goes to respective owners.
	public void reloadCustomConfig() {
		if (configFile == null) {
		    configFile = new File(getDataFolder(), "config.yml");
		    
		    if(!configFile.exists()){
		        configFile.getParentFile().mkdirs();
		        copy(getResource("config.yml"), configFile);
		    }
		}
		
		config = YamlConfiguration.loadConfiguration(configFile);
		 
		// Look for defaults in the jar
		InputStream defConfigStream = this.getResource("config.yml");
		if (defConfigStream != null) {
			FileConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			config.options().copyDefaults(true);
			config.setDefaults(defConfig);
		}
	}
	
	public void saveCustomConfig() {
		if (config == null || configFile == null) {
		    return;
		}
		try {
			config.save(configFile);
		} catch (IOException ex) {
			log.severe("Could not save config to " + configFile.getPath());
		}
	}

	public void sendMessage(CommandSender sender, String message) {
		
		if(sender == null) 
			return;
		
		Player p = null;
		
		if(sender instanceof Player)
			p = (Player) sender;
		
		if(p == null) {
			message = ChatColor.stripColor(message);
			log.info(message);
		} else {
			p.sendMessage(message);
		}
		
	}

	public void sendMessage(CommandSender sender, String[] message) {
		
		if(sender == null) 
			return;
		
		Player p = null;
		
		if(sender instanceof Player)
			p = (Player) sender;
		
		if(p == null) {
			
			for(String s : message) {
				s = ChatColor.stripColor(s);
				log.info(s);
			}
			
		} else {
			p.sendMessage(message);
		}
		
	}
	
	public void setMode(Player p, boolean mode) {
		NeverBreak.mode.put(p, mode);
	}
	
	public void toggleMode(Player p) {
		boolean cur;
		
		if(mode.containsKey(p)) {
			cur = NeverBreak.mode.get(p);
		} else {
			cur = config.getBoolean("autoNeverBreak", false);
		}
		
		NeverBreak.mode.put(p, !cur);
	}
	
	private void putDamages(FileConfiguration config) {
		
		HashMap<Integer, Integer> mappedValues = getItems();
		
		if(mappedValues != null) {
			configuredDurabilities = mappedValues;
		}
		
	}
	
}
