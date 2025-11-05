package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.events.addon.protocollib.ParseComponentItemStackEvent;
import me.jho5245.youpeoplegame.util.ItemLoreModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ParseComponentItemStack implements Listener
{
	@EventHandler
	public void onParseComponentItemStack(ParseComponentItemStackEvent event)
	{
		event.setItemStack(ItemLoreModifier.getInstance().perform(event.getPlayer(), event.getItemStack()));
	}
}
