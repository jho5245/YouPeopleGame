package me.jho5245.youpeoplegame;

import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import io.papermc.paper.plugin.configuration.PluginMeta;
import me.jho5245.youpeoplegame.listener.*;
import me.jho5245.youpeoplegame.service.CookieGivewayEvery5Seconds;
import me.jho5245.youpeoplegame.service.Service;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

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
		registerService();
	}

	private void registerEvents()
	{
		pluginManager.registerEvents(new CraftItem(), this);
		pluginManager.registerEvents(new CustomBlockBreak(), this);
		pluginManager.registerEvents(new InventoryOpen(), this);
		pluginManager.registerEvents(new PlayerItemDamage(), this);
		pluginManager.registerEvents(new PlayerCustomMiningItemDamage(), this);
		pluginManager.registerEvents(new PlayerJoin(), this);
		pluginManager.registerEvents(new PrepareResult(), this);
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
}
