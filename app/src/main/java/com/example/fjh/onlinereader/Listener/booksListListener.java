package com.example.fjh.onlinereader.Listener;

import com.example.fjh.onlinereader.Bean.Book;

import java.util.List;


/**
 * Created by FJH on 2017/6/8.
 */

public interface booksListListener {
    void onSuccess(List<Book> b);
    void onSearchSuccess(List<Book> b);
    void onError(String s);
}
