package com.my.aicai.fragment;

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

import com.my.aicai.R;
import com.my.aicai.application.MyApplication;
import com.my.aicai.util.CommonUtils;
import com.my.aicai.util.FragmentFactory;
import com.my.aicai.util.TabPageIndicator;

/**
 * Created by user999 on 2018/5/4.
 */

public class GoodsFragment extends Fragment {
    private View view;
    private TabPageIndicator indicator;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_goods,container,false);

        initView();

        return view;
    }

    private void initView() {
        indicator= (TabPageIndicator) view.findViewById(R.id.indicator);
        viewPager= (ViewPager) view.findViewById(R.id.viewPager);
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


    class BasePagerAdapter extends FragmentPagerAdapter {
        String[] titles;

        public BasePagerAdapter(FragmentManager fm) {
            super(fm);
            this.titles = CommonUtils.getStringArray(R.array.expand_titles);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createForExpand(position,0);
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
