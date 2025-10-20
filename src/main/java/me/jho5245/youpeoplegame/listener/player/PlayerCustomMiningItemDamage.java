package me.jho5245.youpeoplegame.listener.player;

import com.jho5245.cucumbery.events.item.PlayerCustomMiningItemDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerCustomMiningItemDamage implements Listener
{
	@EventHandler
	public void onPlayerItemDamage(PlayerCustomMiningItemDamageEvent event)
	{
		event.setCancelled(true);
	}
}
