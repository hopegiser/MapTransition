package com.hope.leadmap.mapdemo.fourparameter;

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
import com.core.vo.FourParams;
import com.wang.leadmap.mapdemo.R;

public class FourParameterActivity extends ActionBarActivity {

    private EditText editText_lat;
    private EditText editText_lon;
    private TextView textView_result;
    private Button button;

    private double x_put;
    private double y_put;
    private double x_out;
    private double y_out;


    private static double dx = 0;
    private static double dy = 0;
    private static double red = 0;
    private static double k = 0;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_parameter);
        editText_lat = (EditText) findViewById(R.id.edit_lat);
        editText_lon = (EditText) findViewById(R.id.edit_lon);
        textView_result = (TextView) findViewById(R.id.text_result);
        button = (Button) findViewById(R.id.button_transfer);
        intent = new Intent();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText_lat.getText()) || TextUtils.isEmpty(editText_lon.getText())) {
                    Toast.makeText(FourParameterActivity.this, "坐标不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (select()!=null) {
                        FourParams fourParams=select();
                        dx =fourParams.dx;
                        dy = fourParams.dy;
                        red = fourParams.red;
                        k = fourParams.k;
                    } else {
                        Toast.makeText(FourParameterActivity.this, "四参数尚未设置", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    init();
                    //坐标转换
                    transfer();
                }

            }
        });
    }

    private void init() {
        try {
            x_put = Double.valueOf(editText_lat.getText().toString());
            y_put = Double.valueOf(editText_lon.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void transfer() {

        x_out = x_put * k * Math.cos(red / 180 * Math.PI) - y_put * k * Math.sin(red / 180 * Math.PI) + dx;
        y_out = x_put * k * Math.sin(red / 180 * Math.PI) + y_put * k * Math.cos(red / 180 * Math.PI) + dy;
        textView_result.setText("X坐标：" + x_out + "\n" + "Y坐标：" + y_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fourparameter_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent.setClass(FourParameterActivity.this, SetFourParameterActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private FourParams select(){
        ACache cache=ACache.get(this);
        com.core.vo.FourParams fourParams=(FourParams)cache.getAsObject(ActContent.FOURPARAMS);
        return fourParams;
    }
}

