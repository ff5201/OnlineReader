package com.example.fjh.onlinereader.Listener;

import com.example.fjh.onlinereader.Bean.Catalog;

/**
 * Created by FJH on 2017/6/17.
 */

public interface bookContentListener {
    void getContentSuccess(Catalog c);
    void getContentError(String e);
}
