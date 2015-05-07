package com.github.sickan90.eda397ppapp;

import android.test.InstrumentationTestCase;
import android.widget.TextView;

import java.awt.font.TextAttribute;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class PlanningPokerDisplayTest extends android.test.ActivityInstrumentationTestCase2<PlanningPokerDisplay> {


        private PlanningPokerDisplay pokerActivityTest;
        private PlanningPokerDisplay activity;

        public PlanningPokerDisplayTest() {
            super(PlanningPokerDisplay.class);
        }

        @Override
        protected void setUp() throws Exception {
            super.setUp();
            pokerActivityTest = getActivity();

        }
    }
