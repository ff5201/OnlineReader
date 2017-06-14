package com.example.fjh.onlinereader.Animation;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.example.fjh.onlinereader.Manager.MyApplication;
import com.example.fjh.onlinereader.Util.ScreenUtils;

/**
 * Created by FJH on 2017/6/11.
 */

public class MyAnimation {
    //悬浮按钮属性动画
    public static void startFABAnimation(FloatingActionButton fab) {
        fab.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(500)
                .setDuration(1000)
                .start();
    }
    //RecyclerView动画
    public static void RecyclerInterAnimation(View view, int position) {
        if (position >= 4) {
            return;
        }else{
            view.setTranslationY(ScreenUtils.getScreenHeight(MyApplication.getContext()));
            view.animate()
                    .translationY(0)
                    .setStartDelay(100 * position)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }


}
