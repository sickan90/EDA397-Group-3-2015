package com.github.sickan90.eda397ppapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class PairProgrammingTipsDisplay extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_programming_tips_display);

        String headline = getIntent().getStringExtra("TIP");

        if (headline.equals("Define the task"))
            ((TextView) findViewById(R.id.pairProgrammingDisplayTextView)).setText("Task should be " +
                    "well defined for each person in the team. Also it is important that each " +
                    "person is able to complete the task in the specific time.");
        else if (headline.equals("Start small"))
            ((TextView) findViewById(R.id.pairProgrammingDisplayTextView)).setText("Start with small" +
                    " tasks. Try to explain the problem for your partner and engage your partner " +
                    "to find the solution.");
        else if (headline.equals("Respond"))
            ((TextView) findViewById(R.id.pairProgrammingDisplayTextView)).setText("Respond to " +
                    "your partner's questions when he/she needs help");
        else if (headline.equals("Trust"))
            ((TextView) findViewById(R.id.pairProgrammingDisplayTextView)).setText("When you are " +
                    "driver finish the small task quickly and trust your partner hints.");
        else if (headline.equals("Read code"))
            ((TextView) findViewById(R.id.pairProgrammingDisplayTextView)).setText("Read the " +
                    "code when you are the observer, find bugs, problems and better possible " +
                    "way that is hidden from the driver.");
        else if (headline.equals("No dictating"))
            ((TextView) findViewById(R.id.pairProgrammingDisplayTextView)).setText("Don’t dictate " +
                    "the code when you are observer, driver should actively look for the goal " +
                    "not just typing the code.");
        else if (headline.equals("Discussion"))
            ((TextView) findViewById(R.id.pairProgrammingDisplayTextView)).setText("Discuss a " +
                    "lot about the task, ideas, and possible way to implement them. Find " +
                    "alternative ideas and also find inputs that code does not cover.");
        else if (headline.equals("Sync"))
            ((TextView) findViewById(R.id.pairProgrammingDisplayTextView)).setText("When " +
                    "working together, you will find yourself getting out of sync: This is normal. " +
                    "When it happens, sync up again. The key to good pairing is to sync up very " +
                    "frequently—within seconds or a minute of noticing that you're out of sync. " +
                    "It's the frequent re-syncing that creates the synergy of pairing.");
        else
            ((TextView) findViewById(R.id.pairProgrammingDisplayTextView)).setText("Something went " +
                    "wrong! Please, try again.");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pair_programming_tips_display, menu);
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
}
