package me.jho5245.youpeoplegame.listener.inventory;

import me.jho5245.youpeoplegame.service.listener.GUI;
import me.jho5245.youpeoplegame.service.listener.AutoSack;
import me.jho5245.youpeoplegame.service.listener.ToggleItemLore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener
{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		GUI.get().listen(event);
		ToggleItemLore.get().listen(event);
		AutoSack.get().listen(event);
	}
}
