package com.codeshiv.jsonparser;

import android.content.Context;

import java.io.File;

public class ApplicationDataDirectories {

    public static ApplicationDataDirectories applicationDataDirectories;
    private JsonApplication jsonApplication;
    static final String LOGS_DIR_NAME = "logs";
    private File logsDir;
    private File applicationDir;

    private ApplicationDataDirectories(){

    }

    public static ApplicationDataDirectories getApplicationDataDirectories() {
        if(applicationDataDirectories == null){
            applicationDataDirectories = new ApplicationDataDirectories();
        }
        return applicationDataDirectories;
    }

    public void createApplicationDirectories(){
        jsonApplication = JsonApplication.getJsonApplication();
        Context context = jsonApplication.getApplicationContext();
        applicationDir = context.getExternalFilesDir(null);
        if (applicationDir != null) {
            applicationDir.mkdirs();
        }
        logsDir = createApplicationSubDirectory(LOGS_DIR_NAME);
    }

    private File createApplicationSubDirectory(String dirName) {
        final File dir = new File(applicationDir, dirName);
        mkdir(dir);
        return dir;
    }

    private void mkdir(File dir) {
        final boolean rc = dir.mkdirs();
    }
}