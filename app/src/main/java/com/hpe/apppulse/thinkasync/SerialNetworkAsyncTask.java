package com.hpe.apppulse.thinkasync;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kabizon on 11/18/2015.
 */

public class SerialNetworkAsyncTask extends AsyncTask<Void, Void, Boolean> {
    public static final String HTTPBIN_URL = "http://httpbin.org/delay/2";
    private static int mIterations = 5;
    private static int mCurrentIter = 0;
    private Context mContext;

    public SerialNetworkAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) (new URL(HTTPBIN_URL).openConnection());
            conn.getResponseCode();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {

        if (mCurrentIter++ < mIterations) {
            SerialNetworkAsyncTask newAsync = new SerialNetworkAsyncTask(mContext);
            if (Build.VERSION.SDK_INT >= 11) {
                //--post GB use serial executor by default --
                newAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                //--GB uses ThreadPoolExecutor by default--
                newAsync.execute();
            }
        }
        else {
            //AlertDialogUtil.showAlertDialog(mContext, "Operation ended");
        }

        Toast.makeText(mContext,String.format("Async network #%d finished",mCurrentIter),Toast.LENGTH_SHORT).show();
    }

    public void setIterations(int iterations) {
        mIterations = iterations;
    }

    public static void reset() {
        mCurrentIter = 0;
    }
}
