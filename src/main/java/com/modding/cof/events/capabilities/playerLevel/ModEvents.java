package com.modding.cof.events.capabilities.playerLevel;

import com.modding.cof.CoFMod;
import com.modding.cof.capabilities.playerXP.PlayerXpProvider;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = CoFMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerXpChange(PlayerXpEvent.XpChange event) {
        Player player = event.getEntity();
        int changeAmount = event.getAmount();
        if(changeAmount > 0) {
            player.getCapability(PlayerXpProvider.PLAYER_XP).ifPresent(
                xp -> {
                    int curXP = xp.add(changeAmount, player);
                    player.sendSystemMessage(Component.literal("Xp gained: " + changeAmount));
                    player.sendSystemMessage(Component.literal("Cur xp: " + curXP));
                }
            );
        };
    };
};
