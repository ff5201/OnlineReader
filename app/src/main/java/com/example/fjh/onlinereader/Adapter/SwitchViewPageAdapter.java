package com.example.fjh.onlinereader.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FJH on 2017/6/11.
 */

public class SwitchViewPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> Fragments = new ArrayList<>();
    private final List<String> FragmentTitles = new ArrayList<>();

    public SwitchViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        Fragments.add(fragment);
        FragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return Fragments.get(position);
    }

    @Override
    public int getCount() {
        return Fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentTitles.get(position);
    }

}
