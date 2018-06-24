package com.my.aicai.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.my.aicai.R;
import com.my.aicai.activity.LoginActivity;
import com.my.aicai.activity.MyAddressActivity;
import com.my.aicai.activity.MyCollectionActivity;
import com.my.aicai.activity.MyOrderActivity;
import com.my.aicai.activity.MyPingJiaActivity;
import com.my.aicai.activity.OrderListActivity;
import com.my.aicai.activity.SubmitImageActivity;
import com.my.aicai.util.AbSharedUtil;
import com.my.aicai.util.CircleImageView;
import com.my.aicai.util.PreferenceHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

/**
 * Created by user999 on 2018/5/3.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener{
    private View view;
    private CircleImageView ivPic;
    private TextView tvInfo;
    private TextView tvMoney;
    private LinearLayout llMyCollection;
    private LinearLayout llMyOrder;
    private LinearLayout llMyPinglun;
    private LinearLayout llMyAddress;
    private TextView tv_login_out;
    private static final int Take_Photo = 0;


    private void assignViews() {
        ivPic = (CircleImageView) view.findViewById(R.id.iv_pic);
        tvInfo = (TextView) view.findViewById(R.id.tv_info);
        tvMoney = (TextView) view.findViewById(R.id.tv_money);
        llMyCollection = (LinearLayout) view.findViewById(R.id.ll_my_collection);
        llMyOrder = (LinearLayout) view.findViewById(R.id.ll_my_order);
        llMyPinglun = (LinearLayout) view.findViewById(R.id.ll_my_pinglun);
        llMyAddress = (LinearLayout) view.findViewById(R.id.ll_my_address);
        tv_login_out= (TextView) view.findViewById(R.id.tv_login_out);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_me,container,false);

        File sd= Environment.getExternalStorageDirectory();
        String path=sd.getPath()+"/爱菜";
        File file=new File(path);
        if(!file.exists())
            file.mkdir();



        initView();
        initEvent();
        return view;
    }


    private void initEvent() {
        ivPic.setOnClickListener(this);
        llMyCollection.setOnClickListener(this);
        llMyOrder.setOnClickListener(this);
        llMyPinglun.setOnClickListener(this);
        llMyAddress.setOnClickListener(this);
        tv_login_out.setOnClickListener(this);

    }

    private void initView() {
        assignViews();
        AVUser user=AVUser.getCurrentUser();
//        String money=user.getString("money");
        String nickName=user.getString("nickName");
        String phone=user.getUsername();
        String money=user.getString("money");
        DecimalFormat df = new DecimalFormat("#####0.00");
        String str1 = df.format(Double.parseDouble(money));
        tvMoney.setText(str1+"元");
        tvInfo.setText(nickName+"("+phone+")");

//        DecimalFormat df = new DecimalFormat("#####0.00");
//        String str1 = df.format(Double.parseDouble(money));
//        tvMoney.setText(str1+"元");

        AVFile picFile = (AVFile) AVUser.getCurrentUser().get("pic");
        String imageName = AbSharedUtil.getString(getActivity(), "imageName");
        if (null != imageName) {
            if(picFile!=null) {
                String name = picFile.getOriginalName();
                if (null != name && name.equals(imageName)) {//如果本地有，且图片一致，加载本地缓存的图片
                    Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/爱菜/"
                            + imageName);
                    ivPic.setImageBitmap(bitmap);
                } else {//如果本地有，但是图片不一致，从云端上下载
                    String str = picFile.getUrl();//获取图片文件的url
                    ImageLoader.getInstance().displayImage(str, ivPic);//异步加载图片
                }
            }
        }else{//如果本地没有，从云端上下载
            if(picFile!=null) {
                String str = picFile.getUrl();
                if (str != null && str.length() > 0)
                    ImageLoader.getInstance().displayImage(str, ivPic);
            }
        }

    }



    @SuppressLint("SdCardPath")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case Take_Photo:
                    if (data != null) {
                        String imageName = data.getStringExtra("imageName");
                        //返回数据了
                        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/爱菜/"
                                + imageName);
                        ivPic.setImageBitmap(bitmap);
                        AbSharedUtil.putString(getActivity(), "imageName", imageName);
                        updateAvatarInServer(imageName);
                    }
                    break;


            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void updateAvatarInServer(String imageName) {
        AVUser user = AVUser.getCurrentUser();

        AVFile file = null;
        try {
            file = AVFile.withAbsoluteLocalPath(imageName, "/sdcard/爱菜/" + imageName);
            user.put("pic", file);
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Toast.makeText(getActivity(), "图片上传成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "图片上传失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_login_out:
                AVUser.logOut();             //清除缓存用户对象
                AVUser currentUser = AVUser.getCurrentUser(); // 现在的currentUser是null了
                PreferenceHelper.clean(getContext(),"taocai");
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.iv_pic:
                intent = new Intent();
                intent.setClass(getActivity(), SubmitImageActivity.class);
                startActivityForResult(intent, Take_Photo);
                break;
            case R.id.ll_my_collection:
                intent=new Intent();
                intent.setClass(getActivity(), MyCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_my_order:
                intent=new Intent();
                intent.setClass(getActivity(), OrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_my_pinglun:
                intent=new Intent();
                intent.setClass(getActivity(), MyPingJiaActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_my_address:
                intent=new Intent();
                intent.setClass(getActivity(), MyAddressActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
        }else {
            AVUser user=AVUser.getCurrentUser();
            String money=user.getString("money");
            DecimalFormat df = new DecimalFormat("#####0.00");
            String str1 = df.format(Double.parseDouble(money));
            tvMoney.setText(str1+"元");
        }
    }

}
