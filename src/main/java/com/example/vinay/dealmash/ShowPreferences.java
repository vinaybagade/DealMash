package com.example.vinay.dealmash;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ShowPreferences extends Fragment {


    public static ShowPreferences newInstance() {
        ShowPreferences fragment = new ShowPreferences();
        return fragment;
    }

    public ShowPreferences() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_show_preferences, container, false);

        Session session= new Session(getActivity());
               String values=session.getPreferences(session.getusername());
        String[]val=values.split(",");
       // Toast.makeText(getActivity().getApplicationContext(),"Clicked on Row: " + val[0]+" "+val[1]+" "+val[2],Toast.LENGTH_LONG).show();
        if(val.length>0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_element, val);
            ListView listView= (ListView)view.findViewById(R.id.ShowPreferencelist);
            listView.setAdapter(adapter);
        }
        return view;

    }

}
