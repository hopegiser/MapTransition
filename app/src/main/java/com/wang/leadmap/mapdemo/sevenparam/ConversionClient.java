package com.wang.leadmap.mapdemo.sevenparam;

/**
 * ====================================================
 * 
 * @Copyright (C) 2012-2013 杭州领图信息科技有限公司
 * @All rights reserved
 * @filename :ConversionClient.java
 * @date 2014-4-3
 * @time 上午10:12:59
 * @author 汪家栋
 * @description：
 * 
 * @---------------代码维护与版本信息---------------------------
 * @版本：V1.0 编写人：汪家栋 描述：第一次创建
 * 
 * 
 * @=====================================================
 */

public class ConversionClient {

	private IConversion _iConversion;

	public ConversionClient(CoordConversion c) {
		this._iConversion = c;
	}

	public double[] coordChangeTool(double L, double B, double H) {
		double[] res_xy = { 0.0, 0.0 };
		// 同一坐标系，经纬度TO空间直角
		double[] beforeXyz = this._iConversion.LatLonToXyz(L, B, H);
		// 不同坐标系空间直角坐标转换（七参数）
		double[] afterXyz = this._iConversion.CoordToOther(beforeXyz);
		// 同一坐标系，空间直角To经纬度
		double[] afterLbh = this._iConversion.XyzToLatLon(afterXyz);
		// 高斯投影获得平面坐标
		res_xy = this._iConversion.GaussTool(afterLbh);

		return res_xy;
	}

}
