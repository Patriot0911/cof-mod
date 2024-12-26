package com.modding.cof.skills;

import com.modding.cof.enums.SkillType;

import net.minecraft.world.entity.player.Player;

public interface IBaseSkill {
    public int getMaxLvl();
    public String getName();
    public String getLangName();
    public String getTextureName();
    public SkillType getSkillType();
    public boolean initTrigger(Player player);
    public boolean isLearnable(Player player);
}
