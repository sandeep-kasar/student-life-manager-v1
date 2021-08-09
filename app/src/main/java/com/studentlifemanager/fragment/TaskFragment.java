package com.studentlifemanager.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.clans.fab.FloatingActionMenu;
import com.studentlifemanager.R;
import com.studentlifemanager.activity.SearchTaskActivity;
import com.studentlifemanager.adapter.TaskAdapter;
import com.studentlifemanager.database.MyTaskOperation;
import com.studentlifemanager.helper.CommonUtils;
import com.studentlifemanager.helper.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class TaskFragment extends Fragment implements View.OnClickListener {

    //static var
    private final int REQ_CODE_SPEECH_INPUT = 100;

    //class
    MyTaskOperation myTaskOperation;

    //edt_task to add task
    EditText edt_task;

    //tv_date to add date
    TextView tv_date;

    //tv_time to add time
    TextView tv_time;

    //CoordinatorLayout to showw msg
    public CoordinatorLayout coordinatorLayout;

    //RecyclerView to hold data
    public RecyclerView recycler_view;

    //adapter to ho hold data
    public TaskAdapter taskAdapter;

    //list to hold all object
    public LinkedList<Object> objectList;

    //date
    private String date;

    //floating menu
    private FloatingActionMenu menuInput;
    private com.github.clans.fab.FloatingActionButton fabDone;
    private com.github.clans.fab.FloatingActionButton fabToDO;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_do, container, false);

        //set menu
        setHasOptionsMenu(true);

        //init class
        myTaskOperation = new MyTaskOperation();

        //init list
        objectList = new LinkedList<>();

        //init adapter
        taskAdapter = new TaskAdapter(getActivity(), TaskFragment.this, objectList);

        //init widgets
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.CoordinatorLayout);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        menuInput = (FloatingActionMenu) view.findViewById(R.id.menuInput);
        fabDone = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fabDone);
        fabToDO = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fabToDO);


        //set Adapters
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.setAdapter(taskAdapter);

        //notify adapter
        setData();

        //onclick open dialog and add expense
        fabDone.setOnClickListener(this);
        fabToDO.setOnClickListener(this);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflating menu
        inflater.inflate(R.menu.menu_mytask, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search: //on click search icon on toolbar

                //open search activity
                startActivity(new Intent(getActivity(), SearchTaskActivity.class));

                return true;

            default:
                break;
        }
        return true;
    }

    //dialog for add expense
    public void showdialouge(final Integer type) {
        //create dialogue box
        final Dialog dialog = new Dialog(getActivity());
        //dialog without title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set custom layout to dialog
        dialog.setContentView(R.layout.layout_mytodo_dial);
        dialog.setCancelable(true);
        dialog.show();

        //get widgets
        tv_date = (TextView) dialog.findViewById(R.id.tv_date);
        tv_time = (TextView) dialog.findViewById(R.id.tv_time);
        edt_task = (EditText) dialog.findViewById(R.id.edt_task);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        ImageView img_speak = (ImageView) dialog.findViewById(R.id.img_speak);

        //set todays date
        tv_date.setText(CommonUtils.setTodaysDate());

        //set time
        tv_time.setText(CommonUtils.getTimeNow());

        //cancel dialog
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //hide float
                menuInput.close(true);
                dialog.dismiss();
            }
        });

        //add data in database
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //hide float
                menuInput.close(true);

                String task = edt_task.getText().toString();
                String time = tv_time.getText().toString();

                //check date is selected or not
                if (date == null) {
                    date = CommonUtils.getTodaysDate();
                }

                if (!task.equals("")) {

                    //clear old data
                    objectList.clear();

                    //add data in database
                    myTaskOperation.insertTask(task, date, time, type);

                    //notify adapter
                    setData();

                    //get time in milli
                    long timeinmilli = CommonUtils.getInMilliSecond(date + " " + time);

                    //set alarm
                    CommonUtils.scheduleNotification(getActivity(), timeinmilli, 0, task);

                    //Toast.makeText(getActivity(),"Added in list.",Toast.LENGTH_LONG).show();

                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Added in list successfully.", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    //hide keyboard
                    //hideKeyboard(getActivity());

                    //dismiss dialog
                    dialog.dismiss();
                }
            }
        });

        //set date
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        //set time
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
            }
        });

        //show speaker
        img_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput("Enter task here.");
            }
        });

        edt_task.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


    }

    private void setData() {

        //clear list
        objectList.clear();

        //add in linked list
        objectList = myTaskOperation.getToDoData();

        //init adapter
        taskAdapter = new TaskAdapter(getActivity(), TaskFragment.this, objectList);

        //set adapter to rv
        recycler_view.setAdapter(taskAdapter);

        //notify adapter
        taskAdapter.notifyDataSetChanged();

        hideKeyboard(getActivity());

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
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            tv_date.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));
            date = String.valueOf(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    };

    /**
     * select time to set for entry
     */
    public void getTime() {

        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog tpd = new TimePickerDialog(getActivity(), R.style.DatePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + ":" + i1;

                tv_time.setText(formateTimeFromstring("HH:mm", "h:mm a", time));

            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        tpd.show();
    }

    public static String formateTimeFromstring(String inputFormat, String outputFormat, String inputTime) {

        Date parsed = null;
        String outputTime = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputTime);
            outputTime = df_output.format(parsed);

        } catch (ParseException e) {
            Log.d("dateError", "ParseException - dateFormat");
        }

        return outputTime;

    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput(String title) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, title);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            //Toast.makeText(getActivity(),"speech_not_supported",Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar.make(coordinatorLayout, "speech is not supported", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Receiving speech input
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edt_task.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.fabDone:
                //income
                Integer done = 1;
                showdialouge(done);
                break;

            case R.id.fabToDO:
                //expense
                Integer todo = 0;
                showdialouge(todo);
                break;
        }
    }

}
