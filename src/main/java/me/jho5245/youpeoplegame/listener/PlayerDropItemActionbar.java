package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.events.item.PlayerDropItemActionbarEvent;
import me.jho5245.youpeoplegame.util.ItemLoreModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDropItemActionbar implements Listener
{
	@EventHandler
	public void onPlayerPickupItem(PlayerDropItemActionbarEvent event)
	{
		event.setItemStack(ItemLoreModifier.getInstance().perform(event.getPlayer(), event.getItemStack()));
	}
}
