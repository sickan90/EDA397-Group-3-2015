package com.github.sickan90.eda397ppapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Roadmap extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadmap);

        String nameOfStory = getIntent().getStringExtra("NAME");
        String descriptionOfStory = getIntent().getStringExtra("DESCRIPTION");
        String priorityOfStory = getIntent().getStringExtra("PRIORITY");
        String estimateOfStory = getIntent().getStringExtra("ESTIMATE");
        String statusOfStory = getIntent().getStringExtra("STATUS");

        priorityOfStory = priorityOfStory.replaceAll("\\D+","");

        ((TextView) findViewById(R.id.storyNameText)).setText("Name: " + nameOfStory);
        ((TextView) findViewById(R.id.storyDescriptionText)).setText("Description: " + descriptionOfStory);
        ((TextView) findViewById(R.id.storyPriorityText)).setText("Priority: " + priorityOfStory);
        ((TextView) findViewById(R.id.storyEstimateText)).setText("Estimate: " + estimateOfStory);
        ((TextView) findViewById(R.id.storyStatusText)).setText("Status: " + statusOfStory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_individual_story, menu);
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
