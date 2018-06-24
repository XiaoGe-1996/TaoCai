package com.my.aicai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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


public class AddPingJiaActivity extends Activity implements View.OnClickListener{
    private ImageView iv_back;
    private EditText et_content;
    private TextView tv_ok;
    private String goodId;
    private String name;
    private String url;
    private String price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pingjia);
        goodId=getIntent().getStringExtra("goodId");
        name=getIntent().getStringExtra("name");
        url=getIntent().getStringExtra("url");
        price=getIntent().getStringExtra("price");

        initView();
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    private void initView() {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        et_content= (EditText) findViewById(R.id.et_content);
        tv_ok= (TextView) findViewById(R.id.tv_ok);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_ok:
                String content=et_content.getText().toString().trim();
                if (!content.isEmpty() && content.length()>0){
                    AVObject object=new AVObject("PingJiaEntity");
                    object.put("goodId",goodId);
                    object.put("user", AVUser.getCurrentUser());
                    object.put("content",content);
                    object.put("goodsName",name);
                    object.put("url",url);
                    object.put("userName",AVUser.getCurrentUser().getString("nickName"));
                    object.put("price",price);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null){
                                Toast.makeText(AddPingJiaActivity.this,"评价成功!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Log.e("GGG",e.getMessage().toString());
                            }
                        }
                    });

                }else {
                    Toast.makeText(this,"请先填写评价的内容!", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
}
