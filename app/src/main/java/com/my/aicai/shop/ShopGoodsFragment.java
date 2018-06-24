package com.my.aicai.shop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.my.aicai.R;
import com.my.aicai.application.MyApplication;
import com.my.aicai.util.CommonUtils;
import com.my.aicai.util.FragmentFactory;
import com.my.aicai.util.TabPageIndicator;

/**
 * Created by user999 on 2018/5/4.
 */

public class ShopGoodsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TabPageIndicator indicator;
    private ViewPager viewPager;
    private TextView tv_add_goods;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_shop_goods,container,false);

        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        tv_add_goods.setOnClickListener(this);
    }

    private void initView() {
        indicator= (TabPageIndicator) view.findViewById(R.id.indicator);
        viewPager= (ViewPager) view.findViewById(R.id.viewPager);
        tv_add_goods = (TextView) view.findViewById(R.id.tv_add_goods);
        BasePagerAdapter adapter = new BasePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        setTabPagerIndicator();
    }

    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_NOSAME);// 设置模式，一定要先设置模式
//        indicator.setDividerColor(Color.parseColor("#00bbcf"));// 设置分割线的颜色
        indicator.setDividerColor(Color.parseColor("#00000000"));// 设置分割线的颜色
        indicator.setDividerPadding(CommonUtils.dip2px(MyApplication.getContext(), 50));
        indicator.setIndicatorColor(Color.parseColor("#68c964"));// 设置底部导航线的颜色
        indicator.setTextColorSelected(Color.parseColor("#68c964"));// 设置tab标题选中的颜色
        indicator.setTextColor(Color.parseColor("#797979"));// 设置tab标题未被选中的颜色
        indicator.setTextSize(CommonUtils.sp2px(MyApplication.getContext(), 20));// 设置字体大小
        indicator.setTabPaddingLeftRight(CommonUtils.sp2px(MyApplication.getContext(), 40));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_goods:
                Intent i = new Intent(getContext(), AddGoodsActivity.class);
                startActivity(i);
                break;
        }
    }


    class BasePagerAdapter extends FragmentPagerAdapter {
        String[] titles;

        public BasePagerAdapter(FragmentManager fm) {
            super(fm);
            this.titles = CommonUtils.getStringArray(R.array.expand_titles);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createForExpand(position,1);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
