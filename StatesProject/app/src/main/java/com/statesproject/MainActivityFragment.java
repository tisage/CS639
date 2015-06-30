package com.statesproject;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        String[] states = getResources().getStringArray(R.array.stateList);

        //create adapter
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                states);

        //tell the listview to use this adapter as datasource
        ListView listView = (ListView) rootView.findViewById(R.id.state_list_view);
        listView.setAdapter(stateAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 29) { // New Jersey
                    // Map Intent
                    Intent mapIntent = new Intent(getActivity(), MapsActivity.class);
                    startActivity(mapIntent);

                }
            }

        });

        return rootView;
    }


}