package com.hope.leadmap.mapdemo.sevenparameter;

/**
 * Created by Hope on 2015/12/25.
 */
public class ProjectInfo {
    private static double middleline;
    private static int zonewide;

    public static double getMiddleline() {
        return middleline;
    }

    public static void setMiddleline(double middleline) {
        ProjectInfo.middleline = middleline;
    }

    public static int getZonewide() {
        return zonewide;
    }

    public static void setZonewide(int zonewide) {
        ProjectInfo.zonewide = zonewide;
    }
}
