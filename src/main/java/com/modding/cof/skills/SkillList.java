package com.modding.cof.skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.modding.cof.skills.tree.LvlUpHeal;
import com.modding.cof.skills.tree.MoreHealth;

public class SkillList {
    public static List<Class<? extends IBaseSkill>> treeSkills = new ArrayList<>(
        Arrays.asList(
            LvlUpHeal.class,
            MoreHealth.class
        )
    );
    public static List<Class<? extends IBaseSkill>> generalSkills = new ArrayList<>();
}
