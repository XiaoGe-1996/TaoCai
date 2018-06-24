package com.my.aicai.shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.my.aicai.R;
import com.my.aicai.activity.OrderDetailActivity1;
import com.my.aicai.activity.PayMoneyActivity;
import com.my.aicai.adapter.OrderListAdapter;
import com.my.aicai.entity.OrderClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.start;
import static com.my.aicai.application.MyApplication.getContext;

/**
 * Created by Gc on 2018/5/13.
 * PackageName: com.my.aicai.shop
 * Desc:
 */

public class ShopOrderListFragment extends Fragment{

    private View view;
    private RecyclerView rv_order;
    private SwipeRefreshLayout refreshLayout;
    private List<OrderClass> list=new ArrayList<>();
    private ShopOrderListAdapter orderListAdapter;
    private int type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_order_list,container,false);
        type = getArguments().getInt("type");
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        orderListAdapter.setOnMyclick(new ShopOrderListAdapter.onMyclick() {
            @Override
            public void clickOrder(View view, final int position) {
                OrderClass item = list.get(position);
                switch (item.getStatus()) {
                    case "1"://前往发货页面
                        Intent intent = new Intent(getContext(),SendOrderActivity.class);
                        intent.putExtra("orderID", list.get(position).getId());
                        startActivity(intent);
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setTitle("是否确认收货？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//                                list.get(position).setStatus("2");
//                                AVObject order = AVObject.createWithoutData("OrderClass",list.get(position).getId());
//                                // 修改 content
//                                order.put("status",2+"");
//                                // 保存到云端
//                                order.saveInBackground();
//                                orderListAdapter.notifyDataSetChanged();
//                                Toast.makeText(getContext(),"收货成功",Toast.LENGTH_SHORT).show();
//                            }
//                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        }).setCancelable(true).create().show();
                        break;
                }
            }

            @Override
            public void onItemClick(View view, int position) {
                OrderClass item = list.get(position);
                Intent i = new Intent(getContext(), OrderDetailActivity1.class);
                i.putExtra("status", item.getStatus());
                i.putExtra("orderID", item.getId());
                startActivity(i);
            }
        });
    }

    private void initData() {
        list.clear();
        AVQuery<AVObject> query=new AVQuery<>("OrderClass");
        query.orderByDescending("createdAt");
        if (type != 0) {
            query.whereEqualTo("status", (type-1)+"");
        }
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        String id=object.getObjectId();
                        AVUser user = object.getAVUser("user");
                        String phone = object.getString("OrderPhone");
                        String OrderAddress = object.getString("OrderAddress");
                        String OrderPrice = object.getString("OrderPrice");
                        String status = object.getString("status");
                        String shouhuoName = object.getString("shouhuoName");
                        String expressNum = object.getString("expressNum");
                        String createTime = object.getCreatedAt().toString();
                        SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
                        Date date = null;
                        try {
                            date = sf1.parse(createTime);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        createTime = sf2.format(date);
                        String goodsNum = object.getString("goodsNum");
                        String com = object.getString("com");
                        list.add(new OrderClass(id,user,phone,OrderAddress,OrderPrice,status,shouhuoName,expressNum,createTime,goodsNum,com));
                    }
                    orderListAdapter.notifyDataSetChanged();
                }
            }
        });
        stopRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        startRefresh();
        initData();
    }

    private void initView() {
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        rv_order = (RecyclerView) view.findViewById(R.id.rv_order);
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        orderListAdapter = new ShopOrderListAdapter(getContext(), list);
        rv_order.setAdapter(orderListAdapter);

    }

    /**
     * 开始刷新
     */
    public void startRefresh(){
        refreshLayout.setRefreshing(false);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setProgressViewOffset(false, 0, 30);
        refreshLayout.setRefreshing(true);
    }

    /**
     * 停止刷新
     */
    private void stopRefresh() {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
