package com.example.vinay.dealmash;

import android.*;
import android.Manifest;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class LocationSocketService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    Double latitude;
    Double longitude;
    private int PROXIMITY_RADIUS = 10000;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    ArrayList<String> placeIds;
    ArrayList<String> urls;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    public LocationSocketService() {
        mLastLocation = null;
        latitude = null;
        longitude = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        buildGoogleApiClient();
        //Toast.makeText(this,"In the service",Toast.LENGTH_SHORT).show();
        return  START_STICKY;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(15000);
        mLocationRequest.setFastestInterval(15000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    public void getPlaceIds() {
        Object[] DataTransfer = new Object[5];
        Session session = new Session(this);
        String values = session.getPreferences(session.getusername());
        String radius = session.getRadius(session.getusername());
        if(radius.equals("")){
            radius="5000";
        }
        String[] val = values.split(",");
        if (val.length > 0) {
            DataTransfer[0] = this;
            DataTransfer[1] = values;
            DataTransfer[2] = latitude;
            DataTransfer[3] = longitude;
            DataTransfer[4] = radius;
            Log.d("getplaceids",values);
            PlaceIdRestCall placeIdRestCall = new PlaceIdRestCall();
            placeIdRestCall.execute(DataTransfer);
        }


    }

    public void setPlaceIds(ArrayList<String> places) {
        placeIds = places;
        PlaceDetailsRestCall placeDetailsRestCall = new PlaceDetailsRestCall();
        Object[] datatransfer = new Object[2];
        datatransfer[0] = this;
        datatransfer[1] = places;
        placeDetailsRestCall.execute(datatransfer);

    }

    public void setUrlList(ArrayList<String> list) {
        urls = list;
        Thread socketthread = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket=null;
                try {
                    String host = "localhost";
                    int port = 25000;
                    InetAddress address = InetAddress.getByName(host);
                    socket = new Socket("169.234.36.58", port);
                    OutputStream os = socket.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
                    for (int i = 0; i < urls.size(); i++) {
                        bw.write(urls.get(i) + "\n");
                        bw.flush();
                    }
                    bw.write("" + "\n");
                    bw.flush();
                    String inputLine = "";
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader in = new BufferedReader(isr);
                    while ((inputLine = in.readLine()) != null) {
                        if (inputLine.equals("")) {
                            break;
                        }
                        NotificationCompat.Builder mBuilder =
                                (NotificationCompat.Builder) new NotificationCompat.Builder(LocationSocketService.this)
                                        .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                                        .setContentTitle("Hurry")
                                        .setContentText(inputLine);
                        NotificationManager mNotificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        mNotificationManager.notify(001, mBuilder.build());

                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        socketthread.start();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (latitude == null && longitude == null) {
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            getPlaceIds();
        } else {
            if (Math.abs(latitude - location.getLatitude()) > 2.0) {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                getPlaceIds();
            }
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
