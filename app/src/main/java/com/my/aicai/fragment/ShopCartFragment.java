package com.my.aicai.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.my.aicai.R;
import com.my.aicai.activity.AddressListActivity1;
import com.my.aicai.activity.PayMoneyActivity;
import com.my.aicai.adapter.ShopCartAdapter;
import com.my.aicai.entity.ShopCartEntity;
import com.my.aicai.widget.MyProgressDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by user999 on 2018/5/3.
 */

public class ShopCartFragment extends Fragment {
    private View view;
    private TextView tvClear;
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private TextView tvAddress;
    private TextView tvTotalPrice;
    private TextView tvGoodsNumber;
    private TextView tvOrderNumber;
    private TextView tvJieSuan;
    private List<ShopCartEntity> list = new ArrayList<>();
    private ShopCartAdapter adapter;
    private String shouhuoName;
    private String shouhuoPhone;
    private String orderID;

    MyProgressDialog progressDialog;

    private void assignViews() {
        progressDialog = new MyProgressDialog(getContext());
        tvClear = (TextView) view.findViewById(R.id.tv_clear);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        lv = (ListView) view.findViewById(R.id.lv);
        tvAddress = (TextView) view.findViewById(R.id.tv_address);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        tvGoodsNumber = (TextView) view.findViewById(R.id.tv_goods_number);
        tvOrderNumber = (TextView) view.findViewById(R.id.tv_order_number);
        tvJieSuan = (TextView) view.findViewById(R.id.tv_jie_suan);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop_cart, container, false);

        initView();
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startRefresh();
        initData();
    }

    private void initData() {
        list.clear();
        AVQuery<AVObject> query = new AVQuery<>("ShopCartEntity");
        query.include("user");
        query.whereEqualTo("user", AVUser.getCurrentUser());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                double p = 0;
                int n = 0;

                if (objects != null && objects.size() > 0) {
                    for (AVObject object : objects) {
                        String id = object.getObjectId();
                        String goodId = object.getString("goodId");
                        String url = object.getString("url");
                        String name = object.getString("name");
                        String price = object.getString("price");
                        String des = object.getString("des");
                        String type = object.getString("type");
                        String number = object.getString("number");
                        AVUser user = object.getAVUser("user");

                        n = n + Integer.parseInt(number);
                        p = p + (Integer.parseInt(number) * (Double.parseDouble(price)));
                        list.add(new ShopCartEntity(id, goodId, url, name, price, des, type, number, user));
                    }
                    DecimalFormat df = new DecimalFormat("#####0.00");
                    String str = df.format(p);
                    tvOrderNumber.setText(list.size() + "个订单");
                    tvGoodsNumber.setText(n + "个商品");
                    tvTotalPrice.setText(str);
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        stopRefresh();
    }


    private void initEvent() {
        adapter.setOnJianNumListener(new ShopCartAdapter.JianNumListener() {
            @Override
            public void jianWork(String totalPrice, String goodsNum) {
                tvTotalPrice.setText(totalPrice);
                tvGoodsNumber.setText(goodsNum + "个商品");
            }

            @Override
            public void delete(View view, final int position) {
                Log.e("HH", "1111");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("是否删除该条购物车记录？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AVQuery<AVObject> query = new AVQuery<AVObject>("ShopCartEntity");
                                try {
                                    AVObject object = query.get(list.get(position).getId());
                                    object.deleteInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                list.remove(position);
                                                Message message = new Message();
                                                message.what = 102;
                                                handler.sendMessage(message);
                                            }
                                        }
                                    });

                                } catch (AVException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setCancelable(true).create().show();
            }

        });


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });


        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddressListActivity1.class);
                startActivityForResult(intent, 100);
            }
        });

        tvJieSuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String address = tvAddress.getText().toString().trim();
                if (!address.equals("请选择地址")) {
//                    String money=AVUser.getCurrentUser().getString("money");
//                    final double a=Double.parseDouble(money);
//                    final double b=Double.parseDouble(tvTotalPrice.getText().toString().trim());
//                    if (a>=b){
                    //生成一个新的订单
                    final AVObject order = new AVObject("OrderClass");
                    order.put("user", AVUser.getCurrentUser());
                    order.put("OrderPhone", shouhuoPhone);
                    order.put("OrderAddress", tvAddress.getText().toString().trim());
                    order.put("goodsNum", tvGoodsNumber.getText().toString().trim());
                    order.put("shouhuoName", shouhuoName);
                    order.put("OrderPrice", tvTotalPrice.getText().toString().trim());
                    order.put("expressNum", "889304843384200747");
                    order.put("status", "0");
                    order.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                // 存储成功
                                Log.d(TAG, order.getObjectId());// 保存成功之后，objectId会自动从服务端加载到本地
                                orderID = order.getObjectId();

                                //保存订单中的商品
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Message message = new Message();
                                        message.what = 1001;
                                        handler.sendMessage(message);

                                    }
                                }).start();

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1500);//休眠3秒
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        Intent i = new Intent(getContext(), PayMoneyActivity.class);
                                        i.putExtra("orderID", orderID);
                                        startActivity(i);
                                        progressDialog.dismiss();
                                    }
                                }).start();

                            } else {
                                // 失败的话，请检查网络环境以及 SDK 配置是否正确
                            }
                        }
                    });


