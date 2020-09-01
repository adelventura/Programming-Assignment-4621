package com.example.app_4621;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

public class Util {
    public static void themeStatusBar(Activity activity, boolean isDark) {
        // workaround for status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            if (isDark) {
                activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));

            } else {
                activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorWhite));
            }
        }
    }
}
