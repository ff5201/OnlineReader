package com.example.fjh.onlinereader;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.fjh.onlinereader.Adapter.SwitchStatePagerAdapter;
import com.example.fjh.onlinereader.Bean.Catalog;
import com.example.fjh.onlinereader.Bean.CatalogList;
import com.example.fjh.onlinereader.Fragmnet.BookContentFragment;
import com.example.fjh.onlinereader.Manager.ActivityManager;

import java.util.ArrayList;
import java.util.List;


public class ContentActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition slide_right = TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right);
        getWindow().setEnterTransition(slide_right);
        setContentView(R.layout.activity_content);
        ActivityManager.addActivity(this);
        Catalog catalog = (Catalog) getIntent().getSerializableExtra("bookCatalog");

        viewPager=(ViewPager)findViewById(R.id.book_viewPage);
        List<Catalog> catalogList=CatalogList.getCatalogList();
        List<Fragment> fragmentList=new ArrayList<Fragment>();
        for (Catalog c:catalogList
             ) {
            fragmentList.add(new BookContentFragment().newInstance(c));
        }
        SwitchStatePagerAdapter switchStatePagerAdapter=new SwitchStatePagerAdapter(getSupportFragmentManager());
        switchStatePagerAdapter.setFragments(fragmentList);
        viewPager.setAdapter(switchStatePagerAdapter);
        viewPager.setCurrentItem(CatalogList.getItemLocation(catalog));
        /*FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.book_content,BookContentFragment.newInstance(catalog)).commit();*/
    }
}
