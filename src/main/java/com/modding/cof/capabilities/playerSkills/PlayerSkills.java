package com.modding.cof.capabilities.playerSkills;

import java.util.HashMap;
import java.util.Map;

import com.modding.cof.client.data.subClasses.ClientLocalSkill;
import com.modding.cof.interfaces.ICapabilityPlayerState;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerSkills implements ICapabilityPlayerState<PlayerSkills> {
    private Map<String, ClientLocalSkill> skills;

    public Map<String, ClientLocalSkill> getSkills() {
        return this.skills;
    };

    public void copyFrom(PlayerSkills source) {
        this.skills = source.skills;
    };

    public void saveNBTData(CompoundTag nTag) {
        CompoundTag mapTag = new CompoundTag();
        if(this.skills != null) {
            for(Map.Entry<String, ClientLocalSkill> skill : skills.entrySet()) {
                // можна винести у метод ClientLocalSkill
                CompoundTag skillTag = new CompoundTag();
                skillTag.putString("name", skill.getKey());
                skillTag.putInt("level", skill.getValue().lvl);
                skillTag.putString("args", skill.getValue().args);
                mapTag.put(skill.getKey(), skillTag);
            };
        };
        nTag.put("skills", mapTag);
    };

    public void loadNBTData(CompoundTag nTag) {
        if(this.skills != null) {
            this.skills.clear();
        } else {
            this.skills = new HashMap<>();
        };
        if(nTag.contains("skills")) {
            CompoundTag mapTag = nTag.getCompound("skills");
            for(String key : mapTag.getAllKeys()) {
                CompoundTag skillTag = mapTag.getCompound(key);
                // String skillName = skillTag.getString("name");
                int skillLevel = skillTag.getInt("level");
                String skillArgs = skillTag.getString("args");
                ClientLocalSkill skillItem = new ClientLocalSkill(skillLevel, skillArgs);
                skills.put(key, skillItem);
            };
        };
    };
};
