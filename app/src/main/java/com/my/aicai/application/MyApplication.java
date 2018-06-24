package com.my.aicai.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.avos.avoscloud.AVOSCloud;
import com.my.aicai.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;

import cn.jpush.android.api.JPushInterface;


public class MyApplication extends Application {
    private static Context mContext;
    //默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH= Environment.getExternalStorageDirectory()+ File.separator+"爱菜"+ File.separator+"Images"+ File.separator;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;

        //将在leancloud后台中获得的唯一APPKey和APPId传入该方法中进行初始化，即可连接到leancloud中创建的对应的应用
        AVOSCloud.initialize(this,"nwxt8t5GGEwTR5ujqXCqf5nz-gzGzoHsz","QPQk2z0q7ExTRL5WQY2wQ3Hr");
        initImageLoader();
        /**极光推送初始化*/
        JPushInterface.setDebugMode(true);// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        ZXingLibrary.initDisplayOpinion(this);
    }


    /**
     * 获取全局的context
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        DisplayImageOptions options=new DisplayImageOptions.Builder().showImageForEmptyUri(R.color.bg_no_photo)
                .showImageOnFail(R.color.bg_no_photo).showImageOnLoading(R.color.bg_no_photo).cacheInMemory(true)
                .cacheOnDisk(true).build();

        File cacheDir=new File(DEFAULT_SAVE_IMAGE_PATH);
        ImageLoaderConfiguration imageLoaderConfiguration=new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(200)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(options).build();

        ImageLoader.getInstance().init(imageLoaderConfiguration);

    }

}
