package com.modding.cof.capabilities.level;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerLevelProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerLevel> PLAYER_LEVEL = CapabilityManager.get(
        new CapabilityToken<PlayerLevel>() {}
    );

    private PlayerLevel level = null;
    private final LazyOptional<PlayerLevel> optional = LazyOptional.of(this::initPlayerLevel);

    private PlayerLevel initPlayerLevel() {
        if(this.level == null) {
            this.level = new PlayerLevel();
        };
        return this.level;
    };

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        initPlayerLevel().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        initPlayerLevel().loadNBTData(nbt);
    };

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_LEVEL) {
            return optional.cast();
        };
        return LazyOptional.empty();
    };

}
