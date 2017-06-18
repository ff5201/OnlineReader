package com.example.fjh.onlinereader.Fragmnet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fjh.onlinereader.Bean.Catalog;
import com.example.fjh.onlinereader.Listener.bookContentListener;
import com.example.fjh.onlinereader.Model.bookContentModelImpl;
import com.example.fjh.onlinereader.R;
import com.example.fjh.onlinereader.Util.LogUtil;

/**
 * Created by FJH on 2017/6/16.
 */

public class BookContentFragment extends Fragment implements bookContentListener {

    public final int GET_CONTENT_SUCCESS=1;
    public final int GET_CONTENT_ERROR=2;

    //自定义启动方式
    public static BookContentFragment newInstance(Catalog catalog){
        Bundle args = new Bundle();
        BookContentFragment fragment = new BookContentFragment();
        args.putSerializable("catalog",catalog);
        fragment.setArguments(args);
        return fragment;
    }

    //handler
    private Handler handler;

    private String TAG="小说内容碎片";
    private TextView title;
    private TextView content;
    private Catalog catalog;
    private bookContentModelImpl bcmi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_content_frag,container,false);
        catalog=(Catalog) getArguments().getSerializable("catalog");
        title= (TextView)view.findViewById(R.id.content_title);
        //title.setText(catalog.getTitle()+" "+catalog.getName());
        content=(TextView)view.findViewById(R.id.content_text);
        handler=new myhanlder();
        bcmi=new bookContentModelImpl();
        bcmi.getbookContent(catalog,this);
        return view;
    }

    class myhanlder extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_CONTENT_SUCCESS:showBookTitleAndContent();break;
                case GET_CONTENT_ERROR:break;
                default:break;
            }
        }
    }

    private void showBookTitleAndContent(){
        title.setText(catalog.getTitle()+" "+catalog.getName());
        content.setText(catalog.getContent());
    }

    //获取目录内容成功
    @Override
    public void getContentSuccess(Catalog c) {
        LogUtil.d(TAG,c.getContent());
        catalog=c;
        Message msg = new Message();
        msg.what=GET_CONTENT_SUCCESS;
        handler.sendMessage(msg);
    }
    //获取内容失败
    @Override
    public void getContentError(String e) {

    }

}

