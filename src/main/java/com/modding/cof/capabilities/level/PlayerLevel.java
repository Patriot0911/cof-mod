package com.modding.cof.capabilities.level;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerLevel {
    private int level;
    private final int MAX_LEVEL = 1000;

    public int getLevel() {
        return level;
    };

    public int addLevel(int count) {
        this.level = Math.min(level + count, MAX_LEVEL);
        System.out.println("newLvl: " +this.level);
        // call event
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
