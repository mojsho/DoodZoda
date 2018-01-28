package com.example.mojtaba.doodzoda.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.view.StopSmokingViewPager;

import butterknife.ButterKnife;


public class TimeLapseFragment extends Fragment {

    StopSmokingViewPager stopSmokingViewPager;

    public TimeLapseFragment() {
        // Required empty public constructor
    }

    public static TimeLapseFragment newInstance(String param1, String param2) {
        TimeLapseFragment fragment = new TimeLapseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_lapse, container, false);
        ButterKnife.bind(this, view);

        stopSmokingViewPager = (StopSmokingViewPager) container;

        return view;
    }
}
