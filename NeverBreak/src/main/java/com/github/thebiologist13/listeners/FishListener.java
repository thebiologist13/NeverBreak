package com.github.thebiologist13.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import com.github.thebiologist13.ItemTagger;
import com.github.thebiologist13.NeverBreak;

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
		
		if(plugin.getMode(p))
			stack.setDurability((short) -1);
		
		ItemTagger tagger = new ItemTagger(plugin);
		tagger.recalculateDurability(stack);
	}
}
