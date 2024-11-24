package com.modding.cof.capabilities.playerPoints;

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

public class PlayerPointsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerPoints> PLAYER_SKILL_POINTS = CapabilityManager.get(
        new CapabilityToken<PlayerPoints>() {}
    );

    private PlayerPoints skills = null;
    private final LazyOptional<PlayerPoints> optional = LazyOptional.of(this::initPlayerPoints);

    private PlayerPoints initPlayerPoints() {
        if(this.skills == null) {
            this.skills = new PlayerPoints();
        };
        return this.skills;
    };

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        initPlayerPoints().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        initPlayerPoints().loadNBTData(nbt);
    };

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_SKILL_POINTS) {
            return optional.cast();
        };
        return LazyOptional.empty();
    };
}
