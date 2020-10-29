package my.dmitrylovin.plugin.rpnames.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import my.dmitrylovin.plugin.rpnames.RPNames;
import my.dmitrylovin.plugin.rpnames.utils.MessagesManager;
import my.dmitrylovin.plugin.rpnames.utils.RPNamesUtils;
import my.dmitrylovin.plugin.rpnames.utils.StringUtil;

public class RPNameCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		if(args.length==0)return false;
		RPNamesUtils.removeFromQueue(((Player)sender).getUniqueId().toString());
		
		new BukkitRunnable() {
			@Override
			public void run() {
				String name = args[0];
				for(int i=1;i<args.length;i++) {
					name+=" "+args[i];
				}
				if(MessagesManager.containBlacklist(name)) {
					sender.sendMessage(MessagesManager.get("rpname-blacklist"));
					return;
				}
				if(name.length()>MessagesManager.getLength()) {
					String message = MessagesManager.get("rpname-too-long");
					message = PlaceholderAPI.setPlaceholders((Player)sender, message);
					sender.sendMessage(message);
					return;
				}
				TextComponent text = new TextComponent(MessagesManager.get("are-you-sure-message"));
				TextComponent yes = new TextComponent(StringUtil.colorize("&aYes"));
				yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Yes").create()));
				yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/rpyes"));
				TextComponent btw = new TextComponent(StringUtil.colorize(" &8&l| "));
				TextComponent no = new TextComponent(StringUtil.colorize("&cNo"));
				no.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("No").create()));
				no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/rpno"));
				text.addExtra(yes);
				text.addExtra(btw);
				text.addExtra(no);
				((Player)sender).spigot().sendMessage(text);
				RPNamesUtils.addToQueue(((Player)sender).getUniqueId().toString(), name);
				
				new BukkitRunnable() {
					@Override
					public void run() {
						if(RPNamesUtils.inQueue(((Player)sender).getUniqueId().toString())) {
							sender.sendMessage(MessagesManager.get("rpname-timeout"));
							RPNamesUtils.removeFromQueue(((Player)sender).getUniqueId().toString());	
						}
					}
				}.runTaskLater(RPNames.getPlugin(), MessagesManager.getTimeout());
				
			}
		}.runTaskAsynchronously(RPNames.getPlugin());
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {	
		return new ArrayList<String>();
	}	
}
