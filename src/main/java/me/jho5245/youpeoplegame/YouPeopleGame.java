package me.jho5245.youpeoplegame;

import com.jho5245.cucumbery.util.no_groups.CucumberyCommandExecutor;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import io.papermc.paper.plugin.configuration.PluginMeta;
import me.jho5245.youpeoplegame.command.*;
import me.jho5245.youpeoplegame.custommaterial.CustomMaterialYouPeopleGame;
import me.jho5245.youpeoplegame.listener.*;
import me.jho5245.youpeoplegame.listener.inventory.CraftItem;
import me.jho5245.youpeoplegame.listener.inventory.InventoryClick;
import me.jho5245.youpeoplegame.listener.inventory.InventoryOpen;
import me.jho5245.youpeoplegame.listener.inventory.PrepareResult;
import me.jho5245.youpeoplegame.listener.packet.OpenWindowMerchant;
import me.jho5245.youpeoplegame.listener.packet.SetCursorItem;
import me.jho5245.youpeoplegame.listener.packet.SetSlot;
import me.jho5245.youpeoplegame.listener.packet.WindowItems;
import me.jho5245.youpeoplegame.listener.player.*;
import me.jho5245.youpeoplegame.service.SheepWool;
import me.jho5245.youpeoplegame.service.scheduler.CookieGiveawayEveryNSeconds;
import me.jho5245.youpeoplegame.service.scheduler.SchedulerService;
import me.jho5245.youpeoplegame.service.scheduler.SpeedModifierSerivce;
import org.bukkit.Bukkit;
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

	private String pluginName;

	private String pluginVersion;

	private PluginManager pluginManager;

	private List<SchedulerService> services = new ArrayList<>();

	@Override
	public void onEnable()
	{
		init();
		MessageUtil.consoleSendMessage("%s v.%s has been enabled!", pluginName, pluginVersion);
	}

	private void init()
	{
		plugin = this;
		PluginMeta pluginMeta = this.getPluginMeta();
		pluginName = pluginMeta.getName();
		pluginVersion = pluginMeta.getVersion();
		pluginManager = Bukkit.getPluginManager();
		services = new ArrayList<>();
		CustomMaterialYouPeopleGame.getInstance().registerCustomMaterial();
		registerCommands();
		registerEvents();
		registerService();
	}

	private static final Set<Class<? extends Listener>> events = new HashSet<>(Arrays.asList(
			CraftItem.class,
			CustomBlockBreak.class,
			InventoryClick.class,
			InventoryOpen.class,
			ItemLore3.class,
			ItemLoreCustomMaterial.class,
			OpenWindowMerchant.class,
			PlayerCustomMiningItemDamage.class,
			PlayerInteract.class,
			PlayerShearEntity.class,
			PlayerItemConsume.class,
			PlayerItemDamage.class,
			PlayerJoin.class,
			PlayerSwapHeldItem.class,
			PlayerTelekinesisItem.class,
			PrepareResult.class,
			SetCursorItem.class,
			SetSlot.class,
			WindowItems.class
	));

	private void registerCommands()
	{
		registerCommand("testcommand2", new TestCommand());
		registerCommand("youpeoplegame", new GUICommand());
		registerCommand("giveup", ParkourGiveUp.get());
		registerCommand("material-storage", new MaterialStorageCommand());
		registerCommand("item-storage", new ItemStorageCommand());
		registerCommand("buff", new BuffCommand());
	}

	private void registerCommand(String command, CucumberyCommandExecutor executor)
	{
		PluginCommand pluginCommand = this.getCommand(command);
		if (pluginCommand == null) throw new IllegalArgumentException("Missing command '%s' in plugin.yml".formatted(command));
		pluginCommand.setExecutor(executor);
		pluginCommand.setTabCompleter(executor);
	}

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
		services.add(new CookieGiveawayEveryNSeconds());
		services.add(SheepWool.get());
		services.add(SpeedModifierSerivce.get());
		services.forEach(SchedulerService::run);
	}

	@Override
	public void onDisable()
	{
		CustomMaterialYouPeopleGame.getInstance().unregisterCustomMaterial();
		MessageUtil.consoleSendMessage("%s v.%s has been disabled!", pluginName, pluginVersion);
	}
}
