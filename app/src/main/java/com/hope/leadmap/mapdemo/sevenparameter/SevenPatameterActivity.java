package com.hope.leadmap.mapdemo.sevenparameter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.core.ACache;
import com.core.ActContent;
import com.core.vo.SevenParams;
import com.wang.leadmap.mapdemo.R;

public class SevenPatameterActivity extends ActionBarActivity implements View.OnClickListener {

    //84参数
    private double a_84 = 6378137;
    private double f_84 = 1 / 298.257223563;
    private double e_84; //椭球第一偏心率
    private double N_84; //卯酉圈曲率半径

    //54参数
    private double a_54 = 6378245;
    private double _b = 6356863.019;
    private double f_54 = 1 / 298.3;
    double _e = 1 / f_54;
    // e1_54为参考椭球的第一偏心率-e
    private double e1_54 = 0;
    // e2_54为参考椭球的第二偏心率-e'
    private double e2_54 = 0;


    //80参数
    //    private double a_54 = 6378140;
    //    private double _b = 6356755.2882;
    //    private double f_54 = 1 / 298.257;
    //    double _e = 1 / f_54;
    //    // e1_54为参考椭球的第一偏心率-e
    //    private double e1_54 = 0;
    //    // e2_54为参考椭球的第二偏心率-e'
    //    private double e2_54 = 0;


    private double ee = 0; // 第一偏心率的平方
    private double ee2 = 0;// 第二偏心率的平方

    //度转弧度
    private double IPI = 0.0174532925199433333333; //3.1415926535898/180.0
    //弧度转度
    private static final double EHU = 180 / Math.PI; // 57.295777937149;

    public Intent intent1;
    private EditText editText_lat;
    private EditText editText_lon;
    private TextView latText, lonText;
    private Button button;
    private Button xian_80_button;
    private Button beijing_54_button;
    private Button latMoveAddBut, lonMoveAddBut, moveBut;
    private EditText latMoveEdit, lonMoveEdit;

    private boolean islatMoveAdd = true;
    private boolean islonMoveAdd = true;

    private double B_put;
    private double L_put;
    private double H_put;

    private double X_84;
    private double Y_84;
    private double Z_84;

    private double X_54;
    private double Y_54;
    private double Z_54;

    private double B_54 = 0;
    private double L_54 = 0;

    private double x_54 = 0;
    private double y_54 = 0;

    private double Px = 0;
    private double Py = 0;
    private double Pz = 0;
    private double Rx = 0;
    private double Ry = 0;
    private double Rz = 0;
    private double K = 0;

