package com.github.thebiologist13.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import com.github.thebiologist13.ItemTagger;
import com.github.thebiologist13.NeverBreak;

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
		
		if(plugin.getMode(p))
			stack.setDurability((short) -1);
		
		ItemTagger tagger = new ItemTagger(plugin);
		tagger.recalculateDurability(stack);
	}
}
