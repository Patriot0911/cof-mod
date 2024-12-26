package com.modding.cof.skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillList {
    public static List<Class<? extends IBaseSkill>> skills = new ArrayList<>(
        Arrays.asList(
            LvlUpHeal.class
        )
    );
}
