package com.github.sickan90.eda397ppapp;

public class RemoteRequester {
    private static RemoteRequester ourInstance = new RemoteRequester();

    public static RemoteRequester getInstance() {
        return ourInstance;
    }

    private RemoteRequester() {
    }

}
