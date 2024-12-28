package com.modding.cof.network.packet.skills;

import java.util.function.Supplier;

import com.modding.cof.CoFMod;
import com.modding.cof.capabilities.playerSkills.PlayerSkillsProvider;
import com.modding.cof.capabilities.playerXP.PlayerXpProvider;
import com.modding.cof.network.NetworkManager;
import com.modding.cof.skills.IBaseSkill;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class LearnSkillC2S {
    private String skillName;

    public LearnSkillC2S(String skillName) {
        this.skillName = skillName;
    }

    public LearnSkillC2S(FriendlyByteBuf buffer) {
        this.skillName = buffer.readUtf();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(skillName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            System.out.println("test " + this.skillName);
            player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(
                skills -> {
                    System.out.println("1");
                    IBaseSkill selectedSkill = null;
                    for(IBaseSkill skill : CoFMod.skills) {
                        System.out.println(skill.getName().equals(skillName));
                        System.out.println(skill.getName());
                        System.out.println(skillName);
                        if(skill.getName().equals(skillName)) {
                            if(!skill.isLearnable(player)) break;
                            selectedSkill = skill;
                            break;
                        };
                    };
                    System.out.println("2");
                    if(selectedSkill == null) return;
                    System.out.println("3");
                    if(skills.getSkills().get(selectedSkill.getName()) != null) {
                        System.out.println("This skill is already known");
                        return;
                    };
                    if(!selectedSkill.isLearnable(player)) {
                        System.out.println("This skill cannot be learned");
                        return;
                    };
                    System.out.println("4");
                    int cost = selectedSkill.getLearnCost();
                    final IBaseSkill passSkill = selectedSkill;
                    player.getCapability(PlayerXpProvider.PLAYER_XP).ifPresent(
                        xp -> {
                            if(xp.getTotalXp() <= cost) {
                                System.out.println("not enough total XP");
                                return;
                            };
                            xp.takeTotalXp(cost);
                            skills.addSkill(passSkill);
                            passSkill.initTrigger(player);
                            NetworkManager.sendToPlayer(
                                new PlayerSkillsSyncS2CPacket(skills.getSkills()),
                                player
                            );
                        }
                    );
                }
            );
        });
        return true;
    }
}
