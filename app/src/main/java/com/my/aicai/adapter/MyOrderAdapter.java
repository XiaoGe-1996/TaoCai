package com.my.aicai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.aicai.R;
import com.my.aicai.activity.BuyGoodsActivity;
import com.my.aicai.entity.BuyGoodsEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class MyOrderAdapter extends BaseAdapter {
    private Context context;
    private List<BuyGoodsEntity> list;

    public MyOrderAdapter(Context context, List<BuyGoodsEntity> list) {
        this.context = context;
        this.list = list;
    }


    public void setData(List<BuyGoodsEntity> list){
        this.list=list;
    }

    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View view1= LayoutInflater.from(context).inflate(R.layout.lv_item_my_order,null);

        ImageView iv_pic= (ImageView) view1.findViewById(R.id.iv_pic);
        TextView tv_name= (TextView) view1.findViewById(R.id.tv_name);
        TextView tv_price= (TextView) view1.findViewById(R.id.tv_price);
        TextView tv_number= (TextView) view1.findViewById(R.id.tv_number);

        ImageLoader.getInstance().displayImage(list.get(position).getUrl(),iv_pic,options);
        tv_name.setText(list.get(position).getName());
        tv_price.setText(list.get(position).getPrice()+"元/斤");
        tv_number.setText("数量："+list.get(position).getNumber()+"斤");


        return view1;
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
