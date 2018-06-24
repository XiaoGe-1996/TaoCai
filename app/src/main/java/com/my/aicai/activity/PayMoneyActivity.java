package com.my.aicai.activity;

import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.my.aicai.R;
import com.my.aicai.adapter.OrderGoodsAdapter;
import com.my.aicai.entity.BuyGoodsEntity;
import com.my.aicai.entity.GoodsEntity;
import com.my.aicai.entity.OrderClass;
import com.my.aicai.widget.MyProgressDialog;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by Gc on 2018/5/10.
 * PackageName: com.my.aicai.activity
 * Desc:
 */

public class PayMoneyActivity extends AppCompatActivity{

    private ImageView ivBack;
    private RecyclerView rv_goods;
    private List<BuyGoodsEntity> list=new ArrayList<>();
    private OrderGoodsAdapter orderGoodsAdapter;

    private String orderID;
    private TextView tv_user_name;
    private TextView tv_order_phone;
    private TextView tv_order_address;
    private TextView goods_num;
    private TextView sum_amount;
    private TextView re_pay_amount;
    private TextView payment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        orderID = getIntent().getStringExtra("orderID");

        initView();
        initEvent();
    }

    private void initView() {

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_order_phone = (TextView) findViewById(R.id.tv_order_phone);
        tv_order_address = (TextView) findViewById(R.id.tv_order_address);
        goods_num = (TextView) findViewById(R.id.goods_num);
        sum_amount = (TextView) findViewById(R.id.sum_amount);
        re_pay_amount = (TextView) findViewById(R.id.re_pay_amount);
        payment = (TextView) findViewById(R.id.payment);
        rv_goods = (RecyclerView) findViewById(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        orderGoodsAdapter = new OrderGoodsAdapter(this,list);
        rv_goods.setAdapter(orderGoodsAdapter);

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
                re_pay_amount.setText(avObject.getString("OrderPrice"));
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
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money= AVUser.getCurrentUser().getString("money");
                final double a=Double.parseDouble(money);
                final double b=Double.parseDouble(re_pay_amount.getText().toString().trim());

                if (a >= b) {
                    double c = a - b;
                    AVUser.getCurrentUser().put("money", c + "");
                    AVUser.getCurrentUser().saveInBackground();
                    AVObject order = AVObject.createWithoutData("OrderClass",orderID);
                    // 修改 content
                    order.put("status",1+"");
                    // 保存到云端
                    order.saveInBackground();
                    Toast.makeText(getApplicationContext(),"付款成功!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"余额不足!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
