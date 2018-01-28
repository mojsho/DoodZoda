package com.example.mojtaba.doodzoda.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.fragment.TimeLapseFragment;
import com.example.mojtaba.doodzoda.fragment.TotalTimeLapseFragment;

public class MainPageTimePagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public MainPageTimePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new TimeLapseFragment();
                break;
            case 1:
                fragment = new TotalTimeLapseFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = "";

        switch (position) {
            case 0:
                pageTitle = mContext.getResources().getString(R.string.time_passed_title);
                break;
            case 1:
                pageTitle = mContext.getResources().getString(R.string.total_time_passed_title);
                break;
        }

        return pageTitle;
    }
}
