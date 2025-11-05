package me.jho5245.youpeoplegame.listener.packet;

import com.jho5245.cucumbery.events.addon.protocollib.WindowItemsEvent;
import me.jho5245.youpeoplegame.util.ItemLoreModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WindowItems implements Listener
{
	@EventHandler
	public void onWindowItems(WindowItemsEvent event)
	{
		event.setItemStack(ItemLoreModifier.getInstance().perform(event.getPlayer(), event.getItemStack()));
		event.setItemStacks(ItemLoreModifier.getInstance().perform(event.getPlayer(), event.getItemStacks()));
	}
}
