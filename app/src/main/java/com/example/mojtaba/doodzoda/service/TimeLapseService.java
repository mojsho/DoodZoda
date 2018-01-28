package com.example.mojtaba.doodzoda.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mojtaba.doodzoda.receiver.ResponseReceiver;
import com.example.mojtaba.doodzoda.util.SharedPreferencesManager;

import java.text.DecimalFormat;
import java.util.Date;


public class TimeLapseService extends Service {

    public static final String PARAM_OUT_1 = "services.extra.TIME_INTERVAL";
    public static final String PARAM_OUT_2 = "services.extra.TOTAL_TIME_INTERVAL";

    public static final String APP_LOCAL_STORE = "app-local-store";
    SharedPreferences mSharedPreferences;

    String[] timeList = new String[6];
    String[] totalTimeList = new String[6];
    private String LOG_TAG = null;

    @Override
    public void onCreate() {
        super.onCreate();
        LOG_TAG = this.getClass().getSimpleName();
        Log.i(LOG_TAG, "In onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "In onBind");
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "In onStartCommand");

        final SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);

        if (intent != null) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    while (!isInterrupted()) {
                        try {
                            DecimalFormat decimalFormat = new DecimalFormat("00");
                            long diff = new Date().getTime() - sharedPreferencesManager.getStartTime();
                            long totalSeconds = diff / 1000;
                            long totalMinutes = totalSeconds / 60;
                            long totalHours = totalMinutes / 60;
                            long totalDays = totalHours / 24;
                            int totalMonths = (int) (totalDays / 30);
                            int totalYears = totalMonths / 12;

                            totalTimeList[0] = String.valueOf(totalYears);
                            totalTimeList[1] = String.valueOf(totalMonths);
                            totalTimeList[2] = String.valueOf(totalDays);
                            totalTimeList[3] = String.valueOf(totalHours);
                            totalTimeList[4] = String.valueOf(totalMinutes);
                            totalTimeList[5] = String.valueOf(totalSeconds);

                            String seconds = decimalFormat.format(totalSeconds % 60);
                            String minutes = decimalFormat.format(totalMinutes % 60);
                            String hours = decimalFormat.format(totalHours % 24);
                            String days = decimalFormat.format(totalDays % 30);
                            String months = decimalFormat.format(totalMonths % 12);
                            String years = decimalFormat.format(totalYears);

                            timeList[0] = years;
                            timeList[1] = months;
                            timeList[2] = days;
                            timeList[3] = hours;
                            timeList[4] = minutes;
                            timeList[5] = seconds;

                            Intent healthBroadCastIntent = new Intent();
                            healthBroadCastIntent.setAction(ResponseReceiver.ACTION_HEALTH_PROGRESS);
                            healthBroadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                            healthBroadCastIntent.putExtra(PARAM_OUT_1, timeList);
                            healthBroadCastIntent.putExtra(PARAM_OUT_2, totalTimeList);
                            sendBroadcast(healthBroadCastIntent);

                            Intent timeBroadCastIntent = new Intent();
                            timeBroadCastIntent.setAction(ResponseReceiver.ACTION_TIME_PROGRESS);
                            timeBroadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                            timeBroadCastIntent.putExtra(PARAM_OUT_1, timeList);
                            timeBroadCastIntent.putExtra(PARAM_OUT_2, totalTimeList);
                            sendBroadcast(timeBroadCastIntent);

                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
        }

        return START_STICKY;
    }
}
