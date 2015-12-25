package com.codeshiv.jsonparser;

import android.app.Application;

import com.codeshiv.jsonparser.util.ApplicationDataDirectories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonApplication extends Application {

    public static JsonApplication jsonApplication;
    private ApplicationDataDirectories applicationDataDirectories = ApplicationDataDirectories.getApplicationDataDirectories();
    private final Logger logger = LoggerFactory.getLogger(JsonApplication.class);

    public void onCreate() {
        super.onCreate();
        jsonApplication = this;
        setupLogging();
        logger.debug("JsonApplication = "+jsonApplication);
    }

    public static JsonApplication getJsonApplication(){
        return jsonApplication;
    }

    private void setupLogging(){
        applicationDataDirectories.createApplicationDirectories();
    }
}