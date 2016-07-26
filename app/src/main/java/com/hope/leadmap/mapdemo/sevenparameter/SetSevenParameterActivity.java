package com.hope.leadmap.mapdemo.sevenparameter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.wang.leadmap.mapdemo.R;
import com.wang.leadmap.mapdemo.db.SevenParams;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetSevenParameterActivity extends ActionBarActivity {

    private String msg_str;
    private Toast toast;
    private SevenParams sevenParams;
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
        ActiveAndroid.initialize(this);
        if (select()!=null){
            sevenParams=select();
            panXEdit.setText(String.valueOf(sevenParams.Px));
            panYEdit.setText(String.valueOf(sevenParams.Py));
            panZEdit.setText(String.valueOf(sevenParams.Pz));
            roatXEdit.setText(String.valueOf(sevenParams.Rx));
            roatYEdit.setText(String.valueOf(sevenParams.Ry));
            roatZEdit.setText(String.valueOf(sevenParams.Rz));
            kEdit.setText(String.valueOf(sevenParams.K));
        }
        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(panXEdit.getText())
                        && !TextUtils.isEmpty(panYEdit.getText())
                        && !TextUtils.isEmpty(panZEdit.getText())
                        && !TextUtils.isEmpty(roatXEdit.getText())
                        && !TextUtils.isEmpty(roatYEdit.getText())
                        && !TextUtils.isEmpty(roatZEdit.getText())
                        && !TextUtils.isEmpty(kEdit.getText())) {
                    initSeven();
                    sevenParams=select();
                    if (sevenParams==null){
                        sevenParams=new SevenParams(px,py,pz,rx,ry,rz,k);
                        sevenParams.save();
                    }else {
                        delete();
                        sevenParams=new SevenParams(px,py,pz,rx,ry,rz,k);
                        sevenParams.save();
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
    private void delete(){
        new Delete().from(SevenParams.class).execute();
    }

    private SevenParams select(){
        return new Select().from(SevenParams.class).executeSingle();
    }

}
