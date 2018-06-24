package com.my.aicai.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.aicai.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


public class OrderDetailActivity extends Activity {
    private ImageView ivPic;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvNumber;
    private TextView tvDes;
    private TextView tvAddPinglun;
    private ImageView iv_back;

    private String id;
    private String url;
    private String name;
    private String price;
    private String des;
    private String type;
    private String number;

    private void assignViews() {
        ivPic = (ImageView) findViewById(R.id.iv_pic);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvNumber = (TextView) findViewById(R.id.tv_number);
        tvDes = (TextView) findViewById(R.id.tv_des);
        tvAddPinglun = (TextView) findViewById(R.id.tv_add_pinglun);
        iv_back= (ImageView) findViewById(R.id.iv_back);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        id=getIntent().getStringExtra("goodId");
        name=getIntent().getStringExtra("name");
        url=getIntent().getStringExtra("url");
        price=getIntent().getStringExtra("price");
        des=getIntent().getStringExtra("des");
        type=getIntent().getStringExtra("type");
        number=getIntent().getStringExtra("number");


        initView();
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tvAddPinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("goodId",id);
                intent.putExtra("detail","添加");
                intent.putExtra("name",name);
                intent.putExtra("url", url);
                intent.putExtra("price", price);
                intent.setClass(OrderDetailActivity.this,PingJiaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        assignViews();

        ImageLoader.getInstance().displayImage(url,ivPic,options);
        tvName.setText(name);
        tvPrice.setText(price+"元/斤");
        tvDes.setText(des);
        tvNumber.setText("数量："+number+"斤");

    }

    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.color.bg_no_photo)
            .showImageForEmptyUri(R.color.bg_no_photo)
            .showImageOnFail(R.color.bg_no_photo)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
            .build();
}
