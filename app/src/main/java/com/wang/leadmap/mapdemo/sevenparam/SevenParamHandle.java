package com.wang.leadmap.mapdemo.sevenparam;

/**
 * Created by wang on 15/10/16.
 */
public class SevenParamHandle {

    private  static final SevenParamHandle self = new SevenParamHandle();
    private MapSevenParam mapSevenParam;
    private double meridan;
    private int d;
    private BeijingParam param;
    private SevenParamHandle(){}

    public static SevenParamHandle initHandle() {
        return self;
    }

    public MapSevenParam getMapSevenParam() {
        return mapSevenParam;
    }

    public void setMapSevenParam(MapSevenParam mapSevenParam) {
        this.mapSevenParam = mapSevenParam;
    }

    public double getMeridan() {
        return meridan;
    }

    public void setMeridan(double meridan) {
        this.meridan = meridan;
    }

    public BeijingParam getParam() {
        return param;
    }

    public void setParam(BeijingParam param) {
        this.param = param;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }
}
