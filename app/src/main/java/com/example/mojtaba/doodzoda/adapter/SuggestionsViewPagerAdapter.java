package com.example.mojtaba.doodzoda.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.model.Suggestion;

import java.util.List;

public class SuggestionsViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private Suggestion mSuggestion;
    private List<Suggestion> mSuggestions;
    private LayoutInflater mLayoutInflater;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mSuggestion = mSuggestions.get(position);

        View layout = mLayoutInflater.inflate(R.layout.main_suggestion_viewpager_item, container, false);

        ImageView suggestionImage = layout.findViewById(R.id.suggestion_image);
//        TextView suggestionText = layout.findViewById(R.id.suggestion_text);

        suggestionImage.setImageResource(mSuggestion.getDrawableId());
//        suggestionText.setText(mSuggestion.getTitle());

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public SuggestionsViewPagerAdapter(Context mContext, List<Suggestion> mSuggestions) {
        this.mContext = mContext;
        this.mSuggestions = mSuggestions;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mSuggestions.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mSuggestions.get(position).getTitle();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }
}
