package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WinGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_game);
        ImageButton bt_con = (ImageButton) findViewById(R.id.bt_con);
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String role = sharedPreferences.getString("role", "s2");
//        String room = sharedPreferences.getString("roomid", "");
//        if(role.equals("s1")&&!room.equals("")){
//            DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://sudoku-80cb0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("room").child(room);
//            mDatabase.removeValue();
//        }
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("roomid", "");
//        editor.commit();
        bt_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WinGame.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}