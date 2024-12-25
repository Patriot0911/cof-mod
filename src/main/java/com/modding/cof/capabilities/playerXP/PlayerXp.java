package com.modding.cof.capabilities.playerXP;

import com.modding.cof.capabilities.level.PlayerLevelProvider;
import com.modding.cof.interfaces.ICapabilityPlayerState;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerXp implements ICapabilityPlayerState<PlayerXp> {
    private int xp;
    private int totalXp;

    public int get() {
        return this.xp;
    };

    public int getTotalXp() {
        return this.totalXp;
    };

    /*
     * player - is not dist
     * just data container
    */
    public int add(int count, Player player) {
        player.getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(
            lvl -> {
                int changeLvl = 0;
                int newXp = this.xp + count;
                int startLvl = lvl.getLevel();
                while (lvl.xpForNextLvl(startLvl+changeLvl) <= newXp) {
                    newXp -= lvl.xpForNextLvl(startLvl+changeLvl);
                    ++changeLvl;
                };
                this.totalXp += count;
                this.xp = newXp;
                if(changeLvl != 0) {
                    lvl.addLevel(changeLvl, player);
                };
            }
        );
        // NetworkManager sync with player xp data (not done)
        // call event
        return xp;
    };

    public void copyFrom(PlayerXp source) {
        this.xp = source.xp;
        this.totalXp = source.totalXp;
    };

    public void saveNBTData(CompoundTag nTag) {
        nTag.putInt("pl_xp", this.xp);
        nTag.putInt("pl_total_xp", this.totalXp);
    };

    public void loadNBTData(CompoundTag nTag) {
        this.xp = nTag.getInt("pl_xp");
        this.totalXp = nTag.getInt("pl_total_xp");
    };
};
