package com.my.aicai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.my.aicai.R;
import com.my.aicai.adapter.MyPingJiaAdapter;
import com.my.aicai.adapter.PingJiaAdapter;
import com.my.aicai.entity.PingJiaEntity;

import java.util.ArrayList;
import java.util.List;

public class MyPingJiaActivity extends Activity {
    private ImageView ivBack;
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private List<PingJiaEntity> list = new ArrayList<>();
    private MyPingJiaAdapter adapter;

    private void assignViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv = (ListView) findViewById(R.id.lv);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pingjia);

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
        AVQuery<AVObject> query = new AVQuery<>("PingJiaEntity");
        query.include("user");
        query.whereEqualTo("user", AVUser.getCurrentUser());
        query.orderByDescending("createAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects != null && objects.size() > 0) {
                    for (AVObject object : objects) {
                        String goodId = object.getString("goodId");
                        AVUser user = object.getAVUser("user");
                        String content = object.getString("content");
                        String goodsName = object.getString("goodsName");
                        String url = object.getString("url");
                        String price = object.getString("price");
                        String userName = object.getString("userName");
                        list.add(new PingJiaEntity(goodId, user, content,goodsName,url,price,userName));
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

    }

    private void initView() {
        assignViews();
        adapter = new MyPingJiaAdapter(this, list);
        lv.setAdapter(adapter);
    }


    public void startRefresh() {
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
