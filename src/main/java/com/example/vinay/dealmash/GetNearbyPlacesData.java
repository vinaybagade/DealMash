package com.example.vinay.dealmash;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vinay on 3/5/17.
 */

public class GetNearbyPlacesData extends AsyncTask<Object,String,String[]> {

    GoogleMap mMap;
    static int PROXIMITY_RADIUS =5000;
    String places;
    Double latitude;
    Double longitude;
    ArrayList<String>placeIds;
    @Override
    protected String[] doInBackground(Object... params) {

        mMap = (GoogleMap) params[0];
        places=(String)params[1];
        latitude=(Double)params[2];
        longitude=(Double)params[3];
        String []vals=places.split(",");
        String[]json=new String[vals.length];
        DownloadUrl downloadUrl = new DownloadUrl();
        for(int i=0;i< vals.length;i++){
            String url= getUrl(latitude,longitude,vals[i]);
            try {
                json[i] = downloadUrl.readUrl(url);
                Log.d("json",json[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return json;
    }
    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        for (int i = 0; i < nearbyPlacesList.size(); i++) {

            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        }

    }
    @Override
    protected void onPostExecute(String []result) {
        List<HashMap<String, String>> fullplacelist= new ArrayList<>();
        for (int i=0;i<result.length;i++){
            List<HashMap<String, String>> currentnearbyPlacesList = null;
            DataParser dataParser = new DataParser();
            currentnearbyPlacesList=  dataParser.parse(result[i]);
            fullplacelist.addAll(currentnearbyPlacesList);
        }
        placeIds= new ArrayList<>();
        for(int i=0;i<fullplacelist.size();i++){
            HashMap<String,String> currentplace=fullplacelist.get(i);
            placeIds.add(currentplace.get("placeId"));
        }

        ShowNearbyPlaces(fullplacelist);

    }
    private static String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyCyjNr75Ld3RV4n_d8rk9lQHpAhN3NVm6M");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

}
