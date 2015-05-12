package com.github.sickan90.eda397ppapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.widget.TextView;

import java.awt.font.TextAttribute;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class PlanningPokerDisplayTest extends ActivityInstrumentationTestCase2<PlanningPokerDisplay> {

    PlanningPokerDisplay activity;
    public PlanningPokerDisplayTest() {
        super(PlanningPokerDisplay.class);
    }

    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }
    public void testTextViewNotNull() {
        TextView textView = (TextView) activity.findViewById(R.id.planningPokerDisplayTextView);
        assertNotNull(textView);

    }
}