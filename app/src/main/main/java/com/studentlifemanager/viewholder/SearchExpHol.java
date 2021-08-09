package com.studentlifemanager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.studentlifemanager.R;

/**
 * Created by Owner on 22/05/2017.
 */

public class SearchExpHol extends RecyclerView.ViewHolder {

    public TextView tv_exp_date, tv_total, tv_title, tv_note;

    public SearchExpHol(View itemView) {
        super(itemView);

        tv_exp_date = (TextView) itemView.findViewById(R.id.tv_exp_date);
        tv_total = (TextView) itemView.findViewById(R.id.tv_total);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        tv_note = (TextView) itemView.findViewById(R.id.tv_note);

    }
}
