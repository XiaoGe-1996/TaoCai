package com.my.aicai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.my.aicai.R;

public class AddAddressActivity extends Activity implements View.OnClickListener{
    private ImageView iv_back;
    private EditText et_address;
    private TextView tv_add;

    private EditText et_name;
    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

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
                    AVObject object=new AVObject("AddressEntity");
                    object.put("user", AVUser.getCurrentUser());
                    object.put("shouhuoName",name);
                    object.put("shouhuoPhone",phone);
                    object.put("address",address);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null){
                                Toast.makeText(AddAddressActivity.this,"添加成功!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });

                }else {
                    Toast.makeText(this,"请先填入地址...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
