package com.studentlifemanager.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studentlifemanager.R;
import com.studentlifemanager.adapter.MyExpenseActivtyAdapter;
import com.studentlifemanager.database.MyExpenseOperation;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class JanFragment extends Fragment {

    //class
    MyExpenseOperation myExpenseOperation;


    //RecyclerView to hold data
    public RecyclerView recycler_view;

    //adapter to ho hold data
    public MyExpenseActivtyAdapter myExpenseAdapter;

    //list to hold all object
    private LinkedList<Object> objectList;


    public JanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflating layout
        View view = inflater.inflate(R.layout.fragment_jan, container, false);

        setUpView(view, 1);

        return view;

    }

    public void setUpView(View view, int month) {

        //set menu
        setHasOptionsMenu(true);

        //init class
        myExpenseOperation = new MyExpenseOperation();

        //init list
        objectList = new LinkedList<>();

        //init adapter
        myExpenseAdapter = new MyExpenseActivtyAdapter(getActivity(), JanFragment.this, objectList, month);

        //init widgets
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);

        //set Adapters
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.setAdapter(myExpenseAdapter);


        //get data
        setMonthData(month);
    }

    public void setMonthData(int month) {

        //clear objectlist
        objectList.clear();

        //add in linked list
        objectList = myExpenseOperation.getMonthData(month);

        //init adapter
        myExpenseAdapter = new MyExpenseActivtyAdapter(getActivity(), JanFragment.this, objectList, month);

        //set adapter to rv
        recycler_view.setAdapter(myExpenseAdapter);

        //notify adapter
        myExpenseAdapter.notifyDataSetChanged();
    }


}
