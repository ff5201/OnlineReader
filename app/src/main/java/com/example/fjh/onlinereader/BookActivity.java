package com.example.fjh.onlinereader;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fjh.onlinereader.Adapter.SwitchViewPageAdapter;
import com.example.fjh.onlinereader.Bean.Book;
import com.example.fjh.onlinereader.Fragmnet.BookCatalogFragment;
import com.example.fjh.onlinereader.Fragmnet.BookDetailFragment;
import com.example.fjh.onlinereader.Manager.ActivityManager;
import com.example.fjh.onlinereader.Manager.MyApplication;
import com.example.fjh.onlinereader.Util.LogUtil;

public class BookActivity extends AppCompatActivity {

    //ActionBar
    ActionBar actionBar;
    //CollapsingToolbarLayout
    CollapsingToolbarLayout collapsingToolbar;
    //TabLayout
    TabLayout tabLayout;
    //ViewPager
    ViewPager viewPager;
    Book book;

    public static void actionStart(Context context,Book b){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ActivityManager.addActivity(this);

        //intentGetBook
        book=(Book)getIntent().getSerializableExtra("intentBook");
        //ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_book);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //ImageView
        ImageView imageView=(ImageView)findViewById(R.id.ImageCI_book);
        String imgPath=new url().ImgPath+book.getBookImg();
        Glide.with(MyApplication.getContext()).load(imgPath).error(R.drawable.temp_book).into(imageView);
        //CollapsingToolbarLayout
        collapsingToolbar=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_book);
        collapsingToolbar.setTitle(book.getName());
        //ViewPager
        viewPager= (ViewPager)findViewById(R.id.viewpager_book);
        setSwitchViewPage(viewPager);
        //TabLayout
        tabLayout=(TabLayout)findViewById(R.id.sliding_tabs_book);
        tabLayout.addTab(tabLayout.newTab().setText("内容简介"));
        tabLayout.addTab(tabLayout.newTab().setText("作者简介"));
        tabLayout.addTab(tabLayout.newTab().setText("书籍目录"));
        tabLayout.setupWithViewPager(viewPager);
    }

    //tab切换页面
    private void setSwitchViewPage(ViewPager vg){
        SwitchViewPageAdapter adapter = new SwitchViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(BookDetailFragment.newInstance(book.getContentInfo()), "内容简介");
        adapter.addFragment(BookDetailFragment.newInstance(book.getAuthorInfo()), "作者简介");
        adapter.addFragment(BookCatalogFragment.FragmentStart(book.getID()), "书籍目录");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
