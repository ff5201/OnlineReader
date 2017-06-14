package com.example.fjh.onlinereader.Manager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.fjh.onlinereader.Util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FJH on 2017/5/14.ActivityManager
 * 搜集所有打开的activity
 */

public class ActivityManager extends AppCompatActivity {
    public static List<Activity> activities=new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity: activities){
           if(!activity.isFinishing()){
               activity.finish();
           }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("BaseActivity",getClass().getSimpleName());
    }
}
