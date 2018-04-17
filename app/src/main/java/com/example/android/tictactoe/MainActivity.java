package com.example.android.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Player is assigned X symbol
     * Later play will be able to choose between X and O symbol
     * Keep tracks of wins from both sides
     * Calling checkForWin, playerWins and machineWins(later machine will develop own choosing) methods
     **/

    RadioButton chooseX = findViewById(R.id.choose_X);
    private Button[][] btns = new Button[3][3];
    private boolean playerTurn = true;
    private int roundCount = 0;
    private int myScorePoints = 0;
    private int friendScorePoints = 0;
    private TextView textViewMyScore;
    private TextView textViewFriendScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     // Linking xml attributes with java initialization for scores
        textViewMyScore = findViewById(R.id.text_view_myscore);
        textViewFriendScore = findViewById(R.id.text_view_pcscore);

     //Linking buttons with two dimensional array i and j
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                btns[i][j] = findViewById(resID);
                btns[i][j].setOnClickListener(this);
            }
        }

     /**Linking Reset button xml with java
         calling the reset method too**/
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (chooseX.isChecked()) {
            ((Button) v).setText("X");
            Toast.makeText(this, "Friends Turn", Toast.LENGTH_SHORT).show();
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (playerTurn) {
                myWins();
            } else {
                friendWins();
            }
        } else if (roundCount == 9) {
            draw();
            }
         else {
            playerTurn = !playerTurn;
        }
    }

    // Checks if a player has matched a symbol either(X or O) vertically, horizontally or diagonally
    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = btns[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        return field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("");

    }

    /** Tracks player scores and toast when player wins
     * Calls updatePointsText and resetBoard method
     */
    private void myWins() {
        myScorePoints++;
        Toast.makeText(this, "I win!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    /**Tracks machine scores and toast when machine wins
     * Calls updatePointsText and resetBoard method
     */
    private void friendWins() {
        friendScorePoints++;
        Toast.makeText(this, "Friend wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    // Toast when there is a draw
    private void draw() {
        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
    }

    // Updates the scores of each win
    private void updatePointsText() {
        textViewMyScore.setText("My Score: " + myScorePoints);
        textViewFriendScore.setText("Friend's Score: " + friendScorePoints);
    }

    // Restarts the game when a winner is found or when there is a draw
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btns[i][j].setText("");
            }
        }
        roundCount = 0;
        playerTurn = true;
    }

    // Resets the scores to zero for both plays, begin game afresh
    private void reset() {
        myScorePoints = 0;
        friendScorePoints = 0;
        updatePointsText();
        resetBoard();
    }
}