package com.hpe.apppulse.thinkasync.animationAsyncTasks;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hpe.apppulse.thinkasync.R;

import java.util.concurrent.CountDownLatch;


/**
 * Created by rehana on 24/11/2015.
 */
public class FadeInAnimationThread extends Thread {

    public Handler workerThreadHandler;
    private Activity mainActivity;
    private int state = 0;
    CountDownLatch mLatch;

    public FadeInAnimationThread(Activity activity, CountDownLatch latch){
        mainActivity = activity;
        mLatch = latch;
    }


    public void run() {
        Looper.prepare();
        workerThreadHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    Thread.currentThread().sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("MsgToFadeInThread", "Message received from UI thread...");
                mainActivity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        ImageView mImageView1 = (ImageView) mainActivity.findViewById(R.id.imageview_animated_selector1);
                        mImageView1.setActivated(!mImageView1.isActivated());
                        ImageView mImageView2 = (ImageView) mainActivity.findViewById(R.id.imageview_animated_selector2);
                        mImageView2.setActivated(!mImageView2.isActivated());
                        ImageView mImageView3 = (ImageView) mainActivity.findViewById(R.id.imageview_animated_selector3);
                        mImageView3.setActivated(!mImageView3.isActivated());


                        LinearLayout.LayoutParams supermanAndroidParams = (LinearLayout.LayoutParams)mImageView1.getLayoutParams();
                        LinearLayout.LayoutParams phoneAndroidParams = (LinearLayout.LayoutParams)mImageView2.getLayoutParams();
                        LinearLayout.LayoutParams androidParams = (LinearLayout.LayoutParams)mImageView3.getLayoutParams();

                        float newSupermanAndroidWeight;
                        float newPhoneAndroidWeight;
                        float newAndroidWeight;

                        newSupermanAndroidWeight = 0.2f;
                        newPhoneAndroidWeight = 0.2f;
                        newAndroidWeight = 0.2f;

                        if (state == 0) {
                            // Make SupermanAndroid larger
                            newSupermanAndroidWeight = 0.6f;
                        } else if (state == 1) {
                            // Make phoneAndroid larger
                            newPhoneAndroidWeight = 0.6f;
                        } else {
                            // Make android larger
                            newAndroidWeight = 0.6f;
                        }

                        Log.d("TA", String.format("SupermanAndroid %f -> %f", supermanAndroidParams.weight, newSupermanAndroidWeight));
                        if (Math.abs(supermanAndroidParams.weight - newSupermanAndroidWeight) > 0.001) {
                            AnimationSet animationSet = new AnimationSet(true);
                            Animation animation1 = AnimationUtils.makeInAnimation(mImageView1.getContext(), true);
                            animation1.setDuration(2000);
                            Animation animation3 = new AlphaAnimation(0.5f,  1.0f);
                            animation3.setDuration(2000);
                            animation3.setStartOffset(2000);
                            animationSet.addAnimation(animation1);
                            animationSet.addAnimation(animation3);
                            mImageView1.startAnimation(animationSet);
                        }


                        Log.d("TA", String.format("phoneAndroid %f -> %f", phoneAndroidParams.weight, newPhoneAndroidWeight));
                        if (Math.abs(phoneAndroidParams.weight - newPhoneAndroidWeight) > 0.001) {
                            AnimationSet animationSet = new AnimationSet(true);
                            Animation animation1 = AnimationUtils.makeInAnimation(mImageView2.getContext(), true);
                            animation1.setDuration(2000);
                            Animation animation3 = new AlphaAnimation(0.5f,  1.0f);
                            animation3.setDuration(2000);
                            animation3.setStartOffset(2000);
                            animationSet.addAnimation(animation1);
                            animationSet.addAnimation(animation3);
                            mImageView2.startAnimation(animationSet);
                        }


                        Log.d("TA", String.format("android %f -> %f", androidParams.weight, newAndroidWeight));
                        if (Math.abs(androidParams.weight - newAndroidWeight) > 0.001) {
                            AnimationSet animationSet = new AnimationSet(true);
                            Animation animation1 = AnimationUtils.makeInAnimation(mImageView3.getContext(), true);
                            animation1.setDuration(2000);
                            Animation animation3 = new AlphaAnimation(0.5f,  1.0f);
                            animation3.setDuration(2000);
                            animation3.setStartOffset(2000);
                            animationSet.addAnimation(animation1);
                            animationSet.addAnimation(animation3);
                            mImageView3.startAnimation(animationSet);
                        }

                        state++;

                        if (state > 2) {
                            state = 0;
                        }
                    }
                });

            }

        };
        Log.i("MsgToFadeInThread", "FadeInAnimation thread ready..");
        mLatch.countDown();
        Looper.loop();
    }

}