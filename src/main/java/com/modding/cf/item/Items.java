package com.modding.cf.item;

import com.modding.cf.ExampleMod;
import com.modding.cf.item.custom_items.BasicStaff;
import com.modding.cf.item.custom_items_properties.BasicStaffProperties;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MOD_ID);

    public static final RegistryObject<Item> magicStaff = ITEMS.register(
        "magic_staff", () -> new BasicStaff(new BasicStaffProperties())
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
