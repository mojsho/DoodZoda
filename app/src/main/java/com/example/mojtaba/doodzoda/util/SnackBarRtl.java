package com.example.mojtaba.doodzoda.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;


public class SnackBarRtl {
    private int TEXT_ALIGNMENT;
    private int LENGTH;
    private int textId;
    private View view;

    public SnackBarRtl(int TEXT_ALIGNMENT, int LENGTH, int textId, View view) {
        this.TEXT_ALIGNMENT = TEXT_ALIGNMENT;
        this.LENGTH = LENGTH;
        this.textId = textId;
        this.view = view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showSnack() {
        Snackbar snackbar = Snackbar.make(view, view.getResources().getString(textId), LENGTH);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView snackbar_textview = snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        snackbar_textview.setTextAlignment(TEXT_ALIGNMENT);
        snackbar_textview.setTextDirection(View.TEXT_DIRECTION_RTL);
        snackbar.show();
    }
}
