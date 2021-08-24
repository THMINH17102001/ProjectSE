package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SingleMode extends AppCompatActivity {
    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;

    private Button solveBTN;
    private Button startBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard=findViewById(R.id.SudokuBoard);
        gameBoardSolver=gameBoard.getSolver();

        solveBTN = findViewById(R.id.solveButton);
        startBTN = findViewById(R.id.startButton);
    }

    public void BTNOnePress(View view)
    {
        gameBoardSolver.setNumberPos(1);
        gameBoard.invalidate();
    }
    public void BTNTwoPress(View view)
    {
        gameBoardSolver.setNumberPos(2);
        gameBoard.invalidate();
    }
    public void BTNThreePress(View view)
    {
        gameBoardSolver.setNumberPos(3);
        gameBoard.invalidate();
    }
    public void BTNFourPress(View view)
    {
        gameBoardSolver.setNumberPos(4);
        gameBoard.invalidate();
    }
    public void BTNFivePress(View view)
    {
        gameBoardSolver.setNumberPos(5);
        gameBoard.invalidate();
    }
    public void BTNSixPress(View view)
    {
        gameBoardSolver.setNumberPos(6);
        gameBoard.invalidate();
    }
    public void BTNSevenPress(View view)
    {
        gameBoardSolver.setNumberPos(7);
        gameBoard.invalidate();
    }
    public void BTNEightPress(View view)
    {
        gameBoardSolver.setNumberPos(8);
        gameBoard.invalidate();
    }
    public void BTNNinePress(View view)
    {
        gameBoardSolver.setNumberPos(9);
        gameBoard.invalidate();
    }

    public void solve(View view)
    {
        if(solveBTN.getText().toString().equals(getString(R.string.solve)))
        {
            solveBTN.setText(getString(R.string.clear));
            gameBoardSolver.getEmptyBoxIndexes();
            SolveBoardThread solveBoardThread = new SolveBoardThread();

            new Thread(solveBoardThread).start();
            gameBoard.invalidate();
        }
        else
        {
            solveBTN.setText(getString(R.string.solve));
            gameBoardSolver.resetBoard();
            gameBoard.invalidate();
        }
    }
    public void GameStart(View view)
    {
        if(startBTN.getText().toString().equals(getString(R.string.start)))
        {
            startBTN.setText(getString(R.string.clear));
            ///CreateGameThread createGameThread = new CreateGameThread();
            /// new Thread(createGameThread).start();
            gameBoardSolver.createGameBoard(gameBoard);
            gameBoard.invalidate();

        }
        else
        {
            startBTN.setText(getString(R.string.start));
            gameBoardSolver.resetBoard();
            gameBoard.invalidate();
        }
    }
    class SolveBoardThread implements  Runnable{
        @Override
        public void run()
        {
            gameBoardSolver.solve(gameBoard);
        }
    }
}