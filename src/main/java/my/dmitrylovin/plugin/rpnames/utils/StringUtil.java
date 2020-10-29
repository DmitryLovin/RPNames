package my.dmitrylovin.plugin.rpnames.utils;

import org.bukkit.ChatColor;

public class StringUtil {
	 public static String colorize(final String s) {
	    	return ChatColor.translateAlternateColorCodes('&', s);
	    }
}
