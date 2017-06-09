package com.example.fjh.onlinereader.Model.Interface;

import com.example.fjh.onlinereader.Interface.booksListListener;

/**
 * Created by FJH on 2017/6/8.
 */

public interface booksListModel {
    void getAllBooksList(booksListListener Listener);
    void getSearchBooksList(String bookName, booksListListener listener);
}
