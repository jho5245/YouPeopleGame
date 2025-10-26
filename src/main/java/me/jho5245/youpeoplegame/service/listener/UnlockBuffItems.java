package me.jho5245.youpeoplegame.service.listener;

import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.util.SackManager;
import me.jho5245.youpeoplegame.util.SackManager.SackElement;
import me.jho5245.youpeoplegame.util.SackManager.SackElement.Category;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class UnlockBuffItems
{
	private static final UnlockBuffItems instance = new UnlockBuffItems();

	private UnlockBuffItems()
	{

	}

	public static UnlockBuffItems get()
	{
		return instance;
	}

	public void listen(PlayerItemConsumeEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		CustomMaterial customMaterial = CustomMaterial.itemStackOf(item);
		switch (customMaterial)
		{
			case YOUPEOPLEGAME_DAMP_COOKIE_POTION ->
			{
				if (UserData.getBoolean(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_UNLOCKED))
				{
					event.setCancelled(true);
					MessageUtil.sendWarn(player, "이미 사용한 아이템입니다.");
					return;
				}
				UserData.setToggle(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_UNLOCKED);
				UserData.setToggle(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_USE);
				player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1, 1);
				player.showTitle(Title.title(Component.empty(), ComponentUtil.translate("&e눅눅한 쿠키 포션의 버프가 해금되었습니다!"), 60, 60, 60));
			}
			case YOUPEOPLEGAME_MOIST_COOKIE_BOOSTER ->
			{
				if (UserData.getBoolean(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_UNLOCKED))
				{
					event.setCancelled(true);
					MessageUtil.sendWarn(player, "이미 사용한 아이템입니다.");
					return;
				}
				UserData.setToggle(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_UNLOCKED);
				UserData.setToggle(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_USE);
				player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1, 1);
				player.showTitle(Title.title(Component.empty(), ComponentUtil.translate("&e촉촉한 쿠키 촉진제의 버프가 해금되었습니다!"), 60, 60, 60));
			}
			case YOUPEOPLEGAME_SUPER_MOIST_COOKIE_BOOSTER ->
			{
				if (UserData.getBoolean(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED))
				{
					event.setCancelled(true);
					MessageUtil.sendWarn(player, "이미 사용한 아이템입니다.");
					return;
				}
				UserData.setToggle(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED);
				UserData.setToggle(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_USE);
				player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1, 1);
				player.showTitle(Title.title(Component.empty(), ComponentUtil.translate("&e촉촉한 쿠키 포션의 버프가 해금되었습니다!"), 60, 60, 60));
			}
			case null, default ->
			{
			}
		}
	}

	public void listen(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		if (!ItemStackUtil.itemExists(item))
			return;
		var action = event.getAction();
		if (!action.isRightClick())
			return;
		EquipmentSlot hand = event.getHand();
		CustomMaterial customMaterial = CustomMaterial.itemStackOf(item);
		Category category = Category.getByCustomMaterial(customMaterial);
		if (category != null)
		{
			if (hand != null)
				player.swingHand(hand);
			boolean upradedAtOnce = false;
			UserData.set(player, YouPeopleGameUserData.MATERIAL_STORAGE_USED, true);
			SackManager sackManager = SackManager.get();
			List<SackElement> sackElements = SackElement.getElementsByCategory(category);
			for (SackElement sackElement : sackElements)
			{
				int nextMaxAmount = sackManager.getNextMaxAmount(player, sackElement);
				int previousMaxAmount = sackManager.getMaxAmount(player, sackElement);
				if (nextMaxAmount == -1)
				{
					continue;
				}
				upradedAtOnce = true;
				sackManager.setMaxAmount(player, sackElement, nextMaxAmount);
				MessageUtil.info(player, "%s을(를) 보관할 수 있는 공간이 늘어났습니다. (%s칸 -> %s칸)", sackElement.getItemStack(), previousMaxAmount, nextMaxAmount);
			}
			if (upradedAtOnce)
			{
				sackManager.setCategoryUnlockCount(player, category, sackManager.getCategoryUnlockCount(player, category) + 1);
				item.setAmount(item.getAmount() - 1);
			}
			else
			{
				MessageUtil.sendWarn(player, "이미 모든 재료의 공간이 최대로 확장되어 더 이상 사용할 수 없습니다.");
			}
		}
	}
}
