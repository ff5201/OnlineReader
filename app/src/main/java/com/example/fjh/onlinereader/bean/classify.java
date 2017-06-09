package com.example.fjh.onlinereader.Bean;

/**
 * Created by FJH on 2017/6/5.
 */

public class Classify {
    private int ID;
    private String className;

    public Classify(){}
    public Classify(int ID, String className){
        this.ID=ID;
        this.className=className;
    }

    public void setID(int ID){
        this.ID=ID;
    }
    public int getID(){
        return ID;
    }
    public void setClassName (String className){
        this.className=className;
    }
    public String getClassName(){
        return className;
    }
}
