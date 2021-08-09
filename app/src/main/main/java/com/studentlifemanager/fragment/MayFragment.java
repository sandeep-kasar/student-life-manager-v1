package com.studentlifemanager.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studentlifemanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MayFragment extends JanFragment {


    public MayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jan, container, false);

        setUpView(view, 5);

        return view;

    }

}
