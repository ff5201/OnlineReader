package com.example.fjh.onlinereader.Manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FJH on 2017/5/14.ActivityManager
 * 搜集所有打开的activity
 */

public class ActivityManager {
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
}
