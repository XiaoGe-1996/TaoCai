package com.my.aicai.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.aicai.R;
import com.my.aicai.adapter.OrderListAdapter;
import com.my.aicai.entity.OrderClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gc on 2018/5/13.
 * PackageName: com.my.aicai.shop
 * Desc:
 */

public class ShopOrderListAdapter extends RecyclerView.Adapter<ShopOrderListAdapter.ViewHolder> {

    private Context mContext;
    private List<OrderClass> list= new ArrayList();
    private LayoutInflater inflater;

    public ShopOrderListAdapter(Context mContext,List<OrderClass> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_order;
        private TextView tv_delivery_time;
        private TextView tv_place;
        private TextView tv_num;
        private TextView tv_money;
        private TextView tv_status;
        private Button btn_action;
        private LinearLayout list_item;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_order = (TextView) itemView.findViewById(R.id.tv_order);
            tv_delivery_time = (TextView) itemView.findViewById(R.id.tv_delivery_time);
            tv_place = (TextView) itemView.findViewById(R.id.tv_place);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            btn_action = (Button) itemView.findViewById(R.id.btn_action);
            list_item = (LinearLayout) itemView.findViewById(R.id.list_item);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order_list,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        OrderClass item = list.get(position);
        holder.tv_order.setText("订单号："+item.getId());
        holder.tv_delivery_time.setText("下单时间："+item.getCreateTime());
        holder.tv_place.setText("收货地址："+item.getOrderAddress());
        holder.tv_num.setText("商品数量："+item.getGoodsNum());
        holder.tv_money.setText("订单总价："+item.getOrderPrice());
        switch (item.getStatus()) {
            case "0":
                holder.tv_status.setText("待付款");
                holder.btn_action.setVisibility(View.GONE);
                break;
            case "1":
                holder.tv_status.setText("待发货");
                holder.btn_action.setVisibility(View.VISIBLE);
                holder.btn_action.setText("去发货");
                break;
            case "2":
                holder.tv_status.setText("待收货");
                holder.btn_action.setVisibility(View.GONE);
                break;
            case "3":
                holder.tv_status.setText("已完成");
                holder.btn_action.setVisibility(View.GONE);
                break;
            default:
                holder.tv_status.setText("已完成");
                holder.btn_action.setVisibility(View.GONE);
                break;
        }
        holder.btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyclick.clickOrder(v,position);
            }
        });

        holder.list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyclick.onItemClick(v,position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onMyclick {
        void clickOrder(View view, int position);

        void onItemClick(View view, int position);
    }

    private onMyclick onMyclick;

    public void setOnMyclick(ShopOrderListAdapter.onMyclick onMyclick) {
        this.onMyclick = onMyclick;
    }
}
