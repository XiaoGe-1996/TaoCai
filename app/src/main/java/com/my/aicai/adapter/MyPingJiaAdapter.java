package com.my.aicai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.my.aicai.R;
import com.my.aicai.entity.PingJiaEntity;
import com.my.aicai.util.CircleImageView;

import java.util.List;

import static com.my.aicai.R.id.view;

/**
 * Created by Gc on 2018/5/13.
 * PackageName: com.my.aicai.adapter
 * Desc:
 */

public class MyPingJiaAdapter extends BaseAdapter {

    private Context context;
    private List<PingJiaEntity> list;

    public MyPingJiaAdapter(Context context, List<PingJiaEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<PingJiaEntity> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
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
        PingJiaEntity item = list.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_mypingjia, null);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        ImageView iv_goodsImg = (ImageView) view.findViewById(R.id.iv_goodsImg);
        TextView tv_goods_name = (TextView) view.findViewById(R.id.tv_goods_name);
        TextView tv_goods_price = (TextView) view.findViewById(R.id.tv_goods_price);


        tv_name.setText(list.get(position).getUserName() + "：");
        tv_content.setText(list.get(position).getContent());
        tv_goods_name.setText(item.getGoodsName());
        tv_goods_price.setText(item.getPrice() + "/斤");
        Glide.with(context).load(item.getUrl()).dontAnimate().into(iv_goodsImg);


        return view;
    }

}
