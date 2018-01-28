package com.example.mojtaba.doodzoda.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.adapter.MainPageTimePagerAdapter;
import com.example.mojtaba.doodzoda.adapter.SuggestionsViewPagerAdapter;
import com.example.mojtaba.doodzoda.model.Suggestion;
import com.example.mojtaba.doodzoda.receiver.ResponseReceiver;
import com.example.mojtaba.doodzoda.service.TimeLapseService;
import com.example.mojtaba.doodzoda.util.ServerExchange;
import com.example.mojtaba.doodzoda.util.SharedPreferencesManager;
import com.example.mojtaba.doodzoda.view.StopSmokingCircularProgressBar;
import com.example.mojtaba.doodzoda.view.StopSmokingViewPager;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private static final int READ_EXTERNAL_PERMISSION = 1;
    private static final int TIME_LAPSE_VIEW_PAGER_INTERVAL = 5000;
    private static final int SUGGESTIONS_VIEW_PAGER_INTERVAL = 3000;

    @BindView(R.id.main_progress_circular)
    StopSmokingCircularProgressBar mMainCircularProgress;

    @BindView(R.id.main_time_view_pager)
    StopSmokingViewPager mMainTimeViewPager;

    @BindView(R.id.main_time_view_pager_indicator)
    PageIndicatorView mMainPageTimeViewPagerIndicator;

    @BindView(R.id.more_comments_button)
    TextView mMoreCommentsButton;

    @BindView(R.id.time_lapse_container)
    LinearLayout mTimeLapseContainer;

    @BindView(R.id.time_lapse_expand_button)
    ImageView mTimeLapseExpandButton;

    @BindView(R.id.main_suggestions_viewpager)
    ViewPager mMainSuggestionViewPager;

    @BindView(R.id.main_to_progress_image_container)
    FrameLayout mMainToProgressImageViewContainer;

    @BindView(R.id.main_progress_card_view)
    CardView mMainProgressCardView;

    @BindView(R.id.main_suggestions_card_view)
    CardView mMainSuggestionsCardView;

    @BindView(R.id.main_suggestions_viewpager_title)
    TextView mMainSuggestionsCardViewTitle;

    DateFormat dateFormat;
    IntentFilter intentFilter;
    Intent timeLapseIntent;
    MainPageTimePagerAdapter mainPageTimePagerAdapter;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    SharedPreferencesManager sharedPreferencesManager;
    ServerExchange serverExchange;
    SuggestionsViewPagerAdapter suggestionsViewPagerAdapter;

    private ResponseReceiver responseReceiver;
    private Date startTime;
    private static int PICK_FILE_RESULT_CODE = 0;
    private Handler handler = new Handler();
    public Runnable timeLapseRunnable;
    public Runnable suggestionsRunnable;
    int mainSuggestionsCardViewMeasuredHeight = 0;
    int step = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FILE_RESULT_CODE) {
            serverExchange = new ServerExchange(this, data.getData());
            serverExchange.uploadImage();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        mainSuggestionsCardViewMeasuredHeight = mMainProgressCardView.getHeight();
        setSuggestionsCardViewHeight(mainSuggestionsCardViewMeasuredHeight);

        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Sample Suggestion Generation
        generateSampleSuggestions();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getResources().getString(R.string.font_address))
                .setFontAttrId(R.attr.fontPath).build()
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element));
        }

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_action_bar_background_color));
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.activity_main_action_bar_custom_layout);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        mMainPageTimeViewPagerIndicator.setAnimationType(AnimationType.COLOR);

        final TextView mTimeFragmentTitle = findViewById(R.id.time_fragment_title);
        mainPageTimePagerAdapter = new MainPageTimePagerAdapter(getSupportFragmentManager(), this);
        mMainTimeViewPager.setPagingEnabled(true);
        mMainTimeViewPager.setAdapter(mainPageTimePagerAdapter);
        mTimeFragmentTitle.setText(mainPageTimePagerAdapter.getPageTitle(mMainTimeViewPager.getCurrentItem()));
        mMainTimeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                String pageTitle = (String) mainPageTimePagerAdapter.getPageTitle(position);
                mTimeFragmentTitle.setText(pageTitle);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mainViewPagersShow(mMainTimeViewPager, mMainSuggestionViewPager);

        timeLapseIntent = new Intent(this, TimeLapseService.class);

        intentFilter = new IntentFilter();
        intentFilter.addAction(ResponseReceiver.ACTION_TIME_PROGRESS);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        responseReceiver = new ResponseReceiver();

        if (sharedPreferencesManager.getServiceStarted()) {
            startService(timeLapseIntent);
        }

        dateFormat = new SimpleDateFormat("hh:mm:ss");

        mMainCircularProgress.setMin(0);
        mMainCircularProgress.setMax(20);

        ServerExchange serverExchange = new ServerExchange(this);
        serverExchange.requestAllComments();

        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(R.layout.restart_period_alert_dialog_custom_layout);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();

        intentFilter.addAction(ResponseReceiver.ACTION_TIME_PROGRESS);
        registerReceiver(responseReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        intentFilter.addAction(ResponseReceiver.ACTION_TIME_PROGRESS);
        registerReceiver(responseReceiver, intentFilter);

        handler.postDelayed(timeLapseRunnable, TIME_LAPSE_VIEW_PAGER_INTERVAL);
        handler.postDelayed(suggestionsRunnable, SUGGESTIONS_VIEW_PAGER_INTERVAL);
    }

    @OnClick(R.id.restart_button)
    public void restartButtonClicked() {
        alertDialog = dialogBuilder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(responseReceiver);

        if (handler != null) {
            handler.removeCallbacks(timeLapseRunnable);
            handler.removeCallbacks(suggestionsRunnable);
        }
    }

    public void alertDialogPositive(View v) {
        startTime = new Date();
        sharedPreferencesManager.setPeriodTimeStart(startTime);
        alertDialog.dismiss();
    }

    public void alertDialogNegative(View v) {
        alertDialog.dismiss();
    }

    @OnClick(R.id.main_to_progress_button)
    public void goToHealthProgress() {
        Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.more_comments_button)
    public void goToComments() {
        Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
    }

    @OnClick(R.id.image_upload_test)
    public void imageUpload() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_PERMISSION);
        }

        Intent getFileIntent = new Intent();
        getFileIntent.setType("image/*");
        getFileIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(getFileIntent, "Choose Image!"), PICK_FILE_RESULT_CODE);

    }

    @OnClick(R.id.time_lapse_expand_button)
    public void timeLapseExpandButtonClicked() {
        LinearLayout.LayoutParams mTimeLapseContainerLayoutParams = (LinearLayout.LayoutParams) mTimeLapseContainer.getLayoutParams();

        final int newTopMargin = mTimeLapseContainer.getHeight();

        if (mTimeLapseContainerLayoutParams.topMargin == -newTopMargin) {
            animateExpandTimeLapse(0);
            mTimeLapseExpandButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_white_48px));

        } else {
            animateExpandTimeLapse(newTopMargin);
            mTimeLapseExpandButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_white_48px));
        }
    }

    public void animateExpandTimeLapse(final int topMargin) {
        // With Animation
        Animation timeLapseAnimation = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                LinearLayout.LayoutParams mTimeLapseContainerLayoutParams = (LinearLayout.LayoutParams) mTimeLapseContainer.getLayoutParams();
                mTimeLapseContainerLayoutParams.topMargin = -topMargin;
                mTimeLapseContainer.setLayoutParams(mTimeLapseContainerLayoutParams);
            }
        };

        unregisterReceiver(responseReceiver);
        timeLapseAnimation.setDuration(1000);
        mTimeLapseContainer.startAnimation(timeLapseAnimation);
        intentFilter.addAction(ResponseReceiver.ACTION_TIME_PROGRESS);
        registerReceiver(responseReceiver, intentFilter);

        // Without Animation
