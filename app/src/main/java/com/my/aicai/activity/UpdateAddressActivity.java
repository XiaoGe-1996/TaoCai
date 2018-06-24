package com.my.aicai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.SaveCallback;
import com.my.aicai.R;

import static android.R.id.list;

/**
 * Created by Gc on 2018/5/12.
 * PackageName: com.my.aicai.activity
 * Desc:
 */

public class UpdateAddressActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back;
    private EditText et_address;
    private TextView tv_add;

    private EditText et_name;
    private EditText et_phone;

    private String id,name,address, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);

        Intent i = getIntent();
        id = i.getStringExtra("shouhuoID");
        name = i.getStringExtra("shouhuoName");
        address = i.getStringExtra("address");
        phone = i.getStringExtra("shouhuoPhone");

        initView();
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(this);
        tv_add.setOnClickListener(this);
    }

    private void initView() {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        et_address= (EditText) findViewById(R.id.et_address);
        tv_add= (TextView) findViewById(R.id.tv_add);
        et_name= (EditText) findViewById(R.id.et_name);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_address.setText(address);
        et_name.setText(name);
        et_phone.setText(phone);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                String address=et_address.getText().toString().trim();
                String name=et_name.getText().toString().trim();
                String phone=et_phone.getText().toString().trim();
                if (!address.isEmpty() && address.length()>0 && !name.isEmpty() && name.length()>0 && !phone.isEmpty() && phone.length()>0 ){
//                    AVQuery<AVObject> query=new AVQuery<AVObject>("AddressEntity");
//                    AVObject object=new AVObject("AddressEntity");
                    AVObject save = AVObject.createWithoutData("AddressEntity",id);

                    // 修改 content
                    save.put("shouhuoName",name);
                    save.put("shouhuoPhone",phone);
                    save.put("address",address);
                    // 保存到云端
                    save.saveInBackground();
                    Toast.makeText(this,"保存成功", Toast.LENGTH_SHORT).show();
                    finish();

                }else {
                    Toast.makeText(this,"请先填入地址...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
