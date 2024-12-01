package com.modding.cof.modEvents;

import com.modding.cof.enums.XpReason;
import com.modding.cof.modEvents.construction.ModEvent;

import net.minecraft.server.level.ServerPlayer;

public class XpGainEvent extends ModEvent {
    private final int amount;
    private ServerPlayer player;
    private XpReason reason;

    public XpGainEvent(int amount, ServerPlayer player, XpReason reason) {
        this.amount = amount;
        this.player = player;
        this.reason = reason;
    };

    public int getAmount() {
        return amount;
    };

    public ServerPlayer getPlayer() {
        return player;
    };

    public XpReason getReason() {
        return reason;
    };
};
