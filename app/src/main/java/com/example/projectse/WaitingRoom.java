package com.example.projectse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class WaitingRoom extends AppCompatActivity {

    ListView listView;
    Button button;
    List<String> roomList;
    String playerName="";
    String roomName="";

    //FirebaseDatabase database;
    //DatabaseReference roomRef;
    //DatabaseReference roomsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);

        //get the player name and assign his roomname as his player name
        SharedPreferences preferences=getSharedPreferences("PREPS",0);
        playerName=preferences.getString("playerName","");
        roomName=playerName;

        listView= (ListView)findViewById(R.id.listView);
        button=findViewById(R.id.button);

        //All existing available rooms
        roomList=new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //Create room and add yourself as player 1
            public void onClick(View view) {
                button.setText("CREATE ROOM");
                button.setEnabled(false);
                roomName=playerName;
                //roomRef=database.getReference("rooms/+roomName+"/player1"");
                //addRoomEventListener();
                //roomRef.setvalue(playerName);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                roomName=roomList.get(position);
                //roomRef=database.getReference("rooms/"+roomName+"/player2;
                //addRoomEventListener();
                //roomRef.setvalue(playerName);
            }
        });

        //Show if new room is available
        //addRoomEventListener();
    }
    /*private void addRoomEventListener() {
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void OnDataChange(@NonNull DataSnapShot dataSnapShot){

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }

        })
    }*/
}