package me.jho5245.youpeoplegame.listener.packet;

import com.jho5245.cucumbery.events.addon.protocollib.EntityEquipmentEvent;
import me.jho5245.youpeoplegame.util.ItemLoreModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityEquipment implements Listener
{
	@EventHandler
	public void onEntityEquipment(EntityEquipmentEvent event)
	{
		event.setItemStack(ItemLoreModifier.getInstance().perform(event.getPlayer(), event.getItemStack()));
	}
}
