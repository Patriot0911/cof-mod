package com.modding.cof.events.capabilities;

import com.modding.cof.CoFMod;
import com.modding.cof.capabilities.PlayerCapabilitiesRegisterData;
import com.modding.cof.capabilities.level.PlayerLevelProvider;
import com.modding.cof.capabilities.playerPoints.PlayerPointsProvider;
import com.modding.cof.capabilities.playerSkills.PlayerSkillsProvider;
import com.modding.cof.capabilities.playerXP.PlayerXpProvider;
import com.modding.cof.interfaces.ICapabilityPlayerState;
import com.modding.cof.network.NetworkManager;
import com.modding.cof.network.packet.PlayerLevelSyncS2CPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = CoFMod.MOD_ID)
public class InitCapabilities {
    private static PlayerCapabilitiesRegisterData[] toInitPlayerCapibilities = {
        new PlayerCapabilitiesRegisterData(
            PlayerLevelProvider.class, "properties_cof_pl_lvl", PlayerLevelProvider.PLAYER_LEVEL
        ),
        new PlayerCapabilitiesRegisterData(
            PlayerSkillsProvider.class, "properties_cof_pl_xp", PlayerSkillsProvider.PLAYER_SKILLS
        ),
        new PlayerCapabilitiesRegisterData(
            PlayerXpProvider.class, "properties_cof_pl_skills", PlayerXpProvider.PLAYER_XP
        ),
        new PlayerCapabilitiesRegisterData(
            PlayerPointsProvider.class, "properties_cof_pl_skill_points", PlayerPointsProvider.PLAYER_SKILL_POINTS
        ),
    };

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
            for(int i = 0; i < toInitPlayerCapibilities.length; i++) {
                PlayerCapabilitiesRegisterData data = toInitPlayerCapibilities[i];
                if(!event.getObject().getCapability(data.capability).isPresent()) {
                    try {
                        ICapabilityProvider provider = data.provider.getDeclaredConstructor().newInstance();
                        event.addCapability(
                            new ResourceLocation(CoFMod.MOD_ID, data.resourceName),
                            provider
                        );
                    } catch(Exception e) {
                        CoFMod.LOGGER.error("Cannot create provider instance for capability", e);
                    };
                };
            };
        };
    };

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            for(int i = 0; i < toInitPlayerCapibilities.length; i++) {
                PlayerCapabilitiesRegisterData data = toInitPlayerCapibilities[i];
                event.getOriginal().getCapability(data.capability).ifPresent(
                    oldState -> {
                        event.getEntity().getCapability(data.capability).ifPresent(
                            newState -> {
                                if (newState instanceof ICapabilityPlayerState<?> copyableNewState) {
                                    try {
                                        ((ICapabilityPlayerState<Object>) copyableNewState).copyFrom(oldState);
                                    } catch (ClassCastException e) {
                                        CoFMod.LOGGER.warn("Cannot copy state: incompatible types for capability " + data.capability, e);
                                    }
                                } else {
                                    CoFMod.LOGGER.warn("newState does not implement ICapabilityCopyable for capability " + data.capability);
                                }
                            }
                        );
                    }
                );
            };
        };
    };
};
