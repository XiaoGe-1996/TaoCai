package com.my.aicai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.my.aicai.R;
import com.my.aicai.entity.BuyGoodsEntity;
import com.my.aicai.entity.OrderClass;

import java.util.ArrayList;
import java.util.List;

import static com.my.aicai.R.id.tv_price;

/**
 * Created by Gc on 2018/5/12.
 * PackageName: com.my.aicai.adapter
 * Desc:
 */

public class OrderGoodsAdapter extends RecyclerView.Adapter<OrderGoodsAdapter.ViewHolder> {

    private Context mContext;
    private List<BuyGoodsEntity> list = new ArrayList();
    private LayoutInflater inflater;
    public OrderGoodsAdapter(Context mContext, List<BuyGoodsEntity> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order_goods,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BuyGoodsEntity item = list.get(position);
        holder.tv_goods_name.setText(item.getName());
        holder.tv_goods_guige.setText(item.getPrice()+"/斤");
        holder.tv_price.setText("x"+item.getNumber());

        Glide.with(mContext).load(item.getUrl()).dontAnimate().into(holder.iv_goodsImg);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_goodsImg;
        private TextView tv_goods_name;
        private TextView tv_goods_guige;
        private TextView tv_price;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_goodsImg = (ImageView) itemView.findViewById(R.id.iv_goodsImg);
            tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            tv_goods_guige = (TextView) itemView.findViewById(R.id.tv_goods_guige);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }

}
