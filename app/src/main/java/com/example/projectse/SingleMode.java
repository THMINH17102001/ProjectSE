package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SingleMode extends AppCompatActivity {
    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_mode);
    }
}