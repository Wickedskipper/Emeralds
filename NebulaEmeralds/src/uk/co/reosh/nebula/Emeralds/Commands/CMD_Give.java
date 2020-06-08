package uk.co.reosh.nebula.Emeralds.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import uk.co.reosh.nebula.Emeralds.Emeralds;

public class CMD_Give implements SubCommand {
	
	private boolean adminOnly = true;
	private String permission = "emeralds.admin.give";
	
	public boolean onCommand(Player player, String[] args) {
		if(player.hasPermission(permission) || player.isOp()){
			if(args.length >= 3) {
				try {
					Player p = Bukkit.getPlayer(args[0]);
					int emeralds = Integer.parseInt(args[1]);
					
					StringBuilder buffer = new StringBuilder();
					for(int i = 2; i < args.length; i++)
					{
					    buffer.append(' ').append(args[i]);
					}
					String reason = buffer.toString().trim();
					
					Emeralds.em.giveEmeralds(p, emeralds, player, reason);
				} catch (Exception ex){
					player.sendMessage(Emeralds.PREFIX + "/emeralds give <player> <emeralds> <reason>");
				}
			} else {
				player.sendMessage(Emeralds.PREFIX + "/emeralds give <player> <emeralds> <reason>");
			}
		}
        return false;
    }
	
    public void help(Player p) {
        String help = Emeralds.UI_COLOR + "/emeralds give <player> <emeralds> <reason>" + ChatColor.WHITE + " - Give player emeralds.";
        if (adminOnly == true) {
        	if (p.hasPermission(permission) || p.isOp()) {
        		p.sendMessage(help);
        	}
        } else {
        	p.sendMessage(ChatColor.AQUA + help);
        }
    }

}
