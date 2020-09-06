package com.messieyawo.medhack2020.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.messieyawo.medhack2020.R;


public class MedHackAppointMent extends Fragment {
    private final String JSON_URL = "https://gadsapi.herokuapp.com/api/skilliq";




    public MedHackAppointMent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_appointments, container, false);


        return view;
    }


}