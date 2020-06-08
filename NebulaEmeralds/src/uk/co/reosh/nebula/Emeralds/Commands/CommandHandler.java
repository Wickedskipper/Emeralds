package uk.co.reosh.nebula.Emeralds.Commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import uk.co.reosh.nebula.Emeralds.Emeralds;

public class CommandHandler implements CommandExecutor
{
    @SuppressWarnings("unused")
	private Plugin plugin;
    private HashMap<String, SubCommand> commands;
    
    public CommandHandler(Plugin plugin)
    {
        this.plugin = plugin;
        commands = new HashMap<String, SubCommand>();
        loadCommands();
    }

    private void loadCommands()
    {
    	commands.put("give", new CMD_Give());
    	commands.put("take", new CMD_Take());
    	commands.put("info", new CMD_Info());
    	commands.put("convert", new CMD_Convert());
    }

    public boolean onCommand(CommandSender sender, Command cmd1, String commandLabel, String[] args){
        String cmd = cmd1.getName();

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        
        if(cmd.equalsIgnoreCase("emeralds")){ 
            if(args == null || args.length < 1){
            	player.sendMessage(Emeralds.PREFIX + "You have " + Emeralds.UI_COLOR + Emeralds.em.getEmeralds(player) + ChatColor.WHITE + " emerald/-s");

                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                help(player);
                return true;
            }
            String sub = args[0];

            Vector<String> l  = new Vector<String>();
            l.addAll(Arrays.asList(args));
            l.remove(0);
            args = (String[]) l.toArray(new String[0]);
            try{
            	commands.get(sub).onCommand(player, args);
            }catch(NullPointerException ne){
            	player.sendMessage(Emeralds.PREFIX + "Command not found! " + ChatColor.BOLD + "Try /emeralds help");
            }catch(Exception e){e.printStackTrace(); player.sendMessage(Emeralds.PREFIX + "Type /emeralds help for help" );
            }
            return true;
        }
        return false;
    }
    
    public void help(Player p){
        for(SubCommand v: commands.values()){
        	v.help(p);
        }
    }
}