//        unregisterReceiver(responseReceiver);
//        LinearLayout.LayoutParams mTimeLapseContainerLayoutParams = (LinearLayout.LayoutParams) mTimeLapseContainer.getLayoutParams();
//        mTimeLapseContainerLayoutParams.topMargin = -topMargin;
//        mTimeLapseContainer.setLayoutParams(mTimeLapseContainerLayoutParams);
//        intentFilter.addAction(ResponseReceiver.ACTION_TIME_PROGRESS);
//        registerReceiver(responseReceiver, intentFilter);

    }

    public void mainViewPagersShow(final ViewPager timeLapseViewPager, final ViewPager suggestionsViewPager) {
        timeLapseRunnable = new Runnable() {
            @Override
            public void run() {
                int position = timeLapseViewPager.getCurrentItem();
                int count = timeLapseViewPager.getChildCount();
                if (position == count - 1) {
                    position -= 1;
                } else {
                    position += 1;
                }
                timeLapseViewPager.setCurrentItem(position, true);
                handler.postDelayed(timeLapseRunnable, TIME_LAPSE_VIEW_PAGER_INTERVAL);
            }
        };

        suggestionsRunnable = new Runnable() {
            @Override
            public void run() {
                int position = suggestionsViewPager.getCurrentItem();
                int count = suggestionsViewPager.getChildCount();
                if (position == 3) {
//                    step = -1;
//                    position += step;
                    position = 0;
                } else if (position == 0) {
                    step = 1;
                    position += step;
                } else {
                    position += step;
                }
                suggestionsViewPager.setCurrentItem(position, true);
                handler.postDelayed(suggestionsRunnable, SUGGESTIONS_VIEW_PAGER_INTERVAL);
            }
        };
    }

    public void generateSampleSuggestions() {
        Suggestion suggestion;
        List<Suggestion> suggestions = new ArrayList<>();

        suggestion = new Suggestion(R.drawable.ic_open_book, getResources().getString(R.string.main_suggestion_item1));
        suggestions.add(suggestion);

        suggestion = new Suggestion(R.drawable.ic_theater, getResources().getString(R.string.main_suggestion_item2));
        suggestions.add(suggestion);

        suggestion = new Suggestion(R.drawable.ic_cinema, getResources().getString(R.string.main_suggestion_item3));
        suggestions.add(suggestion);

        suggestion = new Suggestion(R.drawable.ic_herbal, getResources().getString(R.string.main_suggestion_item4));
        suggestions.add(suggestion);

        suggestionsViewPagerAdapter = new SuggestionsViewPagerAdapter(MainActivity.this, suggestions);

        mMainSuggestionViewPager.setAdapter(suggestionsViewPagerAdapter);
        mMainSuggestionsCardViewTitle.setText(suggestionsViewPagerAdapter.getPageTitle(0));

        mMainSuggestionViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mMainSuggestionsCardViewTitle.setText(suggestionsViewPagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setSuggestionsCardViewHeight(int newMeasuredHeight) {
        LinearLayout.LayoutParams mMainSuggestionsCardViewLayoutParams = (LinearLayout.LayoutParams) mMainSuggestionsCardView.getLayoutParams();
        mMainSuggestionsCardViewLayoutParams.height = newMeasuredHeight;
        mMainSuggestionsCardView.setLayoutParams(mMainSuggestionsCardViewLayoutParams);
    }
}
