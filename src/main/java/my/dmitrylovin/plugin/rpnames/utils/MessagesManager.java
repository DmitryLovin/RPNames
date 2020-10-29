package my.dmitrylovin.plugin.rpnames.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import my.dmitrylovin.plugin.rpnames.RPNames;

public class MessagesManager {

	private static Map<String, String> messages;
	private static File messageFile;
	private static YamlConfiguration messageConfig;
	private static String[] symbols;
	private static int length = 26;
	private static int timeout = 300;
	private static String color = "&7";
	
	public static String get(final String s) {
        if (messages == null) {
            loadMessages();
        }
        return StringUtil.colorize((String)getMessages().get(s));
    }
	
	public static boolean containBlacklist(String message) {
		for(String s:symbols) {
			if(message.contains(s))return true;
		}
		return false;
	}
	
	public static void loadMessages() {
        messages = new HashMap<String, String>();
        if (getSavesConfig() == null || getSavesFile() == null) {
            //loadSavesFile();
        }
        RPNames.getPlugin().getLogger().info("Loading configuration...");
        messageFile = new File(RPNames.getPlugin().getDataFolder(), "messages.yml");
        if(!messageFile.exists())createNewFile(messageFile);
        messageConfig = YamlConfiguration.loadConfiguration(getSavesFile());
        messageConfig.options().copyDefaults(true);
        RPNames.getPlugin().saveConfig();
        messageConfig.getKeys(false).forEach(key -> getMessages().put(key, messageConfig.getString(key)));
        length = RPNames.getPlugin().getConfig().getInt("rpname-length");
        timeout = RPNames.getPlugin().getConfig().getInt("timeout");
        String allSymbols = RPNames.getPlugin().getConfig().getString("characters-blacklist");
        symbols = allSymbols.split("(?!^)");
        color = RPNames.getPlugin().getConfig().getString("name-color", "");
        RPNames.getPlugin().getLogger().info("Done.");
    }
    
    public static Map<String, String> getMessages() {
        return messages;
    }
    
    public static int getLength() {
    	return length;
    }
    
    public static int getTimeout() {
    	return timeout;
    }
    
    public static String getColor() {
    	return color;
    }
    
    private static void createNewFile(File file) {
    	final InputStream link = RPNames.getPlugin().getResource("messages.yml");
        try {
            Files.copy(link, file.getAbsoluteFile().toPath(), new CopyOption[0]);
        }
        catch (IOException e) {
            System.out.print("RPNames: Unable to make messages file! Plugin will not enable.");
            RPNames.Disable();
        }
    }
    
    public static File getSavesFile() {
        return messageFile;
    }
    
    public static YamlConfiguration getSavesConfig() {
        return messageConfig;
    }
}
