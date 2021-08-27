package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;
    Boolean undone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        undone=sharedPreferences.getBoolean("unDone", false);

        //Animation
        topAnim = AnimationUtils.loadAnimation( this, R.anim.main_top_animation);
        bottomAnim = AnimationUtils.loadAnimation( this, R.anim.main_bot_animation);

        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(undone) {
                    Intent continueIntent = new Intent(MainActivity.this, Continue.class);
                    startActivity(continueIntent);
                    finish();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, UnsignedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}