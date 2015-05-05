package com.github.sickan90.eda397ppapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private final Context context = this;
    private final static String PREFERENCES = "PREFERENCES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button btnTestLogin = (Button) findViewById(R.id.testLoginButton);
        final Button btnAccountDetails = (Button) findViewById(R.id.accountDetails);

        View.OnClickListener accountListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptsView = layoutInflater.inflate(R.layout.tracker_key_dialog_layout, null);
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(promptsView);
                // Set an EditText view to get user input
                final EditText input = (EditText) promptsView.findViewById(R.id.trackerKeyPrompt);

                builder.setCancelable(false).
                        setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        // We need an Editor object to make preference changes.
                                        // All objects are from android.context.Context
                                        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putString("trackerKey", input.getText().toString());

                                        // Commit the edits!
                                        editor.commit();


                                        String trackerKey = settings.getString("trackerKey", "");

                                        Log.i("Key saved", trackerKey);

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("TrackerKeyDialog", "cancel key input");
                                    }
                                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        };




        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testLogin();
            }
        };

        btnTestLogin.setOnClickListener(clickListener);
        btnAccountDetails.setOnClickListener(accountListener);



        RemoteRequester.getInstance().initialize(getCacheDir());
    }

    public void testLogin() {
        String url ="https://www.pivotaltracker.com/services/v5/projects/1310422/stories";

// Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("EDA397", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
                String trackerKey = settings.getString("trackerKey", "");
                params.put("X-TrackerToken", trackerKey);

                return params;
            }
        };

// Add the request to the RequestQueue.
        RemoteRequester.getInstance().addRequest(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
