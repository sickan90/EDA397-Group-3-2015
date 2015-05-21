package com.github.sickan90.eda397ppapp;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class PlanningPokerDisplayTest extends ActivityInstrumentationTestCase2<PlanningPoker> {

    private PlanningPoker planningPokerActivity;
    private Button pressNumberButton;

    public PlanningPokerDisplayTest() {
        super(PlanningPoker.class);

    }
    protected void setUp() throws Exception{
        super.setUp();
        planningPokerActivity = getActivity();
    }

    /**
     * Testing: Comparing the press button from the planningPoker and receiving the correct output.
     */
    public void testDisplayCorrectNumber(){

        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PlanningPokerDisplay.class.getName(), null, false);
        String button = "0";
        pressNumberButton = (Button) planningPokerActivity.findViewById(R.id.planningPokerButton1);
        String actual = pressNumberButton.getText().toString();
        assertEquals(button, actual);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Pressing the chosen number ("0")
                pressNumberButton.performClick();
            }
        });
        //stating the new activity displaying the chosen number
        PlanningPokerDisplay planningPokerDisplay = (PlanningPokerDisplay) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 50000);
        assertNotNull(planningPokerDisplay);
        TextView textView = (TextView) planningPokerDisplay.findViewById(R.id.planningPokerDisplayTextView);
        assertNotNull(textView);
        assertEquals("0", textView.getText());


    }




}