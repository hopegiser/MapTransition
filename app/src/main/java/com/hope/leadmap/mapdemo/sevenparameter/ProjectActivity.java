package com.hope.leadmap.mapdemo.sevenparameter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wang.leadmap.mapdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProjectActivity extends ActionBarActivity {


    @Bind(R.id.middleline_edit)
    EditText middlelineEdit;
    @Bind(R.id.zonewide_edit)
    EditText zonewideEdit;
    @Bind(R.id.set_project_bt)
    Button setProjectBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);
        if (Double.valueOf(ProjectInfo.getMiddleline())!=0 || Integer.valueOf(ProjectInfo.getZonewide())!=0){
            middlelineEdit.setText(String.valueOf(ProjectInfo.getMiddleline()));
            zonewideEdit.setText(String.valueOf(ProjectInfo.getZonewide()));
        }

        setProjectBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(middlelineEdit.getText()) && !TextUtils.isEmpty(zonewideEdit.getText())){
                        ProjectInfo.setMiddleline(Double.valueOf(middlelineEdit.getText().toString()));
                        ProjectInfo.setZonewide(Integer.valueOf(zonewideEdit.getText().toString()));
                    Toast.makeText(ProjectActivity.this,"参数设置完成",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(ProjectActivity.this,"投影参数不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}
