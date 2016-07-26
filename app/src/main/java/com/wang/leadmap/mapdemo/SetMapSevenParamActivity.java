package com.wang.leadmap.mapdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wang.leadmap.mapdemo.sevenparam.MapSevenParam;
import com.wang.leadmap.mapdemo.sevenparam.SevenParamHandle;

/**
 * Created by wang on 15/10/16.
 */
public class SetMapSevenParamActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText dxEdit,dyEdit,dzEdit,wxEdit,wyEdit,wzEdit,kEdit;
    private Button setBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_set_seven_param);

        initEdit();
        setBtn = (Button)findViewById(R.id.set_but);
        setBtn.setOnClickListener(this);
    }


    private void initEdit()
    {
        dxEdit = (EditText)findViewById(R.id.dx_edit);
        dyEdit = (EditText)findViewById(R.id.dy_edit);
        dzEdit = (EditText)findViewById(R.id.dz_edit);
        wxEdit = (EditText)findViewById(R.id.wx_edit);
        wyEdit = (EditText)findViewById(R.id.wy_edit);
        wzEdit = (EditText)findViewById(R.id.wz_edit);
        kEdit = (EditText)findViewById(R.id.k_edit);
        if (SevenParamHandle.initHandle().getMapSevenParam() != null)
        {
            dxEdit.setText(SevenParamHandle.initHandle().getMapSevenParam().PanX);
            dyEdit.setText(SevenParamHandle.initHandle().getMapSevenParam().PanY);
            dzEdit.setText(SevenParamHandle.initHandle().getMapSevenParam().PanZ);
            wxEdit.setText(SevenParamHandle.initHandle().getMapSevenParam().RotationX);
            wyEdit.setText(SevenParamHandle.initHandle().getMapSevenParam().RotationY);
            wzEdit.setText(SevenParamHandle.initHandle().getMapSevenParam().RotationZ);
            kEdit.setText(SevenParamHandle.initHandle().getMapSevenParam().K);
        }else
        {
//            dxEdit.setText(199.752086+"");
//            dyEdit.setText(127.567109+"");
//            dzEdit.setText(-0.82967+"");
//            wxEdit.setText(-1.5501418983888+"");
//            wyEdit.setText(2.00974113966859+"");
//            wzEdit.setText(-2.9319923413606+"");
//            kEdit.setText(-1.205544e-006+"");

            dxEdit.setText(-46.907088+"");
            dyEdit.setText(77.10001+"");
            dzEdit.setText(55.436015+"");
            wxEdit.setText(0.931079335399394+"");
            wyEdit.setText(-0.302528591322617+"");
            wzEdit.setText(2.10080705162668+"");
            kEdit.setText(4.48064e-007+"");




        }
    }

    @Override
    public void onClick(View v) {
        MapSevenParam param = new MapSevenParam();
        param.PanX = dxEdit.getText().toString();
        param.PanY = dyEdit.getText().toString();
        param.PanZ = dzEdit.getText().toString();
        param.RotationX = wxEdit.getText().toString();
        param.RotationY = wyEdit.getText().toString();
        param.RotationZ = wzEdit.getText().toString();
        param.K = kEdit.getText().toString();
        SevenParamHandle.initHandle().setMapSevenParam(param);
        finish();
    }
}
