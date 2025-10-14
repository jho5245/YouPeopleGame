package me.jho5245.youpeoplegame.util;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion;
import com.jho5245.cucumbery.util.no_groups.CucumberyCommandExecutor;
import com.jho5245.cucumbery.util.shading.NBT;
import com.jho5245.cucumbery.util.shading.NBTCompound;
import com.jho5245.cucumbery.util.shading.NBTContainer;
import com.jho5245.cucumbery.util.shading.handler.NBTHandlers;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
		UserData.ANNOUNCE_ADVANCEMENTS.setToggle(((Player) sender));
		UserData.setToggle(((Player) sender), "foo.bar");
		return false;
	}
}
