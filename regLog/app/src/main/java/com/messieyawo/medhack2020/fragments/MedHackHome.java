package com.messieyawo.medhack2020.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.messieyawo.medhack2020.R;
import com.messieyawo.medhack2020.RegisterActivity;


public class MedHackHome extends Fragment {

    private final String JSON_URL = "https://gadsapi.herokuapp.com/api/hours" ;


    public MedHackHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_medhack_home, container, false);

       Button btn = view.findViewById(R.id.reg_button);

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent regIntent = new Intent(getActivity(), RegisterActivity.class);
               startActivity(regIntent);
           }
       });

        return view;

    }



}