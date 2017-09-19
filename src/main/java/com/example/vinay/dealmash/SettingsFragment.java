package com.example.vinay.dealmash;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class SettingsFragment extends Fragment {

    public SettingsFragment() {
    }
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Switch aSwitch= (Switch)view.findViewById(R.id.mySwitch);
        aSwitch.setChecked(false);
        final Intent intent = new Intent(getActivity(),LocationSocketService.class);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity(),"Hello",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    Session session= new Session(getActivity());
                    String username = session.getusername();
                    EditText editText= (EditText)view.findViewById(R.id.radiusinput);
                    if(!username.equals("")){
                        session.putRadius(username,editText.getText().toString());
                    }
                    getActivity().startService(intent);
                }else{
                    getActivity().stopService(intent);
                }
            }
        });
        return view;
    }




}
