package me.jho5245.youpeoplegame.command;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion;
import com.jho5245.cucumbery.util.no_groups.CucumberyCommandExecutor;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class TestCommand implements CucumberyCommandExecutor

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
		if (sender instanceof Player player)
		{
			Inventory inventory = Bukkit.createInventory(null, 54, ComponentUtil.translate("YouPeopleGame 아이템"));
			List<CustomMaterial> list = Arrays.stream(CustomMaterial.values()).toList().stream().filter(m -> m.toString().toLowerCase().startsWith("youpeoplegame")).toList();
			int maxPage = list.size() / 54 + 1;
			int page = Math.max(1, Math.min(maxPage, args.length >= 1 ? Integer.parseInt(args[0]) : 1));
			int startIndex = (page - 1) * 54;
			int endIndex = Math.min(list.size(), startIndex + 54);
			for (int i = startIndex; i < endIndex; i++) {
				inventory.addItem(list.get(i).create());
			}
			player.openInventory(inventory);
		}
		return false;
	}
}
