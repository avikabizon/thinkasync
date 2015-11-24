package com.hpe.apppulse.thinkasync;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kabizon on 7/24/2015.
 */

public class NetworkUtils {

    //Based on a stackoverflow snippet
    public static boolean isNetworkAvailable(Activity activity) {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        catch (Exception e) {
            return false;
        }
    }
}
