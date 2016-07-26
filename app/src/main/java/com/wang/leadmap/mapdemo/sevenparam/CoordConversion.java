package com.wang.leadmap.mapdemo.sevenparam;

/**
 * ====================================================
 * 
 * @Copyright (C) 2012-2013 杭州领图信息科技有限公司
 * @All rights reserved
 * @filename :CoordConversion.java
 * @date 2014-4-3
 * @time 上午10:05:41
 * @author 汪家栋
 * @description：
 * 
 * @---------------代码维护与版本信息---------------------------
 * @版本：V1.0 编写人：汪家栋 描述：第一次创建
 * 
 * 
 * @=====================================================
 */

public abstract class CoordConversion implements IConversion {

	// ------------需要转换的坐标系参数--------
	// 七参数
	protected double XT = 0.0;
	protected double YT = 0.0;
	protected double ZT = 0.0;
	protected double XR = 0.0;
	protected double YR = 0.0;
	protected double ZR = 0.0;
	protected double K = 0.0;
	// 长轴，短轴，扁率
	protected double _a = 0.0;
	protected double _b = 0.0;
	protected double _f = 0.0;
	protected double _e = 0.0;
	// 带宽
	protected double _beltWidth = 0.0;

	// -----------------84坐标系参数------------------
	private static final double WGS_F = 1 / 298.257223563;
	private static final double WGS_A = 6378137.0;

	// 中央子午线
	protected double MERIDIAN = 0.0;
	// 1弧度=57.295777937149度
	private static final double EHU = 180 / Math.PI; // 57.295777937149;

	/**
	 * 大地转空间直角
	 */
	@Override
	public double[] LatLonToXyz(double L, double B, double H) {

		double[] xyz = new double[3];
		double X, Y, Z;

		// 卯酉圈曲率半径
		double N = 0;
		// e1为参考椭球的第一偏心率-e
		// e2为参考椭球的第二偏心率-e'
		double e2 = 0;

		e2 = 2 * WGS_F - WGS_F * WGS_F;
		N = WGS_A / (Math.sqrt(1.0 - e2 * Math.pow(this.Sin(B), 2)));

		X = (N + H) * this.Cos(B) * this.Cos(L);
		Y = (N + H) * this.Cos(B) * this.Sin(L);
		Z = (N * (1 - e2) + H) * this.Sin(B);

		xyz[0] = X;
		xyz[1] = Y;
		xyz[2] = Z;

		return xyz;
	}

	@Override
	public double[] CoordToOther(double[] xyz) {
		double[] other_xyz = new double[3];
		double X1, Y1, Z1;

		// 秒转换成弧度（七参数的旋转参数）
		XR = GetHudu(XR);
		YR = GetHudu(YR);
		ZR = GetHudu(ZR);

		X1 = XT + (K * xyz[0])
				+ ((1 * xyz[0]) + (ZR * xyz[1]) + (-YR * xyz[2]));
		Y1 = YT + (K * xyz[1])
				+ ((-ZR * xyz[0]) + (1 * xyz[1]) + (XR * xyz[2]));
		Z1 = ZT + (K * xyz[2])
				+ ((YR * xyz[0]) + (-XR * xyz[1]) + (1 * xyz[2]));

		other_xyz[0] = X1;
		other_xyz[1] = Y1;
		other_xyz[2] = Z1;

		return other_xyz;
	}

	@Override
	public double[] XyzToLatLon(double[] xyz) {
		double[] lb = new double[2];
		double B, L = 0;

		// θ(单位：度)
		double O = 0;
		// e1为参考椭球的第一偏心率-e
		double e1 = 0;
		// e2为参考椭球的第二偏心率-e'
		double e2 = 0;

		_f = 1 / _e;
		e1 = 2 * _f - _f * _f;

		e2 = Math.pow(Math.sqrt(Math.pow(_a, 2) - Math.pow(_b, 2)) / _b, 2);

		O = Math.atan((xyz[2] * _a)
				/ (Math.sqrt(Math.pow(xyz[0], 2) + Math.pow(xyz[1], 2)) * _b))
				* EHU;

		L = Math.atan(xyz[1] / xyz[0]) * EHU;
		L = (L < 0 ? L + 180 : L);

		B = Math.atan((xyz[2] + e2 * _b * Math.pow(this.Sin(O), 3))
				/ ((Math.sqrt(Math.pow(xyz[0], 2) + Math.pow(xyz[1], 2)) - (e1
						* _a * Math.pow(this.Cos(O), 3)))))
				* EHU;

		lb[0] = L;
		lb[1] = B;

		return lb;
	}

