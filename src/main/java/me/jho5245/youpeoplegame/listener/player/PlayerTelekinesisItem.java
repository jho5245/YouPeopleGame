package me.jho5245.youpeoplegame.listener.player;

import com.jho5245.cucumbery.events.PlayerTelekinesisItemEvent;
import me.jho5245.youpeoplegame.service.listener.AutoSack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerTelekinesisItem implements Listener
{
	@EventHandler
	public void onPlayerTelekinesisItem(final PlayerTelekinesisItemEvent event)
	{
		AutoSack.get().listen(event);
	}
}
