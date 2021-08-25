package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import java.util.HashMap;
import java.util.Map;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.util.Log;

import androidx.annotation.NonNull;

public class SignupActivity extends AppCompatActivity {

    Button signInSwitchScr, signUpBtn;
    TextInputLayout nUsername, nPassword, nPasswordcf;
    FirebaseFirestore usersDB = FirebaseFirestore.getInstance();

    private static final String TAG = "SignupActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        nUsername = findViewById(R.id.newUsernameLayout);
        nPassword = findViewById(R.id.newPasswordLayout);
        nPasswordcf = findViewById(R.id.newPasswordConfirmLayout);

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
                String username = nUsername.getEditText().getText().toString();
                String password = nPassword.getEditText().getText().toString();
                String cfPassword = nPasswordcf.getEditText().getText().toString();
                Map<String, Object> user = new HashMap<>();
                user.put("username", username);
                user.put("password", password);

// Add a new document with a generated ID
                usersDB.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });            }

        });

    }

}