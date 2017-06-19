package com.example.fjh.onlinereader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fjh.onlinereader.Adapter.SwitchStatePagerAdapter;
import com.example.fjh.onlinereader.Bean.Catalog;
import com.example.fjh.onlinereader.Bean.CatalogList;
import com.example.fjh.onlinereader.Fragmnet.BookContentFragment;
import com.example.fjh.onlinereader.Manager.ActivityManager;
import com.example.fjh.onlinereader.Manager.MyApplication;
import com.example.fjh.onlinereader.Util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ContentActivity extends AppCompatActivity {

    ViewPager viewPager;
    private ImageView battery_image;
    private TextView time_view;
    private AnimationDrawable animationDrawable;
    private Handler handler=new refreshTimeHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition slide_right = TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right);
        getWindow().setEnterTransition(slide_right);
        setContentView(R.layout.activity_content);
        ActivityManager.addActivity(this);
        Catalog catalog = (Catalog) getIntent().getSerializableExtra("bookCatalog");
        battery_image=(ImageView)findViewById(R.id.battery_image);
        time_view=(TextView)findViewById(R.id.time_text);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        //注册接收器以获取电量信息
        MyApplication.getContext().registerReceiver(broadcastReceiver, intentFilter);

        viewPager=(ViewPager)findViewById(R.id.book_viewPage);
        List<Catalog> catalogList=CatalogList.getCatalogList();
        List<Fragment> fragmentList=new ArrayList<Fragment>();
        for (Catalog c:catalogList
             ) {
            fragmentList.add(new BookContentFragment().newInstance(c));
        }
        SwitchStatePagerAdapter switchStatePagerAdapter=new SwitchStatePagerAdapter(getSupportFragmentManager());
        switchStatePagerAdapter.setFragments(fragmentList);
        viewPager.setAdapter(switchStatePagerAdapter);
        viewPager.setCurrentItem(CatalogList.getItemLocation(catalog));
        /*FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.book_content,BookContentFragment.newInstance(catalog)).commit();*/
        new Thread(new refreshTimeThread()).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getContext().unregisterReceiver(broadcastReceiver);
    }

    //刷新时间子进程
    class refreshTimeThread implements Runnable{
        @Override
        public void run() {
            try {
                while (true){
                    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
                    String str=sdf.format(new Date());
                    Message msg=new Message();
                    msg.obj=str;
                    handler.sendMessage(msg);
                    Thread.sleep(1000);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    //更新手柄
    class refreshTimeHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time_view.setText(msg.obj.toString());
        }
    }

    //动态注册电量监听广播
   /* private int batteryLevel;
    private int batteryScale;*/
    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*//获取当前电量，如未获取具体数值，则默认为0
            batteryLevel=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            //获取最大电量，如未获取到具体数值，则默认为100
            batteryScale=intent.getIntExtra(BatteryManager.EXTRA_SCALE,100);
            //显示电量
            textViewLevel.setText("电量"+(batteryLevel*100/batteryScale)+"%");*/
            String action = intent.getAction();
            int status = intent.getIntExtra("status", 0);
            int level = intent.getIntExtra("level", 0);
            int scale = intent.getIntExtra("scale", 100);
            LogUtil.d("电量：：：","status"+status+":manager"+ BatteryManager.BATTERY_STATUS_CHARGING+"Level:"+String.valueOf(level));
            String statusString = "";
            switch (status) {
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    statusString = "unknown";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusString = "charging";
                    battery_image.setImageResource(R.drawable.stat_sys_battery_charge);
                    battery_image.getDrawable().setLevel(level);
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusString = "discharging";
                    battery_image.setImageResource(R.drawable.stat_sys_battery);
                    battery_image.getDrawable().setLevel(level);
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    battery_image.setImageResource(R.drawable.stat_sys_battery);
                    battery_image.getDrawable().setLevel(level);
                    statusString = "not charging";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    statusString = "full";
                    break;
            }
            LogUtil.d("电量状态：",statusString);
        }
    };
}
