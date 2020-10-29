package my.dmitrylovin.plugin.rpnames.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.scheduler.BukkitRunnable;

import my.dmitrylovin.plugin.rpnames.RPNames;
import my.dmitrylovin.plugin.rpnames.utils.MessagesManager;
import my.dmitrylovin.plugin.rpnames.utils.RPNamesUtils;

public class FindNameCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(args.length==0)return false;
		
		new BukkitRunnable() {
			@Override
			public void run() {
				String name = args[0];
				for(int i=1;i<args.length;i++) {
					name+=" "+args[i];
				}
				if(MessagesManager.containBlacklist(name)) {
					sender.sendMessage(MessagesManager.get("find-name-blacklist"));
					return;
				}
				if(RPNamesUtils.getFromName(name)==null) {
					String message = MessagesManager.get("find-name-error");
					message = message.replace("{rpname}", name);
					sender.sendMessage(message);
					return;
				}
				String message = MessagesManager.get("find-name");
				message = message.replace("{rpname}", name);
				UUID uuid = UUID.fromString(RPNamesUtils.getFromName(name));
				message = message.replace("{username}", Bukkit.getPlayer(uuid).getName());
				sender.sendMessage(message);
			}
		}.runTaskAsynchronously(RPNames.getPlugin());
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return new ArrayList<String>();
	}

}
