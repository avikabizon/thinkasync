package com.hpe.apppulse.thinkasync;

        import android.content.Context;
        import android.os.AsyncTask;
        import android.widget.Toast;

        import java.io.IOException;
        import java.net.HttpURLConnection;
        import java.net.URL;

/**
 * Created by selao on 11/26/2015.
 */

public class ExecuterNetworkAsyncTask extends AsyncTask<Void, Void, Boolean> {
    public static final String HTTPBIN_URL = "http://httpbin.org/delay/2";
    private int mIterations = 5;
    private Context mContext;

    public ExecuterNetworkAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpURLConnection conn = null;
        for (int i = 0; i < mIterations; i++) {
            try {
                conn = (HttpURLConnection) (new URL(HTTPBIN_URL).openConnection());
                conn.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return true;
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        //AlertDialogUtil.showAlertDialog(mContext, String.format("Operation %s", aBoolean.booleanValue()==true? "succeeded":"failed"));
        Toast.makeText(mContext,String.format("Operation %s", aBoolean.booleanValue()==true? "succeeded":"failed"),Toast.LENGTH_SHORT).show();
    }

    public void setIterations(int iterations) {
        mIterations = iterations;
    }
}
