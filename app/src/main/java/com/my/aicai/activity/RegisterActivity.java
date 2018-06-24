package com.my.aicai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.my.aicai.R;
import com.my.aicai.util.AbStrUtil;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user999 on 2018/5/3.
 */

public class RegisterActivity extends Activity implements View.OnClickListener{
    private ImageView ivBack;
    private EditText etPhone;
    private EditText etCode;
    private TextView tvSend;
    private EditText etNickName;
    private EditText etPassword;
    private EditText etRePassword;
    private TextView tvOk;
    private int second=60;
    private Handler handler;
    private TextView tv_timer;

    private void assignViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        tvSend = (TextView) findViewById(R.id.tv_send);
        etNickName = (EditText) findViewById(R.id.et_nick_name);
        etPassword = (EditText) findViewById(R.id.et_password);
        etRePassword = (EditText) findViewById(R.id.et_re_password);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tv_timer= (TextView) findViewById(R.id.tv_timer);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initEvent();
    }

    private void initEvent() {
        ivBack.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        tvOk.setOnClickListener(this);

    }

    private void initView() {
        assignViews();
        handler=new TimerHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_send:
                String phone=etPhone.getText().toString().trim();
                if (AbStrUtil.isEmpty(phone)) {
                    Toast.makeText(this,"手机号不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isMobileNO(phone)) {
                    Toast.makeText(this,"请输入正确的手机号码！",Toast.LENGTH_SHORT).show();
                    return;
                }

                getCode(phone);

                break;
            case R.id.tv_ok:
                final String phone1=etPhone.getText().toString().trim();
                String code=etCode.getText().toString().trim();
                final String nickName=etNickName.getText().toString().trim();
                final String password=etPassword.getText().toString().trim();
                final String rePassword=etRePassword.getText().toString().trim();
                if (!AbStrUtil.isEmpty(phone1) && !AbStrUtil.isEmpty(code) && !AbStrUtil.isEmpty(nickName) && !AbStrUtil.isEmpty(password) && !AbStrUtil.isEmpty(rePassword)){
                    AVOSCloud.verifySMSCodeInBackground(code,phone1, new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null){
                                Toast.makeText(RegisterActivity.this,"验证成功！",Toast.LENGTH_SHORT).show();
                                if (password.equals(rePassword)){
                                    AVUser user = new AVUser();
                                    user.setUsername(phone1);
                                    user.setPassword(password);
                                    user.put("nickName",nickName);
                                    user.put("money","9999");
                                    user.signUpInBackground(new SignUpCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e==null){
                                                Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    });
                                    finish();
                                }else {
                                    Toast.makeText(RegisterActivity.this,"两次密码不一致！",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(RegisterActivity.this,"验证码错误！",Toast.LENGTH_SHORT).show();
                                Log.e("HH",e.getMessage().toString());
                            }
                        }
                    });


                }else {
                    Toast.makeText(this,"请先填写完整信息！",Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    private void getCode(String phoneNum){
        tvSend.setEnabled(false);
        final Timer timer = new Timer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 60; i++) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(0x01);
                        }
                    }, 1000);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(0x02);
            }
        }).start();


        AVOSCloud.requestSMSCodeInBackground(phoneNum, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e==null){
                    Log.e("HH","CCCCCCCC");
                }else {
                    Log.e("HH",e.getMessage().toString());
                }
            }
        });

    }


    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        String telRegex = "[1]\\d{10}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return mobiles.matches(telRegex);
    }

    private void setTimerText() {
        Log.e("UU","CCCC");
        if (isOk==false){
            tvSend.setVisibility(View.GONE);
            tv_timer.setVisibility(View.VISIBLE);
            String text = "(  " + second + " s  )";
            tv_timer.setText(text);
            tv_timer.setTextColor(getResources().getColor(R.color.line));
            second--;
        }else {
            tv_timer.setVisibility(View.GONE);
            tvSend.setVisibility(View.VISIBLE);
            isOk=false;

        }

    }

    private boolean isOk=false;

    private void resetTimer() {
        Log.e("UU","TTT");
        isOk=true;
        second = 60;

        tv_timer.setVisibility(View.GONE);
        tvSend.setEnabled(true);
        Log.e("UU","EEEE");
//        tvSend.setVisibility(View.VISIBLE);
        Log.e("UU","MMMMM");
    }


    public static class TimerHandler extends Handler {
        private final WeakReference<RegisterActivity> weakReference;

        public TimerHandler(RegisterActivity registerActivity) {
            weakReference = new WeakReference<RegisterActivity>(registerActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            RegisterActivity activity = weakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0x01:
                        //刷新倒计时
                        activity.setTimerText();
                        break;
                    case 0x02:
                        //计时完成
                        activity.resetTimer();
                        break;
                }
            }
        }
    }
}
