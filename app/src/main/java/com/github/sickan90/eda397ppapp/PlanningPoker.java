package com.github.sickan90.eda397ppapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class PlanningPoker extends ActionBarActivity {
   private Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_poker);

        // Get all buttons
        /*
        buttons = new Button[6];
        buttons[0] = (Button) findViewById(R.id.planningPokerButton1);
        buttons[1] = (Button) findViewById(R.id.planningPokerButton2);
        buttons[2] = (Button) findViewById(R.id.planningPokerButton3);
        buttons[3] = (Button) findViewById(R.id.planningPokerButton4);
        buttons[4] = (Button) findViewById(R.id.planningPokerButton5);
        buttons[5] = (Button) findViewById(R.id.planningPokerButton6);

        // Set text size of all buttons
        for(Button b: buttons) {
            float temp = (float) (b.getHeight() * 0.8);
            Log.d("PlanningPoker", "The button text size is: " + temp);
            b.setTextSize(TypedValue.COMPLEX_UNIT_PX, temp);
        }
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plannig_poker, menu);
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

    public void numberClick(View view){
        Intent intent = new Intent(this, PlanningPokerCard.class);
        intent.putExtra("NUMBER", ((Button) view).getText());
        startActivity(intent);
    }
}
