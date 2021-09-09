package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;
import android.os.Bundle;
import android.view.WindowManager;

public class UnsignedActivity extends Activity {

    Button sgleModeSwitchScrBtn, signInSwitchScrBtn, quitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_unsigned);

        //Buttons
        sgleModeSwitchScrBtn = findViewById(R.id.singlemode);
        signInSwitchScrBtn = findViewById(R.id.signin);
        quitBtn = findViewById(R.id.quit);

        signInSwitchScrBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(UnsignedActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sgleModeSwitchScrBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(UnsignedActivity.this, SingleMode.class);
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