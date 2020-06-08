package uk.co.reosh.nebula.Emeralds.API;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import uk.co.reosh.nebula.Emeralds.Emeralds;
import uk.co.reosh.nebula.Emeralds.DB.Database;

public class EmeraldManager {

	public EmeraldManager() {
		
	}
	
	public boolean userExists(String user){
		try{
			ResultSet rs = Database.MySQL.executeQuery("SELECT 1 FROM users WHERE username='" + user + "'");
	        return rs.next();
		}catch(Exception e){e.printStackTrace(); return false;}
	}
	
	public int getEmeralds(Player player) {
		try {
			if(userExists(player.getName())) {
				ResultSet rs = Database.MySQL.executeQuery("SELECT * FROM users WHERE username='" + player.getName() + "'");
				if(rs.next()) {
					return rs.getInt("emeralds");
				} else {
					return -1;
				}
			} else {
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public boolean hasEnoughEmeralds(Player player, int emeralds) {
		return getEmeralds(player) >= emeralds;
	}
	
	public void giveEmeralds(Player player, int emeralds, Player giver, String reason){
		if (userExists(player.getName())){
			String MESSAGE;
			if (giver == null){
				MESSAGE = Emeralds.PREFIX + "Server gave you " + Emeralds.UI_COLOR + emeralds + ChatColor.WHITE + " emeralds. Reason: " + reason;
			} else {
				MESSAGE = Emeralds.PREFIX + "" + Emeralds.UI_COLOR + giver.getName() + ChatColor.WHITE + " gave you " + Emeralds.UI_COLOR + emeralds + ChatColor.WHITE + " emeralds. Reason: " + reason;
				giver.sendMessage(Emeralds.PREFIX + "You gave " + Emeralds.UI_COLOR + player.getName() + ChatColor.WHITE + " " + Emeralds.UI_COLOR + emeralds + ChatColor.WHITE + " emeralds. Reason: " + reason);
			}
			
			Database.MySQL.executeUpdate("UPDATE users SET emeralds=emeralds+" + emeralds + " WHERE username='" + player.getName() + "'");
			player.sendMessage(MESSAGE);
		}
	}
	
	public void takeEmeralds(Player player, int emeralds, Player taker, String reason){
		if (userExists(player.getName())){
			String MESSAGE;
			if (taker == null){
				MESSAGE = Emeralds.PREFIX + "Server took your " + Emeralds.UI_COLOR + emeralds + ChatColor.WHITE + " emeralds. Reason: " + reason;
			} else {
				MESSAGE = Emeralds.PREFIX + Emeralds.UI_COLOR + emeralds + ChatColor.WHITE + " emeralds were taken from your account by " + Emeralds.UI_COLOR + taker.getName() + ChatColor.WHITE + ". Reason: " + reason;
				taker.sendMessage(Emeralds.PREFIX + "You took " + Emeralds.UI_COLOR + emeralds + ChatColor.WHITE + " emeralds from " + Emeralds.UI_COLOR + player.getName() + ChatColor.WHITE + ". Reason: " + reason);
			}
			
			Database.MySQL.executeUpdate("UPDATE users SET emeralds=emeralds-" + emeralds + " WHERE username='" + player.getName() + "'");
			player.sendMessage(MESSAGE);
		}
	}
}
