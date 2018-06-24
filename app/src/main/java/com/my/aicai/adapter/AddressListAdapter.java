package com.my.aicai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.my.aicai.R;
import com.my.aicai.entity.AddressEntity;

import java.util.List;



public class AddressListAdapter extends BaseAdapter {
    private Context context;
    private List<AddressEntity> list;

    public AddressListAdapter(Context context, List<AddressEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<AddressEntity> list){
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
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_address,null);
        TextView tv_address= (TextView) view.findViewById(R.id.tv_address);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        TextView tv_phone= (TextView) view.findViewById(R.id.tv_phone);

        tv_address.setText("收货地址："+list.get(position).getAddress());
        tv_name.setText("收货人："+list.get(position).getShouhuoName());
        tv_phone.setText("联系电话："+list.get(position).getShouhuoPhone());
        return view;
    }
}
