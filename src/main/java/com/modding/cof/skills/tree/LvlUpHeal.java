package com.modding.cof.skills.tree;

import com.modding.cof.annotations.ModEventHandler;
import com.modding.cof.modEvents.LevelUpEvent;
import com.modding.cof.skills.IBaseSkill;

import net.minecraft.server.level.ServerPlayer;

public class LvlUpHeal implements IBaseSkill {
    public final static String langName = "skill.cof.lvlup_heal";

    public int getMaxLvl() {
        return 100;
    };

    public String getName() {
        return langName;
    };

    @ModEventHandler(eventName = LevelUpEvent.name)
    public static void LvlUpHandle(LevelUpEvent event) {
        System.out.println(event.getAmount());
        ServerPlayer player = event.getPlayer();
        System.out.println(player.getHealth());
    };
}
