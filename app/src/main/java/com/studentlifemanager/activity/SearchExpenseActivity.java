package com.studentlifemanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.studentlifemanager.R;
import com.studentlifemanager.adapter.SearchExpDataAdapter;
import com.studentlifemanager.database.MyExpenseOperation;
import com.studentlifemanager.model.SearchExpObj;

import java.util.ArrayList;

public class SearchExpenseActivity extends AppCompatActivity {

    //declare class
    MyExpenseOperation myExpenseOperation;

    //ed_search
    EditText ed_search;

    //img_search
    ImageView img_search;

    //toolbar
    Toolbar toolbar;

    //RecyclerView for serch data
    RecyclerView recyclerView;

    //adapter to ho hold data
    SearchExpDataAdapter searchExpDataAdapter;

    //list to hold all object
    ArrayList<SearchExpObj> searchExpObjs;

    //adview
    //private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //set add
       /* mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        //init class
        myExpenseOperation=new MyExpenseOperation();

        //init list
        searchExpObjs =new ArrayList<>();

        //get data
        searchExpObjs =myExpenseOperation.getSearchData();

        //init adapter
        searchExpDataAdapter =new SearchExpDataAdapter(getApplicationContext(),SearchExpenseActivity.this, searchExpObjs);

        //init widgets
        ed_search=(EditText)findViewById(R.id.ed_search);
        img_search=(ImageView) findViewById(R.id.img_search);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        //set Adapters
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchExpenseActivity.this));

        //set backnavigation
        toolbar.setNavigationIcon(R.drawable.ic_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

        //add searching functionality
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //adding search functionality
                final String query = s.toString().toLowerCase().trim();//get query

                if (!query.equals("")) { //if query is not null

                    //take data from SearchExpObj and add into new filteredList
                    final ArrayList<SearchExpObj> filteredList = new ArrayList<>();
                    for (int i = 0; i < searchExpObjs.size(); i++) {

                        //find out entered keyword is present in customer list or not
                        final String title = searchExpObjs.get(i).getEx_title().toLowerCase();
                        final String note = searchExpObjs.get(i).getEx_note().toLowerCase();

                        //add sorted list in filteredList
                        if (title.contains(query) || note.contains(query)) {
                            filteredList.add(searchExpObjs.get(i));
                        }
                    }
                    //set new list to adapter and show in recyclerview
                    searchExpDataAdapter = new SearchExpDataAdapter(getApplicationContext(),SearchExpenseActivity.this, filteredList);
                    recyclerView.setAdapter(searchExpDataAdapter);
                    searchExpDataAdapter.notifyDataSetChanged();  // data set changed

                }else {//if query is null
                    //searchExpDataAdapter = new SearchExpDataAdapter(getApplicationContext(),SearchExpenseActivity.this, filteredList);
                    //recyclerView.setAdapter(searchExpDataAdapter);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
