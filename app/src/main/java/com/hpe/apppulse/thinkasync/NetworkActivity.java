package com.hpe.apppulse.thinkasync;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class NetworkActivity extends AppCompatActivity {
    public static final String logTag = "ThinkAsync.Main";
    public static final String httpbinBaseUrl = "http://httpbin.org/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
    }

    public void onSerial1(View view){
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this,"You seem to be disconnected from network",Toast.LENGTH_SHORT).show();
            return;
        }

        SerialNetworkAsyncTask asyncTask = new SerialNetworkAsyncTask(this);
        asyncTask.setIterations(4);
        SerialNetworkAsyncTask.reset();
        if (Build.VERSION.SDK_INT >= 11) {
            //--post GB use serial executor by default --
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            //--GB uses ThreadPoolExecutor by default--
            asyncTask.execute();
        }
    }


    public void onExecutorThread(View view) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this,"You seem to be disconnected from network",Toast.LENGTH_SHORT).show();
            return;
        }

        ExecuterNetworkAsyncTask asyncTask = new ExecuterNetworkAsyncTask(this);
        asyncTask.setIterations(5);
        asyncTask.execute();
    }

    public void onParallelThread(View view) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this,"You seem to be disconnected from network",Toast.LENGTH_SHORT).show();
            return;
        }

        SerialNetworkAsyncTask.reset();
        for(int i = 0; i < 4; i++) {

            SerialNetworkAsyncTask asyncTask = new SerialNetworkAsyncTask(this);
            asyncTask.setIterations(1);

            if (Build.VERSION.SDK_INT >= 11) {
                //--post GB use serial executor by default --
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                //--GB uses ThreadPoolExecutor by default--
                asyncTask.execute();
            }
        }
    }

    private void runAsyncTimes(int times, final TextView viewToUpdate){
        final StringBuffer value = new StringBuffer("");
        for(int i = 0; i< times; i++){
            value.setLength(0);
            value.append(i+1);
            Log.i(logTag, "runAsyncTimes: start the "+ (i+1) +" run");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(logTag, "Updating Status on main thread");
                    viewToUpdate.setText(value.toString());
                }
            });

            try {
                new AsyncNetTester(2).execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void onNetOnMain(View view) {
        //This will crash your application!!
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(httpbinBaseUrl+"get").openConnection());
            conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class AsyncRunnable implements Runnable{
        int times;
        TextView status;
        public AsyncRunnable(int times, TextView status){
            this.times = times;
            this.status = status;
        }
        @Override
        public void run() {
            runAsyncTimes(times, status);
        }
    }



    public class AsyncNetTester extends AsyncTask<String, Integer, Boolean>{

        int delaySeconds = 1;
        int times = 1;
        TextView toUpdate;

        public AsyncNetTester(int secDelay){
            delaySeconds = secDelay;
        }

        public AsyncNetTester(int times, TextView viewToUpdate){
            this.times = times;
            toUpdate = viewToUpdate;
        }
        @Override
        protected Boolean doInBackground(String... params) {
            for(int i = 0; i<times; i++) {
                publishProgress(i+1);
                HttpURLConnection conn = null;
                StringBuilder urlStrBuilder = new StringBuilder(httpbinBaseUrl);

                if (delaySeconds > 0) {
                    urlStrBuilder.append("delay/").append(delaySeconds);
                }
                String urlPath = urlStrBuilder.toString();
            /*if(urlPath != null)
                urlStrBuilder.append(urlPath);
            if(queryParameters != null){
                urlStrBuilder.append("?"+queryParameters);
            }*/
                int responseCode = -1;

                try {
                    String urlStr = urlStrBuilder.toString();
                    conn = (HttpURLConnection) (new URL(urlStr).openConnection());
                /*if(requestHeaders != null){
                    for(Map.Entry<String, String> headers : requestHeaders.entrySet()){
                        conn.addRequestProperty(headers.getKey(), headers.getValue());
                    }
                }
                if(postData != null){
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postData.getBytes());
                }*/
                    responseCode = conn.getResponseCode();
                    Log.i(logTag, "httpGETUrl=" + urlStr + ", ResponseCode=" + responseCode);
                } catch (Exception e) {
                    Log.e(logTag, "Failed to send message", e);
                    Log.d(logTag, e.getStackTrace().toString());
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
            return true;
        }
        protected void onProgressUpdate(Integer... progress) {
            if(toUpdate != null){
                Log.d(logTag, "updating status: "+String.valueOf(progress[0]));
                toUpdate.setText(String.valueOf(progress[0]));
            }
        }
    }
}