	@Override
	public double[] GaussTool(double[] latlon) {
		double[] xy = new double[2];
		double X, Y = 0;
		double L = latlon[0];
		double B = latlon[1];

		// 投影分带的带号
		int beltNum;
		// 经差
		double L0 = 0;

		beltNum = (int) Math.ceil((L - (_beltWidth == 3 ? 1.5 : 0))
				/ _beltWidth);

		if (_beltWidth == 3 && beltNum * 3 == L - 1.5) {
			beltNum += 1;
		}

		L0 = L - beltNum * _beltWidth - (_beltWidth == 6 ? 3 : 0);

		// 高斯正算-计算
		double ee = (2 * _e - 1) / _e / _e; // 第一偏心率的平方
		double ee2 = ee / (1 - ee); // 第二偏心率的平方
		double rB, tB, m;
		rB = B * Math.PI / 180;
		tB = Math.tan(rB);
		m = Math.cos(rB) * L0 * Math.PI / 180;

		double N = _a / Math.sqrt(1 - ee * Math.sin(rB) * Math.sin(rB));
		double it2 = ee2 * Math.pow(Math.cos(rB), 2);
		X = m * m / 2 + (5 - tB * tB + 9 * it2 + 4 * it2 * it2)
				* Math.pow(m, 4) / 24 + (61 - 58 * tB * tB + Math.pow(tB, 4))
				* Math.pow(m, 6) / 720;
		X = MeridianLength(B, _a, _e) + N * tB * X;
		Y = N
				* (m + (1 - tB * tB + it2) * Math.pow(m, 3) / 6 + (5 - 18 * tB
						* tB + Math.pow(tB, 4) + 14 * it2 - 58 * tB * tB * it2)
						* Math.pow(m, 5) / MERIDIAN);

		// 换算成假定坐标平移500km前面加带号

		Y += 500000;

		xy[0] = X;
		xy[1] = Y;

		return xy;
	}

	/**秒转弧度
	 * 
	 * @param Miao  秒
	 * @return
	 */
	private double GetHudu(double Miao) {
		double value = 0;
		// 1秒=0.0002777778度
		// 1度=0.017453293弧度
		value = (Miao / 3600) / 57.29578;
		return value;
	}

	/**
	 * 获取 度 to 弧度 并 Cos
	 * 
	 * @param Degrees
	 *            度数
	 * @return
	 */
	private double Cos(double Degrees) {
		double value = 0;

		value = Math.cos(Degrees * Math.PI / 180);

		return value;
	}

	/**
	 * 获取 度 to 弧度 并 Sin
	 * 
	 * @param Degrees
	 *            度数
	 * @return
	 */
	private double Sin(double Degrees) {
		double value = 0;

		value = Math.sin(Degrees * Math.PI / 180);

		return value;
	}

	/**
	 * 由纬度求解子午线弧长
	 * 
	 * @param B
	 *            纬度
	 * @param a
	 *            长半轴
	 * @param f
	 *            扁率倒数
	 * @return
	 */
	public double MeridianLength(double B, double a, double f) {
		double ee = (2 * f - 1) / f / f; // 第一偏心率的平方
		double rB = B * Math.PI / 180; // 将度转化为弧度
		// 子午线弧长公式的系数
		double cA, cB, cC, cD, cE;
		cA = 1 + 3 * ee / 4 + 45 * Math.pow(ee, 2) / 64 + 175 * Math.pow(ee, 3)
				/ 256 + 11025 * Math.pow(ee, 4) / 16384;
		cB = 3 * ee / 4 + 15 * Math.pow(ee, 2) / 16 + 525 * Math.pow(ee, 3)
				/ 512 + 2205 * Math.pow(ee, 4) / 2048;
		cC = 15 * Math.pow(ee, 2) / 64 + 105 * Math.pow(ee, 3) / 256 + 2205
				* Math.pow(ee, 4) / 4096;
		cD = 35 * Math.pow(ee, 3) / 512 + 315 * Math.pow(ee, 4) / 2048;
		cE = 315 * Math.pow(ee, 4) / 131072;
		// 子午线弧长
		return a
				* (1 - ee)
				* (cA * rB - cB * Math.sin(2 * rB) / 2 + cC * Math.sin(4 * rB)
						/ 4 - cD * Math.sin(6 * rB) / 6 + cE * Math.sin(8 * rB)
						/ 8);
	}

}
