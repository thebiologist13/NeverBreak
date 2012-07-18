package com.github.thebiologist13;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class BowFireListener implements Listener {
	
	private NeverBreak plugin;
	
	public BowFireListener(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBowFire(EntityShootBowEvent ev) {
		//Player
		Player p = null;
		//Entity
		Entity e = ev.getEntity();
		
		if(e instanceof Player) {
			p = (Player) e;
		} else {
			return;
		}
		
		//Item player has in hand
		ItemStack stack = p.getItemInHand();
		
		plugin.resetDurability(stack, p);
	}
}
