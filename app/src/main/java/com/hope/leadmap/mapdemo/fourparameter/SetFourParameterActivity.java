package com.hope.leadmap.mapdemo.fourparameter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.core.ACache;
import com.core.ActContent;
import com.wang.leadmap.mapdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetFourParameterActivity extends ActionBarActivity {

    private double dx,dy,red,k;
    private com.core.vo.FourParams fourParams;

    @Bind(R.id.pan_x_edit)
    EditText panXEdit;
    @Bind(R.id.pan_y_edit)
    EditText panYEdit;
    @Bind(R.id.roat_x_edit)
    EditText roatXEdit;
    @Bind(R.id.k_edit)
    EditText kEdit;
    @Bind(R.id.ok_bt)
    Button okBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_four_parameter);
        ButterKnife.bind(this);
        ACache cache=ACache.get(this);
        fourParams=(com.core.vo.FourParams)cache.getAsObject(ActContent.FOURPARAMS);
        if (fourParams!=null){
            panXEdit.setText(String.valueOf(fourParams.dx));
            panYEdit.setText(String.valueOf(fourParams.dy));
            roatXEdit.setText(String.valueOf(fourParams.red));
            kEdit.setText(String.valueOf(fourParams.k));
        }

        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACache cache=ACache.get(SetFourParameterActivity.this);
                if (!TextUtils.isEmpty(panXEdit.getText()) || !TextUtils.isEmpty(panYEdit.getText()) ||
                        !TextUtils.isEmpty(roatXEdit.getText()) || !TextUtils.isEmpty(kEdit.getText())){
                    initFour();

                    if (fourParams==null){
                        fourParams=new com.core.vo.FourParams();
                        fourParams.dx=dx;
                        fourParams.dy=dy;
                        fourParams.red=red;
                        fourParams.k=k;
                        cache.put(ActContent.FOURPARAMS,fourParams);
                    }else {
                        fourParams=(com.core.vo.FourParams)cache.getAsObject(ActContent.FOURPARAMS);
                    }
                    Toast.makeText(SetFourParameterActivity.this,"参数设置完成",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(SetFourParameterActivity.this,"参数不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    private void initFour() {
        this.dx=Double.valueOf(panXEdit.getText().toString());
        this.dy=Double.valueOf(panYEdit.getText().toString());
        this.red=Double.valueOf(roatXEdit.getText().toString());
        this.k=Double.valueOf(kEdit.getText().toString());
    }

}
