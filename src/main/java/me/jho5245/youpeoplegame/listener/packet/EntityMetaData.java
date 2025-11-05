package me.jho5245.youpeoplegame.listener.packet;

import com.jho5245.cucumbery.events.addon.protocollib.EntityMetaDataEvent;
import me.jho5245.youpeoplegame.util.ItemLoreModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityMetaData implements Listener
{
	@EventHandler
	public void onEntityMetaData(EntityMetaDataEvent event)
	{
		event.setItemStack(ItemLoreModifier.getInstance().perform(event.getPlayer(), event.getItemStack()));
	}
}
