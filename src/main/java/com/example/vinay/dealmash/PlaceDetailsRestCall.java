package com.example.vinay.dealmash;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vinay on 3/18/17.
 */

public class PlaceDetailsRestCall extends AsyncTask<Object,String,ArrayList<String>> {
    LocationSocketService locationSocketService;
    ArrayList<String>Urls;
    @Override
    protected ArrayList<String> doInBackground(Object... params) {
        locationSocketService=(LocationSocketService)params[0];
        ArrayList<String>placeids=(ArrayList<String>)params[1];
        DownloadUrl downloadUrl = new DownloadUrl();
        Urls= new ArrayList<>();
        for(int i=0;i<placeids.size();i++){
            String url= getUrl(placeids.get(i));
            String json="";
            try {
                json = downloadUrl.readUrl(url);
                JSONObject jsonObject= new JSONObject(json);
                if(!jsonObject.getJSONObject("result").isNull("website")) {
                    String website = jsonObject.getJSONObject("result").getString("website");
                    Urls.add(website);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Urls;
    }

    @Override
    protected void onPostExecute(ArrayList<String> list) {
        locationSocketService.setUrlList(list);
    }

    private static String getUrl(String placeid) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        googlePlacesUrl.append("placeid="+placeid);
        googlePlacesUrl.append("&key=" + "AIzaSyCyjNr75Ld3RV4n_d8rk9lQHpAhN3NVm6M");
        return (googlePlacesUrl.toString());
    }
}
