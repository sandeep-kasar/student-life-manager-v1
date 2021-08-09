package com.studentlifemanager.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.studentlifemanager.R;
import com.studentlifemanager.activity.HomeActivity;
import com.studentlifemanager.activity.MyExpenseActivity;
import com.studentlifemanager.database.MyExpenseOperation;
import com.studentlifemanager.fragment.JanFragment;
import com.studentlifemanager.fragment.MyExpenseFragment;
import com.studentlifemanager.helper.CommonUtils;
import com.studentlifemanager.helper.DatePicker;
import com.studentlifemanager.model.ExpActivityHeaderObj;
import com.studentlifemanager.model.ExpDateObj;
import com.studentlifemanager.model.ExpHeaderObj;
import com.studentlifemanager.model.ExpListObj;
import com.studentlifemanager.viewholder.ExpActivityBodyHol;
import com.studentlifemanager.viewholder.ExpBodyHol;
import com.studentlifemanager.viewholder.ExpDateHol;
import com.studentlifemanager.viewholder.ExpHeaderHol;

import java.util.Calendar;
import java.util.List;

public class MyExpenseActivtyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //context list
    Context context;

    //get object list
    List<Object> objectList;

    //get activity object
    JanFragment janFragment;

    //static var
    static final int EXP_HEADER = 0, EXP_DATE = 1, EXP_BODY = 2;
    int month;

    //Edittext
    TextView edt_date;

    /**
     * costructor
     *
     * @param context
     * @param janFragment
     * @param objectList
     */
    public MyExpenseActivtyAdapter(Context context, JanFragment janFragment,
                                   List<Object> objectList, int month) {
        this.context = context;
        this.janFragment = janFragment;
        this.objectList = objectList;
        this.month = month;
    }

    @Override
    public int getItemViewType(int position) {

        //get position of obj
        Object object = objectList.get(position);

        //get instance of particular object
        if (object instanceof ExpActivityHeaderObj) return EXP_HEADER;
        else if (object instanceof ExpDateObj) return EXP_DATE;
        else if (object instanceof ExpListObj) return EXP_BODY;
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //get view type
        int viewType = holder.getItemViewType();

        switch (viewType) {

            case EXP_HEADER:
                ExpActivityBodyHol exp_header = (ExpActivityBodyHol) holder;
                retriveExpHeader(exp_header, position);

                break;

            case EXP_DATE:
                ExpDateHol expDateHol = (ExpDateHol) holder;
                retriveExpDate(expDateHol, position);

                break;

            case EXP_BODY:
                ExpBodyHol expBodyHol = (ExpBodyHol) holder;
                retriveExpBody(expBodyHol, position);

                break;

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //init RecyclerView.ViewHolder
        RecyclerView.ViewHolder viewHolder = null;

        //init LayoutInflater to show view
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == 0) { //EXP_HEADER

            //get EXP_HEADER view
            View view_exp_header = layoutInflater.inflate(R.layout.layout_exp_activity_header, parent, false);

            //init view
            viewHolder = new ExpActivityBodyHol(view_exp_header);

        } else if (viewType == 1) {

            //get EXP_HEADER view
            View view_exp_date = layoutInflater.inflate(R.layout.layout_exp_date, parent, false);

            //init view
            viewHolder = new ExpDateHol(view_exp_date);
        } else {

            //get EXP_HEADER view
            View view_exp_body = layoutInflater.inflate(R.layout.layout_exp_body, parent, false);

            //init view
            viewHolder = new ExpBodyHol(view_exp_body);
        }


        return viewHolder;
    }


    @Override
    public int getItemCount() {
        //return size of object list
        return objectList.size();
    }

    /**
     * retrive header data/balance
     *
     * @param expHeaderHol
     * @param position
     */
    public void retriveExpHeader(final ExpActivityBodyHol expHeaderHol, int position) {

        //get object data
        final ExpActivityHeaderObj expHeaderObj = (ExpActivityHeaderObj) objectList.get(position);
        //set balance
        expHeaderHol.tv_bal_plus.setText("Rs." + Integer.toString(expHeaderObj.getBalance_plus()));
        expHeaderHol.tv_bal_minus.setText("-Rs." + Integer.toString(expHeaderObj.getBalance_minus()));


    }


    /**
     * retrive date and total
     *
     * @param expDateHolHolder
     * @param position
     */
    public void retriveExpDate(final ExpDateHol expDateHolHolder, final int position) {

        //get object data
        final ExpDateObj expDateObj = (ExpDateObj) objectList.get(position);

        //format date
        //String newDate= CommonUtils.getFormatedDate(expDateObj.getExp_date(),"yyyy-mm-dd","EEE,dd MMM yy");
        String newDate = CommonUtils.getFormatedDate(expDateObj.getExp_date() + " 00:00:00", "yyyy-MM-dd HH:mm:ss", "EEE,dd MMM yy");

        //set date / amount
        if (newDate == null) {
            expDateHolHolder.tv_exp_date.setText(expDateObj.getExp_date());
        } else {
            expDateHolHolder.tv_exp_date.setText(newDate);
        }
        expDateHolHolder.tv_total.setText(expDateObj.getExp_total());


    }

    /**
     * retrive title,note, amount
     *
     * @param expBodyHolHolder
     * @param position
     */
    public void retriveExpBody(final ExpBodyHol expBodyHolHolder, final int position) {

        //get object data
        final ExpListObj expListObj = (ExpListObj) objectList.get(position);

        //capital first letter
        String upperString = expListObj.getEx_title().substring(0, 1).toUpperCase()
                + expListObj.getEx_title().substring(1);

        //set title
        expBodyHolHolder.tv_title.setText(upperString);
        //set note
        expBodyHolHolder.tv_descr.setText(expListObj.getEx_note());

        //set color of amount as per type
        if (expListObj.getEx_type() == 0) {
            expBodyHolHolder.tv_amount.setTextColor(context.getResources().getColor(R.color.red_text));
            //set amount
            expBodyHolHolder.tv_amount.setText("-" + expListObj.getEx_amount());
        } else {
            expBodyHolHolder.tv_amount.setTextColor(context.getResources().getColor(R.color.green_text));
            //set amount
            expBodyHolHolder.tv_amount.setText("+" + expListObj.getEx_amount());
        }
        //set circle textview color
        if (position == 0) {
            expBodyHolHolder.tv_mark.setBackgroundResource(R.drawable.circle_seven);
            expBodyHolHolder.tv_mark.setText(expListObj.getEx_title().substring(0, 1));
        } else if (position % 10 == 1) {
            expBodyHolHolder.tv_mark.setBackgroundResource(R.drawable.circle_one);
            expBodyHolHolder.tv_mark.setText(expListObj.getEx_title().substring(0, 1));
        } else if (position % 10 == 2) {
            expBodyHolHolder.tv_mark.setBackgroundResource(R.drawable.circle_two);
            expBodyHolHolder.tv_mark.setText(expListObj.getEx_title().substring(0, 1));
        } else if (position % 10 == 3) {
            expBodyHolHolder.tv_mark.setBackgroundResource(R.drawable.circle_three);
            expBodyHolHolder.tv_mark.setText(expListObj.getEx_title().substring(0, 1));
        } else if (position % 10 == 4) {
            expBodyHolHolder.tv_mark.setBackgroundResource(R.drawable.circle_four);
            expBodyHolHolder.tv_mark.setText(expListObj.getEx_title().substring(0, 1));
        } else if (position % 10 == 5) {
            expBodyHolHolder.tv_mark.setBackgroundResource(R.drawable.circle_five);
            expBodyHolHolder.tv_mark.setText(expListObj.getEx_title().substring(0, 1));
        } else if (position % 10 == 6) {
            expBodyHolHolder.tv_mark.setBackgroundResource(R.drawable.circle_six);
            expBodyHolHolder.tv_mark.setText(expListObj.getEx_title().substring(0, 1));
        } else if (position % 10 == 7) {
            expBodyHolHolder.tv_mark.setBackgroundResource(R.drawable.circle_seven);
            expBodyHolHolder.tv_mark.setText(expListObj.getEx_title().substring(0, 1));
        } else if (position % 10 == 8) {
            expBodyHolHolder.tv_mark.setBackgroundResource(R.drawable.circle_one);
            expBodyHolHolder.tv_mark.setText(expListObj.getEx_title().substring(0, 1));
        } else if (position % 10 == 9) {
            expBodyHolHolder.tv_mark.setBackgroundResource(R.drawable.circle_two);
            expBodyHolHolder.tv_mark.setText(expListObj.getEx_title().substring(0, 1));
        }

        final String editDate = CommonUtils.getFormatedDate(expListObj.getEx_date() + " 00:00:00", "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy");


        expBodyHolHolder.lay_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle delete click
                showdialouge(expListObj.getEx_id(), expListObj.getEx_amount(),
                        expListObj.getEx_title(), expListObj.getEx_note(),
                        editDate, position, expListObj);
            }
        });

    }

    //dialog for add expense
    public void showdialouge(final Integer id, int amount, String title, String note,
                             final String date, final int position, final ExpListObj expListObj) {
        //create dialogue box
        final Dialog dialog = new Dialog(context);
        //dialog without title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set custom layout to dialog
        dialog.setContentView(R.layout.layout_exp_dia_delete);
        dialog.setCancelable(true);
        dialog.show();

        //init class
        final MyExpenseOperation myExpenseOperation = new MyExpenseOperation();

        //get widgets
        final EditText edt_amount = (EditText) dialog.findViewById(R.id.edt_amount);
        final EditText edt_source = (EditText) dialog.findViewById(R.id.edt_source);
        final EditText edt_add_note = (EditText) dialog.findViewById(R.id.edt_add_note);
        edt_date = (TextView) dialog.findViewById(R.id.tv_date);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        Button btn_delete = (Button) dialog.findViewById(R.id.btn_delete);

        String newDate = CommonUtils.getFormatedDate(date + " 00:00:00", "dd/MM/yyyy HH:mm:ss", "dd/M/yyyy");

        //set old data
        edt_amount.setText(String.valueOf(amount));
        edt_source.setText(title);
        edt_add_note.setText(note);
        edt_date.setText(newDate);

        //cancel dialog
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //add data in database
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = edt_amount.getText().toString();
                String source = edt_source.getText().toString();
                String note = edt_add_note.getText().toString();
                String date = edt_date.getText().toString();

                String newDate = CommonUtils.getFormatedDate(date + " 00:00:00", "dd/M/yyyy HH:mm:ss", "yyyy-M-dd");

                if (!amount.equals("") || !source.equals("") || !note.equals("")) {

                    //add data in database
                    myExpenseOperation.updateExpense(id, amount, source, note, newDate, expListObj.getEx_type());

                    //notify adapter
                    //set data
                    janFragment.setMonthData(month);

                    //show message
                    Toast.makeText(janFragment.getActivity(), "Edited successfully.", Toast.LENGTH_LONG).show();

                    //dismiss dialog
                    dialog.dismiss();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle edit click
                myExpenseOperation.deleteTask(expListObj.getEx_id(), expListObj.getEx_type(),
                        expListObj.getEx_amount());
                objectList.remove(objectList.get(position));
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, objectList.size());
                janFragment.setMonthData(month);
                dialog.dismiss();
            }
        });

        //set date
        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        edt_amount.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


    }


    public void showDatePicker() {
        DatePicker date = new DatePicker();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(((MyExpenseActivity) context).getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            edt_date.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));

        }
    };

}

