package com.modding.cof.client.data;

import java.util.Map;

import com.modding.cof.client.data.subClasses.ClientLocalSkill;

public class ClientSkillsData {
    private static Map<String, ClientLocalSkill> skills;

    public static void set(Map<String, ClientLocalSkill> skillsMap) {
        ClientSkillsData.skills = skillsMap;
    };

    public static int getPlayerSkillLvl(String skillName) {
        ClientLocalSkill skill = ClientSkillsData.skills.get(skillName);
        if(skill == null)
            return -1;
        return skill.lvl;
    };

    public static ClientLocalSkill getPlayerSkill(String skillName) {
        ClientLocalSkill skill = ClientSkillsData.skills.get(skillName);
        return skill;
    };
};
