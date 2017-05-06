package com.mfratane.checkersinterface.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by Max on 05/05/2017.
 */

public class GenericAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private int count;


    public GenericAdapter(FragmentManager fm, List<Fragment> fragments, int count) {
        super(fm);
        this.fragments = fragments;
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return count;
    }
}
