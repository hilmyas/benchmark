package com.mamorasoft.app.frameworkbenchmark.helper;

import android.content.Context;

import com.mamorasoft.app.frameworkbenchmark.R;

public class ScreenUtil {
    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        float screenWidthDp = context.getResources().getDimension(R.dimen.tablet_item_list_layout);
        return (int)(screenWidthDp / columnWidthDp); // +0.5 for correct rounding to int.
    }
}
