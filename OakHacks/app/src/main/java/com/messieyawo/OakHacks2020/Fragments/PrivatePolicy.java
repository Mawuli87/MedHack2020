package com.messieyawo.OakHacks2020.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.messieyawo.OakHacks2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivatePolicy extends Fragment {

    public PrivatePolicy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_private_policy, container, false);
    }
}
