package com.studentlifemanager.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.studentlifemanager.R;
import com.studentlifemanager.database.SessionData;

public class MainActivity extends AppCompatActivity {

    //class
    SessionData sessionData;

    //activity show time
    private static final int SPLASH_SHOW_TIME = 2000;

    //email
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set xml/design object activity_main
        setContentView(R.layout.activity_main);

        //init class
        sessionData = new SessionData(MainActivity.this);

        //get email
        email = sessionData.getString("email", "-1");

        new BackgroundSplashTask().execute();
    }

    private class BackgroundSplashTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(SPLASH_SHOW_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (email.equals("-1")) {
                //start SignUpActivity
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            } else {
                //start HomeActivity
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }

        }
    }

}