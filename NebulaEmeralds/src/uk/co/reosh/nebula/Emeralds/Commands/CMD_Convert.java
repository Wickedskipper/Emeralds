package uk.co.reosh.nebula.Emeralds.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import uk.co.reosh.nebula.Emeralds.Emeralds;

public class CMD_Convert implements SubCommand {
	
	private boolean adminOnly = false;
	private String permission = "emeralds.user.convert";
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(Player player, String[] args) {
		if(player.hasPermission(permission) || player.isOp()){
			int emeralds = 0;
			Inventory inv = player.getInventory();
			
			for (ItemStack i : inv) {
				if(i != null) {
					if (i.getType() == Material.EMERALD) {
						emeralds += i.getAmount();
					}
				}
			}
			
			if (emeralds == 0) {
				player.sendMessage(Emeralds.PREFIX + "You have no emeralds to convert in your inventory.");
			} else {
				player.getInventory().removeItem(new ItemStack(Material.EMERALD, emeralds));
				player.updateInventory(); //Temporary workaround
				
				Emeralds.em.giveEmeralds(player, emeralds, null, "Emeralds conversion");
			}
		}
        return false;
    }
	
    public void help(Player p) {
        String help = Emeralds.UI_COLOR + "/emeralds convert" + ChatColor.WHITE + " - Convert your old emeralds to new 'virtual' emeralds.";
        if (adminOnly == true) {
        	if (p.hasPermission(permission) || p.isOp()) {
        		p.sendMessage(help);
        	}
        } else {
        	p.sendMessage(ChatColor.AQUA + help);
        }
    }

}