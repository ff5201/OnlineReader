package com.example.fjh.onlinereader.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fjh.onlinereader.Bean.Catalog;
import com.example.fjh.onlinereader.R;

import java.util.List;

/**
 * Created by FJH on 2017/6/12.
 */

public class bookCatalogAdapter extends RecyclerView.Adapter<bookCatalogAdapter.ViewHolder> {

    private List<Catalog> catalogList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView catalogTitle;
        TextView catalogName;
        public ViewHolder(View view){
            super(view);
            catalogTitle=(TextView)view.findViewById(R.id.catalog_item_title);
            catalogName=(TextView)view.findViewById(R.id.catalog_item_name);
        }
    }

    public bookCatalogAdapter(List<Catalog> catalogList){
        this.catalogList=catalogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_catalog_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Catalog c =catalogList.get(position);
        holder.catalogTitle.setText(c.getTitle());
        holder.catalogName.setText(c.getName());
    }

    @Override
    public int getItemCount() {
        return catalogList.size();
    }

    public Catalog getCatalolg(int position){
        return catalogList.get(position);
    }
}
