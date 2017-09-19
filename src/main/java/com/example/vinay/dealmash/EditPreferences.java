package com.example.vinay.dealmash;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EditPreferences extends Fragment {
    CustomAdapter customAdapter;
    public static EditPreferences newInstance() {
        EditPreferences fragment = new EditPreferences();
        return fragment;
    }

    public EditPreferences() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_preferences, container, false);
        ArrayList<PreferenceObject> preferenceObjects= new ArrayList<PreferenceObject>();
        PreferenceObject preferenceObject = new PreferenceObject("restaurant",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("bar",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("cafe",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("doctor",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("florist",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("furniture_store",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("hair_care",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("hardware_store",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("home_goods_store",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("hospital",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("jewelry_store",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("laundry",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("liquor_store",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("lodging",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("meal_takeaway",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("moving_company",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("pharmacy",false);
        preferenceObjects.add(preferenceObject);
        preferenceObject = new PreferenceObject("shopping_mall",false);
        preferenceObjects.add(preferenceObject);
        customAdapter = new CustomAdapter(getActivity(),R.layout.custom_list_element,preferenceObjects);
        ListView listView= (ListView)view.findViewById(R.id.editPreferenceslistview);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferenceObject preferenceObject1= (PreferenceObject)parent.getItemAtPosition(position);

            }
        });
        Button button = (Button)view.findViewById(R.id.additems);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer stringBuffer= new StringBuffer();
                ArrayList<PreferenceObject> arrayList= customAdapter.preferencelist;
                String prefix="";
                for(int i=0;i<arrayList.size();i++){
                    PreferenceObject preferenceObject1=arrayList.get(i);
                    if(preferenceObject1.isSelected()) {
                        stringBuffer.append(prefix);
                        prefix=",";
                        stringBuffer.append(preferenceObject1.getName());
                    }
                }
                Session session= new Session(getActivity());
                String username = session.getusername();
                if(!username.equals("")){
                    session.putPreferences(username,stringBuffer.toString());
                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ShowPreferences.newInstance())
                        .commit();
            }
        });

        return view;

    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    private class CustomAdapter extends ArrayAdapter<PreferenceObject>{
        ArrayList<PreferenceObject> preferencelist;
        public CustomAdapter(Context context, int resource, ArrayList<PreferenceObject> objects) {
            super(context, resource, objects);
            preferencelist = new ArrayList<PreferenceObject>();
            preferencelist.addAll(objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder viewHolder=null;
            if(convertView==null){
               LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.custom_list_element, null);
                viewHolder= new ViewHolder();
                viewHolder.name=(CheckBox)convertView.findViewById(R.id.preferenceCheckBox);
                convertView.setTag(viewHolder);
                viewHolder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox= (CheckBox)v;
                        PreferenceObject preferenceObject=(PreferenceObject)checkBox.getTag();
                        preferenceObject.setSelected(checkBox.isChecked());

                    }
                });
           }else{
                viewHolder= (ViewHolder)convertView.getTag();

           }
            PreferenceObject preferenceObject= preferencelist.get(position);
            viewHolder.name.setText(preferenceObject.getName());
            viewHolder.name.setChecked(preferenceObject.isSelected());
            viewHolder.name.setTag(preferenceObject);
            return convertView;
        }

        private class ViewHolder{
           private CheckBox name;
        }
    }



}
