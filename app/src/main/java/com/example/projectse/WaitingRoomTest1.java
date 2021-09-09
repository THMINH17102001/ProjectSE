package com.example.projectse;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class WaitingRoomTest1 extends AppCompatActivity {
    private DatabaseReference mDatabase;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room_test1);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String roomid = sharedPreferences.getString("roomid", "");
        mDatabase = FirebaseDatabase.getInstance("https://sudoku-80cb0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("room/"+roomid+"/s2");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Boolean post = dataSnapshot.child("uname").getValue(String.class).equals("&^$%$");
                Toast.makeText(WaitingRoomTest1.this, post.toString(),Toast.LENGTH_SHORT).show();
                if(!post){
                    Intent i = new Intent(WaitingRoomTest1.this, MultiplayerMode.class);
                    startActivity(i);
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(postListener);
    }
}