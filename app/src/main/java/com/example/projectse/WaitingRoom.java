package com.example.projectse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WaitingRoom extends AppCompatActivity {

    ListView listView;
    Button button;
    List<String> roomsList;
    String playerName="";
    String roomName="";
    Boolean run=true;
    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;
    SharedPreferences preferences, sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waiting_room);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        database = FirebaseDatabase.getInstance("https://sudoku-80cb0-default-rtdb.asia-southeast1.firebasedatabase.app");

        //get the player name and assign his roomname as his player name
        preferences = getSharedPreferences("PREPS", 0);
        playerName = preferences.getString("playerName", "");
        roomName = playerName;

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);

        //All existing available rooms
        roomsList = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //Create room and add yourself as player 1
            public void onClick(View view) {
                button.setText("CREATING ROOM");
                button.setEnabled(false);
                roomName = playerName;
                roomRef = database.getReference("room/" + roomName + "/s1/uname");
                roomRef.setValue(playerName);
                Sudoku sdk = new Sudoku(playerName, "Test");
                editor.putString("role", "s1");
                editor.putString("roomdiff", "Test");
                editor.putString("roomid", roomName);
                editor.commit();
                roomRef = database.getReference("room/" + roomName + "/s2/uname");
                roomRef.setValue("&^$%$");
                addRoomEventListener();



            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //join existing room and add yourself as player2
                if(playerName.equals(roomName)) {
                    roomName = roomsList.get(position);
                    roomRef = database.getReference("room/" + roomName + "/s2/uname");
                    addRoomEventListener();
                    roomRef.setValue(playerName);
                    editor.putString("role", "s2");
                    editor.putString("roomdiff", "Easy");
                    editor.putString("roomid", roomName);
                    editor.commit();
                }
            }
        });
        //show if new room is available
        addRoomsEventListener();

    }
    private void addRoomEventListener(){
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(run==true) {
                    //join the room
                    button.setText("CREATE ROOM");
                    button.setEnabled(true);
                    Intent intent = new Intent(getApplicationContext(), WaitingRoomTest1.class); //fix this to Waitingtest2
                    
                    intent.putExtra("roomName", roomName);
                    startActivity(intent);
                    run=false;
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error
                button.setText("CREATE ROOM");
                button.setEnabled(true);
                Toast.makeText(WaitingRoom.this,"Error!",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addRoomsEventListener(){
        //roomRef=database.getReference("rooms");
        roomsRef=database.getReference("room");
        roomsRef.orderByChild("s2/uname").equalTo("&^$%$").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(run==true) {
                    //show list of rooms
                    roomsList.clear();
                    Iterable<DataSnapshot> rooms = snapshot.getChildren();//Update from datasnapshot getchilden
                    for (DataSnapshot snapshot1 : rooms) {
                        roomsList.add(snapshot1.getKey());
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(WaitingRoom.this, android.R.layout.simple_list_item_1, roomsList);
                        listView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }

}