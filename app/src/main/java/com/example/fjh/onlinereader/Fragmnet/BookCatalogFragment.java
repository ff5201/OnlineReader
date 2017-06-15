package com.example.fjh.onlinereader.Fragmnet;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fjh.onlinereader.Adapter.bookCatalogAdapter;
import com.example.fjh.onlinereader.Adapter.bookListAdapter;
import com.example.fjh.onlinereader.Animation.MyAnimation;
import com.example.fjh.onlinereader.Bean.Book;
import com.example.fjh.onlinereader.Bean.Catalog;
import com.example.fjh.onlinereader.BookActivity;
import com.example.fjh.onlinereader.ContentActivity;
import com.example.fjh.onlinereader.Interface.booksCatalogListener;
import com.example.fjh.onlinereader.Manager.MyApplication;
import com.example.fjh.onlinereader.Model.booksCatalogModelImpl;
import com.example.fjh.onlinereader.R;
import com.example.fjh.onlinereader.Util.LogUtil;
import com.example.fjh.onlinereader.Util.RecycleViewDivider;
import com.example.fjh.onlinereader.Widget.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FJH on 2017/6/12.
 */

public class BookCatalogFragment extends Fragment implements booksCatalogListener {

    public static final String TAG="目录页DEBUG";
    public final int LINK_SUCCESS=1;
    public final int LINK_ERROR=2;

    private List<Catalog> catalogList = new ArrayList<Catalog>();
    RecyclerView recyclerView;
    bookCatalogAdapter adapter;
    //model层接口
    booksCatalogModelImpl bcmi=new booksCatalogModelImpl();
    //Handler
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LINK_SUCCESS:
                    adapter =new bookCatalogAdapter(catalogList);
                    recyclerView.setAdapter(adapter);
                    break;
                case LINK_ERROR:
                    Snackbar.make(recyclerView,msg.obj.toString(),Snackbar.LENGTH_LONG).show();
                    Log.d(TAG,msg.obj.toString());
                    break;
                default:break;
            }
        }
    };

    public static BookCatalogFragment FragmentStart(int book_ID){
        BookCatalogFragment Fragment =new BookCatalogFragment();
        Bundle args=new Bundle();
        args.putInt("book_ID",book_ID);
        Fragment.setArguments(args);
        LogUtil.d(TAG,"BookCatalogFragmentNew");
        return Fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.book_catalog_frag,container,false);
        LogUtil.d(TAG,"inflate");
        LogUtil.d(TAG,"获取recyclerView");
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_book_catalog);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(MyApplication.getContext()
                , LinearLayoutManager.HORIZONTAL, R.drawable.line_catalog));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        LogUtil.d(TAG,"设置layoutManager");
        /*bookCatalogAdapter adapter =new bookCatalogAdapter(catalogList);
        recyclerView.setAdapter(adapter);*/
        //调用model层连接网络，获取书籍目录
        int book_ID=getArguments().getInt("book_ID");
        bcmi.getBookCatalog(book_ID,this);
        return view;
    }

    //自定义recyclerItem单击动作
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Catalog c=adapter.getCatalolg(position);
            Intent intent=new Intent(MyApplication.getContext(), ContentActivity.class);
            intent.putExtra("bookCatalog", c);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
            ActivityCompat.startActivity(getContext(), intent, options.toBundle());
        }
    };

    //网络通讯成功
    @Override
    public void onSuccess(List<Catalog> c) {
        catalogList.clear();
        catalogList=c;
        Message msg=new Message();
        msg.what=LINK_SUCCESS;
        handler.sendMessage(msg);
    }

    //网络通讯失败
    @Override
    public void onError(String s) {
        Message message=new Message();
        message.what=LINK_ERROR;
        message.obj=s.toString();
        handler.sendMessage(message);
    }

}
