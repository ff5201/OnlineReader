package com.example.fjh.onlinereader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.fjh.onlinereader.Adapter.bookListAdapter;
import com.example.fjh.onlinereader.Bean.Book;
import com.example.fjh.onlinereader.Interface.booksListListener;
import com.example.fjh.onlinereader.Manager.ActivityManager;
import com.example.fjh.onlinereader.Manager.MyApplication;
import com.example.fjh.onlinereader.Model.booksListModelImpl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements booksListListener {

    public final int LINK_SUCCESS=1;
    public final int LINK_ERROR=3;
    private final String TAG="主页TAG";

    //dramerLayout侧滑菜单控件
    private DrawerLayout mdrawerLayout;
    //模拟书籍
   /* private Book[] books={new Book("JAVA","番剧","5656","2017-8","45464464646",52),
            new Book("JAVA","番剧","5656","2017-8","45464464646",52.14),new Book("JAVA","番剧","5656","2017-8","45464464646",52.14),
            new Book("JAVA","番剧","5656","2017-8","45464464646",52.14),new Book("JAVA","番剧","5656","2017-8","45464464646",52.14),};*/

    //书籍列表
    private List<Book> bookList=new ArrayList<>();
    //RecyclerView
    private RecyclerView bookListRecyclerView;
    //RecyclerView数据适配器
    private bookListAdapter adapter;
    //下拉刷新控件
    private SwipeRefreshLayout swipeRefresh;
    //Toolbar控件
    private Toolbar toolbar_main;
    //定义model接口
    private booksListModelImpl blmi;
    //MaterialDialog
    MaterialDialog materialDialog;

    //handler更新UI
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    adapter=new bookListAdapter(bookList);
                    bookListRecyclerView.setAdapter(adapter);
                    Log.d(TAG,"adapter成功");
                    break;
                case 3:Log.d(TAG,msg.obj.toString()); break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.addActivity(this);

        //Toolbar设置
        toolbar_main = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar_main);

        //drawerLayout设置
        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("在线阅读器");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_view_headline_white_24dp);
        }

        //侧滑导航Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main);
        navigationView.setCheckedItem(R.id.nav_home);
        setNavClick(navigationView);

        //悬浮按钮floatting
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        setFloattingActionButtonOnClickListener(fab);

        //下拉刷新swipeRefresh
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_main);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refereshBooks();
            }
        });

        //初始化RecyclerView
        bookListRecyclerView=(RecyclerView)findViewById(R.id.recycler_book_frag);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        bookListRecyclerView.setLayoutManager(layoutManager);
        /*for (Book BookActivity:books
                ) {
            bookList.add(BookActivity);
        }
        adapter=new bookListAdapter(bookList);
        bookListRecyclerView.setAdapter(adapter);*/

        //Model业务层接口,从网络获取书籍
        blmi=new booksListModelImpl();
        blmi.getAllBooksList(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"MainActivity已被停止");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"MainActivity已被暂停");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"MainActivity已被销毁");
    }

    //设置Navigation点击事件
    private void setNavClick(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /*留着，写选项操作.暂时全部关闭*/
                mdrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    //设置悬浮按钮floatting点击事件
    private void setFloattingActionButtonOnClickListener(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"设置悬浮按钮floatting点击事件");
                materialDialog=new MaterialDialog.Builder(MainActivity.this)
                        .title(R.string.search)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if (!TextUtils.isEmpty(input)) {
                                    doSearch(input.toString());
                                }
                            }
                        }).show();
            }
        });
    }


    //确认搜索
    private void doSearch(String input){
        blmi.getSearchBooksList(input,this);
    }

    //下拉刷新事件
    private void refereshBooks(){
        blmi.getAllBooksList(this);
        swipeRefresh.setRefreshing(false);
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

    //通讯成功
    @Override
    public void onSuccess(List<Book> booksList) {
        bookList.clear();
        this.bookList=booksList;
        /*for (Book b:booksList
                ) {
            Log.d("书本5566:",""+String.valueOf(b.getID())+String.valueOf(b.getClassify())+b.getName()+b.getAuthor()+b.getPublication()+b.getFontNumber()+b.getSubheading()+b.getPrice());
        }*/
        Message message=new Message();
        message.what=LINK_SUCCESS;
        handler.sendMessage(message);
        Log.d(TAG,"通讯成功");
    }

    //搜索成功
    @Override
    public void onSearchSuccess(List<Book> booksList) {
        bookList.clear();
        this.bookList=booksList;
        Message message=new Message();
        message.what=LINK_SUCCESS;
        handler.sendMessage(message);
    }

    //通讯失败
    @Override
    public void onError(String s) {
        Message message=new Message();
        message.what=LINK_ERROR;
        message.obj=s.toString();
        handler.sendMessage(message);
    }

}
