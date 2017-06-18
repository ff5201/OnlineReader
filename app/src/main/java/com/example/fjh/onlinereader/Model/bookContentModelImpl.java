package com.example.fjh.onlinereader.Model;

import android.util.Log;

import com.example.fjh.onlinereader.Bean.Book;
import com.example.fjh.onlinereader.Bean.Catalog;
import com.example.fjh.onlinereader.Listener.bookContentListener;
import com.example.fjh.onlinereader.Model.Interface.bookContentModel;
import com.example.fjh.onlinereader.Util.GsonUtil;
import com.example.fjh.onlinereader.Util.OkHttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.fjh.onlinereader.url.getBookCatalogURL;

/**
 * Created by FJH on 2017/6/17.
 */

public class bookContentModelImpl implements bookContentModel {

    private String bookCatalogUrl=getBookCatalogURL.toString();
    @Override
    public void getbookContent(final Catalog c, final bookContentListener Listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //构造request表单
                    final RequestBody requestBody=new FormBody.Builder()
                            .add("cmd","getBookContent")
                            .add("catalog",String.valueOf(c.getID()))
                            .build();
                    //调用封装的okhttpUtil
                    OkHttpUtil.sendOkHttpRequest(bookCatalogUrl, requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("服务器","连接出错");
                            Listener.getContentError("服务器连接出错!请检查网络!");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //中文编码
                            String responseData = new String(response.body().bytes(),"GB2312");
                            Log.d("服务器返回输入",responseData);
                            //用GSON将json字符串序列化
                            Catalog catalog= GsonUtil.GsonToBean(responseData,Catalog.class);
                            Listener.getContentSuccess(catalog);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
