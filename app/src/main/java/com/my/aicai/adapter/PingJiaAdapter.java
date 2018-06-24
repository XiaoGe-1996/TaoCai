package com.my.aicai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.my.aicai.R;
import com.my.aicai.entity.PingJiaEntity;

import java.util.List;


public class PingJiaAdapter extends BaseAdapter {
    private Context context;
    private List<PingJiaEntity> list;

    public PingJiaAdapter(Context context, List<PingJiaEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<PingJiaEntity> list){
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
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_pingjia,null);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        TextView tv_content= (TextView) view.findViewById(R.id.tv_content);

        tv_name.setText(list.get(position).getUser().getString("nickName")+"：");
        tv_content.setText(list.get(position).getContent());
        return view;
    }
}
