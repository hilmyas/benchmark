package com.mamorasoft.app.frameworkbenchmark.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

public class PermissionManager {
    public static void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET},
                Constants.CODE_REQUEST_PERMISSION);
    }

    public static boolean isRequiredPermissionAllGranted(Context context){
        boolean is = true;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            is = false;
        }

        return is;
    }
}
