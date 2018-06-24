package com.my.aicai.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.my.aicai.R;
import com.my.aicai.fragment.GoodsFragment;
import com.my.aicai.fragment.HomeFragment;
import com.my.aicai.fragment.MeFragment;
import com.my.aicai.fragment.RecommendFragment;
import com.my.aicai.fragment.ShopCartFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private HomeFragment homeFragment;
    private GoodsFragment goodsFragment;
    private ShopCartFragment shopCartFragment;
    private RecommendFragment recommendFragment;
    private MeFragment meFragment;
    private Fragment[] fragments;
    private RelativeLayout[] TAB;
    private ImageView[] IMAGE;
    private int index;
    private int currentTabIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    private void initView() {
        if (null == homeFragment) {
            homeFragment = new HomeFragment();
        }
        if (null == goodsFragment){
            goodsFragment=new GoodsFragment();
        }
        if (null == shopCartFragment){
            shopCartFragment=new ShopCartFragment();
        }
        if (null == recommendFragment){
            recommendFragment=new RecommendFragment();
        }
        if (null == meFragment){
            meFragment=new MeFragment();
        }

        fragments = new Fragment[]{homeFragment,goodsFragment,shopCartFragment,recommendFragment ,meFragment};
        TAB = new RelativeLayout[5];
        IMAGE = new ImageView[5];
        TAB[0] = (RelativeLayout) findViewById(R.id.rl_home);
        TAB[1] = (RelativeLayout) findViewById(R.id.rl_goods);
        TAB[2] = (RelativeLayout) findViewById(R.id.rl_shopcart);
        TAB[3] = (RelativeLayout) findViewById(R.id.rl_recommend);
        TAB[4] = (RelativeLayout) findViewById(R.id.rl_me);
        IMAGE[0] = (ImageView) findViewById(R.id.iv_home);
        IMAGE[1] = (ImageView) findViewById(R.id.iv_goods);
        IMAGE[2] = (ImageView) findViewById(R.id.iv_shopcart);
        IMAGE[3] = (ImageView) findViewById(R.id.iv_recommend);
        IMAGE[4] = (ImageView) findViewById(R.id.iv_me);
        IMAGE[0].setSelected(true);
        TAB[0].setOnClickListener(this);
        TAB[1].setOnClickListener(this);
        TAB[2].setOnClickListener(this);
        TAB[3].setOnClickListener(this);
        TAB[4].setOnClickListener(this);
//        TAB[3].setOnClickListener(this);

        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment, "home")
                .add(R.id.fragment_container, goodsFragment, "goods")
                .add(R.id.fragment_container,shopCartFragment,"shopcart")
                .add(R.id.fragment_container,recommendFragment,"recommend")
                .add(R.id.fragment_container,meFragment,"me")
                .hide(goodsFragment)
                .hide(shopCartFragment)
                .hide(recommendFragment)
                .hide(meFragment)
                .show(homeFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_home:
                index = 0;
                break;
            case R.id.rl_goods:
                index =1;
                break;
            case R.id.rl_shopcart:
                index = 2;
                break;
            case R.id.rl_recommend:
                index =3;
                break;
            case R.id.rl_me:
                index =4;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        IMAGE[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        IMAGE[index].setSelected(true);
        currentTabIndex = index;
    }
}
