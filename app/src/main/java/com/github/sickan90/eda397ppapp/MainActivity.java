package com.github.sickan90.eda397ppapp;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnTestLogin = (Button) findViewById(R.id.testLoginButton);
        final Button accountDetails = (Button) findViewById(R.id.accountDetails);

        View.OnClickListener accountListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment trackerKeyDialog = new TrackerKeyDialog();
                trackerKeyDialog.show(getSupportFragmentManager(), "account_details");
            }

        };




        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testLogin();
            }
        };

        btnTestLogin.setOnClickListener(clickListener);


        RemoteRequester.getInstance().initialize(getCacheDir());
    }

    public void testLogin() {
        String url ="https://www.pivotaltracker.com/services/v5/projects/1310422/stories";

// Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("EDA397", "flajsdflk");
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
                params.put("X-TrackerToken", "f35dec9692a27d0b77ce8379da26f9f0");

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
