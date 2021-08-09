package com.studentlifemanager.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.studentlifemanager.R;
import com.studentlifemanager.activity.HomeActivity;
import com.studentlifemanager.activity.SearchExpenseActivity;
import com.studentlifemanager.adapter.MyExpenseAdapter;
import com.studentlifemanager.database.MyExpenseOperation;
import com.studentlifemanager.helper.CommonUtils;
import com.studentlifemanager.helper.DatePicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Owner on 12-04-2017.
 */

public class MyExpenseFragment extends Fragment implements View.OnClickListener {

    //static var
    private final int REQ_CODE_SPEECH_INPUT = 100;

    //class
    MyExpenseOperation myExpenseOperation;
    public CoordinatorLayout coordinatorLayout;

    //bottom widgets

    //select date
    TextView tv_date;

    //RecyclerView to hold data
    public RecyclerView recycler_view;

    //adapter to ho hold data
    public MyExpenseAdapter myExpenseAdapter;

    //list to hold all object
    private LinkedList<Object> objectList;

    //input
    private EditText edt_amount, edt_source, edt_add_note;

    //get date
    private String date;

    //floating menu
    private FloatingActionMenu menuInput;
    private com.github.clans.fab.FloatingActionButton fabIncome;
    private com.github.clans.fab.FloatingActionButton fabExpense;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflating layout
        View view = inflater.inflate(R.layout.fragment_my_expense, container, false);

        //set menu
        setHasOptionsMenu(true);

        //init class
        myExpenseOperation = new MyExpenseOperation();

        //init list
        objectList = new LinkedList<>();

        //init adapter
        myExpenseAdapter = new MyExpenseAdapter(getActivity(), MyExpenseFragment.this, objectList);

        //init widgets
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.CoordinatorLayout);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        menuInput = (FloatingActionMenu) view.findViewById(R.id.menuInput);
        fabIncome = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fabIncome);
        fabExpense = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fabExpense);

        //set Adapters
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.setAdapter(myExpenseAdapter);

        //onclick open dialog and add expense
        fabIncome.setOnClickListener(this);
        fabExpense.setOnClickListener(this);

        //get data
        setData();


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflating menu
        inflater.inflate(R.menu.menu_expense, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search: //on click search icon on toolbar

                //open search activity
                startActivity(new Intent(getActivity(), SearchExpenseActivity.class));

                return true;

            case R.id.action_backup:

                File file = myExpenseOperation.exportDB();
                backup(file);

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
        dialog.setContentView(R.layout.layout_myexp_dial);
        dialog.setCancelable(true);
        dialog.show();

        //get widgets
        edt_amount = (EditText) dialog.findViewById(R.id.edt_amount);
        edt_source = (EditText) dialog.findViewById(R.id.edt_source);
        edt_add_note = (EditText) dialog.findViewById(R.id.edt_add_note);
        tv_date = (TextView) dialog.findViewById(R.id.tv_date);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        ImageView img_speak = (ImageView) dialog.findViewById(R.id.img_speak);

        //set source
        if (type == 1 || type == 2) {
            edt_source.setHint("Source");
        }

        //set date
        tv_date.setText(CommonUtils.setTodaysDate());

        //cancel dialog
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide float
                menuInput.close(true);
                //dismiss dialog
                dialog.dismiss();
                //hide keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

            }
        });

        //add data in database
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //hide float
                menuInput.close(true);

                String amount = edt_amount.getText().toString();
                String title = edt_source.getText().toString();
                String note = edt_add_note.getText().toString();

                //check date is selected or not
                if (date == null) {
                    date = CommonUtils.getTodaysDate();
                }

                if (!amount.equals("") && !title.equals("")) {

                    //clear old data
                    objectList.clear();

                    //get expense amount in int format
                    Integer exp_amount = Integer.parseInt(amount);

                    //add data in database
                    myExpenseOperation.insertExpence(title, note, exp_amount, type, date);

                    //set data
                    setData();

                    Toast.makeText(getActivity(), "Added in list successfully", Toast.LENGTH_LONG).show();

                    //dismiss dialog
                    dialog.dismiss();

                    //hide keyboard
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);


                }
            }
        });

        //get input date
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        img_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput("Say something..");

            }
        });

        edt_amount.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
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
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "speech is not supported", Snackbar.LENGTH_LONG);
            snackbar.show();
        }


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
                    String edsrc = edt_source.getText().toString().trim();
                    String ednote = edt_add_note.getText().toString().trim();
                    if (edsrc.equals("")) {
                        edt_source.setText(result.get(0));
                    } else if (ednote.equals("")) {
                        edt_add_note.setText(result.get(0));
                    } else {
                        edt_source.setText(result.get(0));
                    }

                }
                break;
            }

        }
    }

    private void showDatePicker() {
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

    public void setData() {

        //clear old data
        objectList.clear();

        //add in linked list
        objectList = myExpenseOperation.getData();

        //init adapter
        myExpenseAdapter = new MyExpenseAdapter(getActivity(), MyExpenseFragment.this, objectList);

        //set adapter to rv
        recycler_view.setAdapter(myExpenseAdapter);

        //notify adapter
        myExpenseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.fabIncome:
                //income
                Integer type = 1;
                showdialouge(type);

                break;

            case R.id.fabExpense:
                //expense
                Integer typein = 0;
                showdialouge(typein);
                break;
        }
    }

    private void backup(File file) {

        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            //You'll need to add proper error handling here
        }


    }


}
