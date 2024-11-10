package com.modding.cof.network;

import com.modding.cof.network.packet.PlayerLevelSyncS2CPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkManager {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int getNextId() {
        return packetId++;
    };

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation("cof", "network"))
            .networkProtocolVersion(() -> "1.0")
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .simpleChannel();
        INSTANCE = net;

        net.messageBuilder(PlayerLevelSyncS2CPacket.class, getNextId(), NetworkDirection.PLAY_TO_CLIENT)
            .decoder(PlayerLevelSyncS2CPacket::new)
            .encoder(PlayerLevelSyncS2CPacket::toBytes)
            .consumerMainThread(PlayerLevelSyncS2CPacket::handle)
            .add();
        // net.messageBuilder(PlayerLevelSyncS2CPacket.class, getNextId(), NetworkDirection.PLAY_TO_CLIENT)
        //     .decoder(PlayerLevelSyncS2CPacket::new)
        //     .encoder(PlayerLevelSyncS2CPacket::toBytes)
        //     .consumerMainThread(PlayerLevelSyncS2CPacket::handle)
        //     .add();
    };

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    };

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    };
};
