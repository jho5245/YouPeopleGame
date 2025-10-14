package me.jho5245.youpeoplegame.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class PlayerItemDamage implements Listener
{
	@EventHandler
	public void onPlayerItemDamage(PlayerItemDamageEvent event)
	{
		event.setCancelled(true);
	}
}
