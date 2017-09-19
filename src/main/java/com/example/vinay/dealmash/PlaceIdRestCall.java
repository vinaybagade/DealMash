package com.example.vinay.dealmash;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vinay on 3/18/17.
 */

public class PlaceIdRestCall extends AsyncTask<Object,String,String[]> {
    LocationSocketService locationSocketService;
    Double latitude;
    Double longitude;
    String places;
    String radius;
    ArrayList<String> placeIds;

    @Override
    protected String[] doInBackground(Object... params) {
        locationSocketService = (LocationSocketService) params[0];
        places = (String) params[1];
        latitude = (Double) params[2];
        longitude = (Double) params[3];
        radius = (String) params[4];
        String[] vals = places.split(",");
        String[] json = new String[vals.length];
        DownloadUrl downloadUrl = new DownloadUrl();
        for (int i = 0; i < vals.length; i++) {
            String url = getUrl(latitude, longitude, vals[i], radius);
            try {
                json[i] = downloadUrl.readUrl(url);
                Log.d("json",json[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return json;


    }

    @Override
    protected void onPostExecute(String[] result) {
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
            Log.d("PlaceID",currentplace.get("placeId"));
            placeIds.add(currentplace.get("placeId"));
        }
        locationSocketService.setPlaceIds(placeIds);
    }

    private static String getUrl(double latitude, double longitude, String nearbyPlace, String radius) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + radius);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyCyjNr75Ld3RV4n_d8rk9lQHpAhN3NVm6M");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());

    }
}
