package com.my.aicai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.my.aicai.R;
import com.my.aicai.entity.BuyGoodsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gc on 2018/5/12.
 * PackageName: com.my.aicai.adapter
 * Desc:
 */

public class OrderGoodsAdapter1 extends RecyclerView.Adapter<OrderGoodsAdapter1.ViewHolder> {

    private Context mContext;
    private List<BuyGoodsEntity> list = new ArrayList();
    private LayoutInflater inflater;
    private String status;

    public OrderGoodsAdapter1(Context mContext, List<BuyGoodsEntity> list, String status) {
        this.mContext = mContext;
        this.list = list;
        this.status = status;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order_goods1, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        BuyGoodsEntity item = list.get(position);
        holder.tv_goods_name.setText(item.getName());
        holder.tv_goods_guige.setText(item.getPrice() + "/斤");
        holder.tv_price.setText("x" + item.getNumber());

        Glide.with(mContext).load(item.getUrl()).dontAnimate().into(holder.iv_goodsImg);

        switch (status) {
            case "0":
            case "1":
                holder.tv_pinglun.setVisibility(View.GONE);
                break;
            case "2":
                holder.tv_pinglun.setVisibility(View.VISIBLE);
                break;
        }
        holder.tv_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyClick.pinglunClick(v, position);
            }
        });

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
        private TextView tv_pinglun;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_goodsImg = (ImageView) itemView.findViewById(R.id.iv_goodsImg);
            tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            tv_goods_guige = (TextView) itemView.findViewById(R.id.tv_goods_guige);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_pinglun = (TextView) itemView.findViewById(R.id.tv_pinglun);
        }
    }

    public interface onMyClick {
        void pinglunClick(View view, int position);
    }

    private onMyClick onMyClick;

    public void setOnMyClick(OrderGoodsAdapter1.onMyClick onMyClick) {
        this.onMyClick = onMyClick;
    }
}
