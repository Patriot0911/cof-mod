package com.modding.cof.capabilities.playerXP;

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

public class PlayerXpProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerXp> PLAYER_XP = CapabilityManager.get(
        new CapabilityToken<PlayerXp>() {}
    );

    private PlayerXp level = null;
    private final LazyOptional<PlayerXp> optional = LazyOptional.of(this::initPlayerLevel);

    private PlayerXp initPlayerLevel() {
        if(this.level == null) {
            this.level = new PlayerXp();
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
        if(cap == PLAYER_XP) {
            return optional.cast();
        };
        return LazyOptional.empty();
    };

}
