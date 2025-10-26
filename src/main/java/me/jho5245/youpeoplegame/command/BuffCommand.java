package me.jho5245.youpeoplegame.command;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion;
import com.jho5245.cucumbery.custom.customeffect.CustomEffectGUI;
import com.jho5245.cucumbery.util.no_groups.CucumberyCommandExecutor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BuffCommand implements CucumberyCommandExecutor
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
		CustomEffectGUI.openGUI(((Player) sender), true);
		return false;
	}
}
