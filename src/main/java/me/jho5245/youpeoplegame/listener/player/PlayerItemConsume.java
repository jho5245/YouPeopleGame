package me.jho5245.youpeoplegame.listener.player;

import me.jho5245.youpeoplegame.service.listener.UnlockBuffItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsume implements Listener
{
	@EventHandler
	public void onPlayerItemConsume(final PlayerItemConsumeEvent event)
	{
		UnlockBuffItems.get().listen(event);
	}
}
