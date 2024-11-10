package com.modding.cof.network.packet;

import java.util.function.Supplier;

import com.modding.cof.capabilities.level.PlayerLevelProvider;
import com.modding.cof.network.NetworkManager;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class PlayerLevelC2SPacket { // rename to smthg like "learn skill"
    public PlayerLevelC2SPacket() {
    }

    public PlayerLevelC2SPacket(FriendlyByteBuf buffer) {
    }

    public void toBytes(FriendlyByteBuf buffer) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            player.getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(
                level -> {
                    /// serverside. code is not valid
                    level.addLevel(1);
                    player.sendSystemMessage(Component.literal(
                        "Level: " + level.getLevel()
                    ));
                }
            );
            NetworkManager.sendToPlayer(new PlayerLevelSyncS2CPacket(2), player);
        });
        return true;
    }
}
