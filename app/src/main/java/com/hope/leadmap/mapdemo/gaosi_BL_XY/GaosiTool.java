package com.hope.leadmap.mapdemo.gaosi_BL_XY;

/**
 * Created by Hope on 2016/2/26.
 */
public class GaosiTool {

    static double a;
    static double e2;
    static double m0, m2, m4, m6, m8, a0, a2, a4, a6, a8, xx, yy, _x, _y, BB, LL;
    static double PI;
    static double _e,f;


    public static void beijing_54(){
        //北京54椭球
        a = 6378245;                 //北京54椭球 IGA75
        e2 = 0.006693421622965949;   //第一偏心率平方
    }

    public static void xian_80(){
        //西安80椭球
        a = 6378140;  //西安80椭球 IGA75
        e2 = 0.006694384999588;
    }

    public  static void cgcs_2000(){
        //cgcs2000
        a=6378137;
        f=1/298.257222101;
        _e=1/f;
        e2=(2 * _e - 1) / _e / _e;
    }

    static void reset()
    {
        if (a==0||e2==0){
            a = 6378245;                 //北京54椭球 IGA75
            e2 = 0.006693421622965949;   //第一偏心率平方
        }
        m0 = a * (1 - e2);
        m2 = 3.0 / 2 * e2 * m0;
        m4 = 5.0 / 4 * e2 * m2;
        m6 = 7.0 / 6 * e2 * m4;
        m8 = 9.0 / 8 * e2 * m6;
        a0 = m0 + m2 / 2 + (3.0 / 8.0) * m4 + (5.0 / 16.0) * m6 + (35.0 / 128.0) * m8;
        a2 = m2 / 2 + m4 / 2 + 15.0 / 32 * m6 + 7.0 / 16 * m8;
        a4 = m4 / 8 + 3.0 / 16 * m6 + 7.0 / 32 * m8;
        a6 = m6 / 32 + m8 / 16;
        a8 = m8 / 128;
        xx = 0;
        yy = 0;
        _x = 0;
        _y = 0;
        BB = 0;
        LL = 0;
        PI = Math.PI;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="B">格式为度分秒，如24.1535628.代表24度15分35.628秒</param>
    /// <param name="L">格式为度分秒</param>
    /// <param name="L0">格式为度。如果118.5代表118.5度，</param>
    public static void GaussPositive(double B, double L, double L0)
    {
        reset();
        double X, t, N, h2, l, m, Bmiao, Lmiao;
        int Bdu, Bfen, Ldu, Lfen;
        Bdu = (int)B;
        Bfen = (int)(B * 100) % 100;
        Bmiao = (B - Bdu - Bfen * 0.01) * 10000.0;
        B = Bdu * PI / 180.0 + (Bfen / 60.0) * PI / 180.0 + Bmiao / 3600.0 * PI / 180.0;
        Ldu = (int)L;
        Lfen = (int)(L * 100) % 100;
        Lmiao = (L - Ldu - Lfen * 0.01) * 10000.0;
        L = Ldu * PI / 180.0 + (Lfen / 60.0) * PI / 180 + Lmiao / 3600.0 * PI / 180.0;
        l = L - L0 * PI / 180;
        X = a0 * B - Math.sin(B) * Math.cos(B) * ((a2 - a4 + a6) + (2 * a4 - 16.0 / 3.0 * a6) * Math.sin(B) * Math.sin(B) + 16.0 / 3.0 * a6 * Math.pow(Math.sin(B), 4)) + a8 / 8.0 * Math.sin(8 * B);
        t = Math.tan(B);
        h2 = e2 / (1 - e2) * Math.cos(B) * Math.cos(B);
        N = a / Math.sqrt(1 - e2 * Math.sin(B) * Math.sin(B));
        m = Math.cos(B) * l;
        xx = X + N * t * ((0.5 + (1.0 / 24.0 * (5 - t * t + 9 * h2 + 4 * h2 * h2) + 1.0 / 720.0 * (61 - 58 * t * t + Math.pow(t, 4)) * m * m) * m * m) * m * m);
        yy = N * ((1 + (1.0 / 6.0 * (1 - t * t + h2) + 1.0 / 120.0 * (5 - 18 * t * t + Math.pow(t, 4) + 14 * h2 - 58 * h2 * t * t) * m * m) * m * m) * m);
        yy = yy + 500000;
    }

    public static void GaussPositive_Du(double B, double L, double L0)
    {
        reset();
        double X, t, N, h2, l, m;
        double Bdu, Ldu;
        Bdu =B;
        Ldu =L;
        B = Bdu * PI / 180.0;
        L = Ldu * PI / 180.0;
        l = L - L0 * PI / 180;
        X = a0 * B - Math.sin(B) * Math.cos(B) * ((a2 - a4 + a6) + (2 * a4 - 16.0 / 3.0 * a6) * Math.sin(B) * Math.sin(B) + 16.0 / 3.0 * a6 * Math.pow(Math.sin(B), 4)) + a8 / 8.0 * Math.sin(8 * B);
        t = Math.tan(B);
        h2 = e2 / (1 - e2) * Math.cos(B) * Math.cos(B);
        N = a / Math.sqrt(1 - e2 * Math.sin(B) * Math.sin(B));
        m = Math.cos(B) * l;
        xx = X + N * t * ((0.5 + (1.0 / 24.0 * (5 - t * t + 9 * h2 + 4 * h2 * h2) + 1.0 / 720.0 * (61 - 58 * t * t + Math.pow(t, 4)) * m * m) * m * m) * m * m);
        yy = N * ((1 + (1.0 / 6.0 * (1 - t * t + h2) + 1.0 / 120.0 * (5 - 18 * t * t + Math.pow(t, 4) + 14 * h2 - 58 * h2 * t * t) * m * m) * m * m) * m);
        yy = yy + 500000;
    }


    public static double getXx() {
        return xx;
    }

    public static double getYy() {
        return yy;
    }
}
