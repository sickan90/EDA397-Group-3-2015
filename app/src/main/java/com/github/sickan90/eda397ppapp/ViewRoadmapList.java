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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ViewRoadmapList extends ActionBarActivity {
    ListView listView;
    private final static String PREFERENCES = "PREFERENCES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_roadmap_list);
            RemoteRequester.getInstance().initialize(getCacheDir());

            JSONArray stories = null;
            try {
                stories = new JSONArray(getIntent().getStringExtra("RESPONSE"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(stories);

            int numberOfStories = countStories(stories);
            final String[] nameOfStory = new String[numberOfStories];
            final String[] descriptionOfStory = new String[numberOfStories];
            final String[] priorityOfStory = new String[numberOfStories];
            final String[] estimateOfStory = new String[numberOfStories];
            final String[] statusOfStory = new String[numberOfStories];
            final String[] idOfStory = new String[numberOfStories];
            if (stories == null || stories.length() == 0) {
                Toast.makeText(getApplicationContext(), "There are no stories in this category.",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                for (int i = 0; i < stories.length(); i++) {
                    try {
                        nameOfStory[i] = stories.getJSONObject(i).getString("name");
                        descriptionOfStory[i] = stories.getJSONObject(i).getString("description");
                        priorityOfStory[i] = stories.getJSONObject(i).getJSONArray("labels").getJSONObject(0).getString("name");
                        //System.out.println(priorityOfStory[i]);
                        estimateOfStory[i] = stories.getJSONObject(i).getString("estimate");
                        statusOfStory[i] = stories.getJSONObject(i).getString("current_state");
                        idOfStory[i] = stories.getJSONObject(i).getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                listView = (ListView) findViewById(R.id.nameOfStoriesList);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, nameOfStory);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                            long id) {
                        String story = (String) listView.getItemAtPosition(position);

                        int index = 0;
                        for (int i = 0; i < nameOfStory.length; i++) {
                            if (nameOfStory[i].equals(story))
                                index = i;
                        }

                        JSONArray tasks = getTasks(idOfStory[index]);
                        System.out.println("prufa: " + tasks);
                        //getNameOfTask(tasks);

                        Intent intent = new Intent(getApplicationContext(), ViewIndividualStory.class);
                        intent.putExtra("NAME", nameOfStory[index]);
                        intent.putExtra("DESCRIPTION", descriptionOfStory[index]);
                        intent.putExtra("PRIORITY", priorityOfStory[index]);
                        intent.putExtra("ESTIMATE", estimateOfStory[index]);
                        intent.putExtra("STATUS", statusOfStory[index]);
                        startActivity(intent);
                    }
                });
            }
    }

    public int countStories(JSONArray stories) {
        int counter = 0;

        for (int i = 0; i < stories.length(); i ++) {
            try {
                if (stories.getJSONObject(i).getString("name") != null) {
                    counter++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return counter;
    }

    public JSONArray getTasks(String storyID) {
        String url = "https://www.pivotaltracker.com/services/v5/projects/1310422/stories/" +
            storyID + "/tasks";
        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
        final String trackerKey = settings.getString("trackerKey", "");
        JSONArray response = null;

        // Formulate the request and handle the response.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
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

        return response;
    }

    public String[] getNameOfTask(JSONArray tasks)
    {
        System.out.println(tasks);
        //int numberOfTasks = getNumberOfTasks(tasks);
        //System.out.println(numberOfTasks);
        String[] nameOfTask = new String[2];

        for (int i = 0; i < tasks.length(); i++) {

        }

        return nameOfTask;
    }

    /*public String[] getStatusOfTask(JSONArray tasks)
    {
        return
    }*/

    public int getNumberOfTasks(JSONArray tasks)
    {
        int counter = 0;
        System.out.println(tasks);

        for (int i = 0; i < tasks.length(); i++) {
            try {
                if (tasks.getJSONObject(i).getString("description") != null) {
                    counter++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return counter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_roadmap_list, menu);
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
