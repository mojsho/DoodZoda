package com.example.mojtaba.doodzoda.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.util.GetLayoutParams;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroToAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroToAppFragment extends Fragment {

    private static final String ARG_PARAM_1 = "intro_title_text_view_height";
    private static final String ARG_PARAM_2 = "intro_title_text_view_height";

    private int titleTextViewHeight;
    private int footerLayoutHeight;

    @BindView(R.id.intro_fragment_top_container)
    LinearLayout mIntroFragmentTopContainer;

    @BindView(R.id.intro_fragment_bottom_container)
    LinearLayout mIntroFragmentBottomContainer;


    public IntroToAppFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 titleTextViewHeight.
     * @param param2 footerLayoutHeight.
     * @return A new instance of fragment IntroToAppFragment.
     */
    public static IntroToAppFragment newInstance(int param1, int param2) {
        IntroToAppFragment fragment = new IntroToAppFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        GetLayoutParams getLayoutParams = new GetLayoutParams(getContext());
        int statusBarHeight = getLayoutParams.getStatusBarHeight();

        LinearLayout.LayoutParams mIntroFragmentTopContainerLayoutParams = (LinearLayout.LayoutParams) mIntroFragmentTopContainer.getLayoutParams();
        mIntroFragmentTopContainerLayoutParams.setMargins(0, statusBarHeight + titleTextViewHeight, 0, 0);

        LinearLayout.LayoutParams mIntroFragmentBottomContainerLayoutParams = (LinearLayout.LayoutParams) mIntroFragmentBottomContainer.getLayoutParams();
        mIntroFragmentBottomContainerLayoutParams.setMargins(0, 0, 0, footerLayoutHeight);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro_to_app, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

}
