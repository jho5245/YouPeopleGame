package me.jho5245.youpeoplegame.command;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion;
import com.jho5245.cucumbery.commands.no_groups.CommandVirtualChest;
import com.jho5245.cucumbery.util.no_groups.CommandTabUtil;
import com.jho5245.cucumbery.util.no_groups.CucumberyCommandExecutor;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class BackpackCommand implements CucumberyCommandExecutor
{
	@Override
	public @NotNull List<Completion> completion(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args,
			@NotNull Location location)
	{
		if (!(sender instanceof Player player))
			return Collections.emptyList();
		;
		if (args.length == 1)
		{
			return CommandTabUtil.tabCompleterIntegerRadius(args, 1, UserData.getInt(player, YouPeopleGameUserData.BACKBACK_UNLOCKED_INDEX) + 1, "<가방 번호>");
		}
		return Collections.emptyList();
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args)
	{
		if (!(sender instanceof Player player))
			return true;
		String chestName = args.length >= 1 ? args[0] : "1";
		try
		{
			int index = Integer.parseInt(chestName) - 1;
			if (index > 8 || index < 0)
				throw new Exception();
			int playerIndex = UserData.getInt(player, YouPeopleGameUserData.BACKBACK_UNLOCKED_INDEX);
			if (playerIndex < index)
			{
				MessageUtil.sendError(player, "아직 해금되지 않은 가방입니다. 최대 %s번까지 사용 가능합니다.", playerIndex + 1);
				return true;
			}
			CommandVirtualChest.openChest(player, "%s번 가방".formatted(chestName));
		}
		catch (Exception e)
		{
			MessageUtil.sendError(sender, "유효하지 않은 가방 번호입니다.");
			return true;
		}
		return true;
	}
}
