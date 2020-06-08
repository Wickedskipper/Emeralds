package uk.co.reosh.nebula.Emeralds;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.reosh.nebula.Emeralds.API.EmeraldManager;
import uk.co.reosh.nebula.Emeralds.Commands.CommandHandler;
import uk.co.reosh.nebula.Emeralds.DB.Database;
import uk.co.reosh.nebula.Emeralds.DB.MySQLAPI;
import uk.co.reosh.nebula.Emeralds.Events.PlayerJoin;

public class Emeralds extends JavaPlugin {
	
	public static String UI_COLOR = "" + ChatColor.GREEN;
	public static String PREFIX = ChatColor.WHITE + "[" + UI_COLOR + "EMERALDS" + ChatColor.WHITE + "] ";
	
	Emeralds p = this;
	public static EmeraldManager em = new EmeraldManager();

	public final Logger logger = Logger.getLogger("Minecraft");
	
	public void onDisable() {
		PluginDescriptionFile pdfile = this.getDescription();
		this.logger.info(pdfile.getName() + " has been disabled!");
	}
	
	public void onEnable() {
		PluginDescriptionFile pdfile = this.getDescription();
		
		PluginManager pm = getServer().getPluginManager();
		// Events //
		pm.registerEvents(new PlayerJoin(), this);
		////////////
		
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		logger.info("[MySQL] Connecting to MySQL");
		Database.MySQL = new MySQLAPI(p);
		Database.MySQL.Connect(this.getConfig().getString("database.host"), this.getConfig().getString("database.port"), this.getConfig().getString("database.db"), this.getConfig().getString("database.user"), this.getConfig().getString("database.pass"));
		
		setCommands();
		
		this.logger.info(pdfile.getName() + " " + pdfile.getVersion() + " has been enabled!");
	}
	
	public void setCommands() {
        getCommand("emeralds").setExecutor(new CommandHandler(p));
    }
	
	public static void registerPlayer(Player pl){
		Database.MySQL.insertEntry("users", new String[]{"username","emeralds"}, new String[]{"'" + pl.getName() + "'", "0"});
	}
}

