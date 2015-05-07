package com.github.sickan90.eda397ppapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


public class TimerActivity extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String PAIR_PROGRAMMING_TIMER_TIME = "PAIR_PROGRAMMING_TIMER_TIME";

    private SharedPreferences settings;
    private SharedPreferences.Editor settingsEditor;
    private int wantedTime;


    private TextView wantedTimeTextView, minTime, maxTime, countdownTime;
    private Button resetButton, startPauseButton;
    private SeekBar timeSeekBar;
    private Switch onOffSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        settings = getSharedPreferences(getResources().getString(R.string.preference_file_name), 0);
        settingsEditor = settings.edit();
        wantedTime = settings.getInt(PAIR_PROGRAMMING_TIMER_TIME, getResources().getInteger(R.integer.timer_default_time));

        // Get views
        wantedTimeTextView = (TextView) findViewById(R.id.timerWantedTimeTextField);
        minTime = (TextView) findViewById(R.id.timerMinTimeTextField);
        maxTime = (TextView) findViewById(R.id.timerMaxTimeTextField);
        countdownTime = (TextView) findViewById(R.id.timerCountdownTextField);
        resetButton = (Button) findViewById(R.id.timerResetButton);
        startPauseButton = (Button) findViewById(R.id.timerStartStopButton);
        timeSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        onOffSwitch = (Switch) findViewById(R.id.timerOnOffSwitch);



        // Set init values
        wantedTimeTextView.setText(wantedTime + "min");
        countdownTime.setText(wantedTime);
        minTime.setText("0");
        maxTime.setText(getResources().getInteger(R.integer.timer_max_time) + "");

        timeSeekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    protected void onStop() {
        settingsEditor.putInt(PAIR_PROGRAMMING_TIMER_TIME, wantedTime);
        settingsEditor.commit();
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


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        wantedTime = progress;
        wantedTimeTextView.setText(wantedTime + " min");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
