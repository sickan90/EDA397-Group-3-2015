package com.github.sickan90.eda397ppapp;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.github.sickan90.eda397ppapp.timer.CountDownTimerSingleton;

import java.text.DecimalFormat;


public class TimerActivity extends ActionBarActivity implements
        SeekBar.OnSeekBarChangeListener,
        CountDownTimerSingleton.ICountDownTimerSingletonListener {

    private final String TAG = "TimerActivity";
    public static final String PAIR_PROGRAMMING_TIMER_TIME = "PAIR_PROGRAMMING_TIMER_TIME";

    // Timer
    private CountDownTimerSingleton timer;
    private int wantedTime;
    private long remainingTime;
    private DecimalFormat intFormatter = new DecimalFormat("00");

    // Settings
    private SharedPreferences settings;
    private SharedPreferences.Editor settingsEditor;

    // Vies
    private TextView wantedTimeTextView, minTime, maxTime, countdownTime;
    private Button resetButton, startPauseButton;
    private SeekBar timeSeekBar;

    private boolean isTimerOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);


        // Setup setting storage
        settings = getSharedPreferences(getResources().getString(R.string.preference_file_name), 0);
        settingsEditor = settings.edit();

        // Setup timer
        wantedTime = settings.getInt(PAIR_PROGRAMMING_TIMER_TIME, getResources().getInteger(R.integer.timer_default_time));
        remainingTime = wantedTime * 60 * 1000;
        timer = CountDownTimerSingleton.getInstance();
        timer.registerListener(this);

        // Get views
        wantedTimeTextView = (TextView) findViewById(R.id.timerWantedTimeTextField);
        minTime = (TextView) findViewById(R.id.timerMinTimeTextField);
        maxTime = (TextView) findViewById(R.id.timerMaxTimeTextField);
        countdownTime = (TextView) findViewById(R.id.timerCountdownTextField);
        resetButton = (Button) findViewById(R.id.timerResetButton);
        startPauseButton = (Button) findViewById(R.id.timerStartStopButton);
        timeSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);

        if(timer.isRunning()) {
            remainingTime = timer.getRemainingTime();
            isTimerOn = true;
            startPauseButton.setText(getResources().getString(R.string.timer_pause_button_text));
        } else {
            remainingTime = wantedTime * 60 * 1000;
        }
        setCountdownTimeSecText(remainingTime);

        // Init view values
        wantedTimeTextView.setText(String.valueOf(wantedTime) + " min");
        minTime.setText("0");
        maxTime.setText(String.valueOf(getResources().getInteger(R.integer.timer_max_time)));
        timeSeekBar.setOnSeekBarChangeListener(this);
        timeSeekBar.setProgress(wantedTime);



    }

    @Override
    protected void onStop() {
        super.onStop();

        settingsEditor.putInt(PAIR_PROGRAMMING_TIMER_TIME, wantedTime);
        settingsEditor.commit();

        timer.unregisterListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onTimerPauseStart(View view) {

        if(isTimerOn) {
            // Pause button was clicked
            timer.cancel();
            startPauseButton.setText(getResources().getString(R.string.timer_start_button_text));
        } else {
            // Start button was clicked
            Log.d(TAG, "remainingTime when starting : " + remainingTime);
            timer.start(remainingTime, 1000);

            startPauseButton.setText(getResources().getString(R.string.timer_pause_button_text));
        }

        isTimerOn = !isTimerOn;

    }

    public void onTimerReset(View view) {
        timer.cancel();
        isTimerOn = false;
        startPauseButton.setText(getResources().getString(R.string.timer_start_button_text));
        remainingTime = wantedTime * 60 * 1000;
        setCountdownTimeSecText(wantedTime, 0);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        wantedTime = progress;
        wantedTimeTextView.setText(String.valueOf(wantedTime) + " min");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCountDownTimerTick(long t) {
        remainingTime = t;
        setCountdownTimeSecText(remainingTime);
    }

    @Override
    public void onCountDownTimerFinish() {
        final MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.solemn);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mPlayer.release();
            }
        });
        mPlayer.start();
        countdownTime.setText("Finish!");
    }

    private void setCountdownTimeSecText(long miliSec) {
        setCountdownTimeSecText(miliSec / 1000 / 60, (miliSec / 1000) % 60);
    }

    private void setCountdownTimeSecText(long min, long sec) {
        countdownTime.setText(intFormatter.format(min) + ":" + intFormatter.format(sec));
    }
}
