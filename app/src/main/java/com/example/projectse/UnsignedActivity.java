package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;
import android.os.Bundle;
import android.view.WindowManager;

public class UnsignedActivity extends AppCompatActivity {

    Button sgleModeSwitchScrBtn, signInSwitchScrBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_unsigned);

        sgleModeSwitchScrBtn = findViewById(R.id.singlemode_UnsignedActivity);
        signInSwitchScrBtn = findViewById(R.id.signin_UnsignedActivity);
        signInSwitchScrBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(UnsignedActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
    }
}