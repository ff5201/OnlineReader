package com.example.fjh.onlinereader.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.example.fjh.onlinereader.Animation.MyAnimation;
import com.example.fjh.onlinereader.Bean.Book;
import com.example.fjh.onlinereader.BookActivity;
import com.example.fjh.onlinereader.Fragmnet.BookListFragment;
import com.example.fjh.onlinereader.MainActivity;
import com.example.fjh.onlinereader.Manager.MyApplication;
import com.example.fjh.onlinereader.R;
import com.example.fjh.onlinereader.url;

import java.util.List;

/**
 * Created by FJH on 2017/6/5.
 */

public class bookListAdapter extends RecyclerView.Adapter<bookListAdapter.ViewHolder> {
    private Context context;

    private List<Book> bookList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView bookImage;
        TextView bookName;
        TextView bookAuthor;
        TextView bookSubheading;
        TextView bookPublication;
        TextView bookFontNumber;
        TextView bookPrice;
        //持久化控件
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            bookImage=(ImageView)view.findViewById(R.id.book_item_images);
            bookName=(TextView)view.findViewById(R.id.book_item_title);
            bookAuthor=(TextView)view.findViewById(R.id.book_item_author);
            bookSubheading=(TextView)view.findViewById(R.id.book_item_subheading);
            bookPublication=(TextView)view.findViewById(R.id.book_item_publication);
            bookFontNumber=(TextView)view.findViewById(R.id.book_item_fontNumber);
            bookPrice=(TextView)view.findViewById(R.id.book_item_price);
        }
    }

    public bookListAdapter(List<Book> bookList){
        this.bookList=bookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
       if(context==null){
           context=parent.getContext();
       }
        View view= LayoutInflater.from(context).inflate(R.layout.book_list_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        /*holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = holder.getAdapterPosition();
                Book book= bookList.get(positon);
                Intent intent=new Intent(context, BookActivity.class);
                intent.putExtra("intentBook",book);
                context.startActivity(intent);
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookName.setText(book.getName());
        holder.bookAuthor.append(book.getAuthor());
        holder.bookSubheading.append(book.getSubheading());
        holder.bookPublication.append(book.getPublication());
        holder.bookFontNumber.append(book.getFontNumber());
        holder.bookPrice.append(String.valueOf(book.getPrice()));
        String imgPath=new url().ImgPath+book.getBookImg();
        Glide.with(context).load(imgPath).error(R.drawable.temp_book).into(holder.bookImage);
        MyAnimation.RecyclerInterAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public Book getBook(int position){
        return bookList.get(position);
    }
}
