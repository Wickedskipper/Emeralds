package uk.co.reosh.nebula.Emeralds.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import uk.co.reosh.nebula.Emeralds.Emeralds;

public class CMD_Info implements SubCommand {
	
	private boolean adminOnly = true;
	private String permission = "emeralds.admin.info";
	
	public boolean onCommand(Player player, String[] args) {
		if(player.hasPermission(permission) || player.isOp()){
			if(args.length >= 1) {
				try {
					Player p = Bukkit.getPlayer(args[0]);
					int emeralds = Emeralds.em.getEmeralds(p);
					player.sendMessage(Emeralds.PREFIX + "Player " + Emeralds.UI_COLOR + p.getName() + ChatColor.WHITE + " has "  + Emeralds.UI_COLOR + emeralds + ChatColor.WHITE + " emerald/-s");
				} catch (Exception ex){
					player.sendMessage(Emeralds.PREFIX + "/emeralds info <player>");
				}
			} else {
				player.sendMessage(Emeralds.PREFIX + "/emeralds info <player>");
			}
		}
        return false;
    }
	
    public void help(Player p) {
        String help = Emeralds.UI_COLOR + "/emeralds info <player>" + ChatColor.WHITE + " - Check player's account.";
        if (adminOnly == true) {
        	if (p.hasPermission(permission) || p.isOp()) {
        		p.sendMessage(help);
        	}
        } else {
        	p.sendMessage(ChatColor.AQUA + help);
        }
    }

}