//                        double c=a-b;
//                        AVUser.getCurrentUser().put("money",c+"");
//                        AVUser.getCurrentUser().saveInBackground();


                    final AVQuery<AVObject> query = new AVQuery<>("ShopCartEntity");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                query.deleteAll();
                                adapter.setData(list);
                                Message message = new Message();
                                message.what = 100;
                                handler.sendMessage(message);

                            } catch (AVException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
//                    }
//                    else {
//                        Toast.makeText(getActivity(),"余额不足!", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(getActivity(), "请先选择好收货地址!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                final AVQuery<AVObject> query = new AVQuery<>("ShopCartEntity");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            query.deleteAll();
                            adapter.setData(list);

                            Message message = new Message();
                            message.what = 101;
                            handler.sendMessage(message);

                        } catch (AVException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
//
//            }
//        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    list.clear();
                    Toast.makeText(getActivity(), "结算成功!", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    tvTotalPrice.setText("0");
                    tvGoodsNumber.setText("0个商品");
                    tvOrderNumber.setText("0个订单");
                    break;
                case 101:
                    Toast.makeText(getActivity(), "清空成功!", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    tvTotalPrice.setText("0");
                    tvGoodsNumber.setText("0个商品");
                    tvOrderNumber.setText("0个订单");
                    break;
                case 102:
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                    double p = 0;
                    int n = 0;
                    for (ShopCartEntity entity : list) {
                        String number = entity.getNumber();
                        String price = entity.getPrice();

                        n = n + Integer.parseInt(number);
                        p = p + (Integer.parseInt(number) * (Double.parseDouble(price)));
                    }
                    DecimalFormat df = new DecimalFormat("#####0.00");
                    String str = df.format(p);
                    tvOrderNumber.setText(list.size() + "个订单");
                    tvGoodsNumber.setText(n + "个商品");
                    tvTotalPrice.setText(str);
                    Toast.makeText(getActivity(), "删除成功!", Toast.LENGTH_SHORT).show();
                    break;
                case 1001:
                    for (final ShopCartEntity entity : list) {
                        final AVObject object = new AVObject("BuyGoodsEntity");
                        object.put("shouhuoName", shouhuoName);
                        object.put("goodId", entity.getGoodId());
                        object.put("name", entity.getName());
                        object.put("url", entity.getUrl());
                        object.put("des", entity.getDes());
                        object.put("price", entity.getPrice());
                        object.put("number", entity.getNumber());
                        object.put("type", entity.getType());
                        object.put("address", tvAddress.getText().toString().trim());
                        object.put("phone", shouhuoPhone);
                        object.put("user", AVUser.getCurrentUser());
                        object.put("orderID", orderID);
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    AVQuery<AVObject> query = new AVQuery<AVObject>("ChangYongEntity");
                                    query.whereEqualTo("user", AVUser.getCurrentUser());
                                    query.whereEqualTo("goodId", entity.getGoodId());
                                    query.findInBackground(new FindCallback<AVObject>() {
                                        @Override
                                        public void done(List<AVObject> objects, AVException e) {
                                            if (objects != null && objects.size() > 0) {
                                                AVObject object1 = objects.get(0);
                                                String number = object1.getString("number");
                                                int num = Integer.parseInt(number);
                                                num = num + Integer.parseInt(entity.getNumber());
                                                object1.put("number", num + "");
                                                object1.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(AVException e) {
                                                        if (e == null) {
                                                        }
                                                    }
                                                });
                                            } else {
                                                AVObject object1 = new AVObject("ChangYongEntity");
                                                object1.put("goodId", entity.getGoodId());
                                                object1.put("name", entity.getName());
                                                object1.put("url", entity.getUrl());
                                                object1.put("des", entity.getDes());
                                                object1.put("price", entity.getPrice());
                                                object1.put("number", entity.getNumber());
                                                object1.put("type", entity.getType());
                                                object1.put("address", tvAddress.getText().toString().trim());
                                                object1.put("phone", AVUser.getCurrentUser().getUsername());
                                                object1.put("user", AVUser.getCurrentUser());
                                                object1.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(AVException e) {
                                                        if (e == null) {

                                                        }
                                                    }
                                                });

                                            }
                                        }
                                    });
                                } else {

                                }
                            }
                        });

                    }
                    break;
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            String address = data.getStringExtra("address");
            shouhuoName = data.getStringExtra("shouhuoName");
            shouhuoPhone = data.getStringExtra("shouhuoPhone");
            tvAddress.setText(address);
        }
    }

    private void initView() {
        assignViews();
        adapter = new ShopCartAdapter(getActivity(), list);
        lv.setAdapter(adapter);

        AVQuery<AVObject> query1 = new AVQuery<>("AddressEntity");
        query1.include("user");
        query1.whereEqualTo("user", AVUser.getCurrentUser());
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects != null && objects.size() > 0) {
                    AVObject object = objects.get(0);
                    String address = object.getString("address");
                    shouhuoName = object.getString("shouhuoName");
                    shouhuoPhone = object.getString("shouhuoPhone");
                    tvAddress.setText(address);
                } else {
                    tvAddress.setText("请选择地址");
                }
            }
        });

    }

    /**
     * 开始刷新
     */
    public void startRefresh() {
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
