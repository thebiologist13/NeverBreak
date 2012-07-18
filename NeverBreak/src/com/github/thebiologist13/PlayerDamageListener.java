package com.github.thebiologist13;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDamageListener implements Listener {
	
	private FileConfiguration config = null;
	
	public PlayerDamageListener(NeverBreak plugin) {
		config = plugin.getCustomConfig();
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent ev) {
		//Player
		Player p = null;
		//Entity
		Entity e = ev.getEntity();
		
		if(e instanceof Player) {
			p = (Player) e;
		} else {
			return;
		}
		
		//Getting the armour of the player
		ItemStack[] armor = p.getInventory().getArmorContents();
		
		if(armor.length == 0) {
			return;
		} else {
			//Items that NeverBreak can be used with
			List<?> items = config.getList("items");
			//Loop for all items from config
			for(Object o : items) {
				//Make sure that it is specifying data IDs 
				if(o instanceof Integer) {
					for(int i = 0; i < armor.length; i++) {
						//If item in armor slot matches one from config
						if(armor[i].getTypeId() == (Integer) o && config.getBoolean("allowArmor", false)) {
							//If a mode has been set for the player
							if(ToggleCommand.mode.containsKey(p)) {
								//If that mode is true
								if(ToggleCommand.mode.get(p) == true) {
									//Set the item to -1 durability
									armor[i].setDurability((short) -128);
								//If that mode is false, proceed as normal 
								} else {
									//Unless it was set to REALLY unused, then make the durability 0 again
									if(armor[i].getDurability() < 0 ) {
										armor[i].setDurability((short) 0);
									}
								}
							} 
						}
					}
				//Continue if not a data ID
				} else {
					continue;
				}
			}
		}
	}
}
