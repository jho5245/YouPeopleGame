package me.jho5245.youpeoplegame.command;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion;
import com.jho5245.cucumbery.util.no_groups.CucumberyCommandExecutor;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ParkourGiveUp implements CucumberyCommandExecutor
{
	public static ParkourGiveUp instance = new ParkourGiveUp();

	private final HashMap<Player, String> giveUpCommandHash = new HashMap<>();

	private final Location lobbyLocation, gooriLobbyLocation;

	private ParkourGiveUp()
	{
		lobbyLocation = new Location(Bukkit.getWorld("youpeople_world"), 85, 65, -0.5, -90, 0);
		gooriLobbyLocation = new Location(Bukkit.getWorld("youpeople_world"), -12.5, 65, -171.5, 0, 0);
	}

	public static ParkourGiveUp get()
	{
		return instance;
	}

	public void listen(Player player)
	{
		setCommand(player);
		String commandHash = giveUpCommandHash.getOrDefault(player, "");
		Component component = Component.translatable("message.youpeoplegame.give_up_parkour.button", NamedTextColor.YELLOW).fallback("[포기하기]")
				.hoverEvent(ComponentUtil.translate("클릭하면 포기하고 로비로 돌아갑니다.")).clickEvent(ClickEvent.runCommand("/giveup " + commandHash));
		MessageUtil.info(player, "포기하시겠습니까? %s", component);
	}

	private void setCommand(Player player)
	{
		giveUpCommandHash.put(player, UUID.randomUUID().toString());
	}

	@Override
	public @NotNull List<Completion> completion(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings,
			@NotNull Location location)
	{
		return Collections.singletonList(Completion.completion("이 명령어는 직접 사용하는 명령어가 아닙니다."));
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args)
	{
		if (!(sender instanceof Player player))
			return true;
		String commandHash = args.length > 0 ? args[0] : null;
		if (player.getScoreboardTags().contains("playing_parkour") && giveUpCommandHash.getOrDefault(player, "").equals(commandHash))
		{
			giveUpCommandHash.remove(player);
			if (player.getScoreboardTags().contains("playing_goori_parkour"))
			{
				player.teleport(gooriLobbyLocation);
			}
			else
			{

				player.teleport(lobbyLocation);
			}
			MessageUtil.info(player, "포기했습니다람쥐썬더!!!!!!");
			player.getScoreboardTags().remove("playing_parkour");
			player.getScoreboardTags().remove("playing_goori_parkour");
		}
		return false;
	}
}
