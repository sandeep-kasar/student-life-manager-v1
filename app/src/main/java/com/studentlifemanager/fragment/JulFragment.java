package com.studentlifemanager.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studentlifemanager.R;


public class JulFragment extends JanFragment {


    public JulFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jan, container, false);

        setUpView(view, 7);

        return view;

    }

}
