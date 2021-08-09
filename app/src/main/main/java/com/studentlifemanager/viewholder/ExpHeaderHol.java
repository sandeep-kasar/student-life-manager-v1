package com.studentlifemanager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.studentlifemanager.R;


/**
 * Created by Owner on 18/04/2017.
 */

public class ExpHeaderHol extends RecyclerView.ViewHolder {

    public TextView tv_bal_plus, tv_bal_minus, tv_bal_remain;


    public ExpHeaderHol(View itemView) {
        super(itemView);

        tv_bal_plus = (TextView) itemView.findViewById(R.id.tv_bal_plus);
        tv_bal_minus = (TextView) itemView.findViewById(R.id.tv_bal_minus);
        tv_bal_remain = (TextView) itemView.findViewById(R.id.tv_bal_remain);
        
    }
}
