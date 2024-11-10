package com.modding.cof.network.packet;

import java.util.function.Supplier;

import com.modding.cof.client.data.ClientLevelData;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PlayerLevelSyncS2CPacket {
    private final int level;

    public PlayerLevelSyncS2CPacket(int lvl) {
        this.level = lvl;
    };

    public PlayerLevelSyncS2CPacket(FriendlyByteBuf buffer) {
        this.level = buffer.readInt();
    };

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(level);
    };

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // ClientSide
            ClientLevelData.set(level);
        });
        return true;
    }
}
