package com.my.aicai.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.aicai.R;


/**
 * Created by fengshuai on 15/8/15.
 */
public class MyProgressDialog extends Dialog {

    private Context context;
    private String progressText;
    private LoadingCancelCallBack loadingCancel;

    public MyProgressDialog(Context context) {
        super(context, R.style.dialog_theme);
        this.context = context;
    }

    public MyProgressDialog(Context context, String progressText) {
        super(context, R.style.dialog_theme);
        this.context = context;
        this.progressText = progressText;
    }


    public void setLoadingCancel(LoadingCancelCallBack loadingCancel) {
        this.loadingCancel = loadingCancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        ImageView iv_progress = (ImageView) findViewById(R.id.iv_progress);
        RotateAnimation rotateAnimation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        iv_progress.startAnimation(rotateAnimation);
        TextView title = (TextView) findViewById(R.id.custom_imageview_progress_title);
        title.setText(progressText == null ? "加载中..." : progressText);
    }

    public void setMessage(String message) {
        TextView title = (TextView) findViewById(R.id.custom_imageview_progress_title);
        title.setText(message);
    }

    @Override
    public void show() {
        if (!isShowing() && context != null) {
            super.show();
        }
    }


    @Override
    public void dismiss() {
        if (loadingCancel != null && isShowing()) {
            loadingCancel.loadCancel();
        }
        super.dismiss();
    }

    public interface LoadingCancelCallBack {
         void loadCancel();
    }

    public static MyProgressDialog show(Context context){
        MyProgressDialog myProgressDialog = new MyProgressDialog(context);
        return myProgressDialog;
    }
    public static MyProgressDialog show(Context context, String progressText){
        MyProgressDialog myProgressDialog = new MyProgressDialog(context,progressText);

        return myProgressDialog;
    }

}
