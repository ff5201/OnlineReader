package com.example.fjh.onlinereader.Model;

import android.util.Log;

import com.example.fjh.onlinereader.Bean.Book;
import com.example.fjh.onlinereader.Listener.booksListListener;
import com.example.fjh.onlinereader.Model.Interface.booksListModel;
import com.example.fjh.onlinereader.Util.GsonUtil;
import com.example.fjh.onlinereader.Util.OkHttpUtil;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.fjh.onlinereader.url.bookListURL;

/**
 * Created by FJH on 2017/6/8.
 */

public class booksListModelImpl implements booksListModel {

    private String bookListUrl=bookListURL.toString();

    public void getAllBooksList(final booksListListener Listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*try {
                    //使用okHTTP框架进行网络通讯，GSON解析json字符串
                    OkHttpClient client = new OkHttpClient();
                    //构造表单
                    RequestBody requestBody=new FormBody.Builder()
                            .add("cmd","all")
                            .build();
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(myUrl)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    byte[] b = response.body().bytes(); //获取数据的bytes
                    String responseData = new String(b, "GB2312"); //然后将其转为gb2312
                    Log.d("服务器返回输入",responseData);
                    //用GSON将json字符串序列化
                    List<Book> booksList =parseJsonWithGSON(responseData);
                    Listener.onSuccess(booksList);
                }catch (Exception e){
                    Log.d("服务器","连接出错");
                    Listener.onError("服务器连接出错!请检查网络!");
                    e.printStackTrace();
                }*/
                try {
                    //构造request表单
                    final RequestBody requestBody=new FormBody.Builder()
                            .add("cmd","all")
                            .build();
                    //调用封装的okhttpUtil
                    OkHttpUtil.sendOkHttpRequest(bookListUrl, requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("服务器","连接出错");
                            Listener.onError("服务器连接出错!请检查网络!");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //中文编码
                            String responseData = new String(response.body().bytes(),"GB2312");
                            Log.d("服务器返回输入",responseData);
                            //用GSON将json字符串序列化
                            //List<Book> booksList = parseJsonWithGSON(responseData);
                            List<Book> booksList= GsonUtil.fromJsonList(responseData,Book.class);
                            Listener.onSuccess(booksList);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getSearchBooksList(final String bookName, final booksListListener Listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //构造request表单
                    RequestBody requestBody=new FormBody.Builder()
                            .add("cmd","search")
                            .add("name",bookName)
                            .build();
                    //调用封装好的okhttpUtil
                    OkHttpUtil.sendOkHttpRequest(bookListUrl, requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("服务器","连接出错");
                            Listener.onError("网络出错!请检查网络!");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseData = new String(response.body().bytes(),"GB2312");
                            Log.d("服务器返回输入",responseData);
                            //用GSON将json字符串序列化
                            //List<Book> booksList = parseJsonWithGSON(responseData);
                            List<Book> booksList= GsonUtil.fromJsonList(responseData,Book.class);
                            Listener.onSearchSuccess(booksList);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*private List<Book> parseJsonWithGSON(String json){
        Gson gson=new Gson();
        List<Book> booksList=gson.fromJson(json,new TypeToken<List<Book>>(){}.getType());
        for (Book b:booksList
             ) {
            Log.d("书本","ID"+String.valueOf(b.getID())+String.valueOf(b.getClassify())+b.getName()+b.getAuthor()+b.getPublication());
        }
        return booksList;
    }*/
}
