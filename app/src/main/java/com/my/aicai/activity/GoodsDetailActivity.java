package com.my.aicai.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.my.aicai.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.DecimalFormat;
import java.util.List;


public class GoodsDetailActivity extends Activity implements View.OnClickListener{
    private ImageView ivPic;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvMinus;
    private TextView tvNumber;
    private TextView tvAdd;
    private TextView tvDes;
    private LinearLayout llShopCart;
    private LinearLayout llCollection;
    private ImageView ivCollection;
    private TextView tvTotalPrice;
    private TextView tvBuy;
    private String id;
    private String url;
    private String name;
    private String price;
    private String des;
    private String type;
    private ImageView iv_back;

    private TextView tvPingLun;

    private void assignViews() {
        ivPic = (ImageView) findViewById(R.id.iv_pic);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvMinus = (TextView) findViewById(R.id.tv_minus);
        tvNumber = (TextView) findViewById(R.id.tv_number);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        tvDes = (TextView) findViewById(R.id.tv_des);
        llShopCart = (LinearLayout) findViewById(R.id.ll_shop_cart);
        llCollection = (LinearLayout) findViewById(R.id.ll_collection);
        ivCollection = (ImageView) findViewById(R.id.iv_collection);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        tvBuy = (TextView) findViewById(R.id.tv_buy);
        tvPingLun= (TextView) findViewById(R.id.tv_pinglun);
        iv_back= (ImageView) findViewById(R.id.iv_back);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        id=getIntent().getStringExtra("id");
        url=getIntent().getStringExtra("url");
        name=getIntent().getStringExtra("name");
        price=getIntent().getStringExtra("price");
        des=getIntent().getStringExtra("des");
        type=getIntent().getStringExtra("type");

        initView();
        initEvent();
    }

    private void initEvent() {
        tvMinus.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvBuy.setOnClickListener(this);
        llShopCart.setOnClickListener(this);
        llCollection.setOnClickListener(this);
        tvPingLun.setOnClickListener(this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        assignViews();
        ImageLoader.getInstance().displayImage(url,ivPic,options);
        tvName.setText(name);
        tvPrice.setText(price+"元/斤");
        tvDes.setText(des);

        tvTotalPrice.setText("￥"+price);

    }

    @Override
    protected void onResume() {
        super.onResume();

        AVQuery<AVObject> query=new AVQuery<>("CollectionEntity");
        query.include("user");
        query.whereEqualTo("goodId",id);
        query.whereEqualTo("user",AVUser.getCurrentUser());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list!=null && list.size()>0){
                    ivCollection.setBackgroundResource(R.drawable.collection_ok);
                }else {
                    ivCollection.setBackgroundResource(R.drawable.collection_no);
                }
            }
        });

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_minus:
                String number=tvNumber.getText().toString().trim();
                int num=Integer.parseInt(number);
                if (num>1){
                    num=num-1;
                    tvNumber.setText(num+"");
                }
                double a=Double.parseDouble(price);
                double e=a*num;
                DecimalFormat df1 = new DecimalFormat("#####0.00");
                String str1 = df1.format(e);
                tvTotalPrice.setText("￥"+str1);
                break;
            case R.id.tv_add:
                String number1=tvNumber.getText().toString().trim();
                int num1=Integer.parseInt(number1);
                num1=num1+1;
                tvNumber.setText(num1+"");
                double b=Double.parseDouble(price);
                double c=b*num1;
                DecimalFormat df = new DecimalFormat("#####0.00");
                String str = df.format(c);
                tvTotalPrice.setText("￥"+str);
                break;
            case R.id.ll_collection:
                final AVQuery<AVObject> query=new AVQuery<>("CollectionEntity");
                query.include("user");
                query.whereEqualTo("goodId",id);
                query.whereEqualTo("user",AVUser.getCurrentUser());
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if (list!=null && list.size()>0){
                            ivCollection.setBackgroundResource(R.drawable.collection_no);
                            AVObject object=list.get(0);
                            object.deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e==null){
                                        Toast.makeText(GoodsDetailActivity.this,"已取消收藏！",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else {
                            ivCollection.setBackgroundResource(R.drawable.collection_ok);

                            AVObject object=new AVObject("CollectionEntity");
                            object.put("url",url);
                            object.put("name",name);
                            object.put("des",des);
                            object.put("price",price);
                            object.put("goodId",id);
                            object.put("type",type);
                            object.put("user", AVUser.getCurrentUser());
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e==null){
                                        Toast.makeText(GoodsDetailActivity.this,"收藏成功！",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });


                break;
            case R.id.ll_shop_cart:
                String number3=tvNumber.getText().toString().trim();
                AVObject object=new AVObject("ShopCartEntity");
                object.put("url",url);
                object.put("name",name);
                object.put("des",des);
                object.put("price",price);
                object.put("number",number3);
                object.put("goodId",id);
                object.put("type",type);
                object.put("user", AVUser.getCurrentUser());
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e==null){
                            Toast.makeText(GoodsDetailActivity.this,"加入购物车成功！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.tv_pinglun:
                Intent intent1=new Intent(this,PingJiaActivity.class);
                intent1.putExtra("goodId",id);
                intent1.putExtra("detail","查看");
                startActivity(intent1);

                break;
            case R.id.tv_buy:
                String number4=tvNumber.getText().toString().trim();
                Intent intent=new Intent();
                intent.putExtra("goodId",id);
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                intent.putExtra("price",price);
                intent.putExtra("des",des);
                intent.putExtra("number",number4);
                intent.putExtra("type",type);
                intent.setClass(this,AddressListActivity.class);
                startActivity(intent);

                break;
        }
    }
}
