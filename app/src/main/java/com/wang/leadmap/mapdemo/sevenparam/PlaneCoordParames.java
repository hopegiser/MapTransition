package com.wang.leadmap.mapdemo.sevenparam;


/**
 * =================================================
 * 
 * @Copyright (C) 杭州领图信息科技有限公司
 * @All rights reserved
 * @filename :PlaneCroodParames.java
 * @date :2014年9月22日
 * @time :上午10:12:13
 * @author :汪家栋
 * @description:平面坐标系参数
 * 
 * @-----------代码维护和版本信息------------
 * @version ： V2.9.9
 * @Maintain ： 汪家栋
 * @description：
 **/
public class PlaneCoordParames extends CoordConversion {


	public PlaneCoordParames() {
        MapSevenParam param = SevenParamHandle.initHandle().getMapSevenParam();
        if (param != null)
        {

            // 七参数
            this.XT = strToDouble(param.PanX);
            this.YT = strToDouble(param.PanY);
            this.ZT = strToDouble(param.PanZ);
            this.XR = strToDouble(param.RotationX);
            this.YR = strToDouble(param.RotationY);
            this.ZR = strToDouble(param.RotationZ);
            this.K = strToDouble(param.K);
            // 带宽
        }

        BeijingParam beijingParam = SevenParamHandle.initHandle().getParam();
        if (beijingParam != null)
        {
            // 萧山的长短轴和扁率
            this._a = beijingParam.A;
            this._b = beijingParam.B;
            this._e = beijingParam.E;
        }

        this.MERIDIAN = SevenParamHandle.initHandle().getMeridan();
        this._beltWidth = SevenParamHandle.initHandle().getD();

	}

    private double strToDouble(String str)
    {
        return Double.parseDouble(str);
    }

}
