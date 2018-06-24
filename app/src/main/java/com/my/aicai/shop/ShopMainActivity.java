package com.my.aicai.shop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.my.aicai.R;
import com.my.aicai.fragment.HomeFragment;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;

/**
 * Created by Gc on 2018/5/13.
 * PackageName: com.my.aicai.shop
 * Desc:
 */

public class ShopMainActivity extends AppCompatActivity {

    private SViewPager viewPager;
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;

    private ShopGoodsFragment shopGoodsFragment;
    private ShopOrderFragment shopOrderFragment;
    private ShopMeFragment shopMeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);

        initView();
        
    }

    private void initView() {

        viewPager = (SViewPager) findViewById(R.id.tabmain_viewPager);
        indicator = (FixedIndicatorView) findViewById(R.id.tabmain_indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(0xFF68c964,Color.BLACK));
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        // 禁止viewpager的滑动事件
        viewPager.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(3);

    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"商品", "订单", "我的"};
        private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector};
        //        private String[] tabNames = {"首页", "客服", " "};
//        private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_3_selector,
//                R.drawable.bg_transparent};
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tab_main, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
            return textView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            switch (position) {
                case 0:
                    shopGoodsFragment = new ShopGoodsFragment();
                    return shopGoodsFragment;
                case 1:
                    shopOrderFragment = new ShopOrderFragment();
                    return shopOrderFragment;
                case 2:
                    shopMeFragment = new ShopMeFragment();
                    return shopMeFragment;
                default:
                    return null;
            }
        }
    }
}
