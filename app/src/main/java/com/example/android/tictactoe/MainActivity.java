package com.example.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] btns = new Button[5][5];

    private boolean playerTurn = true;

    private boolean gridSize = false;
    private int roundCount = 0;

    private int myScorePoints = 0;
    private int pcScorePoints = 0;

    private TextView textViewMyScore;
    private TextView textViewPcScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     // Linking xml attributes with java initialization for scores
        textViewMyScore = findViewById(R.id.text_view_myscore);
        textViewPcScore = findViewById(R.id.text_view_pcscore);

     //Linking buttons with two dimensional array i and j
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
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
    /** Player is assigned X symbol
     *  Later play will be able to choose between X and O symbol
     *  Keep tracks of wins from both sides
     *  Calling checkForWin, playerWins and machineWins(later machine will develop own choosing) methods**/
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (playerTurn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (playerTurn) {
                playerWins();
            } else {
                machineWins();
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

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    /** Tracks player scores and toast when player wins
     * Calls updatePointsText and resetBoard method
     */
    private void playerWins() {
        myScorePoints++;
        Toast.makeText(this, "Player wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    /**Tracks machine scores and toast when machine wins
     * Calls updatePointsText and resetBoard method
     */
    private void machineWins() {
        pcScorePoints++;
        Toast.makeText(this, "PC wins!", Toast.LENGTH_SHORT).show();
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
        textViewPcScore.setText("PC Score: " + pcScorePoints);
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
        pcScorePoints = 0;
        updatePointsText();
        resetBoard();
    }
}