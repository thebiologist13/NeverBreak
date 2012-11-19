package com.github.thebiologist13.listeners;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.github.thebiologist13.NeverBreak;

public class WorldChangeListener implements Listener {
	
	private NeverBreak plugin;
	
	private FileConfiguration config;
	
	public WorldChangeListener(NeverBreak plugin) {
		this.plugin = plugin;
		this.config = plugin.getCustomConfig();
	}
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent ev) {
		
		Player p = ev.getPlayer();
		
		if(!config.getBoolean("globalNeverBreak", true)) {
			plugin.setMode(p, config.getBoolean("autoNeverBreak", false));
			plugin.sendMessage(p, ChatColor.GREEN + "Changing NeverBreak mode to " + ChatColor.GOLD + plugin.getMode(p) + ChatColor.GREEN + " for this world.");
		} 
	}
}
