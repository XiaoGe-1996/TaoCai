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
import com.my.aicai.entity.GoodsEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by user999 on 2018/5/3.
 */

public class HomeAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsEntity> list;

    public HomeAdapter(Context context, List<GoodsEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<GoodsEntity> list){
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
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_home,null);
        ImageView iv= (ImageView) view.findViewById(R.id.iv);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        TextView tv_price= (TextView) view.findViewById(R.id.tv_price);

        GoodsEntity item = list.get(position);
        if (item != null) {
            ImageLoader.getInstance().displayImage(item.getPic() ==null ? "" : item.getPic().getUrl(),iv,options);
            tv_name.setText(item.getName());
            tv_price.setText(item.getPrice()+"元/斤");
        }

        return view;
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
