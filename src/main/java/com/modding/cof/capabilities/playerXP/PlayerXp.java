package com.modding.cof.capabilities.playerXP;

import com.modding.cof.capabilities.level.PlayerLevelProvider;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerXp {
    private int xp;

    public int get() {
        return xp;
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
                this.xp = newXp;
                if(changeLvl != 0) {
                    lvl.addLevel(changeLvl);
                };
            }
        );
        // NetworkManager sync with player xp data (not done)
        // call event
        return xp;
    };

    public void copyFrom(PlayerXp source) {
        this.xp = source.xp;
    };

    public void saveNBTData(CompoundTag nTag) {
        nTag.putInt("pl_level", xp);
    };

    public void loadNBTData(CompoundTag nTag) {
        xp = nTag.getInt("pl_level");
    };
};
