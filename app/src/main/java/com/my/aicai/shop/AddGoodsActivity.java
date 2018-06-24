package com.my.aicai.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
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
import com.avos.avoscloud.SaveCallback;
import com.my.aicai.R;
import com.my.aicai.activity.SubmitImageActivity;
import com.my.aicai.util.AbSharedUtil;
import com.my.aicai.widget.MyProgressDialog;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Gc on 2018/5/13.
 * PackageName: com.my.aicai.shop
 * Desc:
 */

public class AddGoodsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private EditText et_name;
    private EditText et_price;
    private EditText et_des;

    private ImageView iv_goodsImg;

    private TextView tv_add;

    private Spinner sp_type;

    private static final int Take_Photo = 0;
    String type;
    private boolean isImg = false;
    private MyProgressDialog myProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);

        File sd= Environment.getExternalStorageDirectory();
        String path=sd.getPath()+"/爱菜";
        File file=new File(path);
        if(!file.exists())
            file.mkdir();
        type = "叶菜类";
        initView();
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(this);
        iv_goodsImg.setOnClickListener(this);
        tv_add.setOnClickListener(this);
    }

    private void initView() {
        myProgressDialog = new MyProgressDialog(this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_goodsImg = (ImageView) findViewById(R.id.iv_goodsImg);
        et_name = (EditText) findViewById(R.id.et_name);
        et_price = (EditText) findViewById(R.id.et_price);
        et_price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et_des = (EditText) findViewById(R.id.et_des);
        tv_add = (TextView) findViewById(R.id.tv_add);
        sp_type = (Spinner) findViewById(R.id.sp_type);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                final String name = et_name.getText().toString().trim();
                String price = et_price.getText().toString().trim();
                String des = et_des.getText().toString().trim();
                type = sp_type.getSelectedItem().toString();
                if (isImg) {
                    if (!name.isEmpty() && name.length() > 0 && !price.isEmpty() && price.length() > 0 && !des.isEmpty() && des.length() > 0) {
                        myProgressDialog.show();
                        final AVObject object = new AVObject("Goods");
                        object.put("name", name);
                        object.put("type", type);
                        object.put("remen", "否");
                        object.put("tuijian", "NO");
                        object.put("price", price);
                        object.put("des", des);
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
//                                Toast.makeText(AddGoodsActivity.this, "添加成功!", Toast.LENGTH_SHORT).show();
//                                finish();
                                    AVFile file = null;
                                    try {
                                        file = AVFile.withAbsoluteLocalPath(imageName, "/sdcard/爱菜/" + imageName);
                                        object.put("pic", file);
                                        object.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                myProgressDialog.dismiss();
                                                if (e == null) {
                                                    Toast.makeText(getApplicationContext(), "添加商品成功", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "图片上传失败", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } catch (FileNotFoundException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "请填写正确的信息", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "还没有选择封面图片", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.iv_goodsImg:
                Intent intent = new Intent();
                intent.setClass(this, SubmitImageActivity.class);
                startActivityForResult(intent, Take_Photo);
                break;
        }
    }

    String imageName;

    @SuppressLint("SdCardPath")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == this.RESULT_OK) {
            switch (requestCode) {
                case Take_Photo:
                    if (data != null) {
                        imageName = data.getStringExtra("imageName");
                        //返回数据了
                        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/爱菜/"
                                + imageName);
                        iv_goodsImg.setImageBitmap(bitmap);
                        AbSharedUtil.putString(this, "imageName", imageName);
//                        updateAvatarInServer(imageName);
                        isImg = true;
                    }
                    break;


            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
