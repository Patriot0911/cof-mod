package com.modding.cof.client.data.subClasses;

import net.minecraft.network.FriendlyByteBuf;

public class ClientLocalSkill {
    public int lvl = 0;
    public String args = "";

    public ClientLocalSkill(int level, String args) {
        this.lvl = level;
        this.args = args;
    };

    public static ClientLocalSkill read(FriendlyByteBuf buffer) {
        int lvl = buffer.readInt();
        String arg = buffer.readUtf();
        return new ClientLocalSkill(lvl, arg);
    }

    @Override
    public String toString() {
        return "CustomData{Lvl=" + this.lvl + ", args='" + args + "'}";
    }
}
