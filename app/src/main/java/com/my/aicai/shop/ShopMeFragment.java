package com.my.aicai.shop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.my.aicai.R;
import com.my.aicai.activity.LoginActivity;
import com.my.aicai.activity.MyPingJiaActivity;
import com.my.aicai.fragment.BaseFragment;
import com.my.aicai.util.CircleImageView;

import java.io.File;

/**
 * Created by Gc on 2018/5/13.
 * PackageName: com.my.aicai.shop
 * Desc:
 */

public class ShopMeFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private CircleImageView ivPic;
    private TextView tvInfo;
    private LinearLayout llMyPinglun;
    private TextView tv_login_out;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_shop_me,container,false);

        initView();
        initEvent();
        return view;
    }

    private void initView() {
        ivPic = (CircleImageView) view.findViewById(R.id.iv_pic);
        tvInfo = (TextView) view.findViewById(R.id.tv_info);
        llMyPinglun = (LinearLayout) view.findViewById(R.id.ll_my_pinglun);
        tv_login_out= (TextView) view.findViewById(R.id.tv_login_out);

        AVUser user=AVUser.getCurrentUser();
        String nickName=user.getString("nickName");
        tvInfo.setText(nickName);
    }

    private void initEvent() {
        ivPic.setOnClickListener(this);
        llMyPinglun.setOnClickListener(this);
        tv_login_out.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_out:
                AVUser.logOut();             //清除缓存用户对象
                AVUser currentUser = AVUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.ll_my_pinglun:
                Intent intent=new Intent();
                intent.setClass(getActivity(), ShopPingJiaActivity.class);
                startActivity(intent);
                break;
        }
    }
}
