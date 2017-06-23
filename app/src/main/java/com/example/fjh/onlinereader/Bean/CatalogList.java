package com.example.fjh.onlinereader.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FJH on 2017/6/17.
 */

public class CatalogList {
    private static List<Catalog> catalogList;

    public static List<Catalog> getCatalogList(){
        return catalogList;
    }

    public static void setCatalogList(List<Catalog> c){
        catalogList=c;
    }

    public static int getItemLocation(Catalog c){
        int i=0;
        for(;i<catalogList.size();i++){
            if(catalogList.get(i).getID()==c.getID()){
                break;
            }
        }
        return i;
    }

    public static int getCount(){
        return catalogList.size();
    }
}
