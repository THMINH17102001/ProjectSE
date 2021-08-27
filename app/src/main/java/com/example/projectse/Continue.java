package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class Continue extends Activity implements View.OnClickListener {
    Button bt_con, bt_cancel, bt_quit;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();
        bt_con = (Button) findViewById(R.id.bt_con);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_quit = (Button) findViewById(R.id.bt_quit);
        bt_con.setOnClickListener(this);
        bt_quit.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==bt_cancel){
            editor.putBoolean("unDone", false);
            editor.commit();
            Intent toMainActivityIntent = new Intent(Continue.this, MainActivity.class);
            startActivity(toMainActivityIntent);
            finish();
        }
        if(view==bt_con){
            Intent toSingleModeIntent = new Intent(Continue.this, SingleMode.class);
            startActivity(toSingleModeIntent);
            finish();
        }
        if(view==bt_quit){
            finish();
        }
    }
}