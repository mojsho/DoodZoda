package com.example.mojtaba.doodzoda.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.service.TimeLapseService;
import com.example.mojtaba.doodzoda.util.TextParserKt;
import com.example.mojtaba.doodzoda.view.StopSmokingCircularProgressBar;

public class ResponseReceiver extends BroadcastReceiver {
    public static final String ACTION_HEALTH_PROGRESS = "services.extra.ACTION_HEALTH_PROGRESS";
    public static final String ACTION_TIME_PROGRESS = "services.extra.ACTION_TIME_PROGRESS";
    public static final String ACTION_MAIN_PROGRESS = "services.extra.ACTION_MAIN_PROGRESS";
    TextView mTimeYearTextView, mTimeMonthTextView, mTimeDayTextView, mTimeHourTextView, mTimeMinuteTextView, mTimeSecondTextView, mTotalTimeMonthTextView, mTotalTimeDayTextView, mTotalTimeHourTextView, mTotalTimeMinuteTextView, mTotalTimeSecondTextView;
    String[] timeList, totalTimeList;
    String activityName, progressFinishedText, progressRemainingHourText, progressRemainingMinuteText;
    StopSmokingCircularProgressBar mCircularProgressBar1, mCircularProgressBar2, mCircularProgressBar3, mCircularProgressBar4, mCircularProgressBar5, mCircularProgressBar6, mMainCircularProgress;
    TextView mPercentage1, mTimeRemaining1, mTimeRemaining2, mTimeRemaining3, mTimeRemaining4, mTimeRemaining5, mTimeRemaining6, mPercentage2, mPercentage3, mPercentage4, mPercentage5, mPercentage6, mMainProgressText;

    String[] mMainProgressTextList = new String[13];

    public ResponseReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        timeList = intent.getStringArrayExtra(TimeLapseService.PARAM_OUT_1);
        totalTimeList = intent.getStringArrayExtra(TimeLapseService.PARAM_OUT_2);

        progressFinishedText = context.getResources().getString(R.string.progress_finished);
        progressRemainingHourText = context.getResources().getString(R.string.progress_remaining_hour);
        progressRemainingMinuteText = context.getResources().getString(R.string.progress_remaining_minute);

        mMainProgressTextList = context.getResources().getStringArray(R.array.main_progress_items);

