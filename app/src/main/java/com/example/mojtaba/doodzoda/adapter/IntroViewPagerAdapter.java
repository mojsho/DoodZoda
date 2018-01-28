package com.example.mojtaba.doodzoda.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class IntroViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private Context mContext;

    public IntroViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    public IntroViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = mTitleList.get(position);

        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(mContext.getAssets(), "fonts/Shabnam-FD.ttf"));

        SpannableStringBuilder s = new SpannableStringBuilder();
        s.append(title);
        s.setSpan(typefaceSpan, 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return SpannableString.valueOf(s);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mTitleList.add(title);
        mFragmentList.add(fragment);
    }
}
