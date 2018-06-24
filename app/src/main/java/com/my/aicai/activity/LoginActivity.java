package com.my.aicai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.my.aicai.R;
import com.my.aicai.shop.ShopMainActivity;
import com.my.aicai.util.PreferenceHelper;

import static android.R.attr.password;
import static com.my.aicai.util.PreferenceHelper.readString;

/**
 * Created by user999 on 2018/5/3.
 */

public class LoginActivity extends Activity {
    private TextView tvRegister;
    private EditText etUserName;
    private EditText etPassword;
    private TextView tvOk;

    private void assignViews() {
        tvRegister = (TextView) findViewById(R.id.tv_register);
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPassword = (EditText) findViewById(R.id.et_password);
        tvOk = (TextView) findViewById(R.id.tv_ok);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initEvent();
    }

    private void initEvent() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=etUserName.getText().toString().trim();
                final String password=etPassword.getText().toString().trim();
                if(username!=null&&username.length()>0&&password!=null&&password.length()>0) {
                    AVUser.logInInBackground(username, password, new LogInCallback() {
                        public void done(AVUser user, AVException e) {
                            if (e == null) {
                                if (username.equals("admin")) {
                                    Intent intent=new Intent(LoginActivity.this,ShopMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    PreferenceHelper.write(getApplicationContext(),"taocai","username",username);
                                    PreferenceHelper.write(getApplicationContext(),"taocai","password",password);
                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"请填写登录信息", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initView() {
        assignViews();
        String username = PreferenceHelper.readString(getApplicationContext(),"taocai","username");
        String password = PreferenceHelper.readString(getApplicationContext(),"taocai","password");
        if (username != null) {
            etUserName.setText(username);
        }
        if (password != null) {
            etPassword.setText(password);
        }
    }
}
