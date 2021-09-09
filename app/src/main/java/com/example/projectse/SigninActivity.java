package com.example.projectse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.FirebaseFirestore;
public class SigninActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button signUpSwitchScr, signInBtn, returnToUnsignedBtn;
    TextInputLayout sUsername, sPassword;
    private static final String TAG = "SigninActivity";

    FirebaseFirestore usersDB;
    FirebaseDatabase signInDB;
    DatabaseReference playerRef;
    String playerName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();
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

        usersDB = FirebaseFirestore.getInstance();
        signInDB= FirebaseDatabase.getInstance("https://sudoku-80cb0-default-rtdb.asia-southeast1.firebasedatabase.app");
        sUsername = findViewById(R.id.username_SigninActivity);
        sPassword = findViewById(R.id.password_SigninActivity);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String username = sUsername.getEditText().getText().toString();
                String password = sPassword.getEditText().getText().toString();

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
                                boolean x = username.equals(checkUsername);
                                boolean y = password.equals(checkPassword);
                                if( x == true && y == true)
                                {
                                    editor.putString("uname", username);
                                    editor.putString("pword", password);
                                    editor.putBoolean("Signed", true);
                                    editor.commit();
                                    flag = 1;
                                    break;
                                }
                            }
                            if(flag != 1) {
                                sUsername.setError("Wrong username or password");
                            }
                            else
                            {
                                playerName = username;
                                if(!playerName.equals(""))
                                {
                                    playerRef = signInDB.getReference("player/" + playerName);
                                    addEventListener();
                                    playerRef.setValue("");
                                    Intent intent = new Intent(SigninActivity.this, AfterSigninActivity.class);
                                    intent.putExtra("signedInPlayerUsername", playerName);
                                    startActivity(intent);
                                    finish();
                                }
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
    private void addEventListener()
    {
        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!playerName.equals(""))
                {
                    SharedPreferences preferences = getSharedPreferences("PREPS",0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("playerName", playerName);
                    editor.apply();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}