        if (intent.getAction().equals(ACTION_TIME_PROGRESS)) {
            mTimeYearTextView = (TextView) ((Activity) context).findViewById(R.id.time_year_text_view);
            mTimeMonthTextView = (TextView) ((Activity) context).findViewById(R.id.time_month_text_view);
            mTimeDayTextView = (TextView) ((Activity) context).findViewById(R.id.time_day_text_view);
            mTimeHourTextView = (TextView) ((Activity) context).findViewById(R.id.time_hour_text_view);
            mTimeMinuteTextView = (TextView) ((Activity) context).findViewById(R.id.time_minute_text_view);
            mTimeSecondTextView = (TextView) ((Activity) context).findViewById(R.id.time_second_text_view);

//            mTotalTimeMonthTextView = (TextView) ((Activity) context).findViewById(R.id.total_time_month_text_view);
            mTotalTimeDayTextView = (TextView) ((Activity) context).findViewById(R.id.total_time_day_text_view);
            mTotalTimeHourTextView = (TextView) ((Activity) context).findViewById(R.id.total_time_hour_text_view);
            mTotalTimeMinuteTextView = (TextView) ((Activity) context).findViewById(R.id.total_time_minute_text_view);
            mTotalTimeSecondTextView = (TextView) ((Activity) context).findViewById(R.id.total_time_second_text_view);

            mTimeYearTextView.setText(timeList[0]);
            mTimeMonthTextView.setText(timeList[1]);
            mTimeDayTextView.setText(timeList[2]);
            mTimeHourTextView.setText(timeList[3]);
            mTimeMinuteTextView.setText(timeList[4]);
            mTimeSecondTextView.setText(timeList[5]);

//            mTotalTimeMonthTextView.setText(totalTimeList[1]);
            mTotalTimeDayTextView.setText(TextParserKt.numberFormat(totalTimeList[2]));
            mTotalTimeHourTextView.setText(TextParserKt.numberFormat(totalTimeList[3]));
            mTotalTimeMinuteTextView.setText(TextParserKt.numberFormat(totalTimeList[4]));
            mTotalTimeSecondTextView.setText(TextParserKt.numberFormat(totalTimeList[5]));

            // Main progress animation
            mMainCircularProgress = (StopSmokingCircularProgressBar) ((Activity) context).findViewById(R.id.main_progress_circular);
            mMainCircularProgress.setProgressWithAnimation(Float.valueOf(timeList[5]) % 20);

            // Random main progress text setting
            mMainProgressText = (TextView) ((Activity) context).findViewById(R.id.main_progress_circular_text);
            mMainProgressText.setText(mMainProgressTextList[Integer.parseInt(timeList[5]) % 13]);
        } else if (intent.getAction().equals(ACTION_HEALTH_PROGRESS)) {
            mCircularProgressBar1 = (StopSmokingCircularProgressBar) ((Activity) context).findViewById(R.id.circular_progress_bar_1);
            mCircularProgressBar2 = (StopSmokingCircularProgressBar) ((Activity) context).findViewById(R.id.circular_progress_bar_2);
            mCircularProgressBar3 = (StopSmokingCircularProgressBar) ((Activity) context).findViewById(R.id.circular_progress_bar_3);
            mCircularProgressBar4 = (StopSmokingCircularProgressBar) ((Activity) context).findViewById(R.id.circular_progress_bar_4);
            mCircularProgressBar5 = (StopSmokingCircularProgressBar) ((Activity) context).findViewById(R.id.circular_progress_bar_5);
            mCircularProgressBar6 = (StopSmokingCircularProgressBar) ((Activity) context).findViewById(R.id.circular_progress_bar_6);

            mPercentage1 = (TextView) ((Activity) context).findViewById(R.id.percentage_1);
            mTimeRemaining1 = ((Activity) context).findViewById(R.id.time_remaining_1);
            if (!(mCircularProgressBar1.getProgress() > 1200)) {
                mPercentage1.setText(String.format("%%%.1f", mCircularProgressBar1.getProgress() / 1200 * 100));
                mCircularProgressBar1.setProgressWithAnimation(Float.valueOf(totalTimeList[5]));
                mTimeRemaining1.setText(String.format(progressRemainingMinuteText, 20 - Integer.valueOf(totalTimeList[5]) / 3600));
            } else {
                mPercentage1.setText("%100");
                mTimeRemaining1.setText(progressFinishedText);
            }

            mPercentage2 = (TextView) ((Activity) context).findViewById(R.id.percentage_2);
            mTimeRemaining2 = ((Activity) context).findViewById(R.id.time_remaining_2);
            if (!(mCircularProgressBar2.getProgress() > 28800)) {
                mPercentage2.setText(String.format("%%%.1f", mCircularProgressBar2.getProgress() / 28800 * 100));
                mCircularProgressBar2.setProgressWithAnimation(Float.valueOf(totalTimeList[5]));
                mTimeRemaining2.setText(String.format(progressRemainingHourText, 8 - Integer.valueOf(totalTimeList[5]) / 3600));
            } else {
                mPercentage2.setText("%100");
                mTimeRemaining2.setText(progressFinishedText);
            }

            mPercentage3 = (TextView) ((Activity) context).findViewById(R.id.percentage_3);
            mTimeRemaining3 = ((Activity) context).findViewById(R.id.time_remaining_3);
            if (!(mCircularProgressBar3.getProgress() > 43200)) {
                mPercentage3.setText(String.format("%%%.1f", mCircularProgressBar3.getProgress() / 43200 * 100));
                mCircularProgressBar3.setProgressWithAnimation(Float.valueOf(totalTimeList[5]));
                mTimeRemaining3.setText(String.format(progressRemainingHourText, 12 - Integer.valueOf(totalTimeList[5]) / 3600));
            } else {
                mPercentage3.setText("%100");
                mTimeRemaining3.setText(progressFinishedText);
            }

            mPercentage4 = (TextView) ((Activity) context).findViewById(R.id.percentage_4);
            mTimeRemaining4 = ((Activity) context).findViewById(R.id.time_remaining_4);
            if (!(mCircularProgressBar4.getProgress() > 86400)) {
                mPercentage4.setText(String.format("%%%.1f", mCircularProgressBar4.getProgress() / 86400 * 100));
                mCircularProgressBar4.setProgressWithAnimation(Float.valueOf(totalTimeList[5]));
                mTimeRemaining4.setText(String.format(progressRemainingHourText, 24 -Integer.valueOf(totalTimeList[5]) / 3600));
            } else {
                mPercentage4.setText("%100");
                mTimeRemaining4.setText(progressFinishedText);
            }

            mPercentage5 = (TextView) ((Activity) context).findViewById(R.id.percentage_5);
            mTimeRemaining5 = ((Activity) context).findViewById(R.id.time_remaining_5);
            if (!(mCircularProgressBar5.getProgress() > 172800)) {
                mPercentage5.setText(String.format("%%%.1f", mCircularProgressBar5.getProgress() / 172800 * 100));
                mCircularProgressBar5.setProgressWithAnimation(Float.valueOf(totalTimeList[5]));
                mTimeRemaining5.setText(String.format(progressRemainingHourText, 48 - Integer.valueOf(totalTimeList[5]) / 3600));
            } else {
                mPercentage5.setText("%100");
                mTimeRemaining5.setText(progressFinishedText);
            }

            mPercentage6 = (TextView) ((Activity) context).findViewById(R.id.percentage_6);
            mTimeRemaining6 = ((Activity) context).findViewById(R.id.time_remaining_6);
            if (!(mCircularProgressBar6.getProgress() > 345600)) {
                mPercentage6.setText(String.format("%%%.1f", mCircularProgressBar6.getProgress() / 345600 * 100));
                mCircularProgressBar6.setProgressWithAnimation(Float.valueOf(totalTimeList[5]));
                mTimeRemaining6.setText(String.format(progressRemainingHourText, 72 - Integer.valueOf(totalTimeList[5]) / 3600));
            } else {
                mPercentage6.setText("%100");
                mTimeRemaining6.setText(progressFinishedText);
            }
        } else if (intent.getAction().equals(ACTION_MAIN_PROGRESS)) {

            //
        }
    }
}
