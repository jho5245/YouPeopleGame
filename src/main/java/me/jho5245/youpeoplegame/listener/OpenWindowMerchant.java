package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.events.addon.protocollib.OpenWindowMerchantEvent;
import me.jho5245.youpeoplegame.util.ItemLoreModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OpenWindowMerchant implements Listener
{
	@EventHandler
	public void onOpenWindowMerchant(OpenWindowMerchantEvent event)
	{
		event.setResult(ItemLoreModifier.getInstance().perform(event.getResult()));
		event.setIngredients(ItemLoreModifier.getInstance().perform(event.getIngredients()));
	}
}
