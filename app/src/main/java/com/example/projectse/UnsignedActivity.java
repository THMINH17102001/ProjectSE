package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

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

public class UnsignedActivity extends AppCompatActivity {

    Button sgleModeSwitchScrBtn, signInSwitchScrBtn, quitBtn;
    TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_unsigned);

        //Make a textview bold
        text1 = findViewById(R.id.multiTextView_UnsignedActivity);
        String str = "You want to play in multiple player mode ?";
        SpannableString ss = new SpannableString(str);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.rgb(0,0,0));
        ss.setSpan(boldSpan, 19, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(colorSpan, 19, 40,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text1.setText(ss);

        //Buttons
        sgleModeSwitchScrBtn = findViewById(R.id.singlemode_UnsignedActivity);
        signInSwitchScrBtn = findViewById(R.id.signin_UnsignedActivity);
        quitBtn = findViewById(R.id.quit_UnsignedActivity);

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