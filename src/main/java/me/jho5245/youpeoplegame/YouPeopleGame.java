package me.jho5245.youpeoplegame;

import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import io.papermc.paper.plugin.configuration.PluginMeta;
import me.jho5245.youpeoplegame.listener.*;
import me.jho5245.youpeoplegame.service.CookieGivewayEvery5Seconds;
import me.jho5245.youpeoplegame.service.Service;
import me.jho5245.youpeoplegame.util.TestCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class YouPeopleGame extends JavaPlugin
{
	private static YouPeopleGame plugin;

	public static YouPeopleGame getPlugin()
	{
		return plugin;
	}

	public static final Location SPAWN_LOCATION = new Location(Bukkit.getWorld("youpeople_world"), 0, 64, 0);

	private PluginMeta pluginMeta;

	private String pluginName;

	private String pluginVersion;

	private PluginManager pluginManager;

	private List<Service> services = new ArrayList<>();

	@Override
	public void onEnable()
	{
		init();
		MessageUtil.consoleSendMessage("%s v.%s has been enabled!", pluginName, pluginVersion);
	}

	private void init()
	{
		plugin = this;
		pluginMeta = this.getPluginMeta();
		pluginName = pluginMeta.getName();
		pluginVersion = pluginMeta.getVersion();
		pluginManager = Bukkit.getPluginManager();
		services = new ArrayList<>();
		registerEvents();
		PluginCommand pluginCommand = this.getCommand("testcommand2");
		TestCommand testCommand = new TestCommand();
		pluginCommand.setExecutor(testCommand);
		pluginCommand.setTabCompleter(testCommand);
		registerService();
	}

	private static final Set<Class<? extends Listener>> events = new HashSet<>(Arrays.asList(
			CraftItem.class,
			CustomBlockBreak.class,
			InventoryOpen.class,
			OpenWindowMerchant.class,
			PlayerItemConsume.class,
			PlayerItemDamage.class,
			PlayerCustomMiningItemDamage.class,
			PlayerJoin.class,
			PrepareResult.class,
			SetCursorItem.class,
			SetSlot.class,
			WindowItems.class
	));

	private void registerEvents()
	{
		events.forEach(event -> {
			try
			{
				pluginManager.registerEvents((Listener) event.getConstructors()[0].newInstance(), this);
			}
			catch (InstantiationException | InvocationTargetException | IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
		});
	}

	private void registerService()
	{
		services.add(new CookieGivewayEvery5Seconds());

		services.forEach(Service::run);
	}

	@Override
	public void onDisable()
	{
		MessageUtil.consoleSendMessage("%s v.%s has been disabled!", pluginName, pluginVersion);
	}

	public enum YouPeopleGameUserData
	{
		DAMP_COOKIE_POTION_USED("눅눅한-쿠키-포션-사용-여부")
		;
		final String key;

		YouPeopleGameUserData(String key)
		{
			this.key = key;
		}

		public String toString()
		{
			return UserData.CUSTOM_DATA.getKey() + ".YouPeopleGame." + key;
		}
	}
}
