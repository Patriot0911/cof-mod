package com.modding.cof.item;

import com.modding.cof.CoFMod;
import com.modding.cof.item.custom_items.BasicStaff;
import com.modding.cof.item.custom_items.SmithPotion;
import com.modding.cof.item.custom_items.SoulEater;
import com.modding.cof.item.custom_items_properties.BasicStaffProperties;
import com.modding.cof.item.custom_items_properties.SoulEaterProperties;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
                        CoFMod.MOD_ID);

        public static final RegistryObject<Item> magicStaff = ITEMS.register(
                        "magic_staff", () -> new BasicStaff(new BasicStaffProperties()));

        public static final RegistryObject<SwordItem> soulEater = ITEMS.register(
                        "soul_eater_sword", () -> new SoulEater(Tiers.NETHERITE, 15, 0.4f, new SoulEaterProperties()));

        public static final RegistryObject<Item> smithPotion = ITEMS.register(
                        "smith_potion",
                        () -> new SmithPotion(new Item.Properties()
                                        .stacksTo(1)
                                        .rarity(Rarity.UNCOMMON)
                                        .tab(CreativeModeTab.TAB_BREWING)));

        public static void register(IEventBus eventBus) {
                ITEMS.register(eventBus);
        }
}
