package my.dmitrylovin.plugin.rpnames;

import my.dmitrylovin.plugin.rpnames.listeners.PlayerListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import my.dmitrylovin.plugin.rpnames.commands.FindNameCommand;
import my.dmitrylovin.plugin.rpnames.commands.ForceRPNameCommand;
import my.dmitrylovin.plugin.rpnames.commands.RPNameCommand;
import my.dmitrylovin.plugin.rpnames.commands.RPNoCommand;
import my.dmitrylovin.plugin.rpnames.commands.RPYesCommand;
import my.dmitrylovin.plugin.rpnames.placeholder.RPNamePlaceholder;
import my.dmitrylovin.plugin.rpnames.utils.MessagesManager;
import my.dmitrylovin.plugin.rpnames.utils.RPNamesUtils;

public class RPNames extends JavaPlugin{
	
	private static RPNames plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		this.saveDefaultConfig();
		MessagesManager.loadMessages();
		RPNamesUtils.loadNames();
		this.getCommand("rpname").setExecutor((CommandExecutor)new RPNameCommand());
		this.getCommand("rpyes").setExecutor((CommandExecutor)new RPYesCommand());
		this.getCommand("rpno").setExecutor((CommandExecutor)new RPNoCommand());
		this.getCommand("findname").setExecutor((CommandExecutor)new FindNameCommand());
		this.getCommand("forcename").setExecutor((CommandExecutor)new ForceRPNameCommand());
		new RPNamePlaceholder().register();
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(), this);
		this.getLogger().info("RPNames enabled");
	}

	@Override
	public void onDisable() {
		this.getLogger().info("RPNames disabled");
	}
	
	public static void Disable() {
		plugin.setEnabled(false);
	}
	
	public static RPNames getPlugin() {
		return plugin;
	}
}
