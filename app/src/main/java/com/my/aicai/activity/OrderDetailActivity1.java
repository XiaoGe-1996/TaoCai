package com.my.aicai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.my.aicai.R;
import com.my.aicai.adapter.CourierAdapter;
import com.my.aicai.adapter.OrderGoodsAdapter;
import com.my.aicai.adapter.OrderGoodsAdapter1;
import com.my.aicai.entity.BuyGoodsEntity;
import com.my.aicai.entity.CourierData;
import com.my.aicai.util.GsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gc on 2018/5/12.
 * PackageName: com.my.aicai.activity
 * Desc:
 */

public class OrderDetailActivity1 extends AppCompatActivity {

    private ImageView ivBack;
    private RecyclerView rv_goods;
    private RecyclerView rv_express;
    private List<BuyGoodsEntity> list=new ArrayList<>();
    private OrderGoodsAdapter1 orderGoodsAdapter;

    private List<CourierData> courierDatas = new ArrayList<>();
    CourierAdapter courierAdapter;

    private String orderID,status;
    private TextView tv_user_name;
    private TextView tv_order_phone;
    private TextView tv_order_address;
    private TextView goods_num;
    private TextView sum_amount;
    private TextView tv_express;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail1);

        orderID = getIntent().getStringExtra("orderID");
        status = getIntent().getStringExtra("status");
        initView();
        initEvent();
    }

    private void initView() {

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_order_phone = (TextView) findViewById(R.id.tv_order_phone);
        tv_order_address = (TextView) findViewById(R.id.tv_order_address);
        tv_express = (TextView) findViewById(R.id.tv_express);
        goods_num = (TextView) findViewById(R.id.goods_num);
        sum_amount = (TextView) findViewById(R.id.sum_amount);
        rv_goods = (RecyclerView) findViewById(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        orderGoodsAdapter = new OrderGoodsAdapter1(this,list,status);
        rv_goods.setAdapter(orderGoodsAdapter);

        rv_express = (RecyclerView) findViewById(R.id.rv_express);
        rv_express.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });


        //获取到订单的信息
        AVQuery<AVObject> avQuery = new AVQuery<>("OrderClass");
        avQuery.getInBackground(orderID, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                // object 就是 id 为 558e20cbe4b060308e3eb36c 的 Todo 对象实例
                tv_user_name.setText("收货人："+avObject.getString("shouhuoName"));
                tv_order_phone.setText(avObject.getString("OrderPhone"));
                tv_order_address.setText("收货地址："+avObject.getString("OrderAddress"));
                goods_num.setText(avObject.getString("goodsNum"));
                sum_amount.setText(avObject.getString("OrderPrice"));
                switch (avObject.getString("status")) {
                    case "2":
                    case "3":
                        //拼接查询物流信息URL
                        tv_express.setText("物流信息：");
                        String url = "http://v.juhe.cn/exp/index?key=6625426f11cac784e70a67bc58512417"+"&com="+avObject.getString("com")+"&no="+avObject.getString("expressNum");
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
//                            UtilTools.showShrotToast(getApplicationContext(),t);
                                //解析json
                                parsingJson(t);
                                courierAdapter = new CourierAdapter(getApplicationContext(),courierDatas);
                                rv_express.setAdapter(courierAdapter);
                            }
                        });
                        break;
                    default:
                        tv_express.setText("物流信息：无");
                        break;
                }
            }
        });
        //获取到订单的商品的信息
        AVQuery<AVObject> query=new AVQuery<>("BuyGoodsEntity");
        query.whereEqualTo("orderID",orderID);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        String id=object.getObjectId();
                        String goodsID=object.getString("goodId");
                        String url=object.getString("url");
                        String name=object.getString("name");
                        String price=object.getString("price");
                        String des=object.getString("des");
                        String type=object.getString("type");
                        String number=object.getString("number");
                        AVUser user = object.getAVUser("user");
                        String address=object.getString("address");
                        String phone=object.getString("phone");
                        String orderID=object.getString("orderID");
                        list.add(new BuyGoodsEntity(id,goodsID,url,name,price,des,type,number,user,address,phone,orderID));
                    }
                    orderGoodsAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void initEvent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        orderGoodsAdapter.setOnMyClick(new OrderGoodsAdapter1.onMyClick() {
            @Override
            public void pinglunClick(View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("goodId",list.get(position).getGoodId());
                intent.putExtra("url",list.get(position).getUrl());
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("price",list.get(position).getPrice());
                intent.putExtra("des",list.get(position).getDes());
                intent.putExtra("type",list.get(position).getType());
                intent.putExtra("number",list.get(position).getNumber());

                intent.setClass(OrderDetailActivity1.this,OrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String jsonArray = String.valueOf(jsonResult.getJSONArray("list"));

            courierDatas = GsonUtils.jsonToArrayList(jsonArray, CourierData.class);
            if (courierDatas.size() < 0) {
                tv_express.setText("物流信息：无");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
