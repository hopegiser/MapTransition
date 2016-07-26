package com.wang.leadmap.mapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.core.geometry.Point;
import com.wang.leadmap.mapdemo.sevenparam.BeijingParam;
import com.wang.leadmap.mapdemo.sevenparam.ConversionClient;
import com.wang.leadmap.mapdemo.sevenparam.PlaneCoordParames;
import com.wang.leadmap.mapdemo.sevenparam.SevenParamHandle;

/**
 * Created by wang on 15/10/16.
 */
public class MapSevenParamActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText xEdit,yEdit,wEdit,dEdit;
    private Spinner typeSpinner;
    private Button tanrBtn,setBtn;
    private TextView xText,yText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_seven_param);
        xEdit = (EditText)findViewById(R.id.x_edit);
        yEdit = (EditText)findViewById(R.id.y_edit);
        wEdit = (EditText)findViewById(R.id.w_edit);
        dEdit = (EditText)findViewById(R.id.d_edit);

        xText = (TextView)findViewById(R.id.x_text);
        yText = (TextView)findViewById(R.id.y_text);

        initSpinner();
        tanrBtn = (Button)findViewById(R.id.tran_btn);
        setBtn = (Button)findViewById(R.id.set_seven_btn);
        tanrBtn.setOnClickListener(this);
        setBtn.setOnClickListener(this);

    }

    private void initSpinner()
    {
        typeSpinner = (Spinner)findViewById(R.id.type_spinner);
        String[] types = getResources().getStringArray(R.array.types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_text_item, types);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tran_btn:
                if (TextUtils.isEmpty(xEdit.getText().toString()))
                {
                    Toast.makeText(this,"横坐标不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(xEdit.getText().toString()))
                {
                    Toast.makeText(this,"纵坐标不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(xEdit.getText().toString()))
                {
                    Toast.makeText(this,"中央经线不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(dEdit.getText().toString()))
                {
                    Toast.makeText(this,"度带不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (SevenParamHandle.initHandle().getMapSevenParam() == null)
                {
                    Toast.makeText(this,"还没有设置七参数",Toast.LENGTH_SHORT).show();
                    return;
                }

                SevenParamHandle.initHandle().setMeridan(Double.parseDouble(wEdit.getText().toString()));
                SevenParamHandle.initHandle().setD(Integer.parseInt(dEdit.getText().toString()));
                SevenParamHandle.initHandle().setParam(initParam(typeSpinner.getSelectedItemPosition()));

                ConversionClient client = new ConversionClient(new PlaneCoordParames());
                double[] xy = client.coordChangeTool(Double.parseDouble(xEdit.getText().toString()), Double.parseDouble(yEdit.getText().toString()), 0.0);
                xText.setText("X: "+xy[1]);
                yText.setText("Y: "+xy[0]);

//                Point mapPoint = new Point(xy[1], xy[0]);
                break;
            case R.id.set_seven_btn:
                startActivity(new Intent(this,SetMapSevenParamActivity.class));
                break;
        }


    }

    private BeijingParam initParam(int tag)
    {
        BeijingParam param = new BeijingParam();
        switch (tag)
        {
            case 0:
                param.A = 6378245.0;
                param.B = 6356863.0188;
                param.E = 1/298.3;
                break;
            case 1:
                param.A = 6378140.0;
                param.B = 6356755.2882;
                param.E = 1/298.257;
                break;
        }
        return param;
    }
}
