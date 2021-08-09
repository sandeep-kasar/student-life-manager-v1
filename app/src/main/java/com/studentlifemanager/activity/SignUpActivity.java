package com.studentlifemanager.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.studentlifemanager.R;
import com.studentlifemanager.database.SessionData;

public class SignUpActivity extends AppCompatActivity {

    //shared pref
    SessionData sessionData;

    //firebase auth
    //private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set xml/design object activity_main
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        //auth = FirebaseAuth.getInstance();

        //init session data
        sessionData = new SessionData(SignUpActivity.this);

        //init widgets

        //init EditText email
        final EditText ed_email = (EditText) findViewById(R.id.email_id);

        //init EditText password
        final EditText ed_password = (EditText) findViewById(R.id.ed_password);


        //init button register
        Button btn_register = (Button) findViewById(R.id.btn_register);

        //init TextView signin
        TextView tv_signin = (TextView) findViewById(R.id.tv_signin);
        tv_signin.setPaintFlags(tv_signin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        //on click register button
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call register method
                // Register(ed_email, ed_password);
            }
        });

        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * do registration
     *
     * @param ed_email
     * @param ed_password
     */
   /* private void Register(EditText ed_email, EditText ed_password) {

        //get email string from EditText ed_name
        final String email_id = ed_email.getText().toString().trim();

        //get password string from EditText ed_password
        String password = ed_password.getText().toString().trim();

        if (TextUtils.isEmpty(email_id)) {
            CommonUtils.showAlert(SignUpActivity.this, getString(R.string.alert_email));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            CommonUtils.showAlert(SignUpActivity.this, getString(R.string.alert_password));
            return;
        }

        if (password.length() < 6) {
            CommonUtils.showAlert(SignUpActivity.this, getString(R.string.alert_pwd_length));
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //create user
        auth.createUserWithEmailAndPassword(email_id, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            CommonUtils.showAlert(SignUpActivity.this, getString(R.string.alert_auth_failed));
                        } else {
                            sessionData.add("email", email_id);
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                            finish();
                        }
                    }
                });

    }*/
}



