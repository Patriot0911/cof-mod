package com.modding.cof.capabilities.playerPoints;

import com.modding.cof.interfaces.ICapabilityPlayerState;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerPoints implements ICapabilityPlayerState<PlayerPoints> {
    /*
     * points - поточна кількість очків для прокачки здібносте
     * usedPoints - сумарна кількість очків, яку було отримано гравцем
     */
    private int points;
    private int usedPoints;

    public int getPoints() {
        return this.points;
    };

    public int getUsedPoints() {
        return this.usedPoints;
    };

    public int add(int count) {
        this.points += count;
        this.usedPoints += count;
        return this.points;
    };

    public int addCurPoints(int count) {
        this.points += count;
        return this.points;
    };

    public int getCurPoints() {
        return this.points;
    };

    public int getTotalPoints() {
        return this.usedPoints;
    };

    public void copyFrom(PlayerPoints source) {
        this.points = source.points;
        this.usedPoints = source.usedPoints;
    };

    public void saveNBTData(CompoundTag nTag) {
        nTag.putInt("pl_cur_skill_points", this.points);
        nTag.putInt("pl_total_skill_points", this.usedPoints);
    };

    public void loadNBTData(CompoundTag nTag) {
        this.points = nTag.getInt("pl_cur_skill_points");
        this.usedPoints = nTag.getInt("pl_total_skill_points");
    };
};
