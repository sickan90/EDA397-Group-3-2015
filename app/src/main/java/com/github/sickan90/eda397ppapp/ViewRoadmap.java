package com.github.sickan90.eda397ppapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;


public class ViewRoadmap extends ActionBarActivity {
    ListView listView;
    private final static String PREFERENCES = "PREFERENCES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_roadmap);
        RemoteRequester.getInstance().initialize(getCacheDir());

        listView = (ListView) findViewById(R.id.getStoriesList);

        String[] statusOfStories = {"Unstarted", "Started", "Finished", "Delivered",
                "Rejected", "Accepted"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, statusOfStories);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, final View arg1, int position,
                                    long id) {
                String statusOfStories = (String) listView.getItemAtPosition(position);
                System.out.println(statusOfStories);
                statusOfStories = statusOfStories.toLowerCase();
                String url = "https://www.pivotaltracker.com/services/v5/projects/1310422/stories?" +
                        "date_format=millis&with_state=" +
                        statusOfStories + "";
                SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
                final String trackerKey = settings.getString("trackerKey", "");

                // Formulate the request and handle the response.
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                //Log.i("Response", response.toString());
                                if (response == null || response.length() == 0) {
                                    Toast.makeText(getApplicationContext(), "There are no stories in this category.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent intent = new Intent(arg1.getContext(), ViewRoadmapList.class);
                                    intent.putExtra("RESPONSE", response.toString());
                                    startActivity(intent);
                                }
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
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_roadmap, menu);
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

    /*public void getStories(final View view, String statusOfStories) {
        System.out.println(statusOfStories);
        statusOfStories = statusOfStories.toLowerCase();
        String url = "https://www.pivotaltracker.com/services/v5/projects/1310422/stories?" +
                "date_format=millis&with_state=" +
                statusOfStories + "";
        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
        final String trackerKey = settings.getString("trackerKey", "");

        // Formulate the request and handle the response.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.i("Response", response.toString());
                        Intent intent = new Intent(view.getContext(), ViewRoadmapList.class);
                        intent.putExtra("RESPONSE", response.toString());
                        startActivity(intent);
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
    }*/
}