    private int middleline = 0;
    private int zonewide = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_patameter);
        editText_lat = (EditText) findViewById(R.id.edit_lat);
        editText_lon = (EditText) findViewById(R.id.edit_lon);
        latMoveEdit = (EditText) findViewById(R.id.edit_move_lat);
        lonMoveEdit = (EditText) findViewById(R.id.edit_move_lon);
        latText = (TextView) findViewById(R.id.text_lat);
        lonText = (TextView) findViewById(R.id.text_lon);
        button = (Button) findViewById(R.id.button_transfer);
        beijing_54_button = (Button) findViewById(R.id.beijing_54);
        beijing_54_button.setOnClickListener(this);
        xian_80_button = (Button) findViewById(R.id.xian_80);
        xian_80_button.setOnClickListener(this);
        latMoveAddBut = (Button) findViewById(R.id.but_move_lat_add);
        latMoveAddBut.setOnClickListener(this);
        lonMoveAddBut = (Button) findViewById(R.id.but_move_lon_add);
        lonMoveAddBut.setOnClickListener(this);
        moveBut = (Button) findViewById(R.id.but_move);
        moveBut.setOnClickListener(this);

        Toast.makeText(this, "默认转为北京54", Toast.LENGTH_SHORT).show();
        intent1 = new Intent();
        button.setOnClickListener(this);

    }

    private void initpara() {
        middleline = ProjectInfo.getMiddleline();
        zonewide = ProjectInfo.getZonewide();
        e_84 = 2 * f_84 - f_84 * f_84; //(a*a-b*b)/(a*a)
        e1_54 = 2 * f_54 - f_54 * f_54;
        e2_54 = Math.pow(Math.sqrt(Math.pow(a_54, 2) - Math.pow(_b, 2)) / _b, 2);
        ee = (2 * _e - 1) / _e / _e;
        ee2 = ee / (1 - ee);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sevenparameter_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.seven_parameter_settings:
                intent1.setClass(SevenPatameterActivity.this, SetSevenParameterActivity.class);
                startActivity(intent1);
                break;
            case R.id.project_settings:
                intent1.setClass(SevenPatameterActivity.this, ProjectActivity.class);
                startActivity(intent1);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 由纬度求解子午线弧长
     *
     * @param B 纬度
     * @param a 长半轴
     * @param f 扁率倒数
     * @return
     */
    public double MeridianLength(double B, double a, double f) {
        double ee = (2 * f - 1) / f / f; // 第一偏心率的平方
        double rB = B * IPI; // 将度转化为弧度
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


    //高斯正算
    private void BLH_54_xy_54(double B, double L) {
        double X, Y = 0;
        // 投影分带的带号
        int beltNum;
        // 经差
        double L00 = 0;
        beltNum = (int) Math.ceil((L - (zonewide == 3 ? 1.5 : 0))
                / zonewide);
        if (zonewide == 3 && beltNum * 3 == L - 1.5) {
            beltNum += 1;
        }
        L00 = L - beltNum * zonewide - (zonewide == 6 ? 3 : 0);

        // 高斯正算-计算
        double rB, tB, m;
        rB = B * IPI;
        tB = Math.tan(rB);
        m = Math.cos(rB) * L00 * IPI;

        double N = a_54 / Math.sqrt(1 - ee * Math.sin(rB) * Math.sin(rB));
        double it2 = ee2 * Math.pow(Math.cos(rB), 2);
        X = m * m / 2 + (5 - tB * tB + 9 * it2 + 4 * it2 * it2)
                * Math.pow(m, 4) / 24 + (61 - 58 * tB * tB + Math.pow(tB, 4))
                * Math.pow(m, 6) / 720;
        X = MeridianLength(B, a_54, _e) + N * tB * X;
        Y = N
                * (m + (1 - tB * tB + it2) * Math.pow(m, 3) / 6 + (5 - 18 * tB
                * tB + Math.pow(tB, 4) + 14 * it2 - 58 * tB * tB * it2)
                * Math.pow(m, 5) / middleline);
        // 换算成假定坐标平移500km前面加带号
        Y += 500000;
        x_54 = X;
        y_54 = Y;
    }

    private void XYZ_54_BLH_54(double x, double y, double z) {
        // θ(单位：度)
        double O = 0;
        O = Math.atan((z * a_54) / (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) * _b)) * EHU;
        L_54 = Math.atan(y / x) * EHU;
        L_54 = (L_54 < 0 ? L_54 + 180 : L_54);
        B_54 = Math.atan((z + e2_54 * _b * Math.pow(Math.sin(O * IPI), 3))
                / ((Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) - (e1_54
                * a_54 * Math.pow(Math.cos(O * IPI), 3)))))
                * EHU;

    }

    private void XYZ_84To_XYZ_54(double x, double y, double z) {
        double Rx1 = 0;
        double Ry1 = 0;
        double Rz1 = 0;

        Rx1 = Rx * IPI / 3600;
        Ry1 = Ry * IPI / 3600;
        Rz1 = Rz * IPI / 3600;

        X_54 = Px + K * x - Ry1 * z + Rz1 * y + x;
        Y_54 = Py + K * y + Rx1 * z - Rz1 * x + y;
        Z_54 = Pz + K * z - Rx1 * y + Ry1 * x + z;
    }


    private void BLH_84To_XYZ_84(double B, double L, double H) {

        L = L * IPI; // 转为弧度
        B = B * IPI; // 转为弧度
        double sinB = Math.sin(B);
        double cosB = Math.cos(B);
        double sinL = Math.sin(L);
        double cosL = Math.cos(L);
        N_84 = a_84 / Math.sqrt(1 - e_84 * Math.pow(sinB, 2));
        X_84 = (N_84 + H) * cosB * cosL;
        Y_84 = (N_84 + H) * cosB * sinL;
        Z_84 = (N_84 * (1 - e_84) + H) * sinB;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.beijing_54:
                a_54 = 6378245;
                _b = 6356863.019;
                f_54 = 1 / 298.3;
                _e = 1 / f_54;
                // e1_54为参考椭球的第一偏心率-e
                e1_54 = 0;
                // e2_54为参考椭球的第二偏心率-e'
                e2_54 = 0;
                Toast.makeText(this, "将得到北京54的平面坐标", Toast.LENGTH_SHORT).show();
                break;
            case R.id.xian_80:
                a_54 = 6378140;
                _b = 6356755.2882;
                f_54 = 1 / 298.257;
                _e = 1 / f_54;
                // e1_54为参考椭球的第一偏心率-e
                double e1_54 = 0;
                // e2_54为参考椭球的第二偏心率-e'
                double e2_54 = 0;
                Toast.makeText(this, "将得到西安80的平面坐标", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_transfer:
                if (TextUtils.isEmpty(editText_lat.getText()) || TextUtils.isEmpty(editText_lon.getText())) {
                    Toast.makeText(SevenPatameterActivity.this, "坐标不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    B_put = Double.valueOf(editText_lat.getText().toString());
                    L_put = Double.valueOf(editText_lon.getText().toString());
                    H_put = 0.0;

                    initpara();

                    if (select()!=null){
                        SevenParams params=select();
                        Px = params.Px;
                        Py = params.Py;
                        Pz = params.Pz;
                        Rx = params.Rx;
                        Ry = params.Ry;
                        Rz = params.Rz;
                        K = params.K;
                    }else {
                        Toast.makeText(SevenPatameterActivity.this, "尚未填写七参数", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (zonewide == 0 && middleline == 0) {
                        Toast.makeText(SevenPatameterActivity.this, "尚未填写投影信息", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    BLH_84To_XYZ_84(B_put, L_put, H_put);

                    XYZ_84To_XYZ_54(X_84, Y_84, Z_84);

                    XYZ_54_BLH_54(X_54, Y_54, Z_54);

                    BLH_54_xy_54(B_54, L_54);

                    latText.setText(String.valueOf(x_54));
                    lonText.setText(String.valueOf(y_54));
                }

                break;

            case R.id.but_move_lat_add:

                islatMoveAdd = !islatMoveAdd;
                if (islatMoveAdd)
                    latMoveAddBut.setText("+");
                else
                    latMoveAddBut.setText("-");

                break;
            case R.id.but_move_lon_add:

                islonMoveAdd = !islonMoveAdd;
                if (islonMoveAdd)
                    lonMoveAddBut.setText("+");
                else
                    lonMoveAddBut.setText("-");

                break;

            case R.id.but_move:

                if (!TextUtils.isEmpty(latText.getText().toString()) && !TextUtils.isEmpty(lonText.getText().toString())) {

                    double lat =0.0,lat1=0.0,lon=0.0,lon1=0.0;
                    double latMove = 0.0;
                    double lonMove = 0.0;
                    lon1=Double.valueOf(latText.getText().toString());
                    lat1 = Double.valueOf(lonText.getText().toString());

                    lonMove = Double.valueOf(latMoveEdit.getText().toString().isEmpty() ? "0" : latMoveEdit.getText().toString());
                    latMove = Double.valueOf(lonMoveEdit.getText().toString().isEmpty() ? "0" : lonMoveEdit.getText().toString());

                    if (islonMoveAdd)
                        lat = lat1 + latMove;
                    else
                        lat = lat1 - latMove;

                    if (islatMoveAdd)
                        lon = lon1 + lonMove;
                    else
                        lon = lon1 - lonMove;

                    latText.setText(lon + "");
                    lonText.setText(lat + "");

                } else
                    Toast.makeText(this, "请先转换坐标，再偏移", Toast.LENGTH_SHORT).show();

                break;


        }
    }


    private SevenParams select(){
        ACache cache=ACache.get(this);
        SevenParams sevenParams=(SevenParams) cache.getAsObject(ActContent.SEVENPARAMS);
        return sevenParams;
    }
}
