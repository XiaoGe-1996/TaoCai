package com.my.aicai.shop;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.my.aicai.R;
import com.my.aicai.widget.MyProgressDialog;

import java.io.FileNotFoundException;

import static android.R.id.list;

/**
 * Created by Gc on 2018/5/13.
 * PackageName: com.my.aicai.shop
 * Desc:
 */

public class UpdateGoodsActicity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_name;
    private TextView tv_type;
    private TextView tv_delete;
    private TextView tv_save;
    private EditText et_price;
    private EditText et_des;

    private ImageView iv_goodsImg;
    private MyProgressDialog myProgressDialog;

    private String id;
    private String url;
    private String name;
    private String price;
    private String des;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_goods);

        id = getIntent().getStringExtra("id");
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        des = getIntent().getStringExtra("des");
        type = getIntent().getStringExtra("type");

        initView();
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
    }

    private void initView() {
        myProgressDialog = new MyProgressDialog(this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_goodsImg = (ImageView) findViewById(R.id.iv_goodsImg);
        tv_name = (TextView) findViewById(R.id.tv_name);
        et_price = (EditText) findViewById(R.id.et_price);
        et_price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et_des = (EditText) findViewById(R.id.et_des);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_delete = (TextView) findViewById(R.id.tv_delete);

        tv_name.setText(name);
        et_price.setText(price);
        et_des.setText(des);
        Glide.with(this).load(url).dontAnimate().into(iv_goodsImg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                price = et_price.getText().toString().trim();
                des = et_des.getText().toString().trim();

                if (!name.isEmpty() && name.length() > 0 && !price.isEmpty() && price.length() > 0 && !des.isEmpty() && des.length() > 0) {
                    myProgressDialog.show();
                    AVObject save = AVObject.createWithoutData("Goods", id);

                    // 修改 content
                    save.put("price", price);
                    save.put("des", des);
                    // 保存到云端
                    save.saveInBackground();
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                    myProgressDialog.dismiss();
                } else {
                    Toast.makeText(this, "请填写正确的信息", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("是否删除该商品？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AVQuery<AVObject> query = new AVQuery<AVObject>("Goods");
                                try {
                                    AVObject object = query.get(id);
                                    object.deleteInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                                finish();
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
                break;
        }
    }
}
