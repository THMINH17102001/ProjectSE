package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LoseGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose_game);
        ImageButton bt_con = (ImageButton) findViewById(R.id.bt_con);
        bt_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoseGame.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}