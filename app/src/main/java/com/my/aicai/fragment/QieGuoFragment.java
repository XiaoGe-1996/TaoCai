package com.my.aicai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.my.aicai.R;
import com.my.aicai.activity.GoodsDetailActivity;
import com.my.aicai.adapter.HomeAdapter;
import com.my.aicai.entity.GoodsEntity;
import com.my.aicai.shop.UpdateGoodsActicity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class QieGuoFragment extends Fragment {
    private View view;
    private GridView gridView;
    private List<GoodsEntity> list=new ArrayList<>();
    private HomeAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_yecai,container,false);

        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
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
                int action = getArguments().getInt("action");
                if (action == 0) {
                    intent.setClass(getActivity(), GoodsDetailActivity.class);
                } else {
                    intent.setClass(getActivity(), UpdateGoodsActicity.class);
                }
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
        query.whereEqualTo("type","茄果类");
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
                        list.add(new GoodsEntity(id,pic,name,price,des,"茄果类"));
                    }
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        stopRefresh();
    }

    private void initView() {
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        gridView= (GridView) view.findViewById(R.id.gridView);
        adapter=new HomeAdapter(getActivity(),list);
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
