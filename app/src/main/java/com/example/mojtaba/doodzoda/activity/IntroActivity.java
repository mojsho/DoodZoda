package com.example.mojtaba.doodzoda.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eftimoff.viewpagertransformers.StackTransformer;
import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.adapter.IntroViewPagerAdapter;
import com.example.mojtaba.doodzoda.fragment.IntroCostFragment;
import com.example.mojtaba.doodzoda.fragment.IntroPerDayFragment;
import com.example.mojtaba.doodzoda.fragment.IntroTitleFragment;
import com.example.mojtaba.doodzoda.fragment.IntroToAppFragment;
import com.example.mojtaba.doodzoda.receiver.ResponseReceiver;
import com.example.mojtaba.doodzoda.service.TimeLapseService;
import com.example.mojtaba.doodzoda.util.GetLayoutParams;
import com.example.mojtaba.doodzoda.util.SharedPreferencesManager;
import com.example.mojtaba.doodzoda.view.MyNumberPicker;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class IntroActivity extends AppCompatActivity {
    SharedPreferencesManager sharedPreferencesManager;
    private Intent timeLapseIntent;
    private IntentFilter intentFilter;
    private IntroViewPagerAdapter mIntroViewPagerAdapter;

    public static final String ACTION_RESP = "services.extra.TIME_PROCESSED";
    public static final String APP_LOCAL_STORE = "app-local-store";
    public static final String FRAGMENT_ARG_TEXT_VIEW_HEIGHT = "intro_title_text_view_height";
    public static final String FRAGMENT_ARG_FOOTER_LAYOUT_HEIGHT = "intro_footer_layout_height";

    @BindView(R.id.cigarettes_per_day_edit_text)
    TextView mCigarettesPerDay;

    @BindView(R.id.cigarettes_cost_edit_text)
    TextView mCigarettesCost;

    @BindView(R.id.intro_viewpager)
    ViewPager mIntroViewPager;

    @BindView(R.id.intro_title_text)
    TextView mIntroTitleTextView;

    @BindView(R.id.intro_frame_layout)
    FrameLayout mIntroFrameLayout;

    @BindView(R.id.intro_appbar_layout)
    AppBarLayout mIntroAppBarLayout;

    @BindView(R.id.intro_next_button)
    Button mIntroNextButton;

    @BindView(R.id.intro_previous_button)
    Button mIntroPreviousButton;

    @BindView(R.id.intro_footer_layout)
    LinearLayout mIntroFooterLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

//        animateStatusBar();

        final GetLayoutParams getLayoutParams = new GetLayoutParams(this);
        int statusBarHeight = getLayoutParams.getStatusBarHeight();

        FrameLayout.LayoutParams mIntroTitleLayoutParams = (FrameLayout.LayoutParams) mIntroTitleTextView.getLayoutParams();
        mIntroTitleLayoutParams.setMargins(0, statusBarHeight, 0, 0);

        mIntroViewPagerAdapter = new IntroViewPagerAdapter(getSupportFragmentManager(), this);

        mIntroTitleTextView.measure(0, 0);
        mIntroFooterLayout.measure(0, 0);

        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putInt(FRAGMENT_ARG_TEXT_VIEW_HEIGHT, mIntroTitleTextView.getMeasuredHeight());
        fragmentArgs.putInt(FRAGMENT_ARG_FOOTER_LAYOUT_HEIGHT, mIntroFooterLayout.getMeasuredHeight());

        Fragment introTitleFragment = new IntroTitleFragment();
        introTitleFragment.setArguments(fragmentArgs);

        Fragment introPerDayFragment = new IntroPerDayFragment();
        introPerDayFragment.setArguments(fragmentArgs);

        Fragment introCostFragment = new IntroCostFragment();
        introCostFragment.setArguments(fragmentArgs);

        Fragment introToAppFragment = new IntroToAppFragment();
        introToAppFragment.setArguments(fragmentArgs);

        mIntroViewPagerAdapter.addFragment(introTitleFragment, getString(R.string.intro_title));
        mIntroViewPagerAdapter.addFragment(introPerDayFragment, getString(R.string.intro_cigarettes_per_day));
        mIntroViewPagerAdapter.addFragment(introCostFragment, getString(R.string.intro_cigarettes_cost));
        mIntroViewPagerAdapter.addFragment(introToAppFragment, getString(R.string.intro_to_app));

        mIntroTitleTextView.setText(mIntroViewPagerAdapter.getPageTitle(mIntroViewPager.getCurrentItem()));

        mIntroViewPager.setPageTransformer(true, new StackTransformer());
        mIntroViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0: {
                        mIntroNextButton.setText(getString(R.string.intro_count_button_text));
                        mIntroPreviousButton.setVisibility(View.GONE);
                        break;
                    }
                    case 1: {
                        mIntroNextButton.setText(getString(R.string.intro_cost_button_text));
                        mIntroPreviousButton.setVisibility(View.VISIBLE);
                        mIntroPreviousButton.setText(getString(R.string.intro_title_button_text));
                        break;
                    }
                    case 2: {
                        mIntroNextButton.setText(getString(R.string.intro_to_app_button_text));
                        mIntroPreviousButton.setText(getString(R.string.intro_count_button_text));
                        break;
                    }
                    case 3: {
                        mIntroNextButton.setText(getString(R.string.intro_start_button_text));
                        mIntroPreviousButton.setText(getString(R.string.intro_cost_button_text));
                        break;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                CharSequence pageTitle = mIntroViewPagerAdapter.getPageTitle(position);
                mIntroTitleTextView.setText(pageTitle);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIntroViewPager.setAdapter(mIntroViewPagerAdapter);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getResources().getString(R.string.font_address))
                .setFontAttrId(R.attr.fontPath).build()
        );

        sharedPreferencesManager = new SharedPreferencesManager(this);
        if (!sharedPreferencesManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        timeLapseIntent = new Intent(this, TimeLapseService.class);

        intentFilter = new IntentFilter(ResponseReceiver.ACTION_TIME_PROGRESS);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void launchHomeScreen() {
        sharedPreferencesManager.setFirstTimeLaunch(false);
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.start_button)
    public void startButtonClicked() {
        launchHomeScreen();
        finish();

        Date startTime = new Date();

        sharedPreferencesManager.setPeriodTimeStart(startTime);
        sharedPreferencesManager.setUsagePerDay(Integer.parseInt(mCigarettesPerDay.getText().toString()));
        sharedPreferencesManager.setUsageCost(Integer.parseInt(mCigarettesCost.getText().toString()));

        startService(timeLapseIntent);
    }

    @OnClick(R.id.intro_next_button)
    public void introNextButton () {
        int position = mIntroViewPager.getCurrentItem();
        int pagesCount = mIntroViewPagerAdapter.getCount();
        if (position < pagesCount - 1) {
            if (position == 1) {
                MyNumberPicker mIntroCigarettePerDayNumberPicker = findViewById(R.id.intro_cigarettes_per_day_number_picker);

                int perDayIndex = mIntroCigarettePerDayNumberPicker.getValue();
                String perDay = getResources().getStringArray(R.array.intro_cigarette_per_day_items)[perDayIndex];

                sharedPreferencesManager.setUsagePerDay(Integer.valueOf(perDay));
            } else if (position == 2) {
                MyNumberPicker mIntroCigaretteCostNumberPicker = findViewById(R.id.intro_cigarettes_cost_number_picker);

                int costIndex = mIntroCigaretteCostNumberPicker.getValue();
                String cost = getResources().getStringArray(R.array.intro_cigarette_cost_items)[costIndex];

                sharedPreferencesManager.setUsageCost(Integer.valueOf(cost));
            }
            mIntroViewPager.setCurrentItem(position + 1, true);
        } else {
            launchHomeScreen();
            finish();

            Date startTime = new Date();

            sharedPreferencesManager.setPeriodTimeStart(startTime);

            startService(timeLapseIntent);
        }
    }

    @OnClick(R.id.intro_previous_button)
    public void mIntroPreviousButton () {
        mIntroViewPager.setCurrentItem(mIntroViewPager.getCurrentItem() - 1, true);
    }

    private void animateStatusBar() {
        int colorFrom = getResources().getColor(R.color.primaryDarkColor);
        final int colorTo = getResources().getColor(R.color.bloodColor);
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int color = (int) valueAnimator.getAnimatedValue();
                getWindow().setStatusBarColor(color);
            }
        });

        animator.setStartDelay(2000);
        animator.setDuration(250);
        animator.start();
    }
}
