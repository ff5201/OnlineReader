package com.example.fjh.onlinereader.Fragmnet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.fjh.onlinereader.Adapter.bookListAdapter;
import com.example.fjh.onlinereader.Animation.MyAnimation;
import com.example.fjh.onlinereader.Bean.Book;
import com.example.fjh.onlinereader.BookActivity;
import com.example.fjh.onlinereader.Listener.booksListListener;
import com.example.fjh.onlinereader.Manager.MyApplication;
import com.example.fjh.onlinereader.Model.booksListModelImpl;
import com.example.fjh.onlinereader.R;
import com.example.fjh.onlinereader.Widget.RecyclerItemClickListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FJH on 2017/6/4.
 */

public class BookListFragment extends Fragment implements booksListListener {

    public final int LINK_SUCCESS=1;
    public final int LINK_SEARCH_SUCCESS=2;
    public final int LINK_ERROR=3;

    private final String TAG="booklist碎片";

    private FloatingActionButton fab;
    //MaterialDialog
    MaterialDialog materialDialog;
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
    //avi动画
    private AVLoadingIndicatorView avi;
    //searchResult
    private TextView searchResultTextView;

    //定义model接口
    private booksListModelImpl blmi;

    //handler更新UI
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LINK_SUCCESS:
                    adapter=new bookListAdapter(bookList);
                    bookListRecyclerView.setAdapter(adapter);
                    Log.d(TAG,"adapter成功");
                    stopAnim();
                    searchResultTextView.setVisibility(View.GONE);
                    MyAnimation.startFABAnimation(fab);
                    break;
                case LINK_SEARCH_SUCCESS:
                    if(bookList.size()==0){
                        adapter.notifyDataSetChanged();
                        searchResultTextView.setVisibility(View.VISIBLE);
                    }else {
                        adapter.notifyDataSetChanged();
                        searchResultTextView.setVisibility(View.GONE);
                        Log.d(TAG,"adapter成功");
                    }break;
                case LINK_ERROR:
                    Snackbar.make(swipeRefresh,msg.obj.toString(),Snackbar.LENGTH_LONG).show();
                    Log.d(TAG,msg.obj.toString());
                    break;
                default:break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.book_list_frag,container,false);
        //悬浮按钮floatting
        fab = (FloatingActionButton)view.findViewById(R.id.fab_main);
        setFloattingActionButtonOnClickListener(fab);
        //下拉刷新swipeRefresh
        swipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_main);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refereshBooks();
            }
        });

        //初始化RecyclerView
        bookListRecyclerView=(RecyclerView)view.findViewById(R.id.recycler_book_frag);
        bookListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        bookListRecyclerView.setLayoutManager(layoutManager);
        bookListRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));

        //过度动画
        avi=(AVLoadingIndicatorView)view.findViewById(R.id.Book_avi);
        searchResultTextView=(TextView)view.findViewById(R.id.Book_SearchNoResult);

        //Model层接口,从网络获取书籍
        blmi=new booksListModelImpl();
        blmi.getAllBooksList(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //悬浮按钮下移
        fab.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
    }


    //设置悬浮按钮floatting点击事件
    private void setFloattingActionButtonOnClickListener(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"设置悬浮按钮floatting点击事件");
                materialDialog=new MaterialDialog.Builder(getContext())
                        .title(R.string.search)
                        .iconRes(R.drawable.ic_search_black_18dp)
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

    //自定义recyclerItem单击动作
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            Book book = adapter.getBook(position);
            Intent intent = new Intent(getActivity(), BookActivity.class);
            intent.putExtra("intentBook", book);

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            view.findViewById(R.id.book_item_images), getString(R.string.transition_book_imgages));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            //MyApplication.getContext().startActivity(intent);
        }
    };

    //加载动画控制
    void startAnim(){
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim(){
        avi.hide();
        // or avi.smoothToHide();
    }

    //通讯成功
    @Override
    public void onSuccess(List<Book> booksList) {
        bookList.clear();
        this.bookList=booksList;
        /*for (Book b:booksList
                ) {
            LogUtil.d("书本5566:",""+String.valueOf(b.getID())+String.valueOf(b.getClassify())+b.getName()+b.getAuthor()+b.getPublication()+b.getFontNumber()+b.getSubheading()+b.getPrice());
        }*/
        Message message=new Message();
        message.what=LINK_SUCCESS;
        handler.sendMessage(message);
        Log.d(TAG,"通讯成功");
    }

    //搜索成功
    @Override
    public void onSearchSuccess(List<Book> b) {
        bookList.clear();
        for (Book book:b
             ) {
            bookList.add(book);
        }
        Message message=new Message();
        message.what=LINK_SEARCH_SUCCESS;
        handler.sendMessage(message);
        Log.d(TAG,"所搜成功");
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
