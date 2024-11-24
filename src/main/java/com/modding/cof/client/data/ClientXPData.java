package com.modding.cof.client.data;

public class ClientXPData {
    private static int xp;
    private static int totalXp;

    public static void set(int xp, int total) {
        ClientXPData.xp = xp;
        ClientXPData.totalXp = total;
    };

    public static void setCur(int xp) {
        ClientXPData.xp = xp;
    };

    public static void setTotal(int total) {
        ClientXPData.totalXp = total;
    };

    public static int getCurXP() {
        return ClientXPData.xp;
    };

    public static int getTotalXp() {
        return ClientXPData.totalXp;
    };
};
