package com.example.projectse;

/* Java program for Sudoku generator  */
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Sudoku {
    int[][] shwBoard;
    int[][] solBoard;
    int difVal;
    Random rd = new Random();

    Sudoku(int N) {
        this.difVal = N;
        shwBoard = new int[9][9];
        solBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                shwBoard[i][j] = 0;
            }
        }
        create3Box();
        fillAll(0, 3);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                solBoard[i][j] = shwBoard[i][j];
            }
        }
        removeCell();
    }

    Sudoku(String room, String dif){
        if(dif.equals("Easy")) this.difVal= 55;
        else if(dif.equals("Medium")) this.difVal= 65;
        else if(dif.equals("Hard")) this.difVal= 75;
        else this.difVal = 5;
        shwBoard = new int[9][9];
        solBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                shwBoard[i][j] = 0;
            }
        }
        create3Box();
        fillAll(0, 3);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                solBoard[i][j] = shwBoard[i][j];
            }
        }
        removeCell();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sudoku-80cb0-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("room");
        myRef.child(room).child("s1").child("point").setValue(-1);
        myRef.child(room).child("s2").child("point").setValue(-1);
        for(int i=0;i<9;i++){
            for(int j=0; j<9; j++){
                myRef.child(room).child("data").child("data"+Integer.toString(i)+Integer.toString(j)).setValue(solBoard[i][j]);
                myRef.child(room).child("show").child("data"+Integer.toString(i)+Integer.toString(j)).setValue(shwBoard[i][j]);
            }
        }
    }

    private void removeCell() {
        int i, j;
        for (int nore = 0; nore < difVal; nore++) {
            i = rd.nextInt(9);
            j = rd.nextInt(9);
            if (shwBoard[i][j] != 0) {
                shwBoard[i][j] = 0;
            } else {
                nore = nore - 1;
            }
        }
    }

    public void create3Box() {
        int rannum;
        for (int digBox = 0; digBox < 9; digBox = digBox + 3) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rannum = rd.nextInt(9) + 1;
                    while (checkBox(rannum, digBox, digBox) != true) {
                        rannum = rd.nextInt(9) + 1;
                    }
                    shwBoard[digBox + i][digBox + j] = rannum;
                }
            }
        }
    }

    public boolean checkSafe(int val, int x, int y) {
        return (checkBox(val, x - x % 3, y - y % 3)
                && checkRow(val, x)
                && checkCol(val, y));
    }

    public boolean checkBox(int val, int x, int y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (shwBoard[x + i][y + j] == val) return false;
            }
        }
        return true;
    }
    public boolean checkRow(int num, int x) {
        for (int j = 0; j < 9; j++) {
            if (shwBoard[x][j] == num) return false;
        }
        return true;
    }

    public boolean checkCol(int num, int y) {
        for (int i = 0; i < 9; i++) {
            if (shwBoard[i][y] == num) return false;
        }
        return true;
    }

    boolean fillAll(int x, int y) {
        if (y >= 9) {
            if (x < 8) {
                x = x + 1;
                y = 0;
            } else {
                return true;
            }
        }

        if (x < 3) {
            if (y < 3)
                y = 3;
        } else if (x < 6) {
            if (y == (int) (x / 3) * 3)
                y = y + 3;
        } else {
            if (y == 6) {
                x = x + 1;
                y = 0;
                if (x >= 9)
                    return true;
            }
        }

        for (int val = 1; val <= 9; val++) {
            if (checkSafe(val, x, y)) {
                shwBoard[x][y] = val;
                if (fillAll(x, y + 1)) return true;
                shwBoard[x][y] = 0;
            }
        }
        return false;
    }

    public int[][] getShwBoard() {
        return shwBoard;
    }

    public int[][] getSolBoard() {
        return solBoard;
    }
}