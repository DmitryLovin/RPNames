package my.dmitrylovin.plugin.rpnames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.clip.placeholderapi.PlaceholderAPI;
import my.dmitrylovin.plugin.rpnames.RPNames;
import my.dmitrylovin.plugin.rpnames.utils.MessagesManager;
import my.dmitrylovin.plugin.rpnames.utils.RPNamesUtils;

public class RPYesCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player))
			return false;
		final String UUID = ((Player)sender).getUniqueId().toString();
		if(RPNamesUtils.inQueue(UUID)) {
			new BukkitRunnable() {
				@Override
				public void run() {
					if(RPNamesUtils.saveRPName(UUID)) {
						String message = MessagesManager.get("rpname-confirmed");
						message = PlaceholderAPI.setPlaceholders((Player)sender, message);
						sender.sendMessage(message);
					} 
					else 
						sender.sendMessage(MessagesManager.get("rpname-confirmed-error"));
				}
			}.runTaskAsynchronously(RPNames.getPlugin());
			return true;
		}
		else
			return false;
	}
}
