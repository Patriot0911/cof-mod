package com.modding.cof;

import org.slf4j.Logger;

import com.modding.cof.capabilities.level.PlayerLevelProvider;
import com.modding.cof.item.Items;
import com.modding.cof.network.NetworkManager;
import com.modding.cof.network.packet.PlayerLevelSyncS2CPacket;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CoFMod.MOD_ID)
public class CoFMod
{
    public static final String MOD_ID = "cof";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CoFMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        Items.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        NetworkManager.register();
        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    // @SubscribeEvent
    // public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
    //     if(!event.getLevel().isClientSide()) {
    //         if(event.getEntity() instanceof ServerPlayer player) {
    //             player.getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(
    //                 level -> {
    //                     NetworkManager.sendToPlayer(
    //                         new PlayerLevelSyncS2CPacket(level.getLevel()), player
    //                     );
    //                 }
    //             );
    //         };
    //     };
    // };

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
