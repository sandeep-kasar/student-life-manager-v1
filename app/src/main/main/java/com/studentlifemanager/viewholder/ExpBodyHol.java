package com.studentlifemanager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studentlifemanager.R;

/**
 * Created by Owner on 18/04/2017.
 */

public class ExpBodyHol extends RecyclerView.ViewHolder {

    public TextView tv_title,tv_descr,tv_amount,tv_mark;
    public LinearLayout lay_body;

    public ExpBodyHol(View itemView) {
        super(itemView);

        tv_mark=(TextView)itemView.findViewById(R.id.tv_mark);
        tv_title=(TextView)itemView.findViewById(R.id.tv_title);
        tv_descr=(TextView)itemView.findViewById(R.id.tv_descr);
        tv_amount=(TextView)itemView.findViewById(R.id.tv_amount);
        lay_body=(LinearLayout) itemView.findViewById(R.id.lay_body);

    }
}
