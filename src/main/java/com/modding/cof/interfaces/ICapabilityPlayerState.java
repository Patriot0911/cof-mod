package com.modding.cof.interfaces;

import net.minecraft.nbt.CompoundTag;

public interface ICapabilityPlayerState<T> {
    void copyFrom(T source);
    void saveNBTData(CompoundTag nTag);
    void loadNBTData(CompoundTag nTag);
}