package me.jho5245.youpeoplegame.listener.player;

import me.jho5245.youpeoplegame.service.listener.CondenseItem;
import me.jho5245.youpeoplegame.service.listener.UnlockBuffItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener
{
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		CondenseItem.get().listen(event);
		UnlockBuffItems.get().listen(event);
	}
}
