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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.studentlifemanager.R;
import com.studentlifemanager.adapter.DoneAdapter;
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


public class DoneFragment extends Fragment {

    //static var
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private boolean INDIALOG = false;
    //class
    MyTaskOperation myTaskOperation;

    //get task using voice img_voice
    ImageView img_voice;

    //edt_task to add task
    EditText edt_task, edt_task1;

    //tv_date to add date
    TextView tv_date;

    //tv_time to add time
    TextView tv_time;

    //img_save to add task
    TextView img_save;

    //floatingActionButton to add task
    FloatingActionButton floatingActionButton;

    //CoordinatorLayout to showw msg
    public CoordinatorLayout coordinatorLayout;

    //RecyclerView to hold data
    public RecyclerView recycler_view;

    //adapter to ho hold data
    public DoneAdapter doneAdapter;

    //list to hold all object
    public LinkedList<Object> objectList;

    //date
    String date;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_done, container, false);

        //init class
        myTaskOperation = new MyTaskOperation();

        //init list
        objectList = new LinkedList<>();

        //init adapter
        doneAdapter = new DoneAdapter(getActivity(), DoneFragment.this, objectList);

        //init widgets
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.CoordinatorLayout);
        img_voice = (ImageView) view.findViewById(R.id.img_voice);
        img_save = (TextView) view.findViewById(R.id.img_save);
        edt_task = (EditText) view.findViewById(R.id.edt_task);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);

        //set Adapters
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.setAdapter(doneAdapter);


        //floatingActionButton onclick open add dialog
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //income
                showdialouge(1);

            }
        });

        //notify adapter
        setData();

        //add quick task
        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String task = edt_task.getText().toString().trim();
                String date = CommonUtils.getTodaysDate();
                String time = CommonUtils.getTimeNow();


                if (!task.equals("")) {

                    //add data in database
                    myTaskOperation.insertTask(task, date, time, 1);

                    //get data
                    setData();

                    //Toast.makeText(getActivity(),"Added in list.",Toast.LENGTH_LONG).show();

                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Added in list successfully.", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    //clear edittext
                    edt_task.setText("");
                }


            }
        });

        img_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INDIALOG = false;
                promptSpeechInput("Add quick task.");
            }
        });

        return view;
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
        edt_task1 = (EditText) dialog.findViewById(R.id.edt_task);
        tv_date = (TextView) dialog.findViewById(R.id.tv_date);
        tv_time = (TextView) dialog.findViewById(R.id.tv_time);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        ImageView img_speak = (ImageView) dialog.findViewById(R.id.img_speak);

        //set date
        tv_date.setText(CommonUtils.setTodaysDate());

        //set time
        tv_time.setText(CommonUtils.getTimeNow());

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


                //pick data
                String task = edt_task1.getText().toString();
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

                    //Toast.makeText(getActivity(),"Added in list.",Toast.LENGTH_LONG).show();

                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Added in list successfully.", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    //dismiss dialog
                    dialog.dismiss();

                    //hide keyboard
                    //hideKeyboard(getActivity());
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
                //in dialog
                INDIALOG = true;
                promptSpeechInput("Enter task here.");
            }
        });


        edt_task1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void setData() {

        //clere list
        objectList.clear();

        //add in linked list
        objectList = myTaskOperation.getDoneData();

        //init adapter
        doneAdapter = new DoneAdapter(getActivity(), DoneFragment.this, objectList);

        //set adapter to rv
        recycler_view.setAdapter(doneAdapter);

        //notify adapter
        doneAdapter.notifyDataSetChanged();

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
            //Toast.makeText(getActivity(), "speech_not_supported", Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar.make(coordinatorLayout, "speech not supported", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    //hide keyboard
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
                    if (INDIALOG) {
                        edt_task1.setText(result.get(0));
                    } else {
                        edt_task.setText(result.get(0));
                    }

                }
                break;
            }

        }
    }
}
