package me.jho5245.youpeoplegame.command;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion;
import com.jho5245.cucumbery.util.no_groups.CommandTabUtil;
import com.jho5245.cucumbery.util.no_groups.CucumberyCommandExecutor;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.component.util.ItemNameUtil;
import com.jho5245.cucumbery.util.storage.data.Constant;
import com.jho5245.cucumbery.util.storage.data.Prefix;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.util.SackManager;
import me.jho5245.youpeoplegame.util.SackManager.SackElement;
import me.jho5245.youpeoplegame.util.SackManager.SackElement.Category;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SackCommand implements CucumberyCommandExecutor
{
	@Override
	public @NotNull List<Completion> completion(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args,
			@NotNull Location location)
	{
		if (!(sender instanceof Player player))
			return Collections.emptyList();
		if (!MessageUtil.checkQuoteIsValidInArgs(sender, args = MessageUtil.wrapWithQuote(true, args), true))
		{
			return CommandTabUtil.errorMessage(args[0]);
		}
		int length = args.length;
		switch (length)
		{
			case 1 ->
			{
				return CommandTabUtil.tabCompleterList(args, "<인자>", false, "get", "store", "show");
			}
			case 2 ->
			{
				switch (args[0])
				{
					case "get", "store" ->
					{
						List<Completion> list = Arrays.stream(SackElement.values()).map(e -> Completion.completion(e.getItemStackName(), e.getItemStackNameComponent()))
								.toList();
						return CommandTabUtil.sortError(CommandTabUtil.tabCompleterList(args, "<아이템>", false, "--hand"),
								CommandTabUtil.tabCompleterList(args, list, "<아이템>"));
					}
					case "show" ->
					{
						List<Completion> list = CommandTabUtil.tabCompleterList(args, Category.values(), "<보관함 유형>");
						List<Completion> list2 = CommandTabUtil.tabCompleterList(args, "<보관함 유형>", false, "--all");
						return CommandTabUtil.sortError(list, list2);
					}
				}
			}
			case 3 ->
			{
				String mode = args[0];
				String itemName = args[1].equals("--hand") ? MessageUtil.stripColor(
						ComponentUtil.serialize(ItemNameUtil.itemName(player.getInventory().getItemInMainHand()))) : args[1];
				if (args[1].equals("--hand") && !ItemStackUtil.itemExists(player.getInventory().getItemInMainHand()))
				{
					return CommandTabUtil.errorMessage(Prefix.NO_HOLDING_ITEM.get());
				}
				SackElement sackElement = SackElement.getByItemStackName(itemName);
				if (sackElement == null)
				{
					return CommandTabUtil.errorMessage("존재하지 않거나 보관할 수 없는 아이템입니다. (%s)", itemName);
				}
				int playerCountSpace = ItemStackUtil.countSpace(player.getInventory(), sackElement.getItemStack());
				int playerHaveAmount = ItemStackUtil.countItem(player.getInventory(), sackElement.getItemStack());
				int amount = SackManager.get().getAmount(player, sackElement);
				int maxAmount = SackManager.get().getMaxAmount(player, sackElement);
				List<Completion> list = new ArrayList<>();
				if (mode.equalsIgnoreCase("store"))
				{
					int maxStorableAmount = Math.min(maxAmount - amount, playerHaveAmount);
					list.add(Completion.completion("max", ComponentUtil.translate("최대 개수 보관하기(%s개)", maxStorableAmount)));
					return CommandTabUtil.sortError(CommandTabUtil.tabCompleterList(args, list, "[인수]"),
							CommandTabUtil.tabCompleterIntegerRadius(args, 1, maxStorableAmount, "[개수]"));
				}
				else
				{
					int maxTakableAmount = Math.min(amount, playerCountSpace);
					list.add(Completion.completion("max", ComponentUtil.translate("최대 개수 꺼내기(%s개)", maxTakableAmount)));
					return CommandTabUtil.sortError(CommandTabUtil.tabCompleterList(args, list, "[인수]"),
							CommandTabUtil.tabCompleterIntegerRadius(args, 1, maxTakableAmount, "[개수]"));
				}
			}
		}
		return CommandTabUtil.ARGS_LONG;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args)
	{
		if (!(sender instanceof Player player))
			return true;
		if (!MessageUtil.checkQuoteIsValidInArgs(sender, args = MessageUtil.wrapWithQuote(args)))
		{
			return !(sender instanceof BlockCommandSender);
		}
		if (args.length == 0)
		{
			player.performCommand("gfs show --all");
			return true;
		}
		if (args[0].equals("show"))
		{
			String category = args.length > 1 && !args[1].equals("--all") ? args[1] : "";
			Category categoryEnum = Category.getByName(category);
			MessageUtil.info(player, "현재 보관중인 아이템 목록 (%s)", categoryEnum != null ? categoryEnum.toString() : "전체");
			for (SackElement sackElement : SackElement.values())
			{
				if (categoryEnum != null && !sackElement.getCategory().equals(categoryEnum))
					continue;
				int amount = SackManager.get().getAmount(player, sackElement);
				if (amount == 0 && categoryEnum == null)
					continue;
				int maxAmount = SackManager.get().getMaxAmount(player, sackElement);
				ItemStack itemStack = sackElement.getItemStack();
				MessageUtil.info(player, "%s - %s / %s", itemStack, amount, maxAmount);
			}
			MessageUtil.info(player, Constant.SEPARATOR);
			return true;
		}
		if (args.length > 3)
			return false;
		String mode = args[0];
		if ((args.length == 1 || args[1].equals("--hand")) && !ItemStackUtil.itemExists(player.getInventory().getItemInMainHand()))
		{
			MessageUtil.sendError(player, Prefix.NO_HOLDING_ITEM);
			return true;
		}
		String itemName = args.length >= 2 && !args[1].equals("--hand")
				? args[1]
				: MessageUtil.stripColor(ComponentUtil.serialize(ItemNameUtil.itemName(player.getInventory().getItemInMainHand())));
		SackElement sackElement = SackElement.getByItemStackName(itemName);
		if (sackElement == null)
		{
			MessageUtil.sendError(player, "존재하지 않거나 보관할 수 없는 아이템입니다. (%s)", itemName);
			return true;
		}
		if (args.length == 3 && !args[2].equals("max") && !MessageUtil.isInteger(sender, args[2], true))
		{
			return true;
		}
		int amount;
		if (args.length == 3)
		{
			if (args[2].equals("max"))
			{
				switch (mode)
				{
					case "store" ->
					{
						int playerHaveAmount = ItemStackUtil.countItem(player.getInventory(), sackElement.getItemStack());
						int maxStorableAmount = SackManager.get().getMaxAmount(player, sackElement);
						int storedAmount = SackManager.get().getAmount(player, sackElement);
						amount = Math.min(playerHaveAmount, maxStorableAmount - storedAmount);
					}
					case "get" ->
					{
						int playerCountSpace = ItemStackUtil.countSpace(player.getInventory(), sackElement.getItemStack());
						int storedAmount = SackManager.get().getAmount(player, sackElement);
						amount = Math.min(playerCountSpace, storedAmount);
					}
					default ->
					{
						amount = 1;
					}
				}
			}
			else
			{
				amount = Integer.parseInt(args[2]);
			}
		}
		else
		{
			amount = 1;
		}
		switch (mode)
		{
			case "store" ->
			{
				SackManager.get().addItem(player, sackElement, amount);
			}
			case "get" ->
			{
				SackManager.get().takeItem(player, sackElement, amount);
			}
		}
		return true;
	}
}
