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

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.DocumentReference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.NonNull;
public class SignupActivity extends AppCompatActivity {

    Button signInSwitchScr, signUpBtn, returnToSigninScrBtn;
    TextInputLayout nUsername, nPassword, nPasswordcf;
    FirebaseFirestore usersDB = FirebaseFirestore.getInstance();

    FirebaseAuth fAuth;
    String userID;
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
        fAuth = FirebaseAuth.getInstance();

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
                String username = nUsername.getEditText().getText().toString();
                String password = nPassword.getEditText().getText().toString();
                String cfPassword = nPasswordcf.getEditText().getText().toString();

                fAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(SignupActivity.this, "user created", Toast.LENGTH_SHORT).show();
                        userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = usersDB.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("username", username);
                        user.put("password", password);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Successful");
                            }
                        });
                    }
                    else{
                        Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }



        });

    }

}