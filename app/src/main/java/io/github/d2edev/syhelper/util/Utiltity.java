package io.github.d2edev.syhelper.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by d2e on 06.07.16.
 */
public class Utiltity {
    public static final String TAG="TAG_"+Utiltity.class.getSimpleName();


    //gets screen height in pixels
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}
