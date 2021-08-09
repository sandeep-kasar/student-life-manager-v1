package com.studentlifemanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.studentlifemanager.R;
import com.studentlifemanager.activity.SearchTaskActivity;
import com.studentlifemanager.helper.CommonUtils;
import com.studentlifemanager.model.SearchTaskObj;
import com.studentlifemanager.viewholder.SearchTaskHol;

import java.util.ArrayList;

/**
 * Created by Owner on 23/05/2017.
 */

public class SearchTaskDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //context list
    Context context;

    //declare object list
    ArrayList<SearchTaskObj> searchExpObjList;

    //declare class
    SearchTaskActivity searchTaskActivity;

    public SearchTaskDataAdapter(Context context, SearchTaskActivity searchTaskActivity1, ArrayList<SearchTaskObj> searchExpObjList) {
        this.context = context;
        this.searchTaskActivity = searchTaskActivity1;
        this.searchExpObjList = searchExpObjList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //init RecyclerView.ViewHolder
        RecyclerView.ViewHolder viewHolder = null;

        //init LayoutInflater to show view
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //get view
        View view = layoutInflater.inflate(R.layout.layout_search_task, parent, false);

        //get viewHolder
        viewHolder = new SearchTaskHol(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //init class SearchExpHol
        SearchTaskHol searchTaskHol = (SearchTaskHol) holder;

        //init SearchExpObj class
        SearchTaskObj searchTaskObj = searchExpObjList.get(position);

        //capital first letter
        String upperString = searchTaskObj.getTk_title().substring(0, 1).toUpperCase()
                + searchTaskObj.getTk_title().substring(1);

        //set title
        searchTaskHol.tv_task.setText(upperString);

        //set date
        String newDate = CommonUtils.getFormatedDate(searchTaskObj.getTk_date() + " 00:00:00", "yyyy-MM-dd HH:mm:ss", "EEE,dd MMM yy");
        searchTaskHol.tv_date.setText(newDate);

        //set time
        searchTaskHol.tv_time.setText(searchTaskObj.getTk_time());

        //change background if done task
        if (searchTaskObj.getTk_type().equals("1")) {
            searchTaskHol.img_tick.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return searchExpObjList.size();
    }
}
