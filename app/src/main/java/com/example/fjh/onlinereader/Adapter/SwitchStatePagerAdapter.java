package com.example.fjh.onlinereader.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.fjh.onlinereader.ContentActivity;
import com.example.fjh.onlinereader.Manager.MyApplication;
import com.example.fjh.onlinereader.Util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FJH on 2017/6/17.
 */

public class SwitchStatePagerAdapter extends FragmentStatePagerAdapter {

    public static final String TAG = SwitchStatePagerAdapter.class.getSimpleName();

    private List<Fragment> mFragments = new ArrayList<>();


    public SwitchStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    public void removeFragment(Fragment fragment) {
        mFragments.remove(fragment);
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }

    public void clear() {
        for (Fragment fragment : mFragments) {
            if (fragment != null && fragment.isAdded()) {
                fragment.onDestroy();
            }
        }
        mFragments.clear();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtil.d(TAG, "instantiateItem position = " + position);
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
