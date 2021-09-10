package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputLayout;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.util.Log;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.NonNull;
public class SignupActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button signInSwitchScr, signUpBtn, returnToSigninScrBtn;
    TextInputLayout nUsername, nPassword, nPasswordcf;
    FirebaseFirestore usersDB = FirebaseFirestore.getInstance();
    private static final String TAG = "SignupActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();
        nUsername = findViewById(R.id.newUsernameLayout);
        nPassword = findViewById(R.id.newPasswordLayout);
        nPasswordcf = findViewById(R.id.newPasswordConfirmLayout);

        signInSwitchScr = findViewById(R.id.signin_SignupActivity);
        signUpBtn = findViewById(R.id.signup_SignupActivity);
        returnToSigninScrBtn = findViewById(R.id.returnToSignIn);

        signInSwitchScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        returnToSigninScrBtn.setOnClickListener(new View.OnClickListener() {
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
                final String username = nUsername.getEditText().getText().toString();
                final String password = nPassword.getEditText().getText().toString();
                final String passwordCF = nPasswordcf.getEditText().getText().toString();

                DocumentSnapshot read;
                if(TextUtils.isEmpty(username))
                {
                    nUsername.setError("Username cannot be empty ");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    nPassword.setError("Password cannot be empty ");
                    return;
                }

                if(TextUtils.isEmpty(passwordCF))
                {
                    nUsername.setError("Password confirmation cannot be empty ");
                    return;
                }

                boolean checkPasswordCF = password.equalsIgnoreCase(passwordCF);
                if(checkPasswordCF == false)
                {
                    nPasswordcf.setError("Password confirmation does not match ");
                    return;
                }
                Map<String, Object> user = new HashMap<>();
                user.put("username", username);
                user.put("password", password);
                user.put("avt", "0");
                user.put("displayName", username);

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
                                boolean x = username.equals(checkUsername);
                                if( x == true)
                                {
                                    nUsername.setError("Username already exists");
                                    flag = 1;
                                    break;
                                }
                            }
                            if(flag != 1) {
                                usersDB.collection("users").document(username)
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignupActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                                        editor.putString("uname", username);
                                        editor.putString("pword", password);
                                        editor.putBoolean("Signed", true);
                                        editor.commit();
                                        Intent intent = new Intent(SignupActivity.this, SignedIn.class);
                                        intent.putExtra("signedUpPlayerUsername", username);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        String error = e.getMessage();
                                        Toast.makeText(SignupActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
            }
        });

    }

}