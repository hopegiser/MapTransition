package com.wang.leadmap.mapdemo.sevenparam;

/**
 * ====================================================
 * 
 * @Copyright (C) 2012-2013 杭州领图信息科技有限公司
 * @All rights reserved
 * @filename :IConversion.java
 * @date 2014-4-3
 * @time 上午9:49:42
 * @author 汪家栋
 * @description：
 * 
 * @---------------代码维护与版本信息---------------------------
 * @版本：V1.0 编写人：汪家栋 描述：第一次创建
 * 
 * 
 * @=====================================================
 */

public interface IConversion {

	/**
	 * 同一坐标系下，经纬度坐标转换为空间直角坐标系
	 * 
	 * @param L
	 *            经度
	 * @param B
	 *            纬度
	 * @param H
	 *            大地高
	 * @return
	 */
	public double[] LatLonToXyz(double L, double B, double H);

	/**
	 * 不同坐标系下 空间直角坐标转换（如：84空间直角转54空间直角）通过七参数
	 * 
	 * @param xyz
	 *            转换过的空间直角坐标系
	 * @return
	 */
	public double[] CoordToOther(double[] xyz);

	/**
	 * 同一坐标系下，将空间直角转经纬度
	 * 
	 * @param xyz
	 *            转换过的另一个坐标系的空间直角坐标
	 * @return
	 */
	public double[] XyzToLatLon(double[] xyz);

	/**
	 * 通过高斯投影将经纬度转换为平面坐标
	 * 
	 * @param latlon
	 *            经纬度
	 * @return
	 */
	public double[] GaussTool(double[] latlon);

}
