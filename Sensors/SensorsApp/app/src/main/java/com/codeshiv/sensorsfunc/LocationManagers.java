package com.codeshiv.sensorsfunc;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationManagers implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private MainActivity mainActivity;
    private TextView textView = null;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private static LocationManagers locationManagers = null;
    private List<Address> currentLocation = null;
    private String locality = null;

    private LocationManagers(){

    }

    public static LocationManagers getLocationManagers(){
        if(locationManagers == null){
            locationManagers = new LocationManagers();
        }
        return locationManagers;
    }

    public void initializeComponents(TextView textView){

        // Initializing the Activity Components
        this.textView = textView;
        mainActivity = MainActivity.getMainActivity();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mainActivity.getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mGoogleApiClient.connect();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1000);
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        if(mLastLocation != null){
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            this.textView.setText("Latitude = "+latitude +"\nLongitude = "+longitude);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("Location Changed "+location);

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();



        Geocoder geocoder = new Geocoder(mainActivity.getApplicationContext(), Locale.getDefault());
        try {
            currentLocation = geocoder.getFromLocation(latitude, longitude, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Address address : currentLocation){
            System.out.println(address.getAddressLine(0));
            locality = address.getAddressLine(0)+"  "+address.getAddressLine(1);
            break;
        }
        this.textView.setText("Latitude = " + latitude + "\nLongitude = " + longitude+"\n"+locality);

        mainActivity.displayNotification(locality);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
