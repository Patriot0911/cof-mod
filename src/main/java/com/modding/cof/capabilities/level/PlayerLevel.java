package com.modding.cof.capabilities.level;

import com.modding.cof.interfaces.ICapabilityPlayerState;
import com.modding.cof.modEvents.LevelUpEvent;
import com.modding.cof.modEvents.construction.ModEventBus;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerLevel implements ICapabilityPlayerState<PlayerLevel> {
    private int level;
    private final int MAX_LEVEL = 1000;

    public int getLevel() {
        return level;
    };

    public int addLevel(int count, Player player) {
        this.level = Math.min(level + count, MAX_LEVEL);
        System.out.println("newLvl: " + this.level);
        LevelUpEvent event = new LevelUpEvent(count, (ServerPlayer) player);
        ModEventBus.emit(event);
        return level;
    };

    public int xpForNextLvl(int argLvl) {
        int lvl = argLvl < 0 ? this.level : argLvl;
        return (lvl+1)*32+25;
    };

    public void copyFrom(PlayerLevel source) {
        this.level = source.level;
    };

    public void saveNBTData(CompoundTag nTag) {
        nTag.putInt("pl_level", level);
    };

    public void loadNBTData(CompoundTag nTag) {
        level = nTag.getInt("pl_level");
    };
};
