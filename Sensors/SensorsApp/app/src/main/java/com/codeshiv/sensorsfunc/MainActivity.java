package com.codeshiv.sensorsfunc;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

public class MainActivity extends Activity{

    public TextView textView;
    private static MainActivity mainActivity = null;
    private Sensors sensors = null;
    private LocationManagers locationManagers = null;
    private NotificationManager notificationManager = null;
    private int notificationIdOne = 111;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text);
        mainActivity = this;

//        sensors = Sensors.getSensors();
//        sensors.initializeSensor(textView);

        locationManagers = LocationManagers.getLocationManagers();
        locationManagers.initializeComponents(textView);
    }

    public static MainActivity getMainActivity(){
        return mainActivity;
    }

    public void displayNotification(String location){

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);

        notification.setContentTitle("Location Updated");
        notification.setContentText("Current Location = " + location);
        notification.setTicker("New Location");
        notification.setSmallIcon(R.drawable.common_plus_signin_btn_icon_light_normal);
        notification.setNumber(++count);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationIdOne,notification.build());
    }
}