package com.my.aicai.fragment;

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

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.my.aicai.R;
import com.my.aicai.activity.GoodsDetailActivity;
import com.my.aicai.adapter.HomeAdapter;
import com.my.aicai.entity.GoodsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐
 */

public class RecommendFragment extends Fragment {
    private View view;
    private SwipeRefreshLayout refreshLayout;
    private GridView gridView;
    private List<GoodsEntity> list=new ArrayList<>();
    private HomeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_recommend,container,false);

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
                intent.setClass(getActivity(), GoodsDetailActivity.class);
                startActivity(intent);

            }
        });

    }

    private void initView() {
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        gridView= (GridView) view.findViewById(R.id.gridView);
        adapter=new HomeAdapter(getActivity(),list);
        gridView.setAdapter(adapter);
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
        query.include("user");
        query.whereEqualTo("tuijian","OK");
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
