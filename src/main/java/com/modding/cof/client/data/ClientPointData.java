package com.modding.cof.client.data;

public class ClientPointData {
    private static int points;
    private static int usedPoints;

    public static void set(int points, int used) {
        ClientPointData.points = points;
        ClientPointData.usedPoints = used;
    };

    public static void setUsedPoints(int used) {
        ClientPointData.usedPoints = used;
    };

    public static void setCurPoints(int points) {
        ClientPointData.points = points;
    };

    public static int getPoints() {
        return ClientPointData.points;
    };

    public static int getUsedPoints() {
        return ClientPointData.usedPoints;
    };
};
