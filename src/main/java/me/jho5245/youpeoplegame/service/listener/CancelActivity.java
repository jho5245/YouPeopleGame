package me.jho5245.youpeoplegame.service.listener;

import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CancelActivity
{
	private static final CancelActivity instance = new CancelActivity();

	private CancelActivity()
	{
	}

	public static CancelActivity get()
	{
		return instance;
	}

	private final Set<InventoryType> ALLOWED_INVENTORY_TYPES = new HashSet<>(
			Arrays.asList(InventoryType.CHEST, InventoryType.CARTOGRAPHY, InventoryType.BARREL, InventoryType.MERCHANT));

	public void cancelActivity(Player player, Cancellable cancellable)
	{
		switch (cancellable)
		{
			case CraftItemEvent ignored ->
			{
				cancellable.setCancelled(true);
				MessageUtil.sendDebug(player, "아이템 제작 불가");
			}
			case InventoryOpenEvent event ->
			{
				Inventory inventory = event.getInventory();

				InventoryType inventoryType = inventory.getType();
				if (!ALLOWED_INVENTORY_TYPES.contains(inventoryType) && player.getGameMode() != GameMode.CREATIVE)
				{
					event.setCancelled(true);
					if (player.hasPermission("youpeoplegame.admin"))
					{
						MessageUtil.sendDebug(player, "열 수 없는 인벤토리");
					}
				}
			}
			default ->
			{
			}
		}
		if (cancellable instanceof CraftItemEvent)
		{
		}
	}
}
