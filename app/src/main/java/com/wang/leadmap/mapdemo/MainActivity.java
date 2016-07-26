package com.wang.leadmap.mapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.esri.android.map.MapView;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.hope.leadmap.mapdemo.fourparameter.FourParameterActivity;
import com.hope.leadmap.mapdemo.gaosi_BL_XY.GaosiActivity;
import com.hope.leadmap.mapdemo.sevenparameter.SevenPatameterActivity;

import java.text.DecimalFormat;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private MapView mapView;
    private EditText wkidToEdit, wkidOfEdit, latToEdit, latOfEdit, lonToEdit, lonOfEdit, latMoveEdit, lonMoveEdit;
    private TextView latText, lonText;
    private Button setBut, changeBut, latAddBut, lonAddBut, latMoveAddBut, lonMoveAddBut, moveBut, sevenParamBtn;
    private boolean isMap;
    private boolean islatAdd = true;
    private boolean islonAdd = true;
    private boolean islatMoveAdd = true;
    private boolean islonMoveAdd = true;

    private int WKIDTO;
    private int WKIDOF;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent();
        initUI();
        ActiveAndroid.initialize(this);
    }

    private void initUI() {
        wkidToEdit = (EditText) findViewById(R.id.edit_to);
        wkidOfEdit = (EditText) findViewById(R.id.edit_of);
        latToEdit = (EditText) findViewById(R.id.edit_to_lat);
        latOfEdit = (EditText) findViewById(R.id.edit_of_lat);
        lonToEdit = (EditText) findViewById(R.id.edit_to_lon);
        lonOfEdit = (EditText) findViewById(R.id.edit_of_lon);
        latMoveEdit = (EditText) findViewById(R.id.edit_move_lat);
        lonMoveEdit = (EditText) findViewById(R.id.edit_move_lon);

        latText = (TextView) findViewById(R.id.text_lat);
        lonText = (TextView) findViewById(R.id.text_lon);

        setBut = (Button) findViewById(R.id.but_set_wkid);
        sevenParamBtn = (Button) findViewById(R.id.but_sevenparam);
        latAddBut = (Button) findViewById(R.id.but_lat_add);
        lonAddBut = (Button) findViewById(R.id.but_lon_add);
        changeBut = (Button) findViewById(R.id.but_change);
        latMoveAddBut = (Button) findViewById(R.id.but_move_lat_add);
        lonMoveAddBut = (Button) findViewById(R.id.but_move_lon_add);
        moveBut = (Button) findViewById(R.id.but_move);

        setBut.setOnClickListener(this);

        //隐藏七参数转换按钮
        sevenParamBtn.setVisibility(View.GONE);
        sevenParamBtn.setOnClickListener(this);

        changeBut.setOnClickListener(this);
        latAddBut.setOnClickListener(this);
        lonAddBut.setOnClickListener(this);
        latMoveAddBut.setOnClickListener(this);
        lonMoveAddBut.setOnClickListener(this);
        moveBut.setOnClickListener(this);
    }

    //    private void initMap()
    //    {
    //        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
    //        this.mapView = (MapView)findViewById(R.id.map);
    ////        this.mapServiceLayer = new ArcGISDynamicMapServiceLayer(mapurlEdit.getText().toString());
    //        this.layer = new GraphicsLayer();
    //        this.mapView.addLayer(mapServiceLayer);
    //        this.mapView.addLayer(layer);
    //        this.mapView.setMapBackground(0xffffff, 0xffffff, 10, 1);
    //    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.seven_parameter_activity:
                intent.setClass(MainActivity.this, SevenPatameterActivity.class);
                startActivity(intent);
                break;
            case R.id.four_parameter_activity:
                intent.setClass(MainActivity.this, FourParameterActivity.class);
                startActivity(intent);
                break;
            case R.id.gaosi_activity:
                intent.setClass(MainActivity.this, GaosiActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_set_wkid:

                if (isStrNull(wkidToEdit.getText().toString()) && isStrNull(wkidOfEdit.getText().toString())) {
                    isMap = true;
                    Toast.makeText(this, "坐标系设置完成", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "请输入坐标系ID", Toast.LENGTH_SHORT).show();


                break;
            case R.id.but_change:

                if (isMap) {
                    int wkidto = strToInt(wkidToEdit.getText().toString());
                    int wkidof = strToInt(wkidOfEdit.getText().toString());
                    Point mapPoint = null;
                    double latof = 0.0;
                    double lonof = 0.0;
                    double latto, lonto, lat, lon;
                    if (isStrNull(latToEdit.getText().toString()) && isStrNull(lonToEdit.getText().toString())) {
                        latto = strToDouble(latToEdit.getText().toString());
                        lonto = strToDouble(lonToEdit.getText().toString());

                        if (isStrNull(latOfEdit.getText().toString()))
                            latof = strToDouble(latOfEdit.getText().toString());
                        if (isStrNull(lonOfEdit.getText().toString()))
                            lonof = strToDouble(lonOfEdit.getText().toString());

                        if (islatAdd)
                            lat = latto + latof;
                        else
                            lat = latto - latof;

                        if (islonAdd)
                            lon = lonto + lonof;
                        else
                            lon = lonto - lonof;


                        try {

                            mapPoint = (Point) GeometryEngine.project(new Point(lat, lon)
                                    , SpatialReference.create(wkidto)
                                    , SpatialReference.create(wkidof));
                            DecimalFormat decimalFormat=new DecimalFormat("#.#########");
                            String lat_str=decimalFormat.format(mapPoint.getX());

                            latText.setText(lat_str + "");
                            lonText.setText((mapPoint.getY()) + "");

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"转换失败，检查输入是否正确",Toast.LENGTH_SHORT).show();
                        }

                        //                        -13.8747376725019
                        //                        +15.3531064399984
                    } else
                        Toast.makeText(this, "请输入经纬度", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(this, "请设置转换坐标系", Toast.LENGTH_SHORT).show();


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

                if (isStrNull(latText.getText().toString()) && isStrNull(lonText.getText().toString())) {
                    double lat = strToDouble(latText.getText().toString());
                    double lon = strToDouble(lonText.getText().toString());
                    double latMove = 0.0;
                    double lonMove = 0.0;

                    if (isStrNull(latMoveEdit.getText().toString()))
                        latMove = strToDouble(latMoveEdit.getText().toString());
                    if (isStrNull(lonMoveEdit.getText().toString()))
                        lonMove = strToDouble(lonMoveEdit.getText().toString());

                    if (islatMoveAdd)
                        lat = lat + latMove;
                    else
                        lat = lat - latMove;

                    if (islonMoveAdd)
                        lon = lon + lonMove;
                    else
                        lon = lon - lonMove;

                    latText.setText(lat + "");
                    lonText.setText(lon + "");

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

            case R.id.but_sevenparam:
                startActivity(new Intent(this, MapSevenParamActivity.class));
                break;
        }
    }

    //    /**
    //     * 绘制点
    //     * @param point
    //     */
    //    public void drawPoint(Point point)
    //    {
    //        if (point != null)
    //        {
    //            layer.removeAll();
    //            Drawable drawable = getResources().getDrawable(R.drawable.icon_location_coord);
    //            PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable);
    //            Graphic graphic = new Graphic(point,picSymbol);
    //            layer.addGraphic(graphic);
    //            mapView.centerAt(point,false);
    //
    //        }
    //    }

    private boolean isStrNull(String str) {
        if (str != null && !str.equals(""))
            return true;
        return false;
    }

    private double strToDouble(String str) {
        return Double.valueOf(str);
    }

    private int strToInt(String str) {
        return Integer.parseInt(str);
    }

    //    @Override
    //    public void onStatusChanged(Object o, STATUS status) {
    //        if (status == STATUS.INITIALIZED)//地图初始化成功
    //        {
    //
    //            Toast.makeText(this,"地图初始化成功",Toast.LENGTH_SHORT).show();
    //
    //        }else if (status == STATUS.INITIALIZATION_FAILED)
    //        {
    //            Toast.makeText(this, "地图初始化失败", Toast.LENGTH_SHORT).show();
    //        }
    //    }
}
