package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInteract implements Listener
{
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		CustomMaterial customMaterial = CustomMaterial.itemStackOf(item);
		Action action = event.getAction();
		if (action.isRightClick())
		{
			if (customMaterial == CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE_PILE)
			{
				rightClickDampCookiePile(player);
			}
		}
	}

	private void rightClickDampCookiePile(Player player)
	{
		PlayerInventory inventory = player.getInventory();
		if (ItemStackUtil.countItem(inventory, CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE.create()) >= 64)
		{
			inventory.removeItem(CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE.create(64));
			AddItemUtil.addItem(player, CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE_PILE.create());
		}
	}
}
