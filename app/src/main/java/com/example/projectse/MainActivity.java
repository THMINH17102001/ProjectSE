package com.example.projectse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseFirestore usersDB;
    FirebaseDatabase signInDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        //Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.main_top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.main_bot_animation);

        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);
        usersDB = FirebaseFirestore.getInstance();
        signInDB = FirebaseDatabase.getInstance("https://sudoku-80cb0-default-rtdb.asia-southeast1.firebasedatabase.app");
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                check();
            }
        }, 2000);
    }

    void check() {
        if (sharedPreferences.getBoolean("unDone", false)) {
            Intent continueIntent = new Intent(MainActivity.this, Continue.class);
            startActivity(continueIntent);
            finish();
        }
        String uname = sharedPreferences.getString("uname", "").toString();
        String pword = sharedPreferences.getString("pword", "").toString();
        if (sharedPreferences.getBoolean("Signed", false)) {
            usersDB.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String checkUsername = document.getString("username");
                            String checkPassword = document.getString("password");
                            if (uname.equals(checkUsername) && pword.equals(checkPassword)) {
                                Intent SignedIn = new Intent(MainActivity.this, AfterSigninActivity.class);
                                editor.putBoolean("Signed", true);
                                startActivity(SignedIn);
                                finish();
                            }
                        }
                        editor.putBoolean("Signed", false);
                    }
                }
            });
            //Neu doi mat khau bang thiet bi khac se bi treo giao dien
        }
        else{
            Intent intent = new Intent(MainActivity.this, UnsignedActivity.class);
            startActivity(intent);
            finish();
        }
    }
}