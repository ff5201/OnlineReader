package com.example.fjh.onlinereader.Bean;

import java.io.Serializable;

/**
 * Created by FJH on 2017/6/12.
 */

public class Catalog implements Serializable {
    private int ID;
    private String title;
    private String name;
    private String content;
    private int book_ID;

    //模拟用数据
    public Catalog(int id,String title,String name,String content,int book_ID){
        this.book_ID=id;
        this.title=title;
        this.name=name;
        this.content=content;
        this.book_ID=book_ID;
    }

    public void setID(int ID){this.ID=ID;}
    public void setTitle(String title){this.title=title;}
    public void setName(String name){this.name=name;}
    public void setContent(String content){this.content=content;}
    public void setBook_ID(int book_ID){this.book_ID=book_ID;}

    public int getID(){return ID;}
    public String getTitle(){return title;}
    public String getName(){return name;}
    public String getContent(){return content;}
    public int getBook_ID(){return book_ID;}
}
