package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import android.os.Bundle;
import android.view.WindowManager;

public class AfterSigninActivity extends AppCompatActivity {

    Button singleModeSwitchScr, multiModeSwitchScr, quitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_after_signin);

        singleModeSwitchScr = findViewById(R.id.singlemode_AfterSigninActivity);
        multiModeSwitchScr = findViewById(R.id.multimode_AfterSigninActivity);
        quitBtn = findViewById(R.id.quit_AfterSigninActivity);
        singleModeSwitchScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSigninActivity.this, SingleMode.class);
                startActivity(intent);
                //finish();
            }
        });

        multiModeSwitchScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSigninActivity.this, WaitingRoom.class);
                startActivity(intent);
                //finish();
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

    }
}