package com.example.fjh.onlinereader.Model;

import android.util.Log;

import com.example.fjh.onlinereader.Bean.Book;
import com.example.fjh.onlinereader.Bean.Catalog;
import com.example.fjh.onlinereader.Interface.booksCatalogListener;
import com.example.fjh.onlinereader.Model.Interface.booksCatalogModel;
import com.example.fjh.onlinereader.Util.GsonUtil;
import com.example.fjh.onlinereader.Util.LogUtil;
import com.example.fjh.onlinereader.Util.OkHttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.fjh.onlinereader.url.bookListURL;
import static com.example.fjh.onlinereader.url.getBookCatalogURL;

/**
 * Created by FJH on 2017/6/13.
 */

public class booksCatalogModelImpl implements booksCatalogModel {

    private String bookCatalogUrl=getBookCatalogURL.toString();

    @Override
    public void getBookCatalog(final int ID, final booksCatalogListener Listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //构造request表单
                    final RequestBody requestBody=new FormBody.Builder()
                            .add("cmd","getBookCatalog")
                            .add("book_ID",String.valueOf(ID))
                            .build();
                    //调用封装的okhttpUtil
                    OkHttpUtil.sendOkHttpRequest(bookCatalogUrl, requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogUtil.d("服务器","连接出错");
                            Listener.onError("网络出错!请检查网络!");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //中文编码
                            String responseData = new String(response.body().bytes(),"GB2312");
                            LogUtil.d("服务器返回输入",responseData);
                            //用GSON将json字符串序列化
                            List<Catalog> bookCatalog= GsonUtil.fromJsonList(responseData,Catalog.class);
                            Listener.onSuccess(bookCatalog);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
