package com.my.aicai.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.my.aicai.R;
import com.my.aicai.activity.ChangYongActivity;
import com.my.aicai.activity.GoodsDetailActivity;
import com.my.aicai.activity.MainActivity;
import com.my.aicai.activity.MyOrderActivity;
import com.my.aicai.activity.OrderListActivity;
import com.my.aicai.activity.ReMenActivity;
import com.my.aicai.adapter.HomeAdapter;
import com.my.aicai.adapter.MyOrderAdapter;
import com.my.aicai.entity.GoodsEntity;
import com.my.aicai.util.ImageSlideshow;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageSlideshow slideView;
    private LinearLayout llChangyong;
    private LinearLayout llRemen;
    private LinearLayout llDingdan;
    private GridView gridView;
    private List<GoodsEntity> list = new ArrayList<>();
    private HomeAdapter adapter;

    private ImageView iv_code;

    private void assignViews() {
        slideView = (ImageSlideshow) view.findViewById(R.id.slide_view);
        llChangyong = (LinearLayout) view.findViewById(R.id.ll_changyong);
        llRemen = (LinearLayout) view.findViewById(R.id.ll_remen);
        llDingdan = (LinearLayout) view.findViewById(R.id.ll_dingdan);
        gridView = (GridView) view.findViewById(R.id.gridView);
        iv_code = (ImageView) view.findViewById(R.id.iv_code);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initView();
        initEvent();
        initImages();

        // 为ImageSlideshow设置数据
        slideView.setDotSpace(12);
        slideView.setDotSize(12);
        slideView.setDelay(3000);

        slideView.setOnItemClickListener(new ImageSlideshow.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        break;
                }
            }
        });

        slideView.commit();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        list.clear();
        AVQuery<AVObject> query = new AVQuery<>("Goods");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects != null && objects.size() > 0) {
                    int a = 0;
                    for (AVObject object : objects) {
                        String id = object.getObjectId();
                        AVFile pic = object.getAVFile("pic");
                        String name = object.getString("name");
                        String price = object.getString("price");
                        String des = object.getString("des");
                        String type = object.getString("type");
                        if (a < 6) {
                            list.add(new GoodsEntity(id, pic, name, price, des, type));
                        }
                        a++;
                    }
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 初始化轮播图
     */
    private void initImages() {
        String[] imageUrls = {"http://lc-nwxt8t5g.cn-n1.lcfile.com/c8b5393927f1d61b17f0.jpg",
                "http://lc-nwxt8t5g.cn-n1.lcfile.com/62d05c1f493fdbfea2f6.jpg",
                "http://lc-nwxt8t5g.cn-n1.lcfile.com/f0e38dc40d9eed01b18d.jpg",
        };
        String[] titles = {"", "", ""};
        for (int i = 0; i < 3; i++) {
            slideView.addImageTitle(imageUrls[i], titles[i]);
        }
    }

    @Override
    public void onDestroyView() {
        // 释放资源
        slideView.releaseResource();
        super.onDestroyView();

    }


    private void initEvent() {
        llChangyong.setOnClickListener(this);
        llRemen.setOnClickListener(this);
        llDingdan.setOnClickListener(this);
        iv_code.setOnClickListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("url", list.get(position).getPic().getUrl());
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("price", list.get(position).getPrice());
                intent.putExtra("des", list.get(position).getDes());
                intent.putExtra("type", list.get(position).getType());
                intent.setClass(getActivity(), GoodsDetailActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initView() {
        assignViews();
        adapter = new HomeAdapter(getActivity(), list);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_changyong:
                intent = new Intent();
                intent.setClass(getActivity(), ChangYongActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_remen:
                intent = new Intent();
                intent.setClass(getActivity(), ReMenActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_dingdan:
                intent = new Intent();
                intent.setClass(getActivity(), OrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_code:
                intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, 100);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == 100) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse(result);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
