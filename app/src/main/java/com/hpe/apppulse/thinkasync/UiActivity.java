package com.hpe.apppulse.thinkasync;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hpe.apppulse.thinkasync.animationAsyncTasks.FadeInAnimationThread;
import com.hpe.apppulse.thinkasync.animationAsyncTasks.ShrinkAndGrowAnimationThread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by rehana on 30/11/2015.
 */
public class UiActivity extends AppCompatActivity {

    private ImageButton play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_ui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        // Init layout components
        play = (ImageButton)findViewById(R.id.Play);


        CountDownLatch mCountDownLatch = new CountDownLatch(1);

        final ShrinkAndGrowAnimationThread shrinkAndGrowAnimationThread = new ShrinkAndGrowAnimationThread(this,mCountDownLatch);
        shrinkAndGrowAnimationThread.start();

        final FadeInAnimationThread fadeInAnimationThread = new FadeInAnimationThread(this,mCountDownLatch);
        fadeInAnimationThread.start();

        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Message msg = shrinkAndGrowAnimationThread.workerThreadHandler.obtainMessage();
                shrinkAndGrowAnimationThread.workerThreadHandler.sendMessage(msg);


                Message secondMsg = fadeInAnimationThread.workerThreadHandler.obtainMessage();
                fadeInAnimationThread.workerThreadHandler.sendMessage(secondMsg);

                CheckBox shouldMakeToast = (CheckBox)findViewById(R.id.checkBox);
                if (shouldMakeToast.isChecked()) {
                    ChangeLayoutTask changeLayoutTask = new ChangeLayoutTask(v.getContext());
                    if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB )
                        changeLayoutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        changeLayoutTask.execute();
                }
            }
        });


    }

    private class ChangeLayoutTask extends AsyncTask<String, String, String> {

        private Context myApp;

        private ChangeLayoutTask(Context app) {
            myApp = app;
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "yes";
        }

        @Override
        protected void onPostExecute(String versionSupported) {
            Toast.makeText(myApp, "Running animation..", Toast.LENGTH_LONG).show();
        }

    }
}
