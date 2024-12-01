package com.modding.cof.modEvents;

import com.modding.cof.modEvents.construction.ModEvent;

import net.minecraft.server.level.ServerPlayer;

public class LevelUpEvent extends ModEvent {
    private final int amount;
    private ServerPlayer player;

    public LevelUpEvent(int amount, ServerPlayer player) {
        this.amount = amount;
        this.player = player;
    };

    public int getAmount() {
        return amount;
    };

    public ServerPlayer getPlayer() {
        return player;
    };
};
