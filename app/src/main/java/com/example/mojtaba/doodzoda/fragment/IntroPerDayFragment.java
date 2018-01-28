package com.example.mojtaba.doodzoda.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.util.GetLayoutParams;
import com.example.mojtaba.doodzoda.util.SharedPreferencesManager;
import com.example.mojtaba.doodzoda.view.MyNumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroPerDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroPerDayFragment extends Fragment {

    private static final String ARG_PARAM_1 = "intro_title_text_view_height";
    private static final String ARG_PARAM_2 = "intro_footer_layout_height";

    private int titleTextViewHeight;
    private int footerLayoutHeight;
    private SharedPreferencesManager sharedPreferencesManager;

    @BindView(R.id.intro_cigarettes_per_day_number_picker)
    MyNumberPicker mCigarettePerDayNumberPicker;

    @BindView(R.id.intro_fragment_top_container)
    LinearLayout mIntroFragmentTopContainer;

    @BindView(R.id.intro_fragment_bottom_container)
    LinearLayout mIntroFragmentBottomContainer;

    public IntroPerDayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 titleTextViewHeight.
     * @param param2 footerLayoutHeight.
     * @return A new instance of fragment IntroPerDayFragment.
     */
    public static IntroPerDayFragment newInstance(int param1, int param2) {
        IntroPerDayFragment fragment = new IntroPerDayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_1, param1);
        args.putInt(ARG_PARAM_2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            titleTextViewHeight = getArguments().getInt(ARG_PARAM_1);
            footerLayoutHeight = getArguments().getInt(ARG_PARAM_2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro_per_day, container, false);
        ButterKnife.bind(this, view);

        sharedPreferencesManager = new SharedPreferencesManager(getContext());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        GetLayoutParams getLayoutParams = new GetLayoutParams(getContext());
        int statusBarHeight = getLayoutParams.getStatusBarHeight();

        String[] perDayStrings = getContext().getResources().getStringArray(R.array.intro_cigarette_per_day_items);
        mCigarettePerDayNumberPicker.setMinValue(0);
        mCigarettePerDayNumberPicker.setMaxValue(perDayStrings.length - 1);
        mCigarettePerDayNumberPicker.setDisplayedValues(perDayStrings);
        mCigarettePerDayNumberPicker.setWrapSelectorWheel(false);
        mCigarettePerDayNumberPicker.setTextColor(getContext().getResources().getColor(R.color.primaryTextColor));
        mCigarettePerDayNumberPicker.setDividerColor(getContext().getResources().getColor(R.color.primaryTextColor));

        LinearLayout.LayoutParams mIntroFragmentTopContainerLayoutParams = (LinearLayout.LayoutParams) mIntroFragmentTopContainer.getLayoutParams();
        mIntroFragmentTopContainerLayoutParams.setMargins(0, statusBarHeight + titleTextViewHeight, 0, 0);

        LinearLayout.LayoutParams mIntroFragmentBottomContainerLayoutParams = (LinearLayout.LayoutParams) mIntroFragmentBottomContainer.getLayoutParams();
        mIntroFragmentBottomContainerLayoutParams.setMargins(0, 0, 0, footerLayoutHeight);
    }

}
