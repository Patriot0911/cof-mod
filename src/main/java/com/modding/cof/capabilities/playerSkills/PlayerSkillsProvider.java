package com.modding.cof.capabilities.playerSkills;

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

public class PlayerSkillsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerSkills> PLAYER_SKILLS = CapabilityManager.get(
        new CapabilityToken<PlayerSkills>() {}
    );

    private PlayerSkills skills = null;
    private final LazyOptional<PlayerSkills> optional = LazyOptional.of(this::initPlayerSkills);

    private PlayerSkills initPlayerSkills() {
        if(this.skills == null) {
            this.skills = new PlayerSkills();
        };
        return this.skills;
    };

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        initPlayerSkills().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        initPlayerSkills().loadNBTData(nbt);
    };

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_SKILLS) {
            return optional.cast();
        };
        return LazyOptional.empty();
    };

}
