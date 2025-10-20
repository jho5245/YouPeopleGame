package me.jho5245.youpeoplegame.command;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion;
import com.jho5245.cucumbery.util.gui.GUIManager;
import com.jho5245.cucumbery.util.no_groups.CucumberyCommandExecutor;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.data.Constant;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.CreateItemStack;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import me.jho5245.youpeoplegame.service.scheduler.CookieGiveawayEveryNSeconds;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class GUICommand implements CucumberyCommandExecutor
{
	@Override
	public @NotNull List<Completion> completion(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings,
			@NotNull Location location)
	{
		return List.of();
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args)
	{
		if (!(sender instanceof Player player))
			return true;
		openGUI(player);
		return true;
	}

	public static final String COOKIE_GUI_KEY = "cookie_gui";

	private static final ItemStack NOT_UNLOCKED_FEATURE;

	private static final List<Component> TURN_ON = Collections.singletonList(ComponentUtil.translate("&a기능 켜기"));

	private static final List<Component> TURN_OFF = Collections.singletonList(ComponentUtil.translate("&c기능 끄기"));

	static
	{
		NOT_UNLOCKED_FEATURE = new ItemStack(Material.BLACK_WOOL);
		ItemMeta meta = NOT_UNLOCKED_FEATURE.getItemMeta();
		meta.displayName(ComponentUtil.translate("&c해금되지 않은 기능"));
		meta.lore(List.of(ComponentUtil.translate("&7아직 해금되지 않은 기능입니다."), ComponentUtil.translate("&7모험을 통해 알아가셈")));
		NOT_UNLOCKED_FEATURE.setItemMeta(meta);
	}

	public static void openGUI(Player player)
	{
		Inventory inventory = GUIManager.create(1, ComponentUtil.translate("YouPeopleGame GUI Example"), COOKIE_GUI_KEY);

		int waitTick = CookieGiveawayEveryNSeconds.getWaitTick(player);
		String waitTickDisplay = Constant.Sosu2.format(waitTick / 20d) + "초";
		Component waitTickLore = ComponentUtil.translate("&e현재 쿠키 획득 시간 : %s", waitTickDisplay);

		if (UserData.getBoolean(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_UNLOCKED))
		{
			inventory.setItem(0, CreateItemStack.toggleItem(UserData.getBoolean(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_USE),
					ComponentUtil.translate("%s 해금 기능 사용", CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE_POTION),
					Collections.singletonList(ComponentUtil.translate("&725% 확률로 추가 쿠키 획득")),
					TURN_OFF,
					TURN_ON));
		}
		else
		{
			inventory.setItem(0, NOT_UNLOCKED_FEATURE);
		}
		if (UserData.getBoolean(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_UNLOCKED))
		{
			inventory.setItem(1, CreateItemStack.toggleItem(UserData.getBoolean(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_USE),
					ComponentUtil.translate("%s 해금 기능 사용", CustomMaterial.YOUPEOPLEGAME_MOIST_COOKIE_BOOSTER),
					List.of(
							waitTickLore,
							Component.empty(),
							ComponentUtil.translate("&7쿠키 획득 시간 1초 감소")
							),
					TURN_OFF,
					TURN_ON));
		}
		else
		{
			inventory.setItem(1, NOT_UNLOCKED_FEATURE);
		}
		if (UserData.getBoolean(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED))
		{
			inventory.setItem(2, CreateItemStack.toggleItem(UserData.getBoolean(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_USE),
					ComponentUtil.translate("%s 해금 기능 사용", CustomMaterial.YOUPEOPLEGAME_SUPER_MOIST_COOKIE_BOOSTER),
					List.of(
							waitTickLore,
							Component.empty(),
							ComponentUtil.translate("&7쿠키 획득 시간 1.25초 감소 및"),
							ComponentUtil.translate("&750% 확률로 추가 쿠키 획득")
					),
					TURN_ON,
					TURN_OFF));
		}
		else
		{
			inventory.setItem(2, NOT_UNLOCKED_FEATURE);
		}
		player.openInventory(inventory);
		Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> ItemStackUtil.updateInventory(player), 0L);
	}
}
