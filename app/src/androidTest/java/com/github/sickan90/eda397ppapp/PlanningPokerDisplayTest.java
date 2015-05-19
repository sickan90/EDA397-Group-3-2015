package com.github.sickan90.eda397ppapp;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.support.v4.app.FragmentActivity;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.awt.font.TextAttribute;
import java.net.Authenticator;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class PlanningPokerDisplayTest extends ActivityInstrumentationTestCase2<PlanningPoker> {

    private PlanningPoker planningPokerActivity;
    //private PlanningPokerDisplay planningPokerDisplayActivity;
    private Button pressNumberButton;

    public PlanningPokerDisplayTest() {
        super(PlanningPoker.class);

    }
    protected void setUp() throws Exception{
        super.setUp();
        planningPokerActivity = getActivity();



    }
    public void testDisplayCorrectNumber(){

        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PlanningPokerDisplay.class.getName(), null, false);

        String button = "0";
        pressNumberButton = (Button) planningPokerActivity.findViewById(R.id.planningPokerButton1);
        String actual = pressNumberButton.getText().toString();
        assertEquals(button, actual);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pressNumberButton.performClick();
            }
        });

        PlanningPokerDisplay planningPokerDisplay = (PlanningPokerDisplay) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull(planningPokerDisplay);
        TextView textView = (TextView) planningPokerDisplay.findViewById(R.id.planningPokerDisplayTextView);
        assertNotNull(textView);
        assertEquals("0", textView.getText());


    }




}