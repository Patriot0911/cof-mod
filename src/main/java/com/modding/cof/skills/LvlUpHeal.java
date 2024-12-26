package com.modding.cof.skills;

import com.modding.cof.annotations.ModEventHandler;
import com.modding.cof.enums.SkillType;
import com.modding.cof.modEvents.LevelUpEvent;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class LvlUpHeal implements IBaseSkill {
    public final static String name = "lvlup_heal";
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
        return false;
    };

    public boolean isLearnable(Player player) {
        return true;
    };

    public int getMaxLvl() {
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
