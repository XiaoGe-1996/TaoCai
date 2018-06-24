package com.my.aicai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.my.aicai.R;
import com.my.aicai.adapter.MyOrderAdapter;
import com.my.aicai.entity.BuyGoodsEntity;

import java.util.ArrayList;
import java.util.List;


public class MyOrderActivity extends Activity {
    private ImageView ivBack;
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private List<BuyGoodsEntity> list=new ArrayList<>();
    private MyOrderAdapter adapter;

    private void assignViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv = (ListView) findViewById(R.id.lv);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRefresh();
        initData();
    }

    private void initData() {
        list.clear();
        AVQuery<AVObject> query=new AVQuery<>("BuyGoodsEntity");
        query.include("user");
        query.whereEqualTo("user", AVUser.getCurrentUser());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        String id=object.getObjectId();
                        String goodId=object.getString("goodId");
                        String url=object.getString("url");
                        String name=object.getString("name");
                        String price=object.getString("price");
                        String des=object.getString("des");
                        String type=object.getString("type");
                        String number=object.getString("number");

                        String address=object.getString("address");
                        String phone=object.getString("phone");
                        String orderID = object.getString("orderID");
                        list.add(new BuyGoodsEntity(id,goodId,url,name,price,des,type,number,AVUser.getCurrentUser(),address,phone,orderID));
                    }
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        stopRefresh();


    }

    private void initEvent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent();
                intent.putExtra("goodId",list.get(i).getGoodId());
                intent.putExtra("url",list.get(i).getUrl());
                intent.putExtra("name",list.get(i).getName());
                intent.putExtra("price",list.get(i).getPrice());
                intent.putExtra("des",list.get(i).getDes());
                intent.putExtra("type",list.get(i).getType());
                intent.putExtra("number",list.get(i).getNumber());

                intent.setClass(MyOrderActivity.this,OrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        assignViews();
        adapter=new MyOrderAdapter(this,list);
        lv.setAdapter(adapter);
    }


    public void startRefresh(){
        refreshLayout.setRefreshing(false);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setProgressViewOffset(false, 0, 30);
        refreshLayout.setRefreshing(true);
    }


    private void stopRefresh() {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
