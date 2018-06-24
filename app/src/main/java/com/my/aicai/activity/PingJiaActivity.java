package com.my.aicai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.my.aicai.R;
import com.my.aicai.adapter.PingJiaAdapter;
import com.my.aicai.entity.PingJiaEntity;


import java.util.ArrayList;
import java.util.List;


public class PingJiaActivity extends Activity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private ImageView iv_back;
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private List<PingJiaEntity> list=new ArrayList<>();
    private PingJiaAdapter adapter;
    private TextView tv_add;
    private String goodId;
    private String name;
    private String from;
    private String url;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjia);

        from=getIntent().getStringExtra("detail");
        goodId=getIntent().getStringExtra("goodId");
        name=getIntent().getStringExtra("name");
        url=getIntent().getStringExtra("url");
        price=getIntent().getStringExtra("price");
        initView();
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRefresh();
        initData();
    }

    private void initData() {
        list.clear();
        AVQuery<AVObject> query=new AVQuery<>("PingJiaEntity");
        query.whereEqualTo("goodId",goodId);
        query.orderByDescending("createAt");
        query.include("user");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        AVUser user=object.getAVUser("user");
                        String content=object.getString("content");
                        String name=object.getString("name");
                        String url=object.getString("url");
                        String price=object.getString("price");
                        String userName = object.getString("userName");
                        list.add(new PingJiaEntity(goodId,user,content,name,url,price,userName));
                    }
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        stopRefresh();

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

    private void initView() {
        tv_add= (TextView) findViewById(R.id.tv_add);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.refresh);

        if (from.equals("查看")){
            tv_add.setVisibility(View.GONE);
        }else {
            tv_add.setVisibility(View.VISIBLE);
        }

        lv= (ListView) findViewById(R.id.lv);
        adapter=new PingJiaAdapter(this,list);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                Intent intent=new Intent();
                intent.putExtra("goodId",goodId);
                intent.putExtra("name",name);
                intent.putExtra("url",url);
                intent.putExtra("price",price);
                intent.setClass(this,AddPingJiaActivity.class);
                startActivity(intent);
                break;

        }

    }

    @Override
    public void onRefresh() {
        initData();
    }
}
