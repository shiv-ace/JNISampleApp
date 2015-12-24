package com.codeshiv.jsonparser;

import android.app.Application;

public class JsonApplication extends Application {

    public static JsonApplication jsonApplication;
    private ApplicationDataDirectories applicationDataDirectories = ApplicationDataDirectories.getApplicationDataDirectories();

    public void onCreate() {
        super.onCreate();
        jsonApplication = this;
        setupLogging();
    }

    public static JsonApplication getJsonApplication(){
        return jsonApplication;
    }

    private void setupLogging(){
        applicationDataDirectories.createApplicationDirectories();
    }
}