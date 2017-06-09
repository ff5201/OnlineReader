package com.example.fjh.onlinereader.Util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by FJH on 2017/6/9.
 */
public class OkHttpUtil {
    public static void sendOkHttpRequest(String address, RequestBody requestBody, Callback callback){
        OkHttpClient client=new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
