package com.studentlifemanager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studentlifemanager.R;

/**
 * Created by Owner on 23/05/2017.
 */

public class SearchTaskHol extends RecyclerView.ViewHolder {

    public TextView tv_task, tv_date, tv_time;
    public LinearLayout lay_task;
    public ImageView img_tick;

    public SearchTaskHol(View itemView) {
        super(itemView);

        tv_task = (TextView) itemView.findViewById(R.id.tv_task);
        tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        lay_task = (LinearLayout) itemView.findViewById(R.id.lay_task);
        img_tick = (ImageView) itemView.findViewById(R.id.img_tick);

    }
}
