package com.modding.cof.skills.tree;

import com.modding.cof.annotations.ModEventHandler;
import com.modding.cof.modEvents.LevelUpEvent;
import com.modding.cof.skills.IBaseSkill;

public class MoreHealth implements IBaseSkill {
    public final static String langName = "skill.cof.init_more_hp";

    public int getMaxLvl() {
        return 100;
    };

    public String getName() {
        return langName;
    };

    @ModEventHandler(eventName = LevelUpEvent.name)
    public static void LvlUpHandle(LevelUpEvent event) {
        System.out.println(event.getAmount());
        System.out.println("null");
    };
}
