package com.example.fjh.onlinereader;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fjh.onlinereader.Manager.ActivityManager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mdrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.addActivity(this);

        Toolbar toolbar_main=(Toolbar)findViewById(R.id.toolBar_main);
        setSupportActionBar(toolbar_main);
        //drawerLayout设置
        mdrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("在线阅读器");
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_view_headline_white_24dp);
        }
        //侧滑导航Navigation设置
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view_main);
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /*留着，写选项操作.暂时全部关闭*/
                mdrawerLayout.closeDrawers();
                return true;
            }
        });
        //悬浮按钮floatting
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"点击了悬浮按钮",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    //MainActivity选择器监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mdrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
}
