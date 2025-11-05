package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.events.item.PlayerPickupItemActionbarEvent;
import me.jho5245.youpeoplegame.util.ItemLoreModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPickupItemActionbar implements Listener
{
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemActionbarEvent event)
	{
		event.setItemStack(ItemLoreModifier.getInstance().perform(event.getPlayer(), event.getItemStack()));
	}
}
