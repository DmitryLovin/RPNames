package my.dmitrylovin.plugin.rpnames.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import my.dmitrylovin.plugin.rpnames.RPNames;

public class RPNamesUtils {

	private static HashMap<String,String> rpQueue = new HashMap<String,String>();
	private static HashMap<String,String> rpNames = new HashMap<String,String>();
	
	public static void loadNames() {
		for(Player p:Bukkit.getServer().getOnlinePlayers()) {
			loadName(p.getUniqueId().toString());
		}
	}
	
	public static void loadName(String UUID) {
		final File file = new File(RPNames.getPlugin().getDataFolder() + "/Players/" + UUID + ".yml");
		if(!file.exists())return;
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		rpNames.put(UUID, config.getString("rpname"));
	}
	
	public static void unloadName(String UUID) {
		rpNames.remove(UUID);
	}
	
	public static void addToQueue(String UUID, String name) {
		rpQueue.put(UUID, name);
	}
	
	public static boolean inQueue(String UUID) {
		return rpQueue.get(UUID)!=null?true:false;
	}
	
	public static void removeFromQueue(String UUID) {
		rpQueue.remove(UUID);
	}
	
	private static void loadName(String UUID,String name) {
		rpNames.put(UUID, name);
	}
	
	public static String getName(String UUID) {
		return rpNames.get(UUID);
	}
	
	public static String getFromName(String name) {
		return getKey(rpNames,name);
	}
	
	private static <K, V> K getKey(Map<K, V> map, V value) {
	    for (Entry<K, V> entry : map.entrySet()) {
	        if (entry.getValue().equals(value)) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	public static boolean saveRPName(String UUID) {
		File file = new File(RPNames.getPlugin().getDataFolder() + "/Players/" + UUID + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("rpname", rpQueue.get(UUID));
		try {
			config.save(file);
		}
		catch(IOException ex) {
			RPNames.getPlugin().getLogger().info("Unable to save RPName for user:" + UUID);
			removeFromQueue(UUID);
			return false;
		}
		loadName(UUID, rpQueue.get(UUID));
		removeFromQueue(UUID);
		return true;
	}
	
	public static boolean forceSave(String UUID, String name) {
		File file = new File(RPNames.getPlugin().getDataFolder() + "/Players/" + UUID + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("rpname", name);
		try {
			config.save(file);
		}
		catch(IOException ex) {
			RPNames.getPlugin().getLogger().info("Unable to save RPName for user:" + UUID);
			return false;
		}
		loadName(UUID, name);
		return true;
	}
}
