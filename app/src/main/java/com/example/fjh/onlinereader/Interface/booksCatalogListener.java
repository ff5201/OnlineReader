package com.example.fjh.onlinereader.Interface;

import com.example.fjh.onlinereader.Bean.Catalog;

import java.util.List;

/**
 * Created by FJH on 2017/6/13.
 */

public interface booksCatalogListener {
    void onSuccess(List<Catalog> c);
    void onError(String s);
}
