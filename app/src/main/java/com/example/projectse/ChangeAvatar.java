package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;

public class ChangeAvatar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);
        ImageButton ib_avt0;
        ib_avt0 = (ImageButton) findViewById(R.id.ib_avt0);

    }
}