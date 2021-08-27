package com.example.projectse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.FirebaseFirestore;
public class SigninActivity extends AppCompatActivity {

    Button signUpSwitchScr, signInBtn, returnToUnsignedBtn;
    TextInputLayout sUsername, sPassword;
    private static final String TAG = "SigninActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);


        signInBtn = findViewById(R.id.signin_SigninActivity);
        signUpSwitchScr = findViewById(R.id.signup_SigninActivity);
        returnToUnsignedBtn = findViewById(R.id.returnToUnsigned);

        returnToUnsignedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, UnsignedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FirebaseFirestore usersDB = FirebaseFirestore.getInstance();
        sUsername = findViewById(R.id.username_SigninActivity);
        sPassword = findViewById(R.id.password_SigninActivity);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final String username = sUsername.getEditText().getText().toString();
                final String password = sPassword.getEditText().getText().toString();

                usersDB.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            int flag = 0;
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                String checkUsername = document.getString("username");
                                String checkPassword = document.getString("password");
                                boolean x = username.equalsIgnoreCase(checkUsername);
                                boolean y = password.equalsIgnoreCase(checkPassword);
                                if( x == true && y == true)
                                {
                                    flag = 1;
                                    break;
                                }
                            }
                            if(flag != 1) {

                                Log.w(TAG, "Wrong username or password", task.getException());
                            }
                            else
                            {
                                Intent intent = new Intent(SigninActivity.this, AfterSigninActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
            }
        });
        signUpSwitchScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}