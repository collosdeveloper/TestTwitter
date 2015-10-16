package com.daiv.android.twitter.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

public class PhotoPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] urls;
    private Fragment[] fragments;

    public PhotoPagerAdapter(FragmentManager fm, Context context, String[] urls) {
        super(fm);
        this.context = context;
        this.urls = urls;
        this.fragments = new Fragment[urls.length];
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
