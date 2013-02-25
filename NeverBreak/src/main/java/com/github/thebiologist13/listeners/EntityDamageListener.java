package com.github.thebiologist13.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.github.thebiologist13.ItemTagger;
import com.github.thebiologist13.NeverBreak;

public class EntityDamageListener implements Listener {
	
	private NeverBreak plugin = null;
	
	public EntityDamageListener(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent ev) {
		//Player
		Player p = null;
		//Getting a player
		Entity e = ev.getEntity();
		if(e instanceof Player) {
			p = (Player) e;
		} else {
			return;
		}
		
		if(!plugin.getCustomConfig().getBoolean("allowArmor", true))
			return;
		
		//Armor the player is wearing
		ItemStack[] armor = p.getInventory().getArmorContents();
		
		ItemTagger tagger = new ItemTagger(plugin);
		
		for(int i = 0; i < armor.length; i++) {
			
			if(plugin.getMode(p))
				armor[i].setDurability((short) -1);
			
			tagger.recalculateDurability(armor[i]);
		}
		
		p.getInventory().setArmorContents(armor);
		
	}
	
}
