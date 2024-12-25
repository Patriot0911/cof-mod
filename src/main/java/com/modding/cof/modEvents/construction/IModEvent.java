package com.modding.cof.modEvents.construction;

import net.minecraft.server.level.ServerPlayer;

public interface IModEvent {
    ServerPlayer getPlayer();
    boolean isCanceled();
    void setCanceled(boolean staste);
    String getName();
}
