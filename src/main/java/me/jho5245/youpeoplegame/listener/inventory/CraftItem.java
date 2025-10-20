package me.jho5245.youpeoplegame.listener.inventory;

import me.jho5245.youpeoplegame.service.listener.CancelActivity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftItem implements Listener
{
	@EventHandler(ignoreCancelled = true)
	public void onCraftItem(final CraftItemEvent event)
	{
		Player player = (Player) event.getView().getPlayer();
		CancelActivity.get().cancelActivity(player, event);
	}
}
