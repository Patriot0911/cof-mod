package com.modding.cof.skills;

import com.modding.cof.enums.SkillType;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public interface IBaseSkill {
    public int getMaxLvl();
    public String getName();
    public String getLangName();
    public String getTextureName();
    public SkillType getSkillType();
    public boolean initTrigger(Player player);
    public boolean isLearnable(Player player);
    public int getLearnCost();
    public int getLevelCost(); // level * N
    public Component getDescription(int lvl, String[] args);
    public ResourceLocation getIconResource();
}
