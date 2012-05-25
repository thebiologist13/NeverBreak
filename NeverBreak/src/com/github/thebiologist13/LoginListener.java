package com.github.thebiologist13;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {
	
	private FileConfiguration config;
	Logger log = Logger.getLogger("Minecraft");
	
	public LoginListener(NeverBreak neverBreak) {
		this.config = neverBreak.getCustomConfig();
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent ev) {
		Player p = ev.getPlayer();
		ToggleCommand.mode.put(p, config.getBoolean("autoNeverBreak"));
	}
}
