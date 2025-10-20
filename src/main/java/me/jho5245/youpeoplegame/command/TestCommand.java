package me.jho5245.youpeoplegame.command;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion;
import com.jho5245.cucumbery.util.no_groups.CucumberyCommandExecutor;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
		Player p = (Player) sender;
		p.sendMessage("foo");
		UserData.set(p, UserData.CUSTOM_DATA.getKey() + "." + args[0], args[1]);
		MessageUtil.sendMessage(p, UserData.get(p, UserData.CUSTOM_DATA.getKey() + "." + args[0]));
		return false;
	}
}
