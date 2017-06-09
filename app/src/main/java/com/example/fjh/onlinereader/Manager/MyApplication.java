package com.example.fjh.onlinereader.Manager;

import android.app.Application;
import android.content.Context;

/**
 * Created by FJH on 2017/6/9.
 * 随时保存当前context
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
