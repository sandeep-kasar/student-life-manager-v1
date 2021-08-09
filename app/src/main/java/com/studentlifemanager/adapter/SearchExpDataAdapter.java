package com.studentlifemanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studentlifemanager.R;
import com.studentlifemanager.activity.SearchExpenseActivity;
import com.studentlifemanager.helper.CommonUtils;
import com.studentlifemanager.model.SearchExpObj;
import com.studentlifemanager.viewholder.SearchExpHol;

import java.util.ArrayList;

/**
 * Created by Owner on 22/05/2017.
 */

public class SearchExpDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //context list
    Context context;

    //declare object list
    ArrayList<SearchExpObj> searchExpObjList;

    //declare class
    SearchExpenseActivity searchExpenseActivity;

    public SearchExpDataAdapter(Context context, SearchExpenseActivity searchExpenseActivity, ArrayList<SearchExpObj> searchExpObjList) {
        this.context = context;
        this.searchExpenseActivity = searchExpenseActivity;
        this.searchExpObjList = searchExpObjList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //init RecyclerView.ViewHolder
        RecyclerView.ViewHolder viewHolder=null;

        //init LayoutInflater to show view
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //get view
        View view=layoutInflater.inflate(R.layout.layout_searched_data,parent,false);

        //get viewHolder
        viewHolder=new SearchExpHol(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //init class SearchExpHol
        SearchExpHol searchExpHol =(SearchExpHol)holder;

        //init SearchExpObj class
        SearchExpObj searchExpObj = searchExpObjList.get(position);

        //set date
        String newDate= CommonUtils.getFormatedDate(searchExpObj.getEx_date()+" 00:00:00","yyyy-MM-dd HH:mm:ss","EEE,dd MMM yy");
        searchExpHol.tv_exp_date.setText(newDate);

        //set date
        searchExpHol.tv_total.setText(searchExpObj.getEx_amount());

        //set note
        searchExpHol.tv_note.setText(searchExpObj.getEx_note());

        //capital first letter
        String upperString = searchExpObj.getEx_title().substring(0,1).toUpperCase()
                + searchExpObj.getEx_title().substring(1);


        //set circle textview color
        if (position == 0) {
            searchExpHol.tv_title.setBackgroundResource(R.drawable.rectangle_one);
            //set title
            searchExpHol.tv_title.setText(upperString);
        }else if (position % 10 == 1) {
            searchExpHol.tv_title.setBackgroundResource(R.drawable.rectangle_two);
            //set title
            searchExpHol.tv_title.setText(upperString);
        }else if (position % 10 == 2) {
            searchExpHol.tv_title.setBackgroundResource(R.drawable.rectangle_three);
            //set title
            searchExpHol.tv_title.setText(upperString);
        }else if (position % 10 == 3) {
            searchExpHol.tv_title.setBackgroundResource(R.drawable.rectangle_four);
            //set title
            searchExpHol.tv_title.setText(upperString);
        }else if (position % 10 == 4) {
            searchExpHol.tv_title.setBackgroundResource(R.drawable.rectangle_five);
            //set title
            searchExpHol.tv_title.setText(upperString);
        }else if (position % 10 == 5) {
            searchExpHol.tv_title.setBackgroundResource(R.drawable.rectangle_six);
            //set title
            searchExpHol.tv_title.setText(upperString);
        }else if (position % 10 == 6) {
            searchExpHol.tv_title.setBackgroundResource(R.drawable.rectangle_seven);
            //set title
            searchExpHol.tv_title.setText(upperString);
        }else if (position % 10 == 7) {
            searchExpHol.tv_title.setBackgroundResource(R.drawable.rectangle_one);
            //set title
            searchExpHol.tv_title.setText(upperString);
        }else if (position % 10 == 8) {
            searchExpHol.tv_title.setBackgroundResource(R.drawable.rectangle_two);
            //set title
            searchExpHol.tv_title.setText(upperString);
        }else if (position % 10 == 9) {
            searchExpHol.tv_title.setBackgroundResource(R.drawable.rectangle_three);
            //set title
            searchExpHol.tv_title.setText(upperString);
        }

    }

    @Override
    public int getItemCount() {
        return searchExpObjList.size();
    }
}
