package com.mamorasoft.app.frameworkbenchmark.helper;

import android.app.Activity;
import android.widget.Toast;

public class ToastHelper {

    public static void showToast(Activity activity, String message, int duration){
        Toast.makeText(activity, message, duration).show();
    }
}
