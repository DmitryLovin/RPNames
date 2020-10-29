package my.dmitrylovin.plugin.rpnames.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import my.dmitrylovin.plugin.rpnames.RPNames;
import my.dmitrylovin.plugin.rpnames.utils.MessagesManager;
import my.dmitrylovin.plugin.rpnames.utils.RPNamesUtils;

public class ForceRPNameCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length<2)return false;
		
		String name = args[1];
		for(int i=2;i<args.length;i++) {
			name+=" "+args[i];
		}
		Player player = RPNames.getPlugin().getServer().getPlayer(args[0]);
		if(player==null) {
			String message = MessagesManager.get("force-error");
			message = message.replace("{name}", args[0]);
			sender.sendMessage(message);
			return true;
		}
		if(MessagesManager.containBlacklist(name)) {
			sender.sendMessage(MessagesManager.get("rpname-blacklist"));
			return true;
		}
		if(name.length()>MessagesManager.getLength()) {
			String message = MessagesManager.get("rpname-too-long");
			message = PlaceholderAPI.setPlaceholders((Player)sender, message);
			sender.sendMessage(message);
			return true;
		}
		if(RPNamesUtils.forceSave(player.getUniqueId().toString(), name)) {
			String message = MessagesManager.get("force-confirmed");
			message = PlaceholderAPI.setPlaceholders(player, message);
			message = message.replace("{username}", args[0]);
			sender.sendMessage(message);
			return true;
		} 
		else 
			sender.sendMessage(MessagesManager.get("rpname-confirmed-error"));
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length>1)return new ArrayList<String>();
		return null;
	}

}
