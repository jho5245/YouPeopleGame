package me.jho5245.youpeoplegame.listener;

import com.google.common.collect.Lists;
import com.jho5245.cucumbery.custom.custommaterial.CustomMaterial;
import com.jho5245.cucumbery.events.itemlore.ItemLoreCustomMaterialEvent;
import com.jho5245.cucumbery.util.nbt.CucumberyTag;
import com.jho5245.cucumbery.util.shading.NBT;
import com.jho5245.cucumbery.util.shading.handler.NBTHandlers;
import com.jho5245.cucumbery.util.storage.no_groups.ItemCategory.Rarity;
import me.jho5245.youpeoplegame.custommaterial.CustomMaterialYouPeopleGame;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemLoreCustomMaterial implements Listener
{
	@EventHandler
	public void onItemLoreCustomMaterial(ItemLoreCustomMaterialEvent event)
	{
		//		Player player = event.getPlayer();
		ItemStack itemStack = event.getItemStack();
		CustomMaterial customMaterial = CustomMaterial.itemStackOf(itemStack);
		// NBT
		{
			if (customMaterial != null)
			{
				if (List.of(CustomMaterialYouPeopleGame.CRISPY_COOKIE_PICKAXE, CustomMaterialYouPeopleGame.CRISPY_COOKIE_AXE,
						CustomMaterialYouPeopleGame.CRISPY_COOKIE_SHOVEL).contains(customMaterial))
				{
					NBT.modify(itemStack, nbt ->
					{
						nbt.getOrCreateCompound(CucumberyTag.KEY_MAIN).getOrCreateCompound(CucumberyTag.CUSTOM_RARITY_KEY)
								.setString(CucumberyTag.CUSTOM_RARITY_FINAL_KEY, Rarity.RARE.toString());
					});
				}

				if (List.of(CustomMaterialYouPeopleGame.DAMP_COOKIE_POTION, CustomMaterialYouPeopleGame.MOIST_COOKIE_BOOSTER,
						CustomMaterialYouPeopleGame.SUPER_MOIST_COOKIE_BOOSTER).contains(customMaterial))
				{
					NBT.modifyComponents(itemStack, nbt ->
					{
						nbt.set("minecraft:consumable", NBT.createNBTObject(), NBTHandlers.STORE_READWRITE_TAG);
					});
				}
			}
		}

		// ItemMeta
		{
			ItemMeta itemMeta = itemStack.getItemMeta();

			if (customMaterial != null)
			{
				if (List.of(CustomMaterialYouPeopleGame.CRISPY_COOKIE_PICKAXE, CustomMaterialYouPeopleGame.CRISPY_COOKIE_AXE,
						CustomMaterialYouPeopleGame.CRISPY_COOKIE_SHOVEL).contains(customMaterial))
				{
					itemMeta.addEnchant(Enchantment.EFFICIENCY, 3, true);
				}
				if (customMaterial == CustomMaterialYouPeopleGame.CRISPY_COOKIE_SWORD)
				{
					itemMeta.addEnchant(Enchantment.SHARPNESS, 3, true);
					for (Attribute attribute : Lists.newArrayList(Registry.ATTRIBUTE).toArray(new Attribute[0]))
					{
						itemMeta.removeAttributeModifier(attribute);
					}
					itemMeta.addAttributeModifier(Attribute.ATTACK_DAMAGE,
							new AttributeModifier(new NamespacedKey("youpeoplegame", customMaterial.getKey().getKey() + "_attack_damage_1"), 4d, Operation.ADD_NUMBER,
									EquipmentSlotGroup.MAINHAND));
					itemMeta.addAttributeModifier(Attribute.ATTACK_SPEED,
							new AttributeModifier(new NamespacedKey("youpeoplegame", customMaterial.getKey().getKey() + "_attack_speed_1"), -2.4d,
									Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND));
				}
			}

			itemStack.setItemMeta(itemMeta);
		}

		event.setItemStack(itemStack);

	}
}
