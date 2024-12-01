package com.modding.cof.skills;

import com.modding.cof.annotations.ModEventHandler;
import com.modding.cof.modEvents.LevelUpEvent;

public class LvlUpHeal {
    @ModEventHandler(event = LevelUpEvent.class)
    public static void LvlUpHandle(LevelUpEvent event) {
        System.out.println(event.getAmount());
        System.out.println("null");
    };
}
