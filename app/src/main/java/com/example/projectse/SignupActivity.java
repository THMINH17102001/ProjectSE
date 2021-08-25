package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.*;
public class SignupActivity extends AppCompatActivity {

    Button signInSwitchScr, signUpBtn;
    EditText nUsername, nPassword, nPasswordcf;
    FirebaseFirestore users = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        nUsername = findViewById(R.id.newUsername);
        nPassword = findViewById(R.id.newPass);
        nPasswordcf = findViewById(R.id.passcf);

        signInSwitchScr = findViewById(R.id.signin_SignupActivity);
        signUpBtn = findViewById(R.id.signup_SignupActivity);

        signInSwitchScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nUsername.getText().toString();
                String password = nPassword.getText().toString();
                String cfPassword = nPasswordcf.getText().toString();
                if(password != cfPassword)
                {
                    nPasswordcf.setError("Password confirmation does not match");
                }

            }

        });

    }

}