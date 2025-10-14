package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InventoryOpen implements Listener
{
	private static final Set<InventoryType> ALLOWED_IVENTORY_TYPES = new HashSet<>(Arrays.asList(
			InventoryType.CHEST, InventoryType.CARTOGRAPHY, InventoryType.BARREL, InventoryType.MERCHANT
	));

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event)
	{
		if (event.isCancelled())
			return;
		Player player = (Player) event.getPlayer();
		Inventory inventory = event.getInventory();
		InventoryType inventoryType = inventory.getType();
		if (!ALLOWED_IVENTORY_TYPES.contains(inventoryType))
		{
			event.setCancelled(true);
			if (player.hasPermission("youpeoplegame.admin"))
			{
				MessageUtil.sendDebug(player, "열 수 없는 인벤토리");
			}
		}
	}
}
