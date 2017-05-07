package com.mfratane.checkersinterface.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Adapter generico.
 */
public class GenericAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private int count;

    /**
     *
     * @param fm FragmentManager
     * @param fragments Lista com fragments a serem mostrados.
     * @param count numero de elementos a serem mostrados.
     */
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
