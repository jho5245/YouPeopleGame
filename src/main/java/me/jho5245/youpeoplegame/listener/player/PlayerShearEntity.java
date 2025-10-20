package me.jho5245.youpeoplegame.listener.player;

import me.jho5245.youpeoplegame.service.SheepWool;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class PlayerShearEntity implements Listener
{
	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteractEntity(PlayerShearEntityEvent event)
	{
		SheepWool.get().listen(event);
	}
}
