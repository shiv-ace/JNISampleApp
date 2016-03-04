package com.codeshiv.sensorsfunc;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.List;

public class Sensors implements SensorEventListener {

    private Sensor accelorometer;
    private SensorManager sensorManager;
    private static Sensors sensors = null;
    private List<Sensor> allsensors;
    private MainActivity mainActivity;
    private TextView textView = null;

    private Sensors(){

    }

    public static Sensors getSensors(){
        if(sensors == null){
            sensors = new Sensors();
        }
        return sensors;
    }

    public void initializeSensor(TextView textView){

        // Initializing the Activity Components
        this.textView = textView;
        mainActivity = MainActivity.getMainActivity();

        // Initializing the SensorManager
        sensorManager = (SensorManager)mainActivity.getSystemService(Context.SENSOR_SERVICE);
        allsensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        System.out.println(allsensors);

        // Initializing the ACCELEROMETER
        accelorometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accelorometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        this.textView.setText("X : " + event.values[0] + "\n" +
                "Y : " + event.values[1] +
                "\n" + "Z : " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}