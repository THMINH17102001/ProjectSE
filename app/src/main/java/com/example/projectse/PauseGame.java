package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PauseGame extends Activity implements View.OnClickListener {
Button bt;
ImageButton im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_game);
        bt=(Button) findViewById(R.id.bt);
        im=(ImageButton) findViewById(R.id.im);
        bt.setOnClickListener(this);
        im.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}