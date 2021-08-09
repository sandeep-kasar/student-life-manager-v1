package com.studentlifemanager.viewholder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.studentlifemanager.R;

/**
 * Created by Owner on 01/05/2017.
 */

public class ToDoTaskHol extends RecyclerView.ViewHolder{

    public TextView tv_task,tv_date,tv_time;
    public ImageView img_menu;

    public ToDoTaskHol(View itemView) {
        super(itemView);

        tv_task=(TextView)itemView.findViewById(R.id.tv_task);
        tv_date=(TextView)itemView.findViewById(R.id.tv_date);
        tv_time=(TextView)itemView.findViewById(R.id.tv_time);
        img_menu=(ImageView) itemView.findViewById(R.id.img_menu);
    }
}
