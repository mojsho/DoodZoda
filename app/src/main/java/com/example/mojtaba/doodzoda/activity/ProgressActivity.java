package com.example.mojtaba.doodzoda.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.receiver.ResponseReceiver;
import com.example.mojtaba.doodzoda.service.TimeLapseService;
import com.example.mojtaba.doodzoda.view.StopSmokingCircularProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProgressActivity extends AppCompatActivity {

    @BindView(R.id.circular_progress_bar_1)
    StopSmokingCircularProgressBar mCircularProgress1;

    @BindView(R.id.circular_progress_bar_2)
    StopSmokingCircularProgressBar mCircularProgress2;

    @BindView(R.id.circular_progress_bar_3)
    StopSmokingCircularProgressBar mCircularProgress3;

    @BindView(R.id.circular_progress_bar_4)
    StopSmokingCircularProgressBar mCircularProgress4;

    @BindView(R.id.circular_progress_bar_5)
    StopSmokingCircularProgressBar mCircularProgress5;

    @BindView(R.id.circular_progress_bar_6)
    StopSmokingCircularProgressBar mCircularProgress6;

    IntentFilter intentFilter;
    ResponseReceiver responseReceiver;
    Intent timeLapseIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_action_bar_background_color));
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.activity_progress_action_bar_custom_layout);

        ButterKnife.bind(this);

        intentFilter = new IntentFilter();
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        responseReceiver = new ResponseReceiver();

        timeLapseIntent = new Intent(this, TimeLapseService.class);
        startService(timeLapseIntent);

        mCircularProgress1.setMax(1200);
        mCircularProgress2.setMax(28800);
        mCircularProgress3.setMax(43200);
        mCircularProgress4.setMax(86400);
        mCircularProgress5.setMax(172800);
        mCircularProgress6.setMax(345600);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();

        intentFilter.addAction(ResponseReceiver.ACTION_HEALTH_PROGRESS);
        registerReceiver(responseReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(responseReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();

        intentFilter.addAction(ResponseReceiver.ACTION_HEALTH_PROGRESS);
        registerReceiver(responseReceiver, intentFilter);
    }

    @OnClick(R.id.action_bar_back_button)
    public void backButtonPressed() {
        super.onBackPressed();
    }
}
