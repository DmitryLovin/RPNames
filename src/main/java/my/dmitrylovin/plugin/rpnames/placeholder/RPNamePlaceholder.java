package my.dmitrylovin.plugin.rpnames.placeholder;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import my.dmitrylovin.plugin.rpnames.utils.MessagesManager;
import my.dmitrylovin.plugin.rpnames.utils.RPNamesUtils;

public class RPNamePlaceholder extends PlaceholderExpansion{
	
	@Override
    public String onPlaceholderRequest(Player p, String identifier) {
		if(identifier.contains("name")) {
			if(RPNamesUtils.getName(p.getUniqueId().toString())!=null)return MessagesManager.getColor()+RPNamesUtils.getName(p.getUniqueId().toString());
			return p.getName();
		}
		if(identifier.contains("max"))
			return MessagesManager.getLength()+"";
		return null;
	}

	@Override
	public String getAuthor() {
		return "DmitryLovin";
	}

	@Override
	public String getIdentifier() {
		return "rpname";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
