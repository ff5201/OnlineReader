package com.example.fjh.onlinereader.Fragmnet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fjh.onlinereader.R;

/**
 * Created by FJH on 2017/6/11.
 */

public class BookDetailFragment extends Fragment {

    //自定义frag启动
    public static BookDetailFragment DetailFragmentStart(String info){
        Bundle args = new Bundle();
        BookDetailFragment fragment = new BookDetailFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_detail_frag, container,false);
        TextView detail = (TextView) view.findViewById(R.id.text_detailfrag);
        detail.setText(getArguments().getString("info"));
        return view;
    }
}
