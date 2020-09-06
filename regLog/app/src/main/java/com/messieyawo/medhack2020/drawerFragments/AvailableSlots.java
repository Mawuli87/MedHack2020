package com.messieyawo.medhack2020.drawerFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.messieyawo.medhack2020.R;


public class AvailableSlots extends Fragment {



    public AvailableSlots() {
        // Required empty public constructor
    }

   

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_available_slots, container, false);
    }
}