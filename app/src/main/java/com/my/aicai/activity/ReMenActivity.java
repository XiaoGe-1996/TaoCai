package com.my.aicai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.my.aicai.R;
import com.my.aicai.adapter.HomeAdapter;
import com.my.aicai.entity.GoodsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class ReMenActivity extends Activity {
    private GridView gridView;
    private List<GoodsEntity> list=new ArrayList<>();
    private HomeAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remen);
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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent();
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("url",list.get(position).getPic().getUrl());
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("price",list.get(position).getPrice());
                intent.putExtra("des",list.get(position).getDes());
                intent.putExtra("type",list.get(position).getType());
                intent.setClass(ReMenActivity.this, GoodsDetailActivity.class);
                startActivity(intent);

            }
        });


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        startRefresh();
        initData();
    }

    private void initData() {
        list.clear();
        AVQuery<AVObject> query=new AVQuery<>("Goods");
        query.whereEqualTo("remen","是");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        String id=object.getObjectId();
                        AVFile pic=object.getAVFile("pic");
                        String name=object.getString("name");
                        String price=object.getString("price");
                        String des=object.getString("des");
                        String type=object.getString("type");
                        list.add(new GoodsEntity(id,pic,name,price,des,type));
                    }
                    Log.e("BB",list.toString());
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        stopRefresh();
    }

    private void initView() {
        ivBack= (ImageView) findViewById(R.id.iv_back);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.refresh);
        gridView= (GridView) findViewById(R.id.gridView);
        adapter=new HomeAdapter(this,list);
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
