package com.my.aicai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.aicai.R;
import com.my.aicai.entity.ShopCartEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class ShopCartAdapter extends BaseAdapter {
    private Context context;
    private List<ShopCartEntity> list;

    public ShopCartAdapter(Context context, List<ShopCartEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<ShopCartEntity> list){
        this.list=list;
    }

    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View view1= LayoutInflater.from(context).inflate(R.layout.list_item_shop_cart,null);

        ImageView ivPic=(ImageView) view1.findViewById(R.id.iv_pic);;
        TextView tvName = (TextView) view1.findViewById(R.id.tv_name);
        TextView tvPrice = (TextView) view1.findViewById(R.id.tv_price);
        TextView tvMinus = (TextView) view1.findViewById(R.id.tv_minus);
        ImageView ivDelete = (ImageView) view1.findViewById(R.id.iv_delete);
        final TextView tvNumber = (TextView) view1.findViewById(R.id.tv_number);
        TextView tvAdd = (TextView) view1.findViewById(R.id.tv_add);

        ImageLoader.getInstance().displayImage(list.get(position).getUrl(),ivPic,options);
        tvName.setText(list.get(position).getName());
        tvPrice.setText(list.get(position).getPrice()+"元/斤");

        tvNumber.setText(list.get(position).getNumber());

        tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=tvNumber.getText().toString().trim();
                int num=Integer.parseInt(number);
                if (num>1){
                    num=num-1;
                    tvNumber.setText(num+"");
                    list.get(position).setNumber(num+"");
                }

                listener.jianWork(getTotalPrice(),getGoodsNumber());
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number1=tvNumber.getText().toString().trim();
                int num1=Integer.parseInt(number1);
                num1=num1+1;
                tvNumber.setText(num1+"");
                list.get(position).setNumber(num1+"");

                listener.jianWork(getTotalPrice(),getGoodsNumber());
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.delete(v,position);
            }
        });

        return view1;
    }




    public interface JianNumListener{
        void jianWork(String totalPrice,String goodsNum);

        void delete(View view , int position);
    }

    public JianNumListener listener;

    public void setOnJianNumListener(JianNumListener listener){
        this.listener=listener;
    }



    private String getTotalPrice(){
        if (list!=null && list.size()>0){
            double p=0;
            for (ShopCartEntity entity : list){
                String price=entity.getPrice();
                String number=entity.getNumber();
                p=p+(Integer.parseInt(number))*(Double.parseDouble(price));
            }
            DecimalFormat df = new DecimalFormat("#####0.00");
            String str = df.format(p);
            return str;
        }else {
            return "0";
        }
    }


    private String getGoodsNumber(){
        if (list!=null && list.size()>0){
            int g=0;
            for (ShopCartEntity entity : list){
                String number=entity.getNumber();
                g=g+(Integer.parseInt(number));
            }
            return g+"";
        }else {
            return "0";
        }
    }




    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.color.bg_no_photo)
            .showImageForEmptyUri(R.color.bg_no_photo)
            .showImageOnFail(R.color.bg_no_photo)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
            .build();
}
