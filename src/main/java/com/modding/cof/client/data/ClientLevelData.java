package com.modding.cof.client.data;

public class ClientLevelData {
    private static int playerLevel;

    public static void set(int lvl) {
        ClientLevelData.playerLevel = lvl;
    };

    public static int getPlayerLevel() {
        return playerLevel;
    };
};
