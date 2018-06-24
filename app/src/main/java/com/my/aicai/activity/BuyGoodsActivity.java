package com.my.aicai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.my.aicai.R;

import java.util.List;

public class BuyGoodsActivity extends Activity implements View.OnClickListener{
    private String goodId,name,url,des,price,number,type,address,shouhuoName,shouhuoPhone;
    private ImageView iv_back;
    private TextView tv_goodsname,tv_shoujia,tv_address,tv_phone,tv_number,tv_ok,tv_shouhuo_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_goods);

        goodId=getIntent().getStringExtra("goodId");
        name=getIntent().getStringExtra("name");
        url=getIntent().getStringExtra("url");
        des=getIntent().getStringExtra("des");
        price=getIntent().getStringExtra("price");
        number=getIntent().getStringExtra("number");
        type=getIntent().getStringExtra("type");
        address=getIntent().getStringExtra("address");
        shouhuoName=getIntent().getStringExtra("shouhuoName");
        shouhuoPhone=getIntent().getStringExtra("shouhuoPhone");

        initView();
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    private void initView() {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        tv_goodsname= (TextView) findViewById(R.id.tv_goodsname);
        tv_shoujia= (TextView) findViewById(R.id.tv_shoujia);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_phone= (TextView) findViewById(R.id.tv_phone);
        tv_ok= (TextView) findViewById(R.id.tv_ok);
        tv_number= (TextView) findViewById(R.id.tv_number);
        tv_shouhuo_name= (TextView) findViewById(R.id.tv_shouhuo_name);

        tv_goodsname.setText(name);
        tv_address.setText(address);
        tv_shoujia.setText(price+"元");
        tv_phone.setText(shouhuoPhone);
        tv_number.setText(number);
        tv_shouhuo_name.setText(shouhuoName);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_ok:
                String money=AVUser.getCurrentUser().getString("money");
                final double a=Float.parseFloat(money);
                final double b=(Double.parseDouble(price))*(Integer.parseInt(number));
                if (a>=b){
                    final AVObject object=new AVObject("BuyGoodsEntity");
                    object.put("goodId",goodId);
                    object.put("name",name);
                    object.put("shouhuoName",shouhuoName);
                    object.put("url",url);
                    object.put("des",des);
                    object.put("price",price);
                    object.put("number",number);
                    object.put("type",type);
                    object.put("address",address);
                    object.put("phone", shouhuoPhone);
                    object.put("user", AVUser.getCurrentUser());
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null){
                                Toast.makeText(BuyGoodsActivity.this,"购买成功!", Toast.LENGTH_SHORT).show();
                                double c=a-b;
                                AVUser.getCurrentUser().put("money",c+"");
                                AVUser.getCurrentUser().saveInBackground();


                                AVQuery<AVObject> query=new AVQuery<AVObject>("ChangYongEntity");
                                query.whereEqualTo("user",AVUser.getCurrentUser());
                                query.whereEqualTo("goodId",goodId);
                                query.findInBackground(new FindCallback<AVObject>() {
                                    @Override
                                    public void done(List<AVObject> objects, AVException e) {
                                        if (objects!=null && objects.size()>0){
                                            AVObject object1=objects.get(0);
                                            String num1=object1.getString("number");
                                            int num=Integer.parseInt(num1);
                                            num=num+Integer.parseInt(number);
                                            object1.put("number",num+"");
                                            object1.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(AVException e) {
                                                    if (e==null){

                                                    }
                                                }
                                            });
                                        }else {
                                            AVObject object1=new AVObject("ChangYongEntity");
                                            object1.put("goodId",goodId);
                                            object1.put("name",name);
                                            object1.put("url",url);
                                            object1.put("des",des);
                                            object1.put("price",price);
                                            object1.put("number",number);
                                            object1.put("type",type);
                                            object1.put("address",address);
                                            object1.put("phone", AVUser.getCurrentUser().getUsername());
                                            object1.put("user", AVUser.getCurrentUser());
                                            object1.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(AVException e) {
                                                    if (e==null){

                                                    }
                                                }
                                            });

                                        }
                                    }
                                });


                                finish();
                            }
                        }
                    });
                }else {
                    Toast.makeText(BuyGoodsActivity.this,"余额不足!", Toast.LENGTH_SHORT).show();
                }



                break;
        }
    }
}
