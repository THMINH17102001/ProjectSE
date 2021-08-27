package com.example.projectse;


import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MultiplayerMode extends Activity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseReference mDatabase;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Random rand = new Random();
    Button c00, c01, c02, c03, c04, c05, c06, c07, c08,
            c10, c11, c12, c13, c14, c15, c16, c17, c18,
            c20, c21, c22, c23, c24, c25, c26, c27, c28,
            c30, c31, c32, c33, c34, c35, c36, c37, c38,
            c40, c41, c42, c43, c44, c45, c46, c47, c48,
            c50, c51, c52, c53, c54, c55, c56, c57, c58,
            c60, c61, c62, c63, c64, c65, c66, c67, c68,
            c70, c71, c72, c73, c74, c75, c76, c77, c78,
            c80, c81, c82, c83, c84, c85, c86, c87, c88;
    Button bt_1, bt_2, bt_3, bt_4, bt_5, bt_6, bt_7, bt_8, bt_9;
    Button bt_help, bt_hint, bt_note, bt_clear;
    TextView tv_fault, tv_diff, tv_timing, tv_p1, tv_p2, tv_user2, tv_user1;
    String dif;
    int dataBoard[][];
    int showBoard[][];
    int noteBoard[][];
    public int fault, hint, selX, selY, noteOn, maxFault;
    int selfPoint, compPoint, nRemove, timeCounting, selfState, compState;
    public String room, role, comprole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_mode);
        initValue();
        initView();
        initBoard();
        displayView();
        readPoint();
        showAllBoard();
        setListen();
    }

    private void readPoint() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                compPoint=dataSnapshot.child("room").child(room).child(comprole).child("point").getValue(int.class);
                tv_p2.setText(Integer.toString(compPoint));
                compState=dataSnapshot.child("room").child(room).child(comprole).child("state").getValue(int.class);
                selfState=dataSnapshot.child("room").child(room).child(role).child("state").getValue(int.class);
                if(compPoint == nRemove ||compPoint==-1){
                    endGame();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                selfState=selfState-1;
                if(selfState<0) endGame();
            }
        };
        mDatabase.addValueEventListener(postListener);
    }

    private void initBoard() {
        mDatabase.child("room").child(room).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(MultiplayerMode.this, "Server Disconnected", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    for(int i=0;i<9;i++){
                        for(int j =0 ; j<9;j++) {
                            String path = Integer.toString(i) + Integer.toString(j);
                            dataBoard[i][j] = parseInt(task.getResult().child("data").child(path).getValue().toString());
                            showBoard[i][j] = parseInt(task.getResult().child("show").child(path).getValue().toString());
                        }
                    }
                    tv_user1.setText(task.getResult().child(role).child("uname").getValue(String.class));
                    tv_user2.setText(task.getResult().child(comprole).child("uname").getValue(String.class));
                    selfPoint=task.getResult().child(role).child("point").getValue(int.class);
                    compPoint=task.getResult().child(comprole).child("point").getValue(int.class);
                    tv_p1.setText(Integer.toString(selfPoint));
                    tv_p2.setText(Integer.toString(compPoint));
                    showAllBoard();
                    startTimming();
                }
            }
        });
    }
    private void setListen() {
        bt_note.setOnClickListener(this);
        bt_hint.setOnClickListener(this);
        bt_clear.setOnClickListener(this);
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_6.setOnClickListener(this);
        bt_7.setOnClickListener(this);
        bt_8.setOnClickListener(this);
        bt_9.setOnClickListener(this);
        c00.setOnClickListener(this);
        c01.setOnClickListener(this);
        c02.setOnClickListener(this);
        c03.setOnClickListener(this);
        c04.setOnClickListener(this);
        c05.setOnClickListener(this);
        c06.setOnClickListener(this);
        c07.setOnClickListener(this);
        c08.setOnClickListener(this);
        c10.setOnClickListener(this);
        c11.setOnClickListener(this);
        c12.setOnClickListener(this);
        c13.setOnClickListener(this);
        c14.setOnClickListener(this);
        c15.setOnClickListener(this);
        c16.setOnClickListener(this);
        c17.setOnClickListener(this);
        c18.setOnClickListener(this);
        c20.setOnClickListener(this);
        c21.setOnClickListener(this);
        c22.setOnClickListener(this);
        c23.setOnClickListener(this);
        c24.setOnClickListener(this);
        c25.setOnClickListener(this);
        c26.setOnClickListener(this);
        c27.setOnClickListener(this);
        c28.setOnClickListener(this);
        c30.setOnClickListener(this);
        c31.setOnClickListener(this);
        c32.setOnClickListener(this);
        c33.setOnClickListener(this);
        c34.setOnClickListener(this);
        c35.setOnClickListener(this);
        c36.setOnClickListener(this);
        c37.setOnClickListener(this);
        c38.setOnClickListener(this);
        c40.setOnClickListener(this);
        c41.setOnClickListener(this);
        c42.setOnClickListener(this);
        c43.setOnClickListener(this);
        c44.setOnClickListener(this);
        c45.setOnClickListener(this);
        c46.setOnClickListener(this);
        c47.setOnClickListener(this);
        c48.setOnClickListener(this);
        c50.setOnClickListener(this);
        c51.setOnClickListener(this);
        c52.setOnClickListener(this);
        c53.setOnClickListener(this);
        c54.setOnClickListener(this);
        c55.setOnClickListener(this);
        c56.setOnClickListener(this);
        c57.setOnClickListener(this);
        c58.setOnClickListener(this);
        c60.setOnClickListener(this);
        c61.setOnClickListener(this);
        c62.setOnClickListener(this);
        c63.setOnClickListener(this);
        c64.setOnClickListener(this);
        c65.setOnClickListener(this);
        c66.setOnClickListener(this);
        c67.setOnClickListener(this);
        c68.setOnClickListener(this);
        c70.setOnClickListener(this);
        c71.setOnClickListener(this);
        c72.setOnClickListener(this);
        c73.setOnClickListener(this);
        c74.setOnClickListener(this);
        c75.setOnClickListener(this);
        c76.setOnClickListener(this);
        c77.setOnClickListener(this);
        c78.setOnClickListener(this);
        c80.setOnClickListener(this);
        c81.setOnClickListener(this);
        c82.setOnClickListener(this);
        c83.setOnClickListener(this);
        c84.setOnClickListener(this);
        c85.setOnClickListener(this);
        c86.setOnClickListener(this);
        c87.setOnClickListener(this);
        c88.setOnClickListener(this);

    }
    private void initValue() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        mDatabase = FirebaseDatabase.getInstance("https://sudoku-80cb0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        dif=sharedPreferences.getString("dif", "Easy");
        FirebaseDatabase.getInstance("https://sudoku-80cb0-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef;
        dataBoard = new int[9][9];
        showBoard = new int[9][9];
        noteBoard = new int[9][9];
        fault = 0;
        hint = 3;
        noteOn = 0;
        selX = 0;
        selY = 0;
        maxFault=3;
        selfPoint = 0;
        compPoint = 0;
        nRemove = getDiff(dif);
        rand = new Random();
        timeCounting=1*60*1000;
        room="00004";
        role="s2";
        if(role.equals("s2")) comprole="s1";
        else comprole= "s2";
        selfState=3;
        compState=3;
    }

    private int getDiff(String dif) {
        if(dif.equals("Easy")) return 55;
        if(dif.equals("Medium")) return 65;
        if(dif.equals("Hard")) return 75;
        return 5;
    }

    private void initView() {
        initCellView();
        bt_1 = (Button) findViewById(R.id.bt_1);
        bt_2 = (Button) findViewById(R.id.bt_2);
        bt_3 = (Button) findViewById(R.id.bt_3);
        bt_4 = (Button) findViewById(R.id.bt_4);
        bt_5 = (Button) findViewById(R.id.bt_5);
        bt_6 = (Button) findViewById(R.id.bt_6);
        bt_7 = (Button) findViewById(R.id.bt_7);
        bt_8 = (Button) findViewById(R.id.bt_8);
        bt_9 = (Button) findViewById(R.id.bt_9);
        bt_note = (Button) findViewById(R.id.ib_note);
        bt_clear = (Button) findViewById(R.id.ib_clear);
        bt_hint = (Button) findViewById(R.id.ib_hint);
        tv_fault = (TextView) findViewById(R.id.tv_fault);
        tv_diff=(TextView) findViewById(R.id.tv_diff);
        tv_timing= (TextView) findViewById(R.id.tv_timing);
        tv_user1=(TextView) findViewById(R.id.tv_user1);
        tv_user2=(TextView) findViewById(R.id.tv_user2);
        tv_p1=(TextView)findViewById(R.id.tv_p1);
        tv_p2=(TextView) findViewById(R.id.tv_p2);
    }
    private void displayView() {
        tv_fault.setText(Integer.toString(fault) + "/"+ Integer.toString(maxFault));
        tv_diff.setText("  " + dif + "  ");
        bt_hint.setText(Integer.toString(hint));
        if(hint == 0){
            bt_hint.getBackground().setAlpha(44);
            bt_hint.setTextColor(Color.parseColor("#FFFFFF"));
        }
        tv_timing.setText("00:00");
    }

    private void initCellView() {
        c00 = (Button) findViewById(R.id.c00);
        c01 = (Button) findViewById(R.id.c01);
        c02 = (Button) findViewById(R.id.c02);
        c03 = (Button) findViewById(R.id.c03);
        c04 = (Button) findViewById(R.id.c04);
        c05 = (Button) findViewById(R.id.c05);
        c06 = (Button) findViewById(R.id.c06);
        c07 = (Button) findViewById(R.id.c07);
        c08 = (Button) findViewById(R.id.c08);
        c10 = (Button) findViewById(R.id.c10);
        c11 = (Button) findViewById(R.id.c11);
        c12 = (Button) findViewById(R.id.c12);
        c13 = (Button) findViewById(R.id.c13);
        c14 = (Button) findViewById(R.id.c14);
        c15 = (Button) findViewById(R.id.c15);
        c16 = (Button) findViewById(R.id.c16);
        c17 = (Button) findViewById(R.id.c17);
        c18 = (Button) findViewById(R.id.c18);
        c20 = (Button) findViewById(R.id.c20);
        c21 = (Button) findViewById(R.id.c21);
        c22 = (Button) findViewById(R.id.c22);
        c23 = (Button) findViewById(R.id.c23);
        c24 = (Button) findViewById(R.id.c24);
        c25 = (Button) findViewById(R.id.c25);
        c26 = (Button) findViewById(R.id.c26);
        c27 = (Button) findViewById(R.id.c27);
        c28 = (Button) findViewById(R.id.c28);
        c30 = (Button) findViewById(R.id.c30);
        c31 = (Button) findViewById(R.id.c31);
        c32 = (Button) findViewById(R.id.c32);
        c33 = (Button) findViewById(R.id.c33);
        c34 = (Button) findViewById(R.id.c34);
        c35 = (Button) findViewById(R.id.c35);
        c36 = (Button) findViewById(R.id.c36);
        c37 = (Button) findViewById(R.id.c37);
        c38 = (Button) findViewById(R.id.c38);
        c40 = (Button) findViewById(R.id.c40);
        c41 = (Button) findViewById(R.id.c41);
        c42 = (Button) findViewById(R.id.c42);
        c43 = (Button) findViewById(R.id.c43);
        c44 = (Button) findViewById(R.id.c44);
        c45 = (Button) findViewById(R.id.c45);
        c46 = (Button) findViewById(R.id.c46);
        c47 = (Button) findViewById(R.id.c47);
        c48 = (Button) findViewById(R.id.c48);
        c50 = (Button) findViewById(R.id.c50);
        c51 = (Button) findViewById(R.id.c51);
        c52 = (Button) findViewById(R.id.c52);
        c53 = (Button) findViewById(R.id.c53);
        c54 = (Button) findViewById(R.id.c54);
        c55 = (Button) findViewById(R.id.c55);
        c56 = (Button) findViewById(R.id.c56);
        c57 = (Button) findViewById(R.id.c57);
        c58 = (Button) findViewById(R.id.c58);
        c60 = (Button) findViewById(R.id.c60);
        c61 = (Button) findViewById(R.id.c61);
        c62 = (Button) findViewById(R.id.c62);
        c63 = (Button) findViewById(R.id.c63);
        c64 = (Button) findViewById(R.id.c64);
        c65 = (Button) findViewById(R.id.c65);
        c66 = (Button) findViewById(R.id.c66);
        c67 = (Button) findViewById(R.id.c67);
        c68 = (Button) findViewById(R.id.c68);
        c70 = (Button) findViewById(R.id.c70);
        c71 = (Button) findViewById(R.id.c71);
        c72 = (Button) findViewById(R.id.c72);
        c73 = (Button) findViewById(R.id.c73);
        c74 = (Button) findViewById(R.id.c74);
        c75 = (Button) findViewById(R.id.c75);
        c76 = (Button) findViewById(R.id.c76);
        c77 = (Button) findViewById(R.id.c77);
        c78 = (Button) findViewById(R.id.c78);
        c80 = (Button) findViewById(R.id.c80);
        c81 = (Button) findViewById(R.id.c81);
        c82 = (Button) findViewById(R.id.c82);
        c83 = (Button) findViewById(R.id.c83);
        c84 = (Button) findViewById(R.id.c84);
        c85 = (Button) findViewById(R.id.c85);
        c86 = (Button) findViewById(R.id.c86);
        c87 = (Button) findViewById(R.id.c87);
        c88 = (Button) findViewById(R.id.c88);
    }

    private void show(int[][] x) {
        c00.setText(Integer.toString(x[0][0]));
        c01.setText(Integer.toString(x[0][1]));
        c02.setText(Integer.toString(x[0][2]));
        c03.setText(Integer.toString(x[0][3]));
        c04.setText(Integer.toString(x[0][4]));
        c05.setText(Integer.toString(x[0][5]));
        c06.setText(Integer.toString(x[0][6]));
        c07.setText(Integer.toString(x[0][7]));
        c08.setText(Integer.toString(x[0][8]));
        c10.setText(Integer.toString(x[1][0]));
        c11.setText(Integer.toString(x[1][1]));
        c12.setText(Integer.toString(x[1][2]));
        c13.setText(Integer.toString(x[1][3]));
        c14.setText(Integer.toString(x[1][4]));
        c15.setText(Integer.toString(x[1][5]));
        c16.setText(Integer.toString(x[1][6]));
        c17.setText(Integer.toString(x[1][7]));
        c18.setText(Integer.toString(x[1][8]));
        c20.setText(Integer.toString(x[2][0]));
        c21.setText(Integer.toString(x[2][1]));
        c22.setText(Integer.toString(x[2][2]));
        c23.setText(Integer.toString(x[2][3]));
        c24.setText(Integer.toString(x[2][4]));
        c25.setText(Integer.toString(x[2][5]));
        c26.setText(Integer.toString(x[2][6]));
        c27.setText(Integer.toString(x[2][7]));
        c28.setText(Integer.toString(x[2][8]));
        c30.setText(Integer.toString(x[3][0]));
        c31.setText(Integer.toString(x[3][1]));
        c32.setText(Integer.toString(x[3][2]));
        c33.setText(Integer.toString(x[3][3]));
        c34.setText(Integer.toString(x[3][4]));
        c35.setText(Integer.toString(x[3][5]));
        c36.setText(Integer.toString(x[3][6]));
        c37.setText(Integer.toString(x[3][7]));
        c38.setText(Integer.toString(x[3][8]));
        c40.setText(Integer.toString(x[4][0]));
        c41.setText(Integer.toString(x[4][1]));
        c42.setText(Integer.toString(x[4][2]));
        c43.setText(Integer.toString(x[4][3]));
        c44.setText(Integer.toString(x[4][4]));
        c45.setText(Integer.toString(x[4][5]));
        c46.setText(Integer.toString(x[4][6]));
        c47.setText(Integer.toString(x[4][7]));
        c48.setText(Integer.toString(x[4][8]));
        c50.setText(Integer.toString(x[5][0]));
        c51.setText(Integer.toString(x[5][1]));
        c52.setText(Integer.toString(x[5][2]));
        c53.setText(Integer.toString(x[5][3]));
        c54.setText(Integer.toString(x[5][4]));
        c55.setText(Integer.toString(x[5][5]));
        c56.setText(Integer.toString(x[5][6]));
        c57.setText(Integer.toString(x[5][7]));
        c58.setText(Integer.toString(x[5][8]));
        c60.setText(Integer.toString(x[6][0]));
        c61.setText(Integer.toString(x[6][1]));
        c62.setText(Integer.toString(x[6][2]));
        c63.setText(Integer.toString(x[6][3]));
        c64.setText(Integer.toString(x[6][4]));
        c65.setText(Integer.toString(x[6][5]));
        c66.setText(Integer.toString(x[6][6]));
        c67.setText(Integer.toString(x[6][7]));
        c68.setText(Integer.toString(x[6][8]));
        c70.setText(Integer.toString(x[7][0]));
        c71.setText(Integer.toString(x[7][1]));
        c72.setText(Integer.toString(x[7][2]));
        c73.setText(Integer.toString(x[7][3]));
        c74.setText(Integer.toString(x[7][4]));
        c75.setText(Integer.toString(x[7][5]));
        c76.setText(Integer.toString(x[7][6]));
        c77.setText(Integer.toString(x[7][7]));
        c78.setText(Integer.toString(x[7][8]));
        c80.setText(Integer.toString(x[8][0]));
        c81.setText(Integer.toString(x[8][1]));
        c82.setText(Integer.toString(x[8][2]));
        c83.setText(Integer.toString(x[8][3]));
        c84.setText(Integer.toString(x[8][4]));
        c85.setText(Integer.toString(x[8][5]));
        c86.setText(Integer.toString(x[8][6]));
        c87.setText(Integer.toString(x[8][7]));
        c88.setText(Integer.toString(x[8][8]));
    }

    private void showABoard() {
        //For select
        if (selX == 0 && selY == 0) {
            c00.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 0 || selY == 0) {
            c00.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c00.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        //Show !=0 Show == data
        if (showBoard[0][0] != 0 && showBoard[0][0] == dataBoard[0][0]) {
            c00.setText(Integer.toString(showBoard[0][0]));
            setNormal(c00);
        }
        //Show !=0 Show != data
        if (showBoard[0][0] != 0 && showBoard[0][0] != dataBoard[0][0]) {
            c00.setText(Integer.toString(showBoard[0][0]));
            setFalse(c00);
        }
        //Show == 0 Note == 0
        if (showBoard[0][0] == 0 && noteBoard[0][0] == 0) {
            setBlank(c00);
        }
        //Show == 0 Note !=0
        if (showBoard[0][0] == 0 && noteBoard[0][0] != 0) {
            c00.setText(Integer.toString(noteBoard[0][0]));
            setNote(c00);
        }
    }

    private void showAllBoard() {
        if (selX == 0 && selY == 0) {
            c00.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 0 || selY == 0) {
            c00.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c00.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[0][0] != 0 && showBoard[0][0] == dataBoard[0][0]) {
            c00.setText(Integer.toString(showBoard[0][0]));
            setNormal(c00);
        }
        if (showBoard[0][0] != 0 && showBoard[0][0] != dataBoard[0][0]) {
            c00.setText(Integer.toString(showBoard[0][0]));
            setFalse(c00);
        }
        if (showBoard[0][0] == 0 && noteBoard[0][0] == 0) {
            setBlank(c00);
        }
        if (showBoard[0][0] == 0 && noteBoard[0][0] != 0) {
            c00.setText(Integer.toString(noteBoard[0][0]));
            setNote(c00);
        }
        if (selX == 0 && selY == 1) {
            c01.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 0 || selY == 1) {
            c01.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c01.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[0][1] != 0 && showBoard[0][1] == dataBoard[0][1]) {
            c01.setText(Integer.toString(showBoard[0][1]));
            setNormal(c01);
        }
        if (showBoard[0][1] != 0 && showBoard[0][1] != dataBoard[0][1]) {
            c01.setText(Integer.toString(showBoard[0][1]));
            setFalse(c01);
        }
        if (showBoard[0][1] == 0 && noteBoard[0][1] == 0) {
            setBlank(c01);
        }
        if (showBoard[0][1] == 0 && noteBoard[0][1] != 0) {
            c01.setText(Integer.toString(noteBoard[0][1]));
            setNote(c01);
        }
        if (selX == 0 && selY == 2) {
            c02.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 0 || selY == 2) {
            c02.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c02.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[0][2] != 0 && showBoard[0][2] == dataBoard[0][2]) {
            c02.setText(Integer.toString(showBoard[0][2]));
            setNormal(c02);
        }
        if (showBoard[0][2] != 0 && showBoard[0][2] != dataBoard[0][2]) {
            c02.setText(Integer.toString(showBoard[0][2]));
            setFalse(c02);
        }
        if (showBoard[0][2] == 0 && noteBoard[0][2] == 0) {
            setBlank(c02);
        }
        if (showBoard[0][2] == 0 && noteBoard[0][2] != 0) {
            c02.setText(Integer.toString(noteBoard[0][2]));
            setNote(c02);
        }
        if (selX == 0 && selY == 3) {
            c03.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 0 || selY == 3) {
            c03.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c03.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[0][3] != 0 && showBoard[0][3] == dataBoard[0][3]) {
            c03.setText(Integer.toString(showBoard[0][3]));
            setNormal(c03);
        }
        if (showBoard[0][3] != 0 && showBoard[0][3] != dataBoard[0][3]) {
            c03.setText(Integer.toString(showBoard[0][3]));
            setFalse(c03);
        }
        if (showBoard[0][3] == 0 && noteBoard[0][3] == 0) {
            setBlank(c03);
        }
        if (showBoard[0][3] == 0 && noteBoard[0][3] != 0) {
            c03.setText(Integer.toString(noteBoard[0][3]));
            setNote(c03);
        }
        if (selX == 0 && selY == 4) {
            c04.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 0 || selY == 4) {
            c04.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c04.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[0][4] != 0 && showBoard[0][4] == dataBoard[0][4]) {
            c04.setText(Integer.toString(showBoard[0][4]));
            setNormal(c04);
        }
        if (showBoard[0][4] != 0 && showBoard[0][4] != dataBoard[0][4]) {
            c04.setText(Integer.toString(showBoard[0][4]));
            setFalse(c04);
        }
        if (showBoard[0][4] == 0 && noteBoard[0][4] == 0) {
            setBlank(c04);
        }
        if (showBoard[0][4] == 0 && noteBoard[0][4] != 0) {
            c04.setText(Integer.toString(noteBoard[0][4]));
            setNote(c04);
        }
        if (selX == 0 && selY == 5) {
            c05.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 0 || selY == 5) {
            c05.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c05.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[0][5] != 0 && showBoard[0][5] == dataBoard[0][5]) {
            c05.setText(Integer.toString(showBoard[0][5]));
            setNormal(c05);
        }
        if (showBoard[0][5] != 0 && showBoard[0][5] != dataBoard[0][5]) {
            c05.setText(Integer.toString(showBoard[0][5]));
            setFalse(c05);
        }
        if (showBoard[0][5] == 0 && noteBoard[0][5] == 0) {
            setBlank(c05);
        }
        if (showBoard[0][5] == 0 && noteBoard[0][5] != 0) {
            c05.setText(Integer.toString(noteBoard[0][5]));
            setNote(c05);
        }
        if (selX == 0 && selY == 6) {
            c06.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 0 || selY == 6) {
            c06.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c06.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[0][6] != 0 && showBoard[0][6] == dataBoard[0][6]) {
            c06.setText(Integer.toString(showBoard[0][6]));
            setNormal(c06);
        }
        if (showBoard[0][6] != 0 && showBoard[0][6] != dataBoard[0][6]) {
            c06.setText(Integer.toString(showBoard[0][6]));
            setFalse(c06);
        }
        if (showBoard[0][6] == 0 && noteBoard[0][6] == 0) {
            setBlank(c06);
        }
        if (showBoard[0][6] == 0 && noteBoard[0][6] != 0) {
            c06.setText(Integer.toString(noteBoard[0][6]));
            setNote(c06);
        }
        if (selX == 0 && selY == 7) {
            c07.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 0 || selY == 7) {
            c07.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c07.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[0][7] != 0 && showBoard[0][7] == dataBoard[0][7]) {
            c07.setText(Integer.toString(showBoard[0][7]));
            setNormal(c07);
        }
        if (showBoard[0][7] != 0 && showBoard[0][7] != dataBoard[0][7]) {
            c07.setText(Integer.toString(showBoard[0][7]));
            setFalse(c07);
        }
        if (showBoard[0][7] == 0 && noteBoard[0][7] == 0) {
            setBlank(c07);
        }
        if (showBoard[0][7] == 0 && noteBoard[0][7] != 0) {
            c07.setText(Integer.toString(noteBoard[0][7]));
            setNote(c07);
        }
        if (selX == 0 && selY == 8) {
            c08.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 0 || selY == 8) {
            c08.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c08.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[0][8] != 0 && showBoard[0][8] == dataBoard[0][8]) {
            c08.setText(Integer.toString(showBoard[0][8]));
            setNormal(c08);
        }
        if (showBoard[0][8] != 0 && showBoard[0][8] != dataBoard[0][8]) {
            c08.setText(Integer.toString(showBoard[0][8]));
            setFalse(c08);
        }
        if (showBoard[0][8] == 0 && noteBoard[0][8] == 0) {
            setBlank(c08);
        }
        if (showBoard[0][8] == 0 && noteBoard[0][8] != 0) {
            c08.setText(Integer.toString(noteBoard[0][8]));
            setNote(c08);
        }
        if (selX == 1 && selY == 0) {
            c10.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 1 || selY == 0) {
            c10.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c10.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[1][0] != 0 && showBoard[1][0] == dataBoard[1][0]) {
            c10.setText(Integer.toString(showBoard[1][0]));
            setNormal(c10);
        }
        if (showBoard[1][0] != 0 && showBoard[1][0] != dataBoard[1][0]) {
            c10.setText(Integer.toString(showBoard[1][0]));
            setFalse(c10);
        }
        if (showBoard[1][0] == 0 && noteBoard[1][0] == 0) {
            setBlank(c10);
        }
        if (showBoard[1][0] == 0 && noteBoard[1][0] != 0) {
            c10.setText(Integer.toString(noteBoard[1][0]));
            setNote(c10);
        }
        if (selX == 1 && selY == 1) {
            c11.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 1 || selY == 1) {
            c11.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c11.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[1][1] != 0 && showBoard[1][1] == dataBoard[1][1]) {
            c11.setText(Integer.toString(showBoard[1][1]));
            setNormal(c11);
        }
        if (showBoard[1][1] != 0 && showBoard[1][1] != dataBoard[1][1]) {
            c11.setText(Integer.toString(showBoard[1][1]));
            setFalse(c11);
        }
        if (showBoard[1][1] == 0 && noteBoard[1][1] == 0) {
            setBlank(c11);
        }
        if (showBoard[1][1] == 0 && noteBoard[1][1] != 0) {
            c11.setText(Integer.toString(noteBoard[1][1]));
            setNote(c11);
        }
        if (selX == 1 && selY == 2) {
            c12.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 1 || selY == 2) {
            c12.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c12.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[1][2] != 0 && showBoard[1][2] == dataBoard[1][2]) {
            c12.setText(Integer.toString(showBoard[1][2]));
            setNormal(c12);
        }
        if (showBoard[1][2] != 0 && showBoard[1][2] != dataBoard[1][2]) {
            c12.setText(Integer.toString(showBoard[1][2]));
            setFalse(c12);
        }
        if (showBoard[1][2] == 0 && noteBoard[1][2] == 0) {
            setBlank(c12);
        }
        if (showBoard[1][2] == 0 && noteBoard[1][2] != 0) {
            c12.setText(Integer.toString(noteBoard[1][2]));
            setNote(c12);
        }
        if (selX == 1 && selY == 3) {
            c13.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 1 || selY == 3) {
            c13.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c13.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[1][3] != 0 && showBoard[1][3] == dataBoard[1][3]) {
            c13.setText(Integer.toString(showBoard[1][3]));
            setNormal(c13);
        }
        if (showBoard[1][3] != 0 && showBoard[1][3] != dataBoard[1][3]) {
            c13.setText(Integer.toString(showBoard[1][3]));
            setFalse(c13);
        }
        if (showBoard[1][3] == 0 && noteBoard[1][3] == 0) {
            setBlank(c13);
        }
        if (showBoard[1][3] == 0 && noteBoard[1][3] != 0) {
            c13.setText(Integer.toString(noteBoard[1][3]));
            setNote(c13);
        }
        if (selX == 1 && selY == 4) {
            c14.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 1 || selY == 4) {
            c14.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c14.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[1][4] != 0 && showBoard[1][4] == dataBoard[1][4]) {
            c14.setText(Integer.toString(showBoard[1][4]));
            setNormal(c14);
        }
        if (showBoard[1][4] != 0 && showBoard[1][4] != dataBoard[1][4]) {
            c14.setText(Integer.toString(showBoard[1][4]));
            setFalse(c14);
        }
        if (showBoard[1][4] == 0 && noteBoard[1][4] == 0) {
            setBlank(c14);
        }
        if (showBoard[1][4] == 0 && noteBoard[1][4] != 0) {
            c14.setText(Integer.toString(noteBoard[1][4]));
            setNote(c14);
        }
        if (selX == 1 && selY == 5) {
            c15.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 1 || selY == 5) {
            c15.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c15.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[1][5] != 0 && showBoard[1][5] == dataBoard[1][5]) {
            c15.setText(Integer.toString(showBoard[1][5]));
            setNormal(c15);
        }
        if (showBoard[1][5] != 0 && showBoard[1][5] != dataBoard[1][5]) {
            c15.setText(Integer.toString(showBoard[1][5]));
            setFalse(c15);
        }
        if (showBoard[1][5] == 0 && noteBoard[1][5] == 0) {
            setBlank(c15);
        }
        if (showBoard[1][5] == 0 && noteBoard[1][5] != 0) {
            c15.setText(Integer.toString(noteBoard[1][5]));
            setNote(c15);
        }
        if (selX == 1 && selY == 6) {
            c16.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 1 || selY == 6) {
            c16.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c16.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[1][6] != 0 && showBoard[1][6] == dataBoard[1][6]) {
            c16.setText(Integer.toString(showBoard[1][6]));
            setNormal(c16);
        }
        if (showBoard[1][6] != 0 && showBoard[1][6] != dataBoard[1][6]) {
            c16.setText(Integer.toString(showBoard[1][6]));
            setFalse(c16);
        }
        if (showBoard[1][6] == 0 && noteBoard[1][6] == 0) {
            setBlank(c16);
        }
        if (showBoard[1][6] == 0 && noteBoard[1][6] != 0) {
            c16.setText(Integer.toString(noteBoard[1][6]));
            setNote(c16);
        }
        if (selX == 1 && selY == 7) {
            c17.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 1 || selY == 7) {
            c17.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c17.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[1][7] != 0 && showBoard[1][7] == dataBoard[1][7]) {
            c17.setText(Integer.toString(showBoard[1][7]));
            setNormal(c17);
        }
        if (showBoard[1][7] != 0 && showBoard[1][7] != dataBoard[1][7]) {
            c17.setText(Integer.toString(showBoard[1][7]));
            setFalse(c17);
        }
        if (showBoard[1][7] == 0 && noteBoard[1][7] == 0) {
            setBlank(c17);
        }
        if (showBoard[1][7] == 0 && noteBoard[1][7] != 0) {
            c17.setText(Integer.toString(noteBoard[1][7]));
            setNote(c17);
        }
        if (selX == 1 && selY == 8) {
            c18.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 1 || selY == 8) {
            c18.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c18.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[1][8] != 0 && showBoard[1][8] == dataBoard[1][8]) {
            c18.setText(Integer.toString(showBoard[1][8]));
            setNormal(c18);
        }
        if (showBoard[1][8] != 0 && showBoard[1][8] != dataBoard[1][8]) {
            c18.setText(Integer.toString(showBoard[1][8]));
            setFalse(c18);
        }
        if (showBoard[1][8] == 0 && noteBoard[1][8] == 0) {
            setBlank(c18);
        }
        if (showBoard[1][8] == 0 && noteBoard[1][8] != 0) {
            c18.setText(Integer.toString(noteBoard[1][8]));
            setNote(c18);
        }
        if (selX == 2 && selY == 0) {
            c20.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 2 || selY == 0) {
            c20.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c20.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[2][0] != 0 && showBoard[2][0] == dataBoard[2][0]) {
            c20.setText(Integer.toString(showBoard[2][0]));
            setNormal(c20);
        }
        if (showBoard[2][0] != 0 && showBoard[2][0] != dataBoard[2][0]) {
            c20.setText(Integer.toString(showBoard[2][0]));
            setFalse(c20);
        }
        if (showBoard[2][0] == 0 && noteBoard[2][0] == 0) {
            setBlank(c20);
        }
        if (showBoard[2][0] == 0 && noteBoard[2][0] != 0) {
            c20.setText(Integer.toString(noteBoard[2][0]));
            setNote(c20);
        }
        if (selX == 2 && selY == 1) {
            c21.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 2 || selY == 1) {
            c21.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c21.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[2][1] != 0 && showBoard[2][1] == dataBoard[2][1]) {
            c21.setText(Integer.toString(showBoard[2][1]));
            setNormal(c21);
        }
        if (showBoard[2][1] != 0 && showBoard[2][1] != dataBoard[2][1]) {
            c21.setText(Integer.toString(showBoard[2][1]));
            setFalse(c21);
        }
        if (showBoard[2][1] == 0 && noteBoard[2][1] == 0) {
            setBlank(c21);
        }
        if (showBoard[2][1] == 0 && noteBoard[2][1] != 0) {
            c21.setText(Integer.toString(noteBoard[2][1]));
            setNote(c21);
        }
        if (selX == 2 && selY == 2) {
            c22.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 2 || selY == 2) {
            c22.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c22.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[2][2] != 0 && showBoard[2][2] == dataBoard[2][2]) {
            c22.setText(Integer.toString(showBoard[2][2]));
            setNormal(c22);
        }
        if (showBoard[2][2] != 0 && showBoard[2][2] != dataBoard[2][2]) {
            c22.setText(Integer.toString(showBoard[2][2]));
            setFalse(c22);
        }
        if (showBoard[2][2] == 0 && noteBoard[2][2] == 0) {
            setBlank(c22);
        }
        if (showBoard[2][2] == 0 && noteBoard[2][2] != 0) {
            c22.setText(Integer.toString(noteBoard[2][2]));
            setNote(c22);
        }
        if (selX == 2 && selY == 3) {
            c23.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 2 || selY == 3) {
            c23.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c23.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[2][3] != 0 && showBoard[2][3] == dataBoard[2][3]) {
            c23.setText(Integer.toString(showBoard[2][3]));
            setNormal(c23);
        }
        if (showBoard[2][3] != 0 && showBoard[2][3] != dataBoard[2][3]) {
            c23.setText(Integer.toString(showBoard[2][3]));
            setFalse(c23);
        }
        if (showBoard[2][3] == 0 && noteBoard[2][3] == 0) {
            setBlank(c23);
        }
        if (showBoard[2][3] == 0 && noteBoard[2][3] != 0) {
            c23.setText(Integer.toString(noteBoard[2][3]));
            setNote(c23);
        }
        if (selX == 2 && selY == 4) {
            c24.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 2 || selY == 4) {
            c24.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c24.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[2][4] != 0 && showBoard[2][4] == dataBoard[2][4]) {
            c24.setText(Integer.toString(showBoard[2][4]));
            setNormal(c24);
        }
        if (showBoard[2][4] != 0 && showBoard[2][4] != dataBoard[2][4]) {
            c24.setText(Integer.toString(showBoard[2][4]));
            setFalse(c24);
        }
        if (showBoard[2][4] == 0 && noteBoard[2][4] == 0) {
            setBlank(c24);
        }
        if (showBoard[2][4] == 0 && noteBoard[2][4] != 0) {
            c24.setText(Integer.toString(noteBoard[2][4]));
            setNote(c24);
        }
        if (selX == 2 && selY == 5) {
            c25.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 2 || selY == 5) {
            c25.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c25.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[2][5] != 0 && showBoard[2][5] == dataBoard[2][5]) {
            c25.setText(Integer.toString(showBoard[2][5]));
            setNormal(c25);
        }
        if (showBoard[2][5] != 0 && showBoard[2][5] != dataBoard[2][5]) {
            c25.setText(Integer.toString(showBoard[2][5]));
            setFalse(c25);
        }
        if (showBoard[2][5] == 0 && noteBoard[2][5] == 0) {
            setBlank(c25);
        }
        if (showBoard[2][5] == 0 && noteBoard[2][5] != 0) {
            c25.setText(Integer.toString(noteBoard[2][5]));
            setNote(c25);
        }
        if (selX == 2 && selY == 6) {
            c26.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 2 || selY == 6) {
            c26.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c26.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[2][6] != 0 && showBoard[2][6] == dataBoard[2][6]) {
            c26.setText(Integer.toString(showBoard[2][6]));
            setNormal(c26);
        }
        if (showBoard[2][6] != 0 && showBoard[2][6] != dataBoard[2][6]) {
            c26.setText(Integer.toString(showBoard[2][6]));
            setFalse(c26);
        }
        if (showBoard[2][6] == 0 && noteBoard[2][6] == 0) {
            setBlank(c26);
        }
        if (showBoard[2][6] == 0 && noteBoard[2][6] != 0) {
            c26.setText(Integer.toString(noteBoard[2][6]));
            setNote(c26);
        }
        if (selX == 2 && selY == 7) {
            c27.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 2 || selY == 7) {
            c27.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c27.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[2][7] != 0 && showBoard[2][7] == dataBoard[2][7]) {
            c27.setText(Integer.toString(showBoard[2][7]));
            setNormal(c27);
        }
        if (showBoard[2][7] != 0 && showBoard[2][7] != dataBoard[2][7]) {
            c27.setText(Integer.toString(showBoard[2][7]));
            setFalse(c27);
        }
        if (showBoard[2][7] == 0 && noteBoard[2][7] == 0) {
            setBlank(c27);
        }
        if (showBoard[2][7] == 0 && noteBoard[2][7] != 0) {
            c27.setText(Integer.toString(noteBoard[2][7]));
            setNote(c27);
        }
        if (selX == 2 && selY == 8) {
            c28.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 2 || selY == 8) {
            c28.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c28.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[2][8] != 0 && showBoard[2][8] == dataBoard[2][8]) {
            c28.setText(Integer.toString(showBoard[2][8]));
            setNormal(c28);
        }
        if (showBoard[2][8] != 0 && showBoard[2][8] != dataBoard[2][8]) {
            c28.setText(Integer.toString(showBoard[2][8]));
            setFalse(c28);
        }
        if (showBoard[2][8] == 0 && noteBoard[2][8] == 0) {
            setBlank(c28);
        }
        if (showBoard[2][8] == 0 && noteBoard[2][8] != 0) {
            c28.setText(Integer.toString(noteBoard[2][8]));
            setNote(c28);
        }
        if (selX == 3 && selY == 0) {
            c30.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 3 || selY == 0) {
            c30.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c30.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[3][0] != 0 && showBoard[3][0] == dataBoard[3][0]) {
            c30.setText(Integer.toString(showBoard[3][0]));
            setNormal(c30);
        }
        if (showBoard[3][0] != 0 && showBoard[3][0] != dataBoard[3][0]) {
            c30.setText(Integer.toString(showBoard[3][0]));
            setFalse(c30);
        }
        if (showBoard[3][0] == 0 && noteBoard[3][0] == 0) {
            setBlank(c30);
        }
        if (showBoard[3][0] == 0 && noteBoard[3][0] != 0) {
            c30.setText(Integer.toString(noteBoard[3][0]));
            setNote(c30);
        }
        if (selX == 3 && selY == 1) {
            c31.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 3 || selY == 1) {
            c31.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c31.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[3][1] != 0 && showBoard[3][1] == dataBoard[3][1]) {
            c31.setText(Integer.toString(showBoard[3][1]));
            setNormal(c31);
        }
        if (showBoard[3][1] != 0 && showBoard[3][1] != dataBoard[3][1]) {
            c31.setText(Integer.toString(showBoard[3][1]));
            setFalse(c31);
        }
        if (showBoard[3][1] == 0 && noteBoard[3][1] == 0) {
            setBlank(c31);
        }
        if (showBoard[3][1] == 0 && noteBoard[3][1] != 0) {
            c31.setText(Integer.toString(noteBoard[3][1]));
            setNote(c31);
        }
        if (selX == 3 && selY == 2) {
            c32.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 3 || selY == 2) {
            c32.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c32.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[3][2] != 0 && showBoard[3][2] == dataBoard[3][2]) {
            c32.setText(Integer.toString(showBoard[3][2]));
            setNormal(c32);
        }
        if (showBoard[3][2] != 0 && showBoard[3][2] != dataBoard[3][2]) {
            c32.setText(Integer.toString(showBoard[3][2]));
            setFalse(c32);
        }
        if (showBoard[3][2] == 0 && noteBoard[3][2] == 0) {
            setBlank(c32);
        }
        if (showBoard[3][2] == 0 && noteBoard[3][2] != 0) {
            c32.setText(Integer.toString(noteBoard[3][2]));
            setNote(c32);
        }
        if (selX == 3 && selY == 3) {
            c33.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 3 || selY == 3) {
            c33.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c33.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[3][3] != 0 && showBoard[3][3] == dataBoard[3][3]) {
            c33.setText(Integer.toString(showBoard[3][3]));
            setNormal(c33);
        }
        if (showBoard[3][3] != 0 && showBoard[3][3] != dataBoard[3][3]) {
            c33.setText(Integer.toString(showBoard[3][3]));
            setFalse(c33);
        }
        if (showBoard[3][3] == 0 && noteBoard[3][3] == 0) {
            setBlank(c33);
        }
        if (showBoard[3][3] == 0 && noteBoard[3][3] != 0) {
            c33.setText(Integer.toString(noteBoard[3][3]));
            setNote(c33);
        }
        if (selX == 3 && selY == 4) {
            c34.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 3 || selY == 4) {
            c34.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c34.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[3][4] != 0 && showBoard[3][4] == dataBoard[3][4]) {
            c34.setText(Integer.toString(showBoard[3][4]));
            setNormal(c34);
        }
        if (showBoard[3][4] != 0 && showBoard[3][4] != dataBoard[3][4]) {
            c34.setText(Integer.toString(showBoard[3][4]));
            setFalse(c34);
        }
        if (showBoard[3][4] == 0 && noteBoard[3][4] == 0) {
            setBlank(c34);
        }
        if (showBoard[3][4] == 0 && noteBoard[3][4] != 0) {
            c34.setText(Integer.toString(noteBoard[3][4]));
            setNote(c34);
        }
        if (selX == 3 && selY == 5) {
            c35.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 3 || selY == 5) {
            c35.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c35.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[3][5] != 0 && showBoard[3][5] == dataBoard[3][5]) {
            c35.setText(Integer.toString(showBoard[3][5]));
            setNormal(c35);
        }
        if (showBoard[3][5] != 0 && showBoard[3][5] != dataBoard[3][5]) {
            c35.setText(Integer.toString(showBoard[3][5]));
            setFalse(c35);
        }
        if (showBoard[3][5] == 0 && noteBoard[3][5] == 0) {
            setBlank(c35);
        }
        if (showBoard[3][5] == 0 && noteBoard[3][5] != 0) {
            c35.setText(Integer.toString(noteBoard[3][5]));
            setNote(c35);
        }
        if (selX == 3 && selY == 6) {
            c36.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 3 || selY == 6) {
            c36.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c36.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[3][6] != 0 && showBoard[3][6] == dataBoard[3][6]) {
            c36.setText(Integer.toString(showBoard[3][6]));
            setNormal(c36);
        }
        if (showBoard[3][6] != 0 && showBoard[3][6] != dataBoard[3][6]) {
            c36.setText(Integer.toString(showBoard[3][6]));
            setFalse(c36);
        }
        if (showBoard[3][6] == 0 && noteBoard[3][6] == 0) {
            setBlank(c36);
        }
        if (showBoard[3][6] == 0 && noteBoard[3][6] != 0) {
            c36.setText(Integer.toString(noteBoard[3][6]));
            setNote(c36);
        }
        if (selX == 3 && selY == 7) {
            c37.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 3 || selY == 7) {
            c37.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c37.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[3][7] != 0 && showBoard[3][7] == dataBoard[3][7]) {
            c37.setText(Integer.toString(showBoard[3][7]));
            setNormal(c37);
        }
        if (showBoard[3][7] != 0 && showBoard[3][7] != dataBoard[3][7]) {
            c37.setText(Integer.toString(showBoard[3][7]));
            setFalse(c37);
        }
        if (showBoard[3][7] == 0 && noteBoard[3][7] == 0) {
            setBlank(c37);
        }
        if (showBoard[3][7] == 0 && noteBoard[3][7] != 0) {
            c37.setText(Integer.toString(noteBoard[3][7]));
            setNote(c37);
        }
        if (selX == 3 && selY == 8) {
            c38.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 3 || selY == 8) {
            c38.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c38.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[3][8] != 0 && showBoard[3][8] == dataBoard[3][8]) {
            c38.setText(Integer.toString(showBoard[3][8]));
            setNormal(c38);
        }
        if (showBoard[3][8] != 0 && showBoard[3][8] != dataBoard[3][8]) {
            c38.setText(Integer.toString(showBoard[3][8]));
            setFalse(c38);
        }
        if (showBoard[3][8] == 0 && noteBoard[3][8] == 0) {
            setBlank(c38);
        }
        if (showBoard[3][8] == 0 && noteBoard[3][8] != 0) {
            c38.setText(Integer.toString(noteBoard[3][8]));
            setNote(c38);
        }
        if (selX == 4 && selY == 0) {
            c40.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 4 || selY == 0) {
            c40.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c40.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[4][0] != 0 && showBoard[4][0] == dataBoard[4][0]) {
            c40.setText(Integer.toString(showBoard[4][0]));
            setNormal(c40);
        }
        if (showBoard[4][0] != 0 && showBoard[4][0] != dataBoard[4][0]) {
            c40.setText(Integer.toString(showBoard[4][0]));
            setFalse(c40);
        }
        if (showBoard[4][0] == 0 && noteBoard[4][0] == 0) {
            setBlank(c40);
        }
        if (showBoard[4][0] == 0 && noteBoard[4][0] != 0) {
            c40.setText(Integer.toString(noteBoard[4][0]));
            setNote(c40);
        }
        if (selX == 4 && selY == 1) {
            c41.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 4 || selY == 1) {
            c41.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c41.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[4][1] != 0 && showBoard[4][1] == dataBoard[4][1]) {
            c41.setText(Integer.toString(showBoard[4][1]));
            setNormal(c41);
        }
        if (showBoard[4][1] != 0 && showBoard[4][1] != dataBoard[4][1]) {
            c41.setText(Integer.toString(showBoard[4][1]));
            setFalse(c41);
        }
        if (showBoard[4][1] == 0 && noteBoard[4][1] == 0) {
            setBlank(c41);
        }
        if (showBoard[4][1] == 0 && noteBoard[4][1] != 0) {
            c41.setText(Integer.toString(noteBoard[4][1]));
            setNote(c41);
        }
        if (selX == 4 && selY == 2) {
            c42.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 4 || selY == 2) {
            c42.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c42.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[4][2] != 0 && showBoard[4][2] == dataBoard[4][2]) {
            c42.setText(Integer.toString(showBoard[4][2]));
            setNormal(c42);
        }
        if (showBoard[4][2] != 0 && showBoard[4][2] != dataBoard[4][2]) {
            c42.setText(Integer.toString(showBoard[4][2]));
            setFalse(c42);
        }
        if (showBoard[4][2] == 0 && noteBoard[4][2] == 0) {
            setBlank(c42);
        }
        if (showBoard[4][2] == 0 && noteBoard[4][2] != 0) {
            c42.setText(Integer.toString(noteBoard[4][2]));
            setNote(c42);
        }
        if (selX == 4 && selY == 3) {
            c43.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 4 || selY == 3) {
            c43.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c43.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[4][3] != 0 && showBoard[4][3] == dataBoard[4][3]) {
            c43.setText(Integer.toString(showBoard[4][3]));
            setNormal(c43);
        }
        if (showBoard[4][3] != 0 && showBoard[4][3] != dataBoard[4][3]) {
            c43.setText(Integer.toString(showBoard[4][3]));
            setFalse(c43);
        }
        if (showBoard[4][3] == 0 && noteBoard[4][3] == 0) {
            setBlank(c43);
        }
        if (showBoard[4][3] == 0 && noteBoard[4][3] != 0) {
            c43.setText(Integer.toString(noteBoard[4][3]));
            setNote(c43);
        }
        if (selX == 4 && selY == 4) {
            c44.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 4 || selY == 4) {
            c44.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c44.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[4][4] != 0 && showBoard[4][4] == dataBoard[4][4]) {
            c44.setText(Integer.toString(showBoard[4][4]));
            setNormal(c44);
        }
        if (showBoard[4][4] != 0 && showBoard[4][4] != dataBoard[4][4]) {
            c44.setText(Integer.toString(showBoard[4][4]));
            setFalse(c44);
        }
        if (showBoard[4][4] == 0 && noteBoard[4][4] == 0) {
            setBlank(c44);
        }
        if (showBoard[4][4] == 0 && noteBoard[4][4] != 0) {
            c44.setText(Integer.toString(noteBoard[4][4]));
            setNote(c44);
        }
        if (selX == 4 && selY == 5) {
            c45.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 4 || selY == 5) {
            c45.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c45.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[4][5] != 0 && showBoard[4][5] == dataBoard[4][5]) {
            c45.setText(Integer.toString(showBoard[4][5]));
            setNormal(c45);
        }
        if (showBoard[4][5] != 0 && showBoard[4][5] != dataBoard[4][5]) {
            c45.setText(Integer.toString(showBoard[4][5]));
            setFalse(c45);
        }
        if (showBoard[4][5] == 0 && noteBoard[4][5] == 0) {
            setBlank(c45);
        }
        if (showBoard[4][5] == 0 && noteBoard[4][5] != 0) {
            c45.setText(Integer.toString(noteBoard[4][5]));
            setNote(c45);
        }
        if (selX == 4 && selY == 6) {
            c46.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 4 || selY == 6) {
            c46.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c46.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[4][6] != 0 && showBoard[4][6] == dataBoard[4][6]) {
            c46.setText(Integer.toString(showBoard[4][6]));
            setNormal(c46);
        }
        if (showBoard[4][6] != 0 && showBoard[4][6] != dataBoard[4][6]) {
            c46.setText(Integer.toString(showBoard[4][6]));
            setFalse(c46);
        }
        if (showBoard[4][6] == 0 && noteBoard[4][6] == 0) {
            setBlank(c46);
        }
        if (showBoard[4][6] == 0 && noteBoard[4][6] != 0) {
            c46.setText(Integer.toString(noteBoard[4][6]));
            setNote(c46);
        }
        if (selX == 4 && selY == 7) {
            c47.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 4 || selY == 7) {
            c47.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c47.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[4][7] != 0 && showBoard[4][7] == dataBoard[4][7]) {
            c47.setText(Integer.toString(showBoard[4][7]));
            setNormal(c47);
        }
        if (showBoard[4][7] != 0 && showBoard[4][7] != dataBoard[4][7]) {
            c47.setText(Integer.toString(showBoard[4][7]));
            setFalse(c47);
        }
        if (showBoard[4][7] == 0 && noteBoard[4][7] == 0) {
            setBlank(c47);
        }
        if (showBoard[4][7] == 0 && noteBoard[4][7] != 0) {
            c47.setText(Integer.toString(noteBoard[4][7]));
            setNote(c47);
        }
        if (selX == 4 && selY == 8) {
            c48.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 4 || selY == 8) {
            c48.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c48.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[4][8] != 0 && showBoard[4][8] == dataBoard[4][8]) {
            c48.setText(Integer.toString(showBoard[4][8]));
            setNormal(c48);
        }
        if (showBoard[4][8] != 0 && showBoard[4][8] != dataBoard[4][8]) {
            c48.setText(Integer.toString(showBoard[4][8]));
            setFalse(c48);
        }
        if (showBoard[4][8] == 0 && noteBoard[4][8] == 0) {
            setBlank(c48);
        }
        if (showBoard[4][8] == 0 && noteBoard[4][8] != 0) {
            c48.setText(Integer.toString(noteBoard[4][8]));
            setNote(c48);
        }
        if (selX == 5 && selY == 0) {
            c50.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 5 || selY == 0) {
            c50.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c50.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[5][0] != 0 && showBoard[5][0] == dataBoard[5][0]) {
            c50.setText(Integer.toString(showBoard[5][0]));
            setNormal(c50);
        }
        if (showBoard[5][0] != 0 && showBoard[5][0] != dataBoard[5][0]) {
            c50.setText(Integer.toString(showBoard[5][0]));
            setFalse(c50);
        }
        if (showBoard[5][0] == 0 && noteBoard[5][0] == 0) {
            setBlank(c50);
        }
        if (showBoard[5][0] == 0 && noteBoard[5][0] != 0) {
            c50.setText(Integer.toString(noteBoard[5][0]));
            setNote(c50);
        }
        if (selX == 5 && selY == 1) {
            c51.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 5 || selY == 1) {
            c51.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c51.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[5][1] != 0 && showBoard[5][1] == dataBoard[5][1]) {
            c51.setText(Integer.toString(showBoard[5][1]));
            setNormal(c51);
        }
        if (showBoard[5][1] != 0 && showBoard[5][1] != dataBoard[5][1]) {
            c51.setText(Integer.toString(showBoard[5][1]));
            setFalse(c51);
        }
        if (showBoard[5][1] == 0 && noteBoard[5][1] == 0) {
            setBlank(c51);
        }
        if (showBoard[5][1] == 0 && noteBoard[5][1] != 0) {
            c51.setText(Integer.toString(noteBoard[5][1]));
            setNote(c51);
        }
        if (selX == 5 && selY == 2) {
            c52.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 5 || selY == 2) {
            c52.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c52.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[5][2] != 0 && showBoard[5][2] == dataBoard[5][2]) {
            c52.setText(Integer.toString(showBoard[5][2]));
            setNormal(c52);
        }
        if (showBoard[5][2] != 0 && showBoard[5][2] != dataBoard[5][2]) {
            c52.setText(Integer.toString(showBoard[5][2]));
            setFalse(c52);
        }
        if (showBoard[5][2] == 0 && noteBoard[5][2] == 0) {
            setBlank(c52);
        }
        if (showBoard[5][2] == 0 && noteBoard[5][2] != 0) {
            c52.setText(Integer.toString(noteBoard[5][2]));
            setNote(c52);
        }
        if (selX == 5 && selY == 3) {
            c53.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 5 || selY == 3) {
            c53.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c53.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[5][3] != 0 && showBoard[5][3] == dataBoard[5][3]) {
            c53.setText(Integer.toString(showBoard[5][3]));
            setNormal(c53);
        }
        if (showBoard[5][3] != 0 && showBoard[5][3] != dataBoard[5][3]) {
            c53.setText(Integer.toString(showBoard[5][3]));
            setFalse(c53);
        }
        if (showBoard[5][3] == 0 && noteBoard[5][3] == 0) {
            setBlank(c53);
        }
        if (showBoard[5][3] == 0 && noteBoard[5][3] != 0) {
            c53.setText(Integer.toString(noteBoard[5][3]));
            setNote(c53);
        }
        if (selX == 5 && selY == 4) {
            c54.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 5 || selY == 4) {
            c54.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c54.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[5][4] != 0 && showBoard[5][4] == dataBoard[5][4]) {
            c54.setText(Integer.toString(showBoard[5][4]));
            setNormal(c54);
        }
        if (showBoard[5][4] != 0 && showBoard[5][4] != dataBoard[5][4]) {
            c54.setText(Integer.toString(showBoard[5][4]));
            setFalse(c54);
        }
        if (showBoard[5][4] == 0 && noteBoard[5][4] == 0) {
            setBlank(c54);
        }
        if (showBoard[5][4] == 0 && noteBoard[5][4] != 0) {
            c54.setText(Integer.toString(noteBoard[5][4]));
            setNote(c54);
        }
        if (selX == 5 && selY == 5) {
            c55.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 5 || selY == 5) {
            c55.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c55.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[5][5] != 0 && showBoard[5][5] == dataBoard[5][5]) {
            c55.setText(Integer.toString(showBoard[5][5]));
            setNormal(c55);
        }
        if (showBoard[5][5] != 0 && showBoard[5][5] != dataBoard[5][5]) {
            c55.setText(Integer.toString(showBoard[5][5]));
            setFalse(c55);
        }
        if (showBoard[5][5] == 0 && noteBoard[5][5] == 0) {
            setBlank(c55);
        }
        if (showBoard[5][5] == 0 && noteBoard[5][5] != 0) {
            c55.setText(Integer.toString(noteBoard[5][5]));
            setNote(c55);
        }
        if (selX == 5 && selY == 6) {
            c56.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 5 || selY == 6) {
            c56.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c56.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[5][6] != 0 && showBoard[5][6] == dataBoard[5][6]) {
            c56.setText(Integer.toString(showBoard[5][6]));
            setNormal(c56);
        }
        if (showBoard[5][6] != 0 && showBoard[5][6] != dataBoard[5][6]) {
            c56.setText(Integer.toString(showBoard[5][6]));
            setFalse(c56);
        }
        if (showBoard[5][6] == 0 && noteBoard[5][6] == 0) {
            setBlank(c56);
        }
        if (showBoard[5][6] == 0 && noteBoard[5][6] != 0) {
            c56.setText(Integer.toString(noteBoard[5][6]));
            setNote(c56);
        }
        if (selX == 5 && selY == 7) {
            c57.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 5 || selY == 7) {
            c57.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c57.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[5][7] != 0 && showBoard[5][7] == dataBoard[5][7]) {
            c57.setText(Integer.toString(showBoard[5][7]));
            setNormal(c57);
        }
        if (showBoard[5][7] != 0 && showBoard[5][7] != dataBoard[5][7]) {
            c57.setText(Integer.toString(showBoard[5][7]));
            setFalse(c57);
        }
        if (showBoard[5][7] == 0 && noteBoard[5][7] == 0) {
            setBlank(c57);
        }
        if (showBoard[5][7] == 0 && noteBoard[5][7] != 0) {
            c57.setText(Integer.toString(noteBoard[5][7]));
            setNote(c57);
        }
        if (selX == 5 && selY == 8) {
            c58.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 5 || selY == 8) {
            c58.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c58.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[5][8] != 0 && showBoard[5][8] == dataBoard[5][8]) {
            c58.setText(Integer.toString(showBoard[5][8]));
            setNormal(c58);
        }
        if (showBoard[5][8] != 0 && showBoard[5][8] != dataBoard[5][8]) {
            c58.setText(Integer.toString(showBoard[5][8]));
            setFalse(c58);
        }
        if (showBoard[5][8] == 0 && noteBoard[5][8] == 0) {
            setBlank(c58);
        }
        if (showBoard[5][8] == 0 && noteBoard[5][8] != 0) {
            c58.setText(Integer.toString(noteBoard[5][8]));
            setNote(c58);
        }
        if (selX == 6 && selY == 0) {
            c60.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 6 || selY == 0) {
            c60.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c60.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[6][0] != 0 && showBoard[6][0] == dataBoard[6][0]) {
            c60.setText(Integer.toString(showBoard[6][0]));
            setNormal(c60);
        }
        if (showBoard[6][0] != 0 && showBoard[6][0] != dataBoard[6][0]) {
            c60.setText(Integer.toString(showBoard[6][0]));
            setFalse(c60);
        }
        if (showBoard[6][0] == 0 && noteBoard[6][0] == 0) {
            setBlank(c60);
        }
        if (showBoard[6][0] == 0 && noteBoard[6][0] != 0) {
            c60.setText(Integer.toString(noteBoard[6][0]));
            setNote(c60);
        }
        if (selX == 6 && selY == 1) {
            c61.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 6 || selY == 1) {
            c61.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c61.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[6][1] != 0 && showBoard[6][1] == dataBoard[6][1]) {
            c61.setText(Integer.toString(showBoard[6][1]));
            setNormal(c61);
        }
        if (showBoard[6][1] != 0 && showBoard[6][1] != dataBoard[6][1]) {
            c61.setText(Integer.toString(showBoard[6][1]));
            setFalse(c61);
        }
        if (showBoard[6][1] == 0 && noteBoard[6][1] == 0) {
            setBlank(c61);
        }
        if (showBoard[6][1] == 0 && noteBoard[6][1] != 0) {
            c61.setText(Integer.toString(noteBoard[6][1]));
            setNote(c61);
        }
        if (selX == 6 && selY == 2) {
            c62.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 6 || selY == 2) {
            c62.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c62.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[6][2] != 0 && showBoard[6][2] == dataBoard[6][2]) {
            c62.setText(Integer.toString(showBoard[6][2]));
            setNormal(c62);
        }
        if (showBoard[6][2] != 0 && showBoard[6][2] != dataBoard[6][2]) {
            c62.setText(Integer.toString(showBoard[6][2]));
            setFalse(c62);
        }
        if (showBoard[6][2] == 0 && noteBoard[6][2] == 0) {
            setBlank(c62);
        }
        if (showBoard[6][2] == 0 && noteBoard[6][2] != 0) {
            c62.setText(Integer.toString(noteBoard[6][2]));
            setNote(c62);
        }
        if (selX == 6 && selY == 3) {
            c63.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 6 || selY == 3) {
            c63.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c63.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[6][3] != 0 && showBoard[6][3] == dataBoard[6][3]) {
            c63.setText(Integer.toString(showBoard[6][3]));
            setNormal(c63);
        }
        if (showBoard[6][3] != 0 && showBoard[6][3] != dataBoard[6][3]) {
            c63.setText(Integer.toString(showBoard[6][3]));
            setFalse(c63);
        }
        if (showBoard[6][3] == 0 && noteBoard[6][3] == 0) {
            setBlank(c63);
        }
        if (showBoard[6][3] == 0 && noteBoard[6][3] != 0) {
            c63.setText(Integer.toString(noteBoard[6][3]));
            setNote(c63);
        }
        if (selX == 6 && selY == 4) {
            c64.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 6 || selY == 4) {
            c64.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c64.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[6][4] != 0 && showBoard[6][4] == dataBoard[6][4]) {
            c64.setText(Integer.toString(showBoard[6][4]));
            setNormal(c64);
        }
        if (showBoard[6][4] != 0 && showBoard[6][4] != dataBoard[6][4]) {
            c64.setText(Integer.toString(showBoard[6][4]));
            setFalse(c64);
        }
        if (showBoard[6][4] == 0 && noteBoard[6][4] == 0) {
            setBlank(c64);
        }
        if (showBoard[6][4] == 0 && noteBoard[6][4] != 0) {
            c64.setText(Integer.toString(noteBoard[6][4]));
            setNote(c64);
        }
        if (selX == 6 && selY == 5) {
            c65.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 6 || selY == 5) {
            c65.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c65.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[6][5] != 0 && showBoard[6][5] == dataBoard[6][5]) {
            c65.setText(Integer.toString(showBoard[6][5]));
            setNormal(c65);
        }
        if (showBoard[6][5] != 0 && showBoard[6][5] != dataBoard[6][5]) {
            c65.setText(Integer.toString(showBoard[6][5]));
            setFalse(c65);
        }
        if (showBoard[6][5] == 0 && noteBoard[6][5] == 0) {
            setBlank(c65);
        }
        if (showBoard[6][5] == 0 && noteBoard[6][5] != 0) {
            c65.setText(Integer.toString(noteBoard[6][5]));
            setNote(c65);
        }
        if (selX == 6 && selY == 6) {
            c66.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 6 || selY == 6) {
            c66.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c66.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[6][6] != 0 && showBoard[6][6] == dataBoard[6][6]) {
            c66.setText(Integer.toString(showBoard[6][6]));
            setNormal(c66);
        }
        if (showBoard[6][6] != 0 && showBoard[6][6] != dataBoard[6][6]) {
            c66.setText(Integer.toString(showBoard[6][6]));
            setFalse(c66);
        }
        if (showBoard[6][6] == 0 && noteBoard[6][6] == 0) {
            setBlank(c66);
        }
        if (showBoard[6][6] == 0 && noteBoard[6][6] != 0) {
            c66.setText(Integer.toString(noteBoard[6][6]));
            setNote(c66);
        }
        if (selX == 6 && selY == 7) {
            c67.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 6 || selY == 7) {
            c67.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c67.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[6][7] != 0 && showBoard[6][7] == dataBoard[6][7]) {
            c67.setText(Integer.toString(showBoard[6][7]));
            setNormal(c67);
        }
        if (showBoard[6][7] != 0 && showBoard[6][7] != dataBoard[6][7]) {
            c67.setText(Integer.toString(showBoard[6][7]));
            setFalse(c67);
        }
        if (showBoard[6][7] == 0 && noteBoard[6][7] == 0) {
            setBlank(c67);
        }
        if (showBoard[6][7] == 0 && noteBoard[6][7] != 0) {
            c67.setText(Integer.toString(noteBoard[6][7]));
            setNote(c67);
        }
        if (selX == 6 && selY == 8) {
            c68.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 6 || selY == 8) {
            c68.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c68.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[6][8] != 0 && showBoard[6][8] == dataBoard[6][8]) {
            c68.setText(Integer.toString(showBoard[6][8]));
            setNormal(c68);
        }
        if (showBoard[6][8] != 0 && showBoard[6][8] != dataBoard[6][8]) {
            c68.setText(Integer.toString(showBoard[6][8]));
            setFalse(c68);
        }
        if (showBoard[6][8] == 0 && noteBoard[6][8] == 0) {
            setBlank(c68);
        }
        if (showBoard[6][8] == 0 && noteBoard[6][8] != 0) {
            c68.setText(Integer.toString(noteBoard[6][8]));
            setNote(c68);
        }
        if (selX == 7 && selY == 0) {
            c70.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 7 || selY == 0) {
            c70.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c70.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[7][0] != 0 && showBoard[7][0] == dataBoard[7][0]) {
            c70.setText(Integer.toString(showBoard[7][0]));
            setNormal(c70);
        }
        if (showBoard[7][0] != 0 && showBoard[7][0] != dataBoard[7][0]) {
            c70.setText(Integer.toString(showBoard[7][0]));
            setFalse(c70);
        }
        if (showBoard[7][0] == 0 && noteBoard[7][0] == 0) {
            setBlank(c70);
        }
        if (showBoard[7][0] == 0 && noteBoard[7][0] != 0) {
            c70.setText(Integer.toString(noteBoard[7][0]));
            setNote(c70);
        }
        if (selX == 7 && selY == 1) {
            c71.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 7 || selY == 1) {
            c71.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c71.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[7][1] != 0 && showBoard[7][1] == dataBoard[7][1]) {
            c71.setText(Integer.toString(showBoard[7][1]));
            setNormal(c71);
        }
        if (showBoard[7][1] != 0 && showBoard[7][1] != dataBoard[7][1]) {
            c71.setText(Integer.toString(showBoard[7][1]));
            setFalse(c71);
        }
        if (showBoard[7][1] == 0 && noteBoard[7][1] == 0) {
            setBlank(c71);
        }
        if (showBoard[7][1] == 0 && noteBoard[7][1] != 0) {
            c71.setText(Integer.toString(noteBoard[7][1]));
            setNote(c71);
        }
        if (selX == 7 && selY == 2) {
            c72.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 7 || selY == 2) {
            c72.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c72.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[7][2] != 0 && showBoard[7][2] == dataBoard[7][2]) {
            c72.setText(Integer.toString(showBoard[7][2]));
            setNormal(c72);
        }
        if (showBoard[7][2] != 0 && showBoard[7][2] != dataBoard[7][2]) {
            c72.setText(Integer.toString(showBoard[7][2]));
            setFalse(c72);
        }
        if (showBoard[7][2] == 0 && noteBoard[7][2] == 0) {
            setBlank(c72);
        }
        if (showBoard[7][2] == 0 && noteBoard[7][2] != 0) {
            c72.setText(Integer.toString(noteBoard[7][2]));
            setNote(c72);
        }
        if (selX == 7 && selY == 3) {
            c73.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 7 || selY == 3) {
            c73.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c73.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[7][3] != 0 && showBoard[7][3] == dataBoard[7][3]) {
            c73.setText(Integer.toString(showBoard[7][3]));
            setNormal(c73);
        }
        if (showBoard[7][3] != 0 && showBoard[7][3] != dataBoard[7][3]) {
            c73.setText(Integer.toString(showBoard[7][3]));
            setFalse(c73);
        }
        if (showBoard[7][3] == 0 && noteBoard[7][3] == 0) {
            setBlank(c73);
        }
        if (showBoard[7][3] == 0 && noteBoard[7][3] != 0) {
            c73.setText(Integer.toString(noteBoard[7][3]));
            setNote(c73);
        }
        if (selX == 7 && selY == 4) {
            c74.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 7 || selY == 4) {
            c74.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c74.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[7][4] != 0 && showBoard[7][4] == dataBoard[7][4]) {
            c74.setText(Integer.toString(showBoard[7][4]));
            setNormal(c74);
        }
        if (showBoard[7][4] != 0 && showBoard[7][4] != dataBoard[7][4]) {
            c74.setText(Integer.toString(showBoard[7][4]));
            setFalse(c74);
        }
        if (showBoard[7][4] == 0 && noteBoard[7][4] == 0) {
            setBlank(c74);
        }
        if (showBoard[7][4] == 0 && noteBoard[7][4] != 0) {
            c74.setText(Integer.toString(noteBoard[7][4]));
            setNote(c74);
        }
        if (selX == 7 && selY == 5) {
            c75.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 7 || selY == 5) {
            c75.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c75.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[7][5] != 0 && showBoard[7][5] == dataBoard[7][5]) {
            c75.setText(Integer.toString(showBoard[7][5]));
            setNormal(c75);
        }
        if (showBoard[7][5] != 0 && showBoard[7][5] != dataBoard[7][5]) {
            c75.setText(Integer.toString(showBoard[7][5]));
            setFalse(c75);
        }
        if (showBoard[7][5] == 0 && noteBoard[7][5] == 0) {
            setBlank(c75);
        }
        if (showBoard[7][5] == 0 && noteBoard[7][5] != 0) {
            c75.setText(Integer.toString(noteBoard[7][5]));
            setNote(c75);
        }
        if (selX == 7 && selY == 6) {
            c76.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 7 || selY == 6) {
            c76.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c76.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[7][6] != 0 && showBoard[7][6] == dataBoard[7][6]) {
            c76.setText(Integer.toString(showBoard[7][6]));
            setNormal(c76);
        }
        if (showBoard[7][6] != 0 && showBoard[7][6] != dataBoard[7][6]) {
            c76.setText(Integer.toString(showBoard[7][6]));
            setFalse(c76);
        }
        if (showBoard[7][6] == 0 && noteBoard[7][6] == 0) {
            setBlank(c76);
        }
        if (showBoard[7][6] == 0 && noteBoard[7][6] != 0) {
            c76.setText(Integer.toString(noteBoard[7][6]));
            setNote(c76);
        }
        if (selX == 7 && selY == 7) {
            c77.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 7 || selY == 7) {
            c77.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c77.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[7][7] != 0 && showBoard[7][7] == dataBoard[7][7]) {
            c77.setText(Integer.toString(showBoard[7][7]));
            setNormal(c77);
        }
        if (showBoard[7][7] != 0 && showBoard[7][7] != dataBoard[7][7]) {
            c77.setText(Integer.toString(showBoard[7][7]));
            setFalse(c77);
        }
        if (showBoard[7][7] == 0 && noteBoard[7][7] == 0) {
            setBlank(c77);
        }
        if (showBoard[7][7] == 0 && noteBoard[7][7] != 0) {
            c77.setText(Integer.toString(noteBoard[7][7]));
            setNote(c77);
        }
        if (selX == 7 && selY == 8) {
            c78.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 7 || selY == 8) {
            c78.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c78.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[7][8] != 0 && showBoard[7][8] == dataBoard[7][8]) {
            c78.setText(Integer.toString(showBoard[7][8]));
            setNormal(c78);
        }
        if (showBoard[7][8] != 0 && showBoard[7][8] != dataBoard[7][8]) {
            c78.setText(Integer.toString(showBoard[7][8]));
            setFalse(c78);
        }
        if (showBoard[7][8] == 0 && noteBoard[7][8] == 0) {
            setBlank(c78);
        }
        if (showBoard[7][8] == 0 && noteBoard[7][8] != 0) {
            c78.setText(Integer.toString(noteBoard[7][8]));
            setNote(c78);
        }
        if (selX == 8 && selY == 0) {
            c80.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 8 || selY == 0) {
            c80.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c80.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[8][0] != 0 && showBoard[8][0] == dataBoard[8][0]) {
            c80.setText(Integer.toString(showBoard[8][0]));
            setNormal(c80);
        }
        if (showBoard[8][0] != 0 && showBoard[8][0] != dataBoard[8][0]) {
            c80.setText(Integer.toString(showBoard[8][0]));
            setFalse(c80);
        }
        if (showBoard[8][0] == 0 && noteBoard[8][0] == 0) {
            setBlank(c80);
        }
        if (showBoard[8][0] == 0 && noteBoard[8][0] != 0) {
            c80.setText(Integer.toString(noteBoard[8][0]));
            setNote(c80);
        }
        if (selX == 8 && selY == 1) {
            c81.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 8 || selY == 1) {
            c81.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c81.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[8][1] != 0 && showBoard[8][1] == dataBoard[8][1]) {
            c81.setText(Integer.toString(showBoard[8][1]));
            setNormal(c81);
        }
        if (showBoard[8][1] != 0 && showBoard[8][1] != dataBoard[8][1]) {
            c81.setText(Integer.toString(showBoard[8][1]));
            setFalse(c81);
        }
        if (showBoard[8][1] == 0 && noteBoard[8][1] == 0) {
            setBlank(c81);
        }
        if (showBoard[8][1] == 0 && noteBoard[8][1] != 0) {
            c81.setText(Integer.toString(noteBoard[8][1]));
            setNote(c81);
        }
        if (selX == 8 && selY == 2) {
            c82.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 8 || selY == 2) {
            c82.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c82.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[8][2] != 0 && showBoard[8][2] == dataBoard[8][2]) {
            c82.setText(Integer.toString(showBoard[8][2]));
            setNormal(c82);
        }
        if (showBoard[8][2] != 0 && showBoard[8][2] != dataBoard[8][2]) {
            c82.setText(Integer.toString(showBoard[8][2]));
            setFalse(c82);
        }
        if (showBoard[8][2] == 0 && noteBoard[8][2] == 0) {
            setBlank(c82);
        }
        if (showBoard[8][2] == 0 && noteBoard[8][2] != 0) {
            c82.setText(Integer.toString(noteBoard[8][2]));
            setNote(c82);
        }
        if (selX == 8 && selY == 3) {
            c83.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 8 || selY == 3) {
            c83.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c83.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[8][3] != 0 && showBoard[8][3] == dataBoard[8][3]) {
            c83.setText(Integer.toString(showBoard[8][3]));
            setNormal(c83);
        }
        if (showBoard[8][3] != 0 && showBoard[8][3] != dataBoard[8][3]) {
            c83.setText(Integer.toString(showBoard[8][3]));
            setFalse(c83);
        }
        if (showBoard[8][3] == 0 && noteBoard[8][3] == 0) {
            setBlank(c83);
        }
        if (showBoard[8][3] == 0 && noteBoard[8][3] != 0) {
            c83.setText(Integer.toString(noteBoard[8][3]));
            setNote(c83);
        }
        if (selX == 8 && selY == 4) {
            c84.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 8 || selY == 4) {
            c84.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c84.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[8][4] != 0 && showBoard[8][4] == dataBoard[8][4]) {
            c84.setText(Integer.toString(showBoard[8][4]));
            setNormal(c84);
        }
        if (showBoard[8][4] != 0 && showBoard[8][4] != dataBoard[8][4]) {
            c84.setText(Integer.toString(showBoard[8][4]));
            setFalse(c84);
        }
        if (showBoard[8][4] == 0 && noteBoard[8][4] == 0) {
            setBlank(c84);
        }
        if (showBoard[8][4] == 0 && noteBoard[8][4] != 0) {
            c84.setText(Integer.toString(noteBoard[8][4]));
            setNote(c84);
        }
        if (selX == 8 && selY == 5) {
            c85.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 8 || selY == 5) {
            c85.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c85.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[8][5] != 0 && showBoard[8][5] == dataBoard[8][5]) {
            c85.setText(Integer.toString(showBoard[8][5]));
            setNormal(c85);
        }
        if (showBoard[8][5] != 0 && showBoard[8][5] != dataBoard[8][5]) {
            c85.setText(Integer.toString(showBoard[8][5]));
            setFalse(c85);
        }
        if (showBoard[8][5] == 0 && noteBoard[8][5] == 0) {
            setBlank(c85);
        }
        if (showBoard[8][5] == 0 && noteBoard[8][5] != 0) {
            c85.setText(Integer.toString(noteBoard[8][5]));
            setNote(c85);
        }
        if (selX == 8 && selY == 6) {
            c86.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 8 || selY == 6) {
            c86.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c86.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[8][6] != 0 && showBoard[8][6] == dataBoard[8][6]) {
            c86.setText(Integer.toString(showBoard[8][6]));
            setNormal(c86);
        }
        if (showBoard[8][6] != 0 && showBoard[8][6] != dataBoard[8][6]) {
            c86.setText(Integer.toString(showBoard[8][6]));
            setFalse(c86);
        }
        if (showBoard[8][6] == 0 && noteBoard[8][6] == 0) {
            setBlank(c86);
        }
        if (showBoard[8][6] == 0 && noteBoard[8][6] != 0) {
            c86.setText(Integer.toString(noteBoard[8][6]));
            setNote(c86);
        }
        if (selX == 8 && selY == 7) {
            c87.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 8 || selY == 7) {
            c87.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c87.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[8][7] != 0 && showBoard[8][7] == dataBoard[8][7]) {
            c87.setText(Integer.toString(showBoard[8][7]));
            setNormal(c87);
        }
        if (showBoard[8][7] != 0 && showBoard[8][7] != dataBoard[8][7]) {
            c87.setText(Integer.toString(showBoard[8][7]));
            setFalse(c87);
        }
        if (showBoard[8][7] == 0 && noteBoard[8][7] == 0) {
            setBlank(c87);
        }
        if (showBoard[8][7] == 0 && noteBoard[8][7] != 0) {
            c87.setText(Integer.toString(noteBoard[8][7]));
            setNote(c87);
        }
        if (selX == 8 && selY == 8) {
            c88.setBackground(getResources().getDrawable(R.drawable.selcell));
        } else if (selX == 8 || selY == 8) {
            c88.setBackground(getResources().getDrawable(R.drawable.selcol));
        } else {
            c88.setBackground(getResources().getDrawable(R.drawable.sudokucell));
        }
        if (showBoard[8][8] != 0 && showBoard[8][8] == dataBoard[8][8]) {
            c88.setText(Integer.toString(showBoard[8][8]));
            setNormal(c88);
        }
        if (showBoard[8][8] != 0 && showBoard[8][8] != dataBoard[8][8]) {
            c88.setText(Integer.toString(showBoard[8][8]));
            setFalse(c88);
        }
        if (showBoard[8][8] == 0 && noteBoard[8][8] == 0) {
            setBlank(c88);
        }
        if (showBoard[8][8] == 0 && noteBoard[8][8] != 0) {
            c88.setText(Integer.toString(noteBoard[8][8]));
            setNote(c88);
        }
    }

    private void setNormal(Button c00) {
        c00.setTextColor(Color.parseColor("#433D3F"));
    }
    private void setFalse(Button c00) {
        c00.setTextColor(Color.parseColor("#FF825A"));
    }
    private void setNote(Button c00) {
        c00.setTextColor(Color.parseColor("#DCDE29"));
    }
    private void setBlank(Button c00) {
        c00.setTextColor(Color.parseColor("#433D3F"));
        c00.setText(" ");
    }

    @Override
    public void onClick(View view) {
        if (view == bt_hint) {
            if (selX < 0 || selX > 8 || selY < 0 || selY > 8) {
                selX = 0;
                selY = 0;
            }
            if (hint > 0) {
                //Toast.makeText(SingleMode.this, Boolean.toString(showBoard[selX][selY] == dataBoard[selX][selY]) , Toast.LENGTH_SHORT).show();
                while (showBoard[selX][selY] == dataBoard[selX][selY]) {
                    selX = rand.nextInt(9);
                    selY = rand.nextInt(9);
                    //Toast.makeText(SingleMode.this, Integer.toString(selX) + " " + Integer.toString(selY)+ " " + Integer.toString(showBoard[selX][selY]), Toast.LENGTH_SHORT).show();
                }
                if (showBoard[selX][selY] != dataBoard[selX][selY]) {
                    showBoard[selX][selY] = dataBoard[selX][selY];
                    hint = hint - 1;
                    bt_hint.setText(Integer.toString(hint));
                }
                if(hint == 0){
                    bt_hint.getBackground().setAlpha(44);
                    bt_hint.setTextColor(Color.parseColor("#FFFFFF"));
                }
                countPoint();
            }
        }
        if (view == bt_clear) {
            if (showBoard[selX][selY] != dataBoard[selX][selY]) {
                showBoard[selX][selY] = 0;
            }
            if (noteBoard[selX][selY] != 0) {
                noteBoard[selX][selY] = 0;
            }
        }
        if (view == bt_note) {
            if (noteOn == 0) {
                noteOn = 1;
                bt_note.setBackground(getResources().getDrawable(R.drawable.playnoteon));
            } else {
                noteOn = 0;
                bt_note.setBackground(getResources().getDrawable(R.drawable.playnote));
            }
        }
        if (view == bt_9) {
            if (selX >= 0 && selX <= 8 && selY >= 0 && selY <= 8) {
                if (noteOn == 1) {
                    noteBoard[selX][selY] = 9;
                } else if (noteOn == 0 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 9;
                }
                if (noteOn == 1 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 0;
                }
            }
            countPoint();
        }
        if (view == bt_8) {
            if (selX >= 0 && selX <= 8 && selY >= 0 && selY <= 8) {
                if (noteOn == 1) {
                    noteBoard[selX][selY] = 8;
                } else if (noteOn == 0 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 8;
                }
                if (noteOn == 1 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 0;
                }
            }
            countPoint();
        }
        if (view == bt_7) {
            if (selX >= 0 && selX <= 8 && selY >= 0 && selY <= 8) {
                if (noteOn == 1) {
                    noteBoard[selX][selY] = 7;
                } else if (noteOn == 0 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 7;
                }
                if (noteOn == 1 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 0;
                }
            }
            countPoint();
        }
        if (view == bt_6) {
            if (selX >= 0 && selX <= 8 && selY >= 0 && selY <= 8) {
                if (noteOn == 1) {
                    noteBoard[selX][selY] = 6;
                } else if (noteOn == 0 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 6;
                }
                if (noteOn == 1 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 0;
                }
            }
            countPoint();
        }
        if (view == bt_5) {
            if (selX >= 0 && selX <= 8 && selY >= 0 && selY <= 8) {
                if (noteOn == 1) {
                    noteBoard[selX][selY] = 5;
                } else if (noteOn == 0 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 5;
                }
                if (noteOn == 1 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 0;
                }
            }
            countPoint();
        }
        if (view == bt_4) {
            if (selX >= 0 && selX <= 8 && selY >= 0 && selY <= 8) {
                if (noteOn == 1) {
                    noteBoard[selX][selY] = 4;
                } else if (noteOn == 0 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 4;
                }
                if (noteOn == 1 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 0;
                }
            }
            countPoint();
        }
        if (view == bt_3) {
            if (selX >= 0 && selX <= 8 && selY >= 0 && selY <= 8) {
                if (noteOn == 1) {
                    noteBoard[selX][selY] = 3;
                } else if (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY]) {
                    showBoard[selX][selY] = 3;
                }
                if (noteOn == 1 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 0;
                }
            }
            countPoint();
        }
        if (view == bt_2) {
            if (selX >= 0 && selX <= 8 && selY >= 0 && selY <= 8) {
                if (noteOn == 1) {
                    noteBoard[selX][selY] = 2;
                } else if (noteOn == 0 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 2;
                }
                if (noteOn == 1 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 0;
                }
            }
            countPoint();
        }
        if (view == bt_1) {
            if (selX >= 0 && selX <= 8 && selY >= 0 && selY <= 8) {
                if (noteOn == 1) {
                    noteBoard[selX][selY] = 1;
                } else if (noteOn == 0 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 1;
                }
                if (noteOn == 1 && (showBoard[selX][selY] == 0 || showBoard[selX][selY] != dataBoard[selX][selY])) {
                    showBoard[selX][selY] = 0;
                }
            }
            countPoint();
        }
        if (view == c00) {
            selX = 0;
            selY = 0;
        }
        if (view == c01) {
            selX = 0;
            selY = 1;
        }
        if (view == c02) {
            selX = 0;
            selY = 2;
        }
        if (view == c03) {
            selX = 0;
            selY = 3;
        }
        if (view == c04) {
            selX = 0;
            selY = 4;
        }
        if (view == c05) {
            selX = 0;
            selY = 5;
        }
        if (view == c06) {
            selX = 0;
            selY = 6;
        }
        if (view == c07) {
            selX = 0;
            selY = 7;
        }
        if (view == c08) {
            selX = 0;
            selY = 8;
        }
        if (view == c10) {
            selX = 1;
            selY = 0;
        }
        if (view == c11) {
            selX = 1;
            selY = 1;
        }
        if (view == c12) {
            selX = 1;
            selY = 2;
        }
        if (view == c13) {
            selX = 1;
            selY = 3;
        }
        if (view == c14) {
            selX = 1;
            selY = 4;
        }
        if (view == c15) {
            selX = 1;
            selY = 5;
        }
        if (view == c16) {
            selX = 1;
            selY = 6;
        }
        if (view == c17) {
            selX = 1;
            selY = 7;
        }
        if (view == c18) {
            selX = 1;
            selY = 8;
        }
        if (view == c20) {
            selX = 2;
            selY = 0;
        }
        if (view == c21) {
            selX = 2;
            selY = 1;
        }
        if (view == c22) {
            selX = 2;
            selY = 2;
        }
        if (view == c23) {
            selX = 2;
            selY = 3;
        }
        if (view == c24) {
            selX = 2;
            selY = 4;
        }
        if (view == c25) {
            selX = 2;
            selY = 5;
        }
        if (view == c26) {
            selX = 2;
            selY = 6;
        }
        if (view == c27) {
            selX = 2;
            selY = 7;
        }
        if (view == c28) {
            selX = 2;
            selY = 8;
        }
        if (view == c30) {
            selX = 3;
            selY = 0;
        }
        if (view == c31) {
            selX = 3;
            selY = 1;
        }
        if (view == c32) {
            selX = 3;
            selY = 2;
        }
        if (view == c33) {
            selX = 3;
            selY = 3;
        }
        if (view == c34) {
            selX = 3;
            selY = 4;
        }
        if (view == c35) {
            selX = 3;
            selY = 5;
        }
        if (view == c36) {
            selX = 3;
            selY = 6;
        }
        if (view == c37) {
            selX = 3;
            selY = 7;
        }
        if (view == c38) {
            selX = 3;
            selY = 8;
        }
        if (view == c40) {
            selX = 4;
            selY = 0;
        }
        if (view == c41) {
            selX = 4;
            selY = 1;
        }
        if (view == c42) {
            selX = 4;
            selY = 2;
        }
        if (view == c43) {
            selX = 4;
            selY = 3;
        }
        if (view == c44) {
            selX = 4;
            selY = 4;
        }
        if (view == c45) {
            selX = 4;
            selY = 5;
        }
        if (view == c46) {
            selX = 4;
            selY = 6;
        }
        if (view == c47) {
            selX = 4;
            selY = 7;
        }
        if (view == c48) {
            selX = 4;
            selY = 8;
        }
        if (view == c50) {
            selX = 5;
            selY = 0;
        }
        if (view == c51) {
            selX = 5;
            selY = 1;
        }
        if (view == c52) {
            selX = 5;
            selY = 2;
        }
        if (view == c53) {
            selX = 5;
            selY = 3;
        }
        if (view == c54) {
            selX = 5;
            selY = 4;
        }
        if (view == c55) {
            selX = 5;
            selY = 5;
        }
        if (view == c56) {
            selX = 5;
            selY = 6;
        }
        if (view == c57) {
            selX = 5;
            selY = 7;
        }
        if (view == c58) {
            selX = 5;
            selY = 8;
        }
        if (view == c60) {
            selX = 6;
            selY = 0;
        }
        if (view == c61) {
            selX = 6;
            selY = 1;
        }
        if (view == c62) {
            selX = 6;
            selY = 2;
        }
        if (view == c63) {
            selX = 6;
            selY = 3;
        }
        if (view == c64) {
            selX = 6;
            selY = 4;
        }
        if (view == c65) {
            selX = 6;
            selY = 5;
        }
        if (view == c66) {
            selX = 6;
            selY = 6;
        }
        if (view == c67) {
            selX = 6;
            selY = 7;
        }
        if (view == c68) {
            selX = 6;
            selY = 8;
        }
        if (view == c70) {
            selX = 7;
            selY = 0;
        }
        if (view == c71) {
            selX = 7;
            selY = 1;
        }
        if (view == c72) {
            selX = 7;
            selY = 2;
        }
        if (view == c73) {
            selX = 7;
            selY = 3;
        }
        if (view == c74) {
            selX = 7;
            selY = 4;
        }
        if (view == c75) {
            selX = 7;
            selY = 5;
        }
        if (view == c76) {
            selX = 7;
            selY = 6;
        }
        if (view == c77) {
            selX = 7;
            selY = 7;
        }
        if (view == c78) {
            selX = 7;
            selY = 8;
        }
        if (view == c80) {
            selX = 8;
            selY = 0;
        }
        if (view == c81) {
            selX = 8;
            selY = 1;
        }
        if (view == c82) {
            selX = 8;
            selY = 2;
        }
        if (view == c83) {
            selX = 8;
            selY = 3;
        }
        if (view == c84) {
            selX = 8;
            selY = 4;
        }
        if (view == c85) {
            selX = 8;
            selY = 5;
        }
        if (view == c86) {
            selX = 8;
            selY = 6;
        }
        if (view == c87) {
            selX = 8;
            selY = 7;
        }
        if (view == c88) {
            selX = 8;
            selY = 8;
        }
        showAllBoard();
    }

    private void countPoint() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sudoku-80cb0-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("room");
        if (showBoard[selX][selY] != 0 && showBoard[selX][selY] != dataBoard[selX][selY]) {
            //tang so loi
            fault = fault + 1;
            tv_fault.setText(Integer.toString(fault) + "/"+ Integer.toString(maxFault));
            if(fault>maxFault){
                //lose game -> do something
                Toast.makeText(MultiplayerMode.this, "You lose!", Toast.LENGTH_SHORT).show();
                myRef.child(room).child(role).child("point").setValue(-1);
                finish();
            }
        }
        selfPoint = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (dataBoard[i][j] == showBoard[i][j]) selfPoint = selfPoint + 1;
            }
        }
        selfPoint = nRemove + selfPoint - 81;
        myRef.child(room).child(role).child("point").setValue(selfPoint);
        tv_p1.setText(Integer.toString(selfPoint));
        if (selfPoint == nRemove) {
            //finish game -> do something
            Toast.makeText(MultiplayerMode.this, "You win!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void startTimming(){
        CountDownTimer countDownTimer = new CountDownTimer(timeCounting, 1000) {
            @Override
            public void onTick(long l) {
                int min = timeCounting / 60000;
                int sec = (timeCounting % 60000)/1000;
                String timeSet="";
                if(min<10) timeSet="0";
                timeSet+=Integer.toString(min)+":";
                if(sec<10) timeSet="0";
                timeSet+=Integer.toString(sec);
                tv_timing.setText(timeSet);
                timeCounting=timeCounting-1000;
                if(min%3==0) { //update time every 3mins
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://sudoku-80cb0-default-rtdb.asia-southeast1.firebasedatabase.app");
                    DatabaseReference myRef = database.getReference("room");
                    myRef.child(room).child("time").setValue(min);
                }
//                if(role.equals("s1")){
//                    if(timeCounting%10000==0){
//                        myRef.child(room).child(role).child("state").setValue(selfState+1);
//                        if(compState<0){
//
//                        }
//                        myRef.child(room).child(comprole).child("state").setValue(compState-1);
//
//                    }
//                }
//                if(role.equals("s2")){
//                    if(timeCounting%10000==5000){
//
//                    }
//                }
            }
            @Override
            public void onFinish() {
                endGame();
            }
        }.start();
    }

    private void endGame() {
        if(selfPoint>compPoint){
            //win
            finish();
        }
        if(selfPoint<compPoint){
            //lose
            finish();
        }
        if(selfPoint==compPoint){
            //tie
            finish();
        }
    }
}