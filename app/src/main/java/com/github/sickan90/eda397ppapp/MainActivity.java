package com.github.sickan90.eda397ppapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private final Context context = this;
    private final static String PREFERENCES = "PREFERENCES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RemoteRequester.getInstance().initialize(getCacheDir());
    }

    public void testLogin() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void planningGameButton(View view){
        Intent intent = new Intent(this, PlanningPoker.class);
        startActivity(intent);
    }

    public void trackerKeyButton(View view) {
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

    public void getStoriesButton(View view) {
        String url = "https://www.pivotaltracker.com/services/v5/projects/1310422/stories?date_format=millis&with_state=unstarted";
        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
        final String trackerKey = settings.getString("trackerKey", "");

        // Formulate the request and handle the response.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("Response", response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("X-Tracker-Project-Version", "62");
                headers.put("X-Tracker-Pagination-Limit", "100");
                headers.put("X-Tracker-Pagination-Offset", "0");
                headers.put("X-Tracker-Pagination-Total", "4");
                headers.put("X-Tracker-Pagination-Returned", "4");
                headers.put("X-TrackerToken", trackerKey);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        RemoteRequester.getInstance().addRequest(jsonArrayRequest);
    }

    public void addStoriesButton(View view) {

        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
        final String trackerKey = settings.getString("trackerKey", "");

        String requestURL = "https://www.pivotaltracker.com/services/v5/projects/1310422/stories";
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("name", "new_Story");


        // Formulate the request and handle the response.
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(
                        Request.Method.POST,
                        requestURL,
                        new JSONObject(postParams),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("Response", response.toString());
                                try {
                                    Log.i("Response", response.getString("name"));
                                    if (response.getInt("id") != 0)
                                        Log.i("Response ID", "Success!");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("Error", "Error: " + error.getMessage());
                                Toast.makeText(getApplicationContext(),
                                        error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        headers.put("X-Tracker-Project-Version", "63");
                        headers.put("X-TrackerToken", trackerKey);
                        return headers;
                    }
                };

        RemoteRequester.getInstance().addRequest(jsonObjectRequest);
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
