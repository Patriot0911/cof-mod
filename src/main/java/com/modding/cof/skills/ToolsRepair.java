package com.modding.cof.skills;

import com.modding.cof.CoFMod;
import com.modding.cof.annotations.ModEventHandler;
import com.modding.cof.enums.SkillType;
import com.modding.cof.modEvents.LevelUpEvent;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ToolsRepair implements IBaseSkill {
    public final static String name = "tools_repair";
    public final static String langName = "skill.cof." + name;

    public String getLangName() {
        return langName;
    };

    public String getName() {
        return name;
    };

    public SkillType getSkillType() {
        return SkillType.Learnable;
    };

    public boolean initTrigger(Player player) {
        player.sendSystemMessage(
            Component.literal("treiggered init skill")
        );
        return true;
    };

    public Component getDescription(int lvl, String[] args) {
        return Component.literal(langName);
    };

    public ResourceLocation getIconResource() {
        String skillTextureName = "tools_repair.png";
        return new ResourceLocation(CoFMod.MOD_ID, "textures/skills/" + skillTextureName);
    };

    public boolean isLearnable(Player player) {
        return true;
    };

    public int getMaxLvl() {
        return 50;
    };

    public int getLearnCost() {
        return 5;
    };

    public int getLevelCost() {
        return 50;
    };

    public String getTextureName() {
        return null;
    };

    @ModEventHandler(eventName = LevelUpEvent.name)
    public static void LvlUpHandle(LevelUpEvent event) {
        System.out.println(event.getAmount());
        ServerPlayer player = event.getPlayer();
        System.out.println(player.getHealth());
    };
}
