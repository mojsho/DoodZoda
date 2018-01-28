package com.example.mojtaba.doodzoda.util;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SharedPreferencesManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    private static final String APP_LOCAL_STORE = "app-local-store";
    private static final String IS_FIRST_TIME_LAUNCH = "is_first_time_launch";
    private static final String CIGARETTES_PER_DAY = "cigarettes_per_day";
    private static final String CIGARETTES_COST = "cigarettes_cost";
    private static final String SERVICE_DATE = "services.extra.SERVICE_DATE";
    private static final String SERVICE_STARTED = "services.extra.SERVICE_STARTED";

    public SharedPreferencesManager(Context context) {
        this.context = context;
        this.pref = context.getSharedPreferences(APP_LOCAL_STORE, PRIVATE_MODE);
        this.editor = pref.edit();
        editor.apply();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUsagePerDay(int perDay) {
        editor.putInt(CIGARETTES_PER_DAY, perDay);
        editor.commit();
    }

    public void setUsageCost(int cost) {
        editor.putInt(CIGARETTES_COST, cost);
        editor.commit();
    }

    public List<Integer> getUsageData() {
        List<Integer> usageDataList = new ArrayList<>();

        usageDataList.add(pref.getInt(CIGARETTES_PER_DAY, 0));
        usageDataList.add(pref.getInt(CIGARETTES_COST, 0));

        return usageDataList;
    }

    public void setPeriodTimeStart(Date date) {
        editor.putBoolean(SERVICE_STARTED, true);
        editor.putLong(SERVICE_DATE, date.getTime());
        editor.apply();
    }

    public boolean getServiceStarted() {
        return pref.getBoolean(SERVICE_STARTED, false);
    }

    public long getStartTime() {
        return pref.getLong(SERVICE_DATE, -1);
    }
}
