package com.my.aicai.shop;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.my.aicai.R;


/**
 * Created by Gc on 2018/5/13.
 * PackageName: com.my.aicai.shop
 * Desc:
 */

public class SendOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp_type;
    private ImageView iv_back;
    private EditText et_express;
    private TextView tv_add;

    private String com;
    private String orderID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);

        com = "sf";
        orderID = getIntent().getStringExtra("orderID");

        initView();
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(this);
        tv_add.setOnClickListener(this);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        sp_type = (Spinner) findViewById(R.id.sp_type);
        et_express = (EditText) findViewById(R.id.et_express);
        tv_add = (TextView) findViewById(R.id.tv_add);

        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        com = "sf";
                        break;
                    case 1:
                        com = "sto";
                        break;
                    case 2:
                        com = "yt";
                        break;
                    case 3:
                        com = "yd";
                        break;
                    case 4:
                        com = "tt";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("是否确定发货？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String express = et_express.getText().toString().trim();
                        if (!express.isEmpty() && express.length() > 0) {
                            AVObject save = AVObject.createWithoutData("OrderClass",orderID);
                            // 修改 content
                            save.put("com",com);
                            save.put("expressNum",express);
                            save.put("status","2");
                            // 保存到云端
                            save.saveInBackground();
                            Toast.makeText(getApplicationContext(),"发货成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "请填写订单号", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setCancelable(true).create().show();
                break;
        }
    }
}
