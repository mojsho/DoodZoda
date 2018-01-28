package com.example.mojtaba.doodzoda.util;

import android.content.Context;

public class GetLayoutParams {
    private Context context;

    public GetLayoutParams(Context context) {
        this.context = context;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
