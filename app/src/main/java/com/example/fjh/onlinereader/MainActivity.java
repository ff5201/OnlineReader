package com.example.fjh.onlinereader;

import android.app.Activity;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.fjh.onlinereader.Fragmnet.AboutFragment;
import com.example.fjh.onlinereader.Fragmnet.BookListFragment;
import com.example.fjh.onlinereader.Manager.ActivityManager;
import com.example.fjh.onlinereader.Util.LogUtil;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity  {

    public final int LINK_SUCCESS=1;
    public final int LINK_ERROR=3;
    private final String TAG="主页TAG";

    //dramerLayout侧滑菜单控件
    private DrawerLayout mdrawerLayout;

    //Toolbar控件
    private Toolbar toolbar_main;
    //actionBar
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.addActivity(this);

        //Toolbar设置
        toolbar_main = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar_main);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled (true );
            actionBar.setHomeButtonEnabled (true );
            actionBar.setDisplayShowHomeEnabled (true );
            actionBar.setHomeAsUpIndicator(R.drawable.ic_view_headline_white_24dp);
        }

        //drawerLayout设置
        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle=new ActionBarDrawerToggle(this, mdrawerLayout,toolbar_main,R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mdrawerLayout.addDrawerListener(mDrawerToggle);
        setDrawerLeftEdgeSize(this,mdrawerLayout,0.2f);


        //侧滑导航Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main);
        navigationView.setCheckedItem(R.id.nav_home);
        setNavClick(navigationView);

        switchToMain();

    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG,"MainActivity已被停止");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG,"MainActivity已被暂停");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG,"MainActivity已被销毁");
    }

    private void switchToMain() {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new BookListFragment()).commit();
        actionBar.setTitle(R.string.home);
    }

    private void switchToAbout(){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new AboutFragment()).commit();
        actionBar.setTitle(R.string.about);
    }

    private void switchToExit(){
        ActivityManager.finishAll();
    }

    //设置Navigation点击事件
    private void setNavClick(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:switchToMain();break;
                    //case R.id.nav_set:break;
                    case R.id.nav_about:switchToAbout();break;
                    case R.id.nav_exit:switchToExit();break;
                    default:break;
                }
                mdrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    //DrawerLayout全屏侧滑
    private void setDrawerLeftEdgeSize (Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }


    //MainActivity选择器监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mdrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

}
