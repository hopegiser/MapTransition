package com.hope.leadmap.mapdemo.sevenparameter;

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

public class SetSevenParameterActivity extends ActionBarActivity {

    private String msg_str;
    private Toast toast;
    private com.core.vo.SevenParams sevenParam;
    private double px,py,pz,rx,ry,rz,k;
    @Bind(R.id.pan_x_edit)
    EditText panXEdit;
    @Bind(R.id.pan_y_edit)
    EditText panYEdit;
    @Bind(R.id.pan_z_edit)
    EditText panZEdit;
    @Bind(R.id.roat_x_edit)
    EditText roatXEdit;
    @Bind(R.id.roat_y_edit)
    EditText roatYEdit;
    @Bind(R.id.roat_z_edit)
    EditText roatZEdit;
    @Bind(R.id.k_edit)
    EditText kEdit;
    @Bind(R.id.ok_bt)
    Button okBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_seven_parameter);
        ButterKnife.bind(this);
        ACache cache=ACache.get(this);
        sevenParam= (com.core.vo.SevenParams) cache.getAsObject(ActContent.SEVENPARAMS);
        if (sevenParam!=null){
            panXEdit.setText(String.valueOf(sevenParam.Px));
            panYEdit.setText(String.valueOf(sevenParam.Py));
            panZEdit.setText(String.valueOf(sevenParam.Pz));
            roatXEdit.setText(String.valueOf(sevenParam.Rx));
            roatYEdit.setText(String.valueOf(sevenParam.Ry));
            roatZEdit.setText(String.valueOf(sevenParam.Rz));
            kEdit.setText(String.valueOf(sevenParam.K));
        }

        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACache cache=ACache.get(SetSevenParameterActivity.this);
                if (!TextUtils.isEmpty(panXEdit.getText())
                        && !TextUtils.isEmpty(panYEdit.getText())
                        && !TextUtils.isEmpty(panZEdit.getText())
                        && !TextUtils.isEmpty(roatXEdit.getText())
                        && !TextUtils.isEmpty(roatYEdit.getText())
                        && !TextUtils.isEmpty(roatZEdit.getText())
                        && !TextUtils.isEmpty(kEdit.getText())) {
                    initSeven();

                    if (sevenParam==null){
                        sevenParam=new com.core.vo.SevenParams();
                        sevenParam.Px=px;
                        sevenParam.Py=py;
                        sevenParam.Pz=pz;
                        sevenParam.Rx=rx;
                        sevenParam.Ry=ry;
                        sevenParam.Rz=rz;
                        sevenParam.K=k;
                        cache.put(ActContent.SEVENPARAMS,sevenParam);
                    }else {
                        sevenParam= (com.core.vo.SevenParams) cache.getAsObject(ActContent.SEVENPARAMS);
                    }
                    msg_str = "参数设置完成";
                    toast = Toast.makeText(SetSevenParameterActivity.this, msg_str, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                } else {
                    msg_str = "参数不能为空";
                    toast = Toast.makeText(SetSevenParameterActivity.this, msg_str, Toast.LENGTH_SHORT);
                    toast.show();
                    msg_str = null;
                    return;
                }
            }


        });
    }
    private void initSeven(){
        this.px=Double.valueOf(panXEdit.getText().toString());
        this.py=Double.valueOf(panYEdit.getText().toString());
        this.pz=Double.valueOf(panZEdit.getText().toString());
        this.rx=Double.valueOf(roatXEdit.getText().toString());
        this.ry=Double.valueOf(roatYEdit.getText().toString());
        this.rz=Double.valueOf(roatZEdit.getText().toString());
        this.k=Double.valueOf(kEdit.getText().toString());
    }
}
