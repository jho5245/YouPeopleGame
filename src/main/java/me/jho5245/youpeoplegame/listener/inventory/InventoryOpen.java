package me.jho5245.youpeoplegame.listener.inventory;

import me.jho5245.youpeoplegame.service.listener.CancelActivity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryOpen implements Listener
{


	@EventHandler(ignoreCancelled = true)
	public void onInventoryOpen(InventoryOpenEvent event)
	{
		Player player = (Player) event.getPlayer();
		CancelActivity.get().cancelActivity(player, event);
	}
}
