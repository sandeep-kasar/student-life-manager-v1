package com.studentlifemanager.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;

import com.studentlifemanager.R;
import com.studentlifemanager.activity.HomeActivity;
import com.studentlifemanager.database.MyTaskOperation;
import com.studentlifemanager.fragment.TaskFragment;
import com.studentlifemanager.helper.CommonUtils;
import com.studentlifemanager.helper.DatePicker;
import com.studentlifemanager.model.ToDoTaskObj;
import com.studentlifemanager.model.TodoDateObj;
import com.studentlifemanager.viewholder.ToDoDateHol;
import com.studentlifemanager.viewholder.ToDoTaskHol;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Owner on 01/05/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //context list
    Context context;

    //get object list
    List<Object> objectList;

    //get activity object
    TaskFragment taskFragment;

    //edt_date
    TextView edt_date, edt_time;

    //static var
    static final int DATE = 0, TASK = 1;

    /**
     * costructor
     *
     * @param context
     * @param objectList
     */
    public TaskAdapter(Context context, TaskFragment taskFragment, List<Object> objectList) {
        this.context = context;
        this.taskFragment = taskFragment;
        this.objectList = objectList;
    }

    @Override
    public int getItemViewType(int position) {

        //get position of obj
        Object object = objectList.get(position);

        //get instance of particular object
        if (object instanceof TodoDateObj) return DATE;
        else if (object instanceof ToDoTaskObj) return TASK;

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //get view type
        int viewType = holder.getItemViewType();

        switch (viewType) {

            case DATE:
                ToDoDateHol toDoDateHol = (ToDoDateHol) holder;
                retriveToDoDate(toDoDateHol, position);

                break;

            case TASK:
                ToDoTaskHol toDoTaskHol = (ToDoTaskHol) holder;
                retriveToDoTask(toDoTaskHol, position);

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
            View view = layoutInflater.inflate(R.layout.layout_todo_date, parent, false);

            //init view
            viewHolder = new ToDoDateHol(view);

        } else {

            //get EXP_HEADER view
            View view = layoutInflater.inflate(R.layout.layout_todo_task, parent, false);

            //init view
            viewHolder = new ToDoTaskHol(view);
        }


        return viewHolder;
    }


    @Override
    public int getItemCount() {
        //return size of object list
        return objectList.size();
    }

    /**
     * @param toDoDateHol
     * @param position
     */
    public void retriveToDoDate(final ToDoDateHol toDoDateHol, int position) {

        //get object data
        final TodoDateObj todoDateObj = (TodoDateObj) objectList.get(position);
        String date = todoDateObj.getDate();
        String newDate;
        String today = CommonUtils.getTodaysDate();
        String tomorrow = CommonUtils.getTom();
        String overdue = CommonUtils.getOverdue();
        if (date.equals(today)) {
            newDate = "Today";
        } else if (date.equals(tomorrow)) {
            newDate = "Tommoroww";
        } else if (date.equals(overdue)) {
            newDate = "Yesterday";
        } else {
            //format date
            newDate = CommonUtils.getFormatedDate(date + " 00:00:00", "yyyy-MM-dd HH:mm:ss", "d MMM");
        }
        //set date
        toDoDateHol.tv_date.setText(newDate);

    }

    /**
     * @param toDoTaskHol
     * @param position
     */
    public void retriveToDoTask(final ToDoTaskHol toDoTaskHol, final int position) {

        //get object data
        final ToDoTaskObj toDoTaskObj = (ToDoTaskObj) objectList.get(position);
        //format date
        String newDate = CommonUtils.getFormatedDate(toDoTaskObj.getDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss", "EEE,dd MMM yy");

        //capital first letter
        String upperString = toDoTaskObj.getTitle().substring(0, 1).toUpperCase()
                + toDoTaskObj.getTitle().substring(1);

        //set date / amount
        toDoTaskHol.tv_task.setText(upperString);
        toDoTaskHol.tv_date.setText(newDate);
        toDoTaskHol.tv_time.setText(toDoTaskObj.getTime());

        toDoTaskHol.img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //init class MyTaskOperation
                final MyTaskOperation myTaskOperation = new MyTaskOperation();
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, toDoTaskHol.img_menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_todo_list);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.done:
                                //handle edit click
                                //handle edit click
                                myTaskOperation.donetheTask(toDoTaskObj.getId());
                                objectList.remove(objectList.get(position));
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, objectList.size());
                                // Toast.makeText(context,"Task added in done list.",Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();

                                //open ToDoListFragment fragment
                               /* FragmentManager fragmentManager = taskFragment.getFragmentManager();
                                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                ToDoListFragment doListFragment = new ToDoListFragment();
                                fragmentManager.beginTransaction().replace(R.id.mycontainer, doListFragment, "DO_LIST_FRAGMENT")
                                        .commit();*/

                                Snackbar snackbar = Snackbar.make(taskFragment.coordinatorLayout, "Task added in done List.", Snackbar.LENGTH_LONG);
                                snackbar.show();

                                break;
                            case R.id.edit:
                                //handle delete click
                                showdialouge(toDoTaskObj.getId(), toDoTaskObj.getTitle(),
                                        toDoTaskObj.getDate(), toDoTaskObj.getTime());

                                break;

                            case R.id.delete:
                                MyTaskOperation myTaskOperation = new MyTaskOperation();
                                //handle edit click
                                myTaskOperation.deleteTask(toDoTaskObj.getId());
                                objectList.remove(objectList.get(position));
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, objectList.size());

                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }

        });
    }

    //dialog for add expense
    public void showdialouge(final Integer id, String title, String date, String time) {
        //create dialogue box
        final Dialog dialog = new Dialog(context);
        //dialog without title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set custom layout to dialog
        dialog.setContentView(R.layout.layout_mytodo_dial);
        dialog.setCancelable(true);
        dialog.show();

        //get widgets
        final EditText edt_task = (EditText) dialog.findViewById(R.id.edt_task);
        edt_date = (TextView) dialog.findViewById(R.id.tv_date);
        edt_time = (TextView) dialog.findViewById(R.id.tv_time);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        ImageView img_speak = (ImageView) dialog.findViewById(R.id.img_speak);

        String newDate = CommonUtils.getFormatedDate(date + " 00:00:00", "yyyy-MM-dd HH:mm:ss", "dd/M/yyyy");

        //set old data
        edt_task.setText(title);
        edt_date.setText(newDate);
        edt_time.setText(time);

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

                String task = edt_task.getText().toString();
                String date = edt_date.getText().toString();
                String time = edt_time.getText().toString();

                String newDate = CommonUtils.getFormatedDate(date + " 00:00:00", "dd/M/yyyy HH:mm:ss", "yyyy-M-dd");

                if (!task.equals("")) {

                    //clear old data
                    objectList.clear();

                    MyTaskOperation myTaskOperation = new MyTaskOperation();

                    //add data in database
                    myTaskOperation.updateTask(id, task, newDate, time);

                    //notify adapter
                    //add in linked list
                    objectList = myTaskOperation.getToDoData();

                    //init adapter
                    taskFragment.taskAdapter = new TaskAdapter(context, taskFragment, objectList);

                    //set adapter to rv
                    taskFragment.recycler_view.setAdapter(taskFragment.taskAdapter);

                    //notify adapter
                    taskFragment.taskAdapter.notifyDataSetChanged();

                    //get time in milli
                    long timeinmilli = CommonUtils.getInMilliSecond(date + " " + time);

                    //set alarm
                    CommonUtils.scheduleNotification(taskFragment.getActivity(), timeinmilli, 0, task);

                    //Toast.makeText(taskFragment.getActivity(),"Edited successfully.",Toast.LENGTH_LONG).show();

                    Snackbar snackbar = Snackbar.make(taskFragment.coordinatorLayout, "Edited successfully.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    //dismiss dialog
                    dialog.dismiss();
                }
            }
        });

        //set date
        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        //set time
        edt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
            }
        });


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
        date.show(((HomeActivity) context).getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            edt_date.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));

        }
    };

    /**
     * select time to set for entry
     */
    public void getTime() {

        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + ":" + i1;

                edt_time.setText(TaskFragment.formateTimeFromstring("HH:mm", "h:mm a", time));

            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        tpd.show();
    }


}
