package com.aaryan.brainexercise;

//Importing Essential
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountedCompleter;

public class MainActivity extends AppCompatActivity {

    //Before start is pressed
    Button startGame;
    TextView title;

    //After start is pressed
    GridLayout gridLayout;
    TextView answer1;
    TextView answer2;
    TextView answer3;
    TextView answer4;

    TextView questionText;
    TextView scoreText;
    TextView timeText;
    TextView resultText;
    CountDownTimer timer;
    Button playAgain;

    Random r;
    public int answer;
    public int answerSpot;
    public int scoreNumerator;
    public int scoreDenominator;

    public static ArrayList<String> gameScores = new ArrayList<String>();



    String message = new String("Score: ");

    //Method that creates the question and displays it
    public void createQuestion() {
        resultText = findViewById(R.id.resultText);
        questionText = findViewById(R.id.questionText);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);


        r = new Random();

        int q1 = r.nextInt(50) + 1;
        int q2 = r.nextInt(50) + 1;



        int sign = r.nextInt(4);
        answerSpot = r.nextInt(4) + 1;

        if (sign == 1) {
            questionText.setText(Integer.toString(q1) + " + " + Integer.toString(q2) + " =");
            answer = q1 + q2;
        } else {
            questionText.setText(Integer.toString(q1) + " - " + Integer.toString(q2) + " =");
            answer = q1 - q2;
        }

        if (answerSpot == 1) {
            answer1.setText(Integer.toString(answer));

            answer2.setText(Integer.toString(r.nextInt(50) + 1));
            answer3.setText(Integer.toString(r.nextInt(50) + 1));
            answer4.setText(Integer.toString(r.nextInt(50) + 1));
        } else if (answerSpot == 2) {
            answer2.setText(Integer.toString(answer));

            answer1.setText(Integer.toString(r.nextInt(50) + 1));
            answer3.setText(Integer.toString(r.nextInt(50) + 1));
            answer4.setText(Integer.toString(r.nextInt(50) + 1));
        } else if (answerSpot == 3) {
            answer3.setText(Integer.toString(answer));

            answer2.setText(Integer.toString(r.nextInt(50) + 1));
            answer1.setText(Integer.toString(r.nextInt(50) + 1));
            answer4.setText(Integer.toString(r.nextInt(50) + 1));
        } else {
            answer4.setText(Integer.toString(answer));

            answer2.setText(Integer.toString(r.nextInt(50) + 1));
            answer3.setText(Integer.toString(r.nextInt(50) + 1));
            answer1.setText(Integer.toString(r.nextInt(50) + 1));
        }

        //resultText.animate().alpha(0f).setDuration(500);

    }

    //Code to Start the game
    public void startGame(View view) {
        startGame = findViewById(R.id.StartButton);
        title = findViewById(R.id.TITLE);

        gridLayout = findViewById(R.id.gridLayout);
        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        timeText = findViewById(R.id.timeText);

        startGame.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);

        gridLayout.animate().alpha(1f).setDuration(1000);
        questionText.animate().alpha(1f).setDuration(1000);
        scoreText.animate().alpha(1f).setDuration(1000);
        timeText.animate().alpha(1f).setDuration(1000);

        createQuestion();
        timer.start();
    }

    public void checkAnswer(View view) {
        scoreText = findViewById(R.id.scoreText);
        resultText = findViewById(R.id.resultText);
        TextView userAnswer = (TextView) view;

        if ((resultText.getText()).equals("YOU ARE FINISHED")) {

            resultText.animate().alpha(0f).setDuration(500);
        } else {
            int usersAnswer = Integer.parseInt(userAnswer.getText().toString());
            Log.i("User's Answer: ", Integer.toString(usersAnswer));

            if (answer == usersAnswer) {
                scoreNumerator++;
                scoreDenominator++;

                scoreText.setText(Integer.toString(scoreNumerator) + "/" + Integer.toString(scoreDenominator));
                resultText.setText("CORRECT!");
                resultText.animate().rotation(360f).alpha(1f).setDuration(500);
            } else {
                scoreDenominator++;

                scoreText.setText(Integer.toString(scoreNumerator) + "/" + Integer.toString(scoreDenominator));
                resultText.setText("WRONG!");
                resultText.animate().rotation(360f).alpha(1f).setDuration(500);
            }

            createQuestion();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame = findViewById(R.id.StartButton);
        title = findViewById(R.id.TITLE);

        gridLayout = findViewById(R.id.gridLayout);
        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        timeText = findViewById(R.id.timeText);
        resultText = findViewById(R.id.resultText);
        playAgain = findViewById(R.id.playAgain);

        startGame.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);

        gridLayout.animate().alpha(0f).setDuration(0);
        questionText.animate().alpha(0f).setDuration(0);
        scoreText.animate().alpha(0f).setDuration(0);
        timeText.animate().alpha(0f).setDuration(0);
        resultText.animate().alpha(0f).setDuration(0);
        playAgain.animate().alpha(0f).setDuration(0);

        scoreNumerator = 0;
        scoreDenominator = 0;
        scoreText.setText("0/0");

        timer = new CountDownTimer(30000 + 900, 1000) {
            @Override
                    public void onTick(long l) {

                        if(l < 10000){
                            timeText.setText("0 : 0" + Long.toString(l / 1000));
                        }else{
                            timeText.setText("0 : " + Long.toString(l / 1000));
                        }
                    }

            @Override
            public void onFinish() {
                resultText.animate().alpha(0f).setDuration(0);
                resultText.setText("YOU ARE FINISHED");
                playAgain.animate().alpha(1f).setDuration(1000);

                double percent = scoreNumerator / scoreDenominator;

                if(percent < 0.7 || scoreDenominator < 6){
                    message = "You did so bad";

                }else if(scoreDenominator >= 5 &&  scoreDenominator < 12 && percent > 0.7){
                    message = "better";
                }else if(scoreNumerator > 11 && scoreNumerator < 17 && percent > 0.8){
                    message = "good";
                }else{
                    message = "your einstein";
                }

                Toast.makeText(getApplicationContext(), message + " with a score of: " + scoreNumerator + "/" + scoreDenominator, Toast.LENGTH_LONG).show();
                gameScores.add(scoreNumerator + "/" + scoreDenominator);
            }
        };


    }

    public void playAgain(View view) {
        startGame = findViewById(R.id.StartButton);
        title = findViewById(R.id.TITLE);

        gridLayout = findViewById(R.id.gridLayout);
        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        timeText = findViewById(R.id.timeText);
        playAgain = findViewById(R.id.playAgain);

        startGame.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);

        playAgain.animate().alpha(0f).setDuration(0);
        gridLayout.animate().alpha(1f).setDuration(1000);
        questionText.animate().alpha(1f).setDuration(1000);
        scoreText.animate().alpha(1f).setDuration(1000);
        timeText.animate().alpha(1f).setDuration(1000);

        Toast.makeText(getApplicationContext(), "Previous Game Score    "+ gameScores.remove(0), Toast.LENGTH_LONG).show();

        createQuestion();
        timer.start();

        scoreNumerator = 0;
        scoreDenominator = 0;
        scoreText.setText("0/0");

        resultText.setText("");

    }
}
