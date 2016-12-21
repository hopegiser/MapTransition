package com.hope.leadmap.mapdemo.gaosi_BL_XY;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.core.vo.GpsWgs;
import com.utils.BdLocChangeCore;
import com.wang.leadmap.mapdemo.R;

import java.text.DecimalFormat;

public class GaosiActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText wkidToEdit, latToEdit, latOfEdit, lonToEdit, lonOfEdit, latMoveEdit, lonMoveEdit;
    private TextView latText, lonText;
    private Button setBut, changeBut, latAddBut, lonAddBut, latMoveAddBut, lonMoveAddBut, moveBut;
    private Button beijing_54_but,xian_80_but,cgcs_200;

    private boolean islatAdd = true;
    private boolean islonAdd = true;
    private boolean islatMoveAdd = true;
    private boolean islonMoveAdd = true;

    private double B_put;
    private double L_put;
    private double middleline_put;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaosi);
        Toast.makeText(this,"默认转为北京54",Toast.LENGTH_SHORT).show();
        initUi();
    }

    private void initUi() {
        wkidToEdit = (EditText) findViewById(R.id.edit_middleline);

        latToEdit = (EditText) findViewById(R.id.edit_to_lat);
        latOfEdit = (EditText) findViewById(R.id.edit_of_lat);
        lonToEdit = (EditText) findViewById(R.id.edit_to_lon);
        lonOfEdit = (EditText) findViewById(R.id.edit_of_lon);

        latMoveEdit = (EditText) findViewById(R.id.edit_move_lat);
        lonMoveEdit = (EditText) findViewById(R.id.edit_move_lon);

        latText = (TextView) findViewById(R.id.text_lat);
        lonText = (TextView) findViewById(R.id.text_lon);

        setBut = (Button) findViewById(R.id.but_set_middle_line);
        latAddBut = (Button) findViewById(R.id.but_lat_add);
        lonAddBut = (Button) findViewById(R.id.but_lon_add);

        changeBut = (Button) findViewById(R.id.but_change);

        latMoveAddBut = (Button) findViewById(R.id.but_move_lat_add);
        lonMoveAddBut = (Button) findViewById(R.id.but_move_lon_add);
        moveBut = (Button) findViewById(R.id.but_move);
        beijing_54_but=(Button)findViewById(R.id.but_beijing_54);
        xian_80_but=(Button)findViewById(R.id.but_xian_80);
        cgcs_200=(Button)findViewById(R.id.but_cgcs_2000);


        setBut.setOnClickListener(this);
        changeBut.setOnClickListener(this);
        latAddBut.setOnClickListener(this);
        lonAddBut.setOnClickListener(this);
        latMoveAddBut.setOnClickListener(this);
        lonMoveAddBut.setOnClickListener(this);
        moveBut.setOnClickListener(this);
        beijing_54_but.setOnClickListener(this);
        xian_80_but.setOnClickListener(this);
        cgcs_200.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_set_middle_line:

                if (!TextUtils.isEmpty(wkidToEdit.getText().toString())) {
                    middleline_put = Double.valueOf(wkidToEdit.getText().toString());
                    Toast.makeText(this, "中央经线设置完成", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "请输入中央经线", Toast.LENGTH_SHORT).show();
                break;
            case R.id.but_change:

                if (TextUtils.isEmpty(wkidToEdit.getText().toString())){
                    Toast.makeText(this, "请输入中央经线", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    middleline_put = Double.valueOf(wkidToEdit.getText().toString());
                }
                if (TextUtils.isEmpty(latToEdit.getText()) || TextUtils.isEmpty(lonToEdit.getText())) {
                    Toast.makeText(GaosiActivity.this, "坐标不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    B_put = Double.valueOf(latToEdit.getText().toString());
                    L_put = Double.valueOf(lonToEdit.getText().toString());

                    GpsWgs gpsWgs= BdLocChangeCore.bd09ToWGSExactly(B_put,L_put);


                    double latof=0.0;
                    double lonof=0.0;

                    latof = Double.valueOf(latOfEdit.getText().toString().isEmpty() ? "0":latOfEdit.getText().toString());
                    lonof = Double.valueOf(lonOfEdit.getText().toString().isEmpty() ? "0":lonOfEdit.getText().toString());
                    double latto;
                    double lonto;

                    if (islatAdd){
                        latto = gpsWgs.getWgLat() + latof;
                    }

                    else{
                        latto = gpsWgs.getWgLon() - latof;
                    }

                    if (islonAdd)
                        lonto = L_put + lonof;
                    else
                        lonto = L_put - lonof;
                    if (TextUtils.isEmpty(wkidToEdit.getText().toString())){
                        Toast.makeText(GaosiActivity.this,"中央经线不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    GaosiTool.GaussPositive_Du(latto, lonto, middleline_put);
                    DecimalFormat decimalFormat = new DecimalFormat("#.#########");
                    String lat_str = decimalFormat.format(GaosiTool.xx);
                    String lon_str = decimalFormat.format(GaosiTool.yy);
                    latText.setText(lon_str);
                    lonText.setText(lat_str);
                }

                break;
            case R.id.but_lat_add:

                islatAdd = !islatAdd;
                if (islatAdd)
                    latAddBut.setText("+");
                else
                    latAddBut.setText("-");
                break;
            case R.id.but_lon_add:

                islonAdd = !islonAdd;
                if (islonAdd)
                    lonAddBut.setText("+");
                else
                    lonAddBut.setText("-");
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

            case R.id.but_beijing_54:
                GaosiTool.beijing_54();
                Toast.makeText(this,"将得到北京54的平面坐标",Toast.LENGTH_SHORT).show();
                break;

            case R.id.but_xian_80:
                GaosiTool.xian_80();
                Toast.makeText(this,"将得到西安80的平面坐标",Toast.LENGTH_SHORT).show();
                break;

            case R.id.but_cgcs_2000:
                GaosiTool.cgcs_2000();
                Toast.makeText(this,"将得到国家2000的平面坐标",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
