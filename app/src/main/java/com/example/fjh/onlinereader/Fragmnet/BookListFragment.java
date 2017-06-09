package com.example.fjh.onlinereader.Fragmnet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fjh.onlinereader.Adapter.bookListAdapter;
import com.example.fjh.onlinereader.Bean.Book;
import com.example.fjh.onlinereader.Interface.booksListListener;
import com.example.fjh.onlinereader.Model.booksListModelImpl;
import com.example.fjh.onlinereader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FJH on 2017/6/4.
 */

public class BookListFragment extends Fragment implements booksListListener {

    //模拟书籍
    private Book[] books={new Book("JAVA","番剧","5656","2017-8","45464464646",52),
            new Book("JAVA","番剧","5656","2017-8","45464464646",52.14),new Book("JAVA","番剧","5656","2017-8","45464464646",52.14),
            new Book("JAVA","番剧","5656","2017-8","45464464646",52.14),new Book("JAVA","番剧","5656","2017-8","45464464646",52.14),};

    List<Book> bookList=new ArrayList<>();
    bookListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.book_list_frag,container,false);
        Log.d("服务器:","1");
        booksListModelImpl blmi=new booksListModelImpl();
        blmi.getAllBooksList(this);
        RecyclerView bookListRecyclerView=(RecyclerView)view.findViewById(R.id.recycler_book_frag);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        bookListRecyclerView.setLayoutManager(layoutManager);
        for (Book book:books
             ) {
            bookList.add(book);
        }
        adapter=new bookListAdapter(bookList);
        bookListRecyclerView.setAdapter(adapter);
        return view;
    }

    //通讯成功
    @Override
    public void onSuccess(List<Book> bookList) {
        this.bookList.clear();
        this.bookList=bookList;
        for (Book b:bookList
                ) {
            Log.d("书本5566:","ID"+String.valueOf(b.getID())+String.valueOf(b.getClassify())+b.getName()+b.getAuthor()+b.getPublication());
        }
        adapter.notifyDataSetChanged();
        Log.d("服务器:","通讯成功");
    }

    //搜索成功
    @Override
    public void onSearchSuccess(List<Book> bookList) {

    }

    //通讯失败
    @Override
    public void onError(String s) {
        Log.d("服务器:","通讯失败");
    }
}
