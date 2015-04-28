package com.github.sickan90.eda397ppapp;

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

import java.io.File;

public class RemoteRequester {
    private static RemoteRequester ourInstance = new RemoteRequester();

    public static RemoteRequester getInstance() {
        return ourInstance;
    }

    private RequestQueue requestQueue;

    private RemoteRequester() {

    }

    /**
     * Initialize the request queue with a cache directory.
     * @param cacheDir The cache directory.
     */
    public void initialize(File cacheDir) {
        // Instantiate the cache
        Cache cache = new DiskBasedCache(cacheDir, 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);
    }

    /**
     * Adds a volley request to the request queue. It will be sent using a separate thread.
     * @param request The request to be sent.
     */
    public void addRequest(Request request) {
        requestQueue.add(request);
    }

}
