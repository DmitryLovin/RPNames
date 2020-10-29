package my.dmitrylovin.plugin.rpnames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import my.dmitrylovin.plugin.rpnames.utils.MessagesManager;
import my.dmitrylovin.plugin.rpnames.utils.RPNamesUtils;

public class RPNoCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player))
			return false;
		final String UUID = ((Player)sender).getUniqueId().toString();
		if(RPNamesUtils.inQueue(UUID)) {
			RPNamesUtils.removeFromQueue(UUID);
			sender.sendMessage(MessagesManager.get("rpname-decline"));
		}
		return false;
	}
	

}
