package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.events.addon.protocollib.SetCursorItemEvent;
import me.jho5245.youpeoplegame.util.ItemLoreModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SetCursorItem implements Listener
{
	@EventHandler
	public void onWindowItems(SetCursorItemEvent event)
	{
		event.setItemStack(ItemLoreModifier.getInstance().perform(event.getItemStack()));
	}
}
