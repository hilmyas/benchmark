package com.mamorasoft.app.frameworkbenchmark.helper;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class ProgressBarSwitch {
    public static void setTouchFalse(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void setTouchEnable(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void showProgressBar(Activity activity, ProgressBar progressBar, boolean show){
        if(show){
            progressBar.setVisibility(View.VISIBLE);
            setTouchFalse(activity);
        } else {
            progressBar.setVisibility(View.GONE);
            setTouchEnable(activity);
        }
    }
}
