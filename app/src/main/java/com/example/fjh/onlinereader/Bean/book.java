package com.example.fjh.onlinereader.Bean;

import java.io.Serializable;

/**
 * Created by FJH on 2017/6/5. 没完整，后期修改，测试用
 */

public class Book implements Serializable {
    private int ID;
    private int classify_ID;
    private String name;
    private String author;
    private String subheading;
    private String publication;
    private String authorInfo;
    private String contentInfo;
    private String fontNumber;
    private double price;
    private String bookImg;
    //还有一个图片字段
    public Book(String name, String author, String subheading, String publication, String fontNumber, double price){
        this.name=name;
        this.author=author;
        this.subheading=subheading;
        this.publication=publication;
        this.fontNumber=fontNumber;
        this.price=price;
    }

    public void setID(int ID){this.ID=ID;}
    public void setClassify(int classify_ID){this.classify_ID=classify_ID;}
    public void setName(String name){this.name=name;}
    public void setAuthor(String author){this.author=author;}
    public void setSubheading(String subheading){this.subheading=subheading;}
    public void setPublication(String publication){this.publication=publication;}
    public void setAuthorInfo(String authorInfo){this.authorInfo=authorInfo;}
    public void setContentInfo(String contentInfo){this.contentInfo=contentInfo;}
    public void setFontNumber(String fontNumber){this.fontNumber=fontNumber;}
    public void setPrice(double price){this.price=price;}
    public void setBookImg(String bookImg){this.bookImg=bookImg;}


    public int getID(){return ID;}
    public int getClassify(){return classify_ID;}
    public String getName(){
        return name;
    }
    public String getAuthor(){return author;}
    public String getSubheading(){
        return subheading;
    }
    public String getPublication(){
        return publication;
    }
    public String getAuthorInfo(){return authorInfo;}
    public String getContentInfo(){return contentInfo;}
    public String getFontNumber(){
        return fontNumber;
    }
    public double getPrice(){
        return price;
    }
    public String getBookImg(){return bookImg;}
}
