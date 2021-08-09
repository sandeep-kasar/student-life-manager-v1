package com.studentlifemanager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.studentlifemanager.R;

/**
 * Created by Owner on 01/05/2017.
 */

public class ToDoDateHol  extends RecyclerView.ViewHolder{

    public TextView tv_date;

    public ToDoDateHol(View itemView) {
        super(itemView);

        tv_date=(TextView)itemView.findViewById(R.id.tv_date);
    }
}
