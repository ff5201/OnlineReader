package com.example.fjh.onlinereader.Model.Interface;

import com.example.fjh.onlinereader.Bean.Catalog;
import com.example.fjh.onlinereader.Listener.bookContentListener;

/**
 * Created by FJH on 2017/6/16.
 */

public interface bookContentModel {
    void getbookContent(Catalog c, bookContentListener Listener);
}
