package com.modding.cof.modEvents.construction;

import net.minecraft.server.level.ServerPlayer;

public abstract class ModEvent implements IModEvent {
    private boolean canceled = false;
    private String name;

    public String getName() {
        return name;
    }

    public ServerPlayer getPlayer() {
        return null;
    };

    public boolean isCanceled(boolean state) {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
