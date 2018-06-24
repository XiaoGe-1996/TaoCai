package com.my.aicai.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.my.aicai.fragment.CongJiangFragment;
import com.my.aicai.fragment.DouFragment;
import com.my.aicai.fragment.GenJingFragment;
import com.my.aicai.fragment.JunGuFragment;
import com.my.aicai.fragment.QieGuoFragment;
import com.my.aicai.fragment.TeCaiFragment;
import com.my.aicai.fragment.YeCaiFragment;


/**
 * Created by shan_yao on 2016/6/17.
 */
public class FragmentFactory {

    public static Fragment createForExpand(int position,int action) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new YeCaiFragment();
                break;
            case 1:
                fragment = new GenJingFragment();
                break;
            case 2:
                fragment = new QieGuoFragment();
                break;
            case 3:
                fragment = new CongJiangFragment();
                break;
            case 4:
                fragment = new JunGuFragment();
                break;
            case 5:
                fragment = new DouFragment();
                break;
            case 6:
                fragment = new TeCaiFragment();
                break;

        }
        Bundle bundle = new Bundle();
        bundle.putInt("action",action);
        fragment.setArguments(bundle);
        return fragment;
    }
}
