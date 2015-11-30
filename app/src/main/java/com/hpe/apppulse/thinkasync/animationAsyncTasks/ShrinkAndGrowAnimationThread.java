package com.hpe.apppulse.thinkasync.animationAsyncTasks;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hpe.apppulse.thinkasync.R;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * Created by rehana on 24/11/2015.
 */
public class ShrinkAndGrowAnimationThread extends Thread {

    public Handler workerThreadHandler;
    private Activity mainActivity;
    private int state = 0;
    CountDownLatch mLatch;

    public ShrinkAndGrowAnimationThread(Activity activity, CountDownLatch latch){
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
                Log.i("MsgToShrinkAGrowThread", "Message received from UI thread...");
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
                        // Total will be 0.2 + 0.6 + 0.2 = 1.0
                        List<Animator> animators = new LinkedList<Animator>();

                        Log.d("TA", String.format("supermanAndroid %f -> %f", supermanAndroidParams.weight, newSupermanAndroidWeight));
                        if (Math.abs(supermanAndroidParams.weight - newSupermanAndroidWeight) > 0.001) {
                            // new weight is different from existing weight
                            Animator va = makeWeightAnimator(mImageView1, supermanAndroidParams.weight, newSupermanAndroidWeight);
                            animators.add(va);
                        }


                        Log.d("TA", String.format("phoneAndroid %f -> %f", phoneAndroidParams.weight, newPhoneAndroidWeight));
                        if (Math.abs(phoneAndroidParams.weight - newPhoneAndroidWeight) > 0.001) {
                            // new weight is different from existing weight
                            Animator va = makeWeightAnimator(mImageView2, phoneAndroidParams.weight, newPhoneAndroidWeight);
                            animators.add(va);
                        }


                        Log.d("TA", String.format("android %f -> %f", androidParams.weight, newAndroidWeight));
                        if (Math.abs(androidParams.weight - newAndroidWeight) > 0.001) {
                            // new weight is different from existing weight
                            Animator va = makeWeightAnimator(mImageView3, androidParams.weight, newAndroidWeight);
                            animators.add(va);
                        }

                        if (animators.size() > 0) {
                            AnimatorSet s = new AnimatorSet();
                            s.playTogether(animators);
                            s.start();

                        }

                        state++;

                        if (state > 2) {
                            state = 0;
                        }
                    }
                });

            }

        };
        Log.i("MsgToShrinkAGrowThread", "ShrinkAndGrow thread ready..");
        mLatch.countDown();
        Looper.loop();
    }

    public Animator makeWeightAnimator(final View v, float startingWeight, float endingWeight) {
        long duration = 2000;
        ValueAnimator va = ValueAnimator.ofFloat(startingWeight, endingWeight);
        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                LinearLayout.LayoutParams paramsanim = (LinearLayout.LayoutParams) v.getLayoutParams();
                paramsanim.weight = value.floatValue();
                v.requestLayout();
            }
        });

        return va;
    }
}