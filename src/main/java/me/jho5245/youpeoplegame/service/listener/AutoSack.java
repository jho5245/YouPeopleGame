package me.jho5245.youpeoplegame.service.listener;

import com.jho5245.cucumbery.events.PlayerTelekinesisItemEvent;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.listener.ItemLore3;
import me.jho5245.youpeoplegame.util.SackManager;
import me.jho5245.youpeoplegame.util.SackManager.SackElement;
import me.jho5245.youpeoplegame.util.SackManager.SackElement.Category;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AutoSack
{
	private static final AutoSack instance = new AutoSack();

	private AutoSack(){}

	public static AutoSack get()
	{
		return instance;
	}

	public void listen(PlayerTelekinesisItemEvent event)
	{
		Player player = event.getPlayer();
		Collection<ItemStack> drops = event.getDrops();
		if (UserData.getBoolean(player, YouPeopleGameUserData.SACK_AUTO_FILL))
		{
			Collection<ItemStack> newDrops = new ArrayList<>();
			for (ItemStack drop : drops)
			{
				int dropAmount = drop.getAmount();
				SackElement sackElement = SackElement.getByItemStack(drop);
				if (sackElement != null)
				{
					int amount = SackManager.get().getAmount(player, sackElement);
					int maxAmount = SackManager.get().getMaxAmount(player, sackElement);
					if (amount + dropAmount <= maxAmount)
					{
						SackManager.get().setAmount(player, sackElement, amount + dropAmount);
					}
					else
					{
						SackManager.get().setAmount(player, sackElement, maxAmount);
						int remainingAmount = dropAmount - (maxAmount - amount);
						drop = drop.clone();
						drop.setAmount(remainingAmount);
						newDrops.add(drop);
					}
				}
			}
			event.setDrops(newDrops);
		}
	}

	private final Set<Player> cooldowns = new HashSet<>();

	public void listen(InventoryClickEvent event)
	{
		Player player = (Player) event.getView().getPlayer();
		if (event.getClick() == ClickType.NUMBER_KEY && event.getHotbarButton() == 7 && ItemLore3.TOGGLE_ITEM_LORE_ALLOWED_INVENTORY.contains(player.getOpenInventory().getType()))
		{
			ItemStack item = event.getCurrentItem();
			// item이 sack일 경우에만 기능 전환
			if (Category.getByCustomMaterial(CustomMaterial.itemStackOf(item)) == null)
			{
				return;
			}
			event.setCancelled(true);
			if (cooldowns.contains(player))
				return;
			cooldowns.add(player);
			Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> cooldowns.remove(player), 10L);
			UserData.setToggle(player, YouPeopleGameUserData.SACK_AUTO_FILL);
			Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> ItemStackUtil.updateInventory(player), 1L);
		}
	}
}
