package me.jho5245.youpeoplegame.service.listener;

import com.jho5245.cucumbery.events.PlayerTelekinesisItemEvent;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import me.jho5245.youpeoplegame.util.SackManager;
import me.jho5245.youpeoplegame.util.SackManager.SackElement;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

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
		if (UserData.getBoolean(player, YouPeopleGameUserData.MATERIAL_STORAGE_AUTO_FILL))
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
}
