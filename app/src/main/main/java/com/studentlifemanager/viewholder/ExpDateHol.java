package com.studentlifemanager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.studentlifemanager.R;

/**
 * Created by Owner on 18/04/2017.
 */

public class ExpDateHol extends RecyclerView.ViewHolder {

    public TextView tv_exp_date,tv_total;

    public ExpDateHol(View itemView) {
        super(itemView);

        tv_exp_date=(TextView)itemView.findViewById(R.id.tv_exp_date);
        tv_total=(TextView)itemView.findViewById(R.id.tv_total);
    }
}
