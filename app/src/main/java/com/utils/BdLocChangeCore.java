package com.utils;

import com.core.vo.GpsWgs;

/**
 * Created by wang on 15/4/29.
 */
public class BdLocChangeCore {

    /**
     * 坐标转换程序
     * <p/>
     * WGS84坐标系：即地球坐标系，国际上通用的坐标系。Earth
     * <p/>
     * GCJ02坐标系：即火星坐标系，WGS84坐标系经加密后的坐标系。Mars
     * <p/>
     * BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系。  Bd09
     * <p/>
     * 搜狗坐标系、图吧坐标系等，估计也是在GCJ02基础上加密而成的。
     * <p/>
     * 百度地图API        百度坐标
     * 腾讯搜搜地图API 火星坐标
     * 搜狐搜狗地图API 搜狗坐标*
     * 阿里云地图API     火星坐标
     * 图吧MapBar地图API 图吧坐标
     * 高德MapABC地图API 火星坐标
     * 灵图51ditu地图API 火星坐标
     */
    private static double PI = Math.PI;
    private static double AXIS = 6378245.0;  //
    private static double OFFSET = 0.00669342162296594323;  //(a^2 - b^2) / a^2
    private static double X_PI = PI * 3000.0 / 180.0;

    //GCJ-02=>BD09 火星坐标系=>百度坐标系
    public static GpsWgs gcj2ToBD09(double glat, double glon) {
        double x = glon;
        double y = glat;
        double[] latlon = new double[2];
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);

        double lat = z * Math.sin(theta) + 0.006;
        double lon = z * Math.cos(theta) + 0.0065;
        return new GpsWgs(lat,lon);
    }

    //BD09=>GCJ-02 百度坐标系=>火星坐标系
    public static GpsWgs bd09ToGCJ2(double glat, double glon) {
        double x = glon - 0.0065;
        double y = glat - 0.006;
        double[] latlon = new double[2];
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double lat = z * Math.sin(theta);
        double lon = z * Math.cos(theta);
        return new GpsWgs(lat,lon);
    }

    //BD09=>WGS84 百度坐标系=>地球坐标系(粗略)
    public static GpsWgs bd09ToWGS(double glat, double glon) {
        GpsWgs gps = bd09ToGCJ2(glat, glon);
        return gcj2ToWGS(gps.getWgLat(), gps.getWgLon());
    }

    //BD09=>WGS84 百度坐标系=>地球坐标系(精确)
    public static GpsWgs bd09ToWGSExactly(double glat, double glon) {
        GpsWgs gps = bd09ToGCJ2(glat, glon);
        return gcj2ToWGSExactly(gps.getWgLat(), gps.getWgLon());
    }

    // WGS84=》BD09   地球坐标系=>百度坐标系
    public static GpsWgs wgsToBD09(double wgLat, double wgLon) {
        GpsWgs gps = wgsToGCJ(wgLat, wgLon);
        return gcj2ToBD09(gps.getWgLat(), gps.getWgLon());
    }

    // WGS84=》GCJ02   地球坐标系=>火星坐标系
    public static GpsWgs wgsToGCJ(double wgLat, double wgLon) {
        double[] latlon = new double[2];
        if (outOfChina(wgLat, wgLon)) {
            return new GpsWgs(wgLat,wgLon);
        }
        double[] deltaD = delta(wgLat, wgLon);
        double lat = wgLat + deltaD[0];
        double lon = wgLon + deltaD[1];
        return new GpsWgs(lat,lon);
    }

    //GCJ02=>WGS84   火星坐标系=>地球坐标系(粗略)
    public static GpsWgs gcj2ToWGS(double glat, double glon) {
        if (outOfChina(glat, glon)) {
            double lat = glat;
            double lon = glon;
            return new GpsWgs(lat,lon);
        }
        double[] deltaD = delta(glat, glon);
        double lat = glat - deltaD[0];
        double lon = glon - deltaD[1];
        return new GpsWgs(lat,lon);
    }

    //GCJ02=>WGS84   火星坐标系=>地球坐标系（精确）
    public static GpsWgs gcj2ToWGSExactly(double gcjLat, double gcjLon) {
        double initDelta = 0.01;
        double threshold = 0.000000001;
        double dLat = initDelta, dLon = initDelta;
        double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
        double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
        double wgsLat, wgsLon, i = 0;
        while (true) {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;
            GpsWgs gps = wgsToGCJ(wgsLat, wgsLon);
            dLat = gps.getWgLat() - gcjLat;
            dLon = gps.getWgLon() - gcjLon;
            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
                break;

            if (dLat > 0) pLat = wgsLat;
            else mLat = wgsLat;
            if (dLon > 0) pLon = wgsLon;
            else mLon = wgsLon;

            if (++i > 10000) break;
        }
        return new GpsWgs(wgsLat,wgsLon);
    }

    //两点距离
    public static double distance(double latA, double logA, double latB, double logB) {
        int earthR = 6371000;
        double x = Math.cos(latA * Math.PI / 180) * Math.cos(latB * Math.PI / 180) * Math.cos((logA - logB) * Math.PI / 180);
        double y = Math.sin(latA * Math.PI / 180) * Math.sin(latB * Math.PI / 180);
        double s = x + y;
        if (s > 1)
            s = 1;
        if (s < -1)
            s = -1;
        double alpha = Math.acos(s);
        double distance = alpha * earthR;
        return distance;
    }

    public static double[] delta(double wgLat, double wgLon) {
        double[] latlng = new double[2];
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - OFFSET * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((AXIS * (1 - OFFSET)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (AXIS / sqrtMagic * Math.cos(radLat) * PI);
        latlng[0] = dLat;
        latlng[1] = dLon;
        return latlng;
    }

    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

}