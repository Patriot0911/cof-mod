package com.modding.cof.network.packet.skills;

import java.util.Map;
import java.util.function.Supplier;

import com.modding.cof.client.data.ClientSkillsData;
import com.modding.cof.client.data.subClasses.ClientLocalSkill;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PlayerSkillsSyncS2CPacket {
    private final Map<String, ClientLocalSkill> skills;

    public PlayerSkillsSyncS2CPacket(Map<String, ClientLocalSkill> skillsArg) {
        this.skills = skillsArg;
    };

    public PlayerSkillsSyncS2CPacket(FriendlyByteBuf buffer) {
        this.skills = buffer.readMap(
            FriendlyByteBuf::readUtf,
            ClientLocalSkill::read
        );
    };

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeMap(
            this.skills,
            FriendlyByteBuf::writeUtf,
            (buf, skillData) -> {
                buf.writeInt(skillData.lvl);
                buf.writeUtf(skillData.args);
            }
        );
    };

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // ClientSide
            ClientSkillsData.set(this.skills);
        });
        return true;
    }
}
