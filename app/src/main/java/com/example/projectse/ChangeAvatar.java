package com.example.projectse;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangeAvatar extends Activity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button bt_change, bt_cancel;
    ImageButton avt0, avt1, avt2, avt3, avt4, avt5, avt6, avt7;
    int choosing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);
        initView();
        avt0.setOnClickListener(this);
        avt1.setOnClickListener(this);
        avt2.setOnClickListener(this);
        avt3.setOnClickListener(this);
        avt4.setOnClickListener(this);
        avt5.setOnClickListener(this);
        avt6.setOnClickListener(this);
        avt7.setOnClickListener(this);
        bt_change.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    private void initView() {
        bt_change=(Button) findViewById(R.id.bt_change);
        bt_cancel=(Button) findViewById(R.id.bt_cancel);
        avt0=(ImageButton) findViewById(R.id.ib_avt0);
        avt1=(ImageButton) findViewById(R.id.ib_avt1);
        avt2=(ImageButton) findViewById(R.id.ib_avt2);
        avt3=(ImageButton) findViewById(R.id.ib_avt3);
        avt4=(ImageButton) findViewById(R.id.ib_avt4);
        avt5=(ImageButton) findViewById(R.id.ib_avt5);
        avt6=(ImageButton) findViewById(R.id.ib_avt6);
        avt7=(ImageButton) findViewById(R.id.ib_avt7);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();
        resetAvatar(sharedPreferences.getInt("avt", 0));
    }

    private void resetAvatar(int choosingAvatar) {
        setAllUnpick();
        switch (choosingAvatar){
            case 0:
                avt0.setBackground(getResources().getDrawable(R.drawable.round_buttonpick));
                break;
            case 1:
                avt1.setBackground(getResources().getDrawable(R.drawable.round_buttonpick));
                break;
            case 2:
                avt2.setBackground(getResources().getDrawable(R.drawable.round_buttonpick));
                break;
            case 3:
                avt3.setBackground(getResources().getDrawable(R.drawable.round_buttonpick));
                break;
            case 4:
                avt4.setBackground(getResources().getDrawable(R.drawable.round_buttonpick));
                break;
            case 5:
                avt5.setBackground(getResources().getDrawable(R.drawable.round_buttonpick));
                break;
            case 6:
                avt6.setBackground(getResources().getDrawable(R.drawable.round_buttonpick));
                break;
            case 7:
                avt7.setBackground(getResources().getDrawable(R.drawable.round_buttonpick));
                break;
        }
    }

    private void setAllUnpick() {
        avt0.setBackground(getResources().getDrawable(R.drawable.round_buttonunpick));
        avt1.setBackground(getResources().getDrawable(R.drawable.round_buttonunpick));
        avt2.setBackground(getResources().getDrawable(R.drawable.round_buttonunpick));
        avt3.setBackground(getResources().getDrawable(R.drawable.round_buttonunpick));
        avt4.setBackground(getResources().getDrawable(R.drawable.round_buttonunpick));
        avt5.setBackground(getResources().getDrawable(R.drawable.round_buttonunpick));
        avt6.setBackground(getResources().getDrawable(R.drawable.round_buttonunpick));
        avt7.setBackground(getResources().getDrawable(R.drawable.round_buttonunpick));
    }

    @Override
    public void onClick(View view) {
        if(view==avt0){
            resetAvatar(0);
            choosing=0;
        }
        if(view==avt1){
            resetAvatar(1);
            choosing=1;
        }
        if(view==avt2){
            resetAvatar(2);
            choosing=2;
        }
        if(view==avt3){
            resetAvatar(3);
            choosing=3;
        }
        if(view==avt4){
            resetAvatar(4);
            choosing=4;
        }
        if(view==avt5){
            resetAvatar(5);
            choosing=5;
        }
        if(view==avt6){
            resetAvatar(6);
            choosing=6;
        }
        if(view==avt7) {
            resetAvatar(7);
            choosing=7;
        }
        if(view==bt_change){
            Toast.makeText(ChangeAvatar.this, "Changed Avatar", Toast.LENGTH_SHORT).show();
            editor.putInt("avt", choosing);
            editor.commit();
            finish();
        }
        if(view==bt_cancel){
            finish();
        }
    }
}