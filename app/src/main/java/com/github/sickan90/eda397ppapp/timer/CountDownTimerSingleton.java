package com.github.sickan90.eda397ppapp.timer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.CountDownTimer;
import android.util.Log;

import com.github.sickan90.eda397ppapp.TimerActivity;

import java.util.LinkedList;
import java.util.List;

public class CountDownTimerSingleton {
    private final String TAG = "CountDownTimerSingleton";
    private static CountDownTimerSingleton instance;
    private long remainingTime;
    private CountDownTimer timer;
    private List<ICountDownTimerSingletonListener> listeners = new LinkedList<ICountDownTimerSingletonListener>();
    private boolean isRunning = false;

    protected CountDownTimerSingleton() {
        // Protect from being istanciated from other class
    }

    public static CountDownTimerSingleton getInstance() {
        if(instance == null) {
            instance = new CountDownTimerSingleton();
        }
        return instance;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void startTimer() {
        start(remainingTime, 1000);
    }

    public void start(long time) {
        start(time, 1000);
    }

    public void start(long time, long intervall) {
        if(timer != null) {
            timer.cancel();
        }
        isRunning = true;
        timer = new CountDownTimer(time, intervall) {
            @Override
            public void onTick(long l) {
                remainingTime = l;
                for(ICountDownTimerSingletonListener listener : listeners) {
                    listener.onCountDownTimerTick(remainingTime);
                    Log.d(TAG, "Tick: " + l);
                }
            }

            @Override
            public void onFinish() {

                for(ICountDownTimerSingletonListener listener : listeners) {
                    listener.onCountDownTimerFinish();
                    isRunning = false;
                }
            }
        }.start();
    }

    public void cancel () {
        timer.cancel();
        isRunning = false;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void registerListener(ICountDownTimerSingletonListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(ICountDownTimerSingletonListener listener) {
        listeners.remove(listener);
    }

    public interface ICountDownTimerSingletonListener {
        public void onCountDownTimerTick(long t);

        public void onCountDownTimerFinish();
    }
}
