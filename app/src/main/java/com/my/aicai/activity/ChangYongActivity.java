package com.my.aicai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.my.aicai.R;
import com.my.aicai.adapter.ChangYongAdapter;
import com.my.aicai.entity.ChangYongEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class ChangYongActivity extends Activity {
    private ImageView ivBack;
    private SwipeRefreshLayout refreshLayout;
    private GridView gridView;
    private ChangYongAdapter adapter;
    private List<ChangYongEntity> list=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changyong);

        initView();
        initEvent();
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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent();
                intent.putExtra("id",list.get(position).getGoodId());
                intent.putExtra("url",list.get(position).getUrl());
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("price",list.get(position).getPrice());
                intent.putExtra("des",list.get(position).getDes());
                intent.putExtra("type",list.get(position).getType());
                intent.setClass(ChangYongActivity.this, GoodsDetailActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRefresh();
        initData();
    }

    private void initData() {
        list.clear();
        AVQuery<AVObject> query=new AVQuery<>("ChangYongEntity");
        query.whereEqualTo("user", AVUser.getCurrentUser());
        query.include("user");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        String id=object.getObjectId();
                        String url=object.getString("url");
                        String name=object.getString("name");
                        String price=object.getString("price");
                        String des=object.getString("des");
                        String type=object.getString("type");
                        String number=object.getString("number");
                        String goodId=object.getString("goodId");
                        int num=Integer.parseInt(number);
                        if (num>=20){
                            list.add(new ChangYongEntity(id,goodId,url,name,price,des,type));
                        }
                    }
                    adapter.setData(list);
                    gridView.setAdapter(adapter);

                }

            }
        });

        stopRefresh();
    }

    private void initView() {
        ivBack= (ImageView) findViewById(R.id.iv_back);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.refresh);
        gridView= (GridView) findViewById(R.id.gridView);
        adapter=new ChangYongAdapter(this,list);
        gridView.setAdapter(adapter);
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
