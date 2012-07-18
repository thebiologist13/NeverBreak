package com.github.thebiologist13;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishListener implements Listener {
	
	private NeverBreak plugin = null;
	
	public FishListener(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerFish(PlayerFishEvent ev) {
		//Player Variable
		Player p = ev.getPlayer();
		//Item player has in hand
		ItemStack stack = p.getItemInHand();
		
		plugin.resetDurability(stack, p);
	}
}
