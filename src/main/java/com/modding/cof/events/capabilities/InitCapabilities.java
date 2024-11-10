package com.modding.cof.events.capabilities;

import com.modding.cof.CoFMod;
import com.modding.cof.capabilities.level.PlayerLevelProvider;
import com.modding.cof.capabilities.playerXP.PlayerXpProvider;
import com.modding.cof.network.NetworkManager;
import com.modding.cof.network.packet.PlayerLevelSyncS2CPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = CoFMod.MOD_ID)
public class InitCapabilities {
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(
                    level -> {
                        NetworkManager.sendToPlayer(
                            new PlayerLevelSyncS2CPacket(level.getLevel()), player
                        );
                    }
                );
                // player.getCapability(PlayerXpProvider.PLAYER_XP).ifPresent(
                //     xp -> {
                //         NetworkManager.sendToPlayer(
                //             new PlayerLevelSyncS2CPacket(xp.get()), player
                //         );
                //     }
                // );
            };
        };
    };

    @SubscribeEvent
    public static void onAttachCapabilites(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerLevelProvider.PLAYER_LEVEL).isPresent()) {
                event.addCapability(
                    new ResourceLocation(CoFMod.MOD_ID, "properties_cof_pl_lvl"), new PlayerLevelProvider()
                );
            };
            if(!event.getObject().getCapability(PlayerXpProvider.PLAYER_XP).isPresent()) {
                event.addCapability(
                    new ResourceLocation(CoFMod.MOD_ID, "properties_cof_pl_xp"), new PlayerXpProvider()
                );
            };
        };
    };

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(
                oldState -> {
                    event.getEntity().getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(
                        newState -> {
                            newState.copyFrom(oldState);
                        }
                    );
                }
            );
            event.getOriginal().getCapability(PlayerXpProvider.PLAYER_XP).ifPresent(
                oldState -> {
                    event.getEntity().getCapability(PlayerXpProvider.PLAYER_XP).ifPresent(
                        newState -> {
                            newState.copyFrom(oldState);
                        }
                    );
                }
            );
        };
    };
};
