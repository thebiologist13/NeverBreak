package com.github.thebiologist13.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.github.thebiologist13.ItemTagger;
import com.github.thebiologist13.NeverBreak;

public class ToolUseEvent implements Listener {

	private NeverBreak plugin;
	
	public ToolUseEvent(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onToolUse(PlayerInteractEvent ev) {
		/*
		 * ItemMeta format:
		 *   NeverBreak-<NB usage>:<NB total>:<Previous Durability> 
		 */
		Player p = ev.getPlayer();
		
		ItemStack stack = p.getItemInHand();
		ItemTagger tagger = new ItemTagger(plugin);
		
		if(plugin.getMode(p)) {
			tagger.cancelDurabilityLoss(stack);
			return;
		}
		
		tagger.recalculateDurability(stack);
		
	}
	
}
