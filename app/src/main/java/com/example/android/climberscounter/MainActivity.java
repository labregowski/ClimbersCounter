package com.example.android.climberscounter;

import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.os.Handler;

import junit.framework.Test;

import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static android.R.attr.format;
import static android.R.attr.id;
import static com.example.android.climberscounter.R.id.id_A1_Quit;
import static com.example.android.climberscounter.R.id.id_A1_Top;
import static com.example.android.climberscounter.R.id.id_A1_btnSTart;
import static com.example.android.climberscounter.R.id.id_A1_numberOfTries;
import static com.example.android.climberscounter.R.id.id_A_BonusCounter;
import static com.example.android.climberscounter.R.id.id_A_TimeCounter;
import static com.example.android.climberscounter.R.id.id_A_TopsCounter;
import static com.example.android.climberscounter.R.id.id_A_teamScore;

import static com.example.android.climberscounter.R.string.currentTry;


public class MainActivity extends AppCompatActivity {
    //variables Team A
    // Global variables initialization
    int A_teamScorePoints = 0;
    int A_TopsCounter = 0;
    int A_BonusCounter = 0;
    int A1_numberOfTries = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//A Timer

        A_TimeCounter = (TextView) findViewById(R.id.id_A_TimeCounter);
        A1_btnSTart = (Button) findViewById(R.id.id_A1_btnSTart);

//A Button Functions
//      A1 Start Button
        A1_btnSTart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                A_startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });
//      A1 Quit Button
        A1quitButton = (Button) findViewById(R.id.id_A1_Quit);
        A1quitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                 pauseCounter ();
//                  call increase counter function
                    incrementNumberofTries();
                }
                }
        );
    }
    //          Update # of tries

    public void incrementNumberofTries() {
        A1_numberOfTries = +1;
        TextView updatedAttempt = (TextView) findViewById(R.id.id_A1_numberOfTries);
        updatedAttempt.setText(A1_numberOfTries);
    }

    private Button A1_btnSTart;
    private Button A1quitButton;
    private TextView A_TimeCounter;
    private Handler customHandler = new Handler();
    private long A_startTime = 0L;
    long A_timeInMilliseconds = 0L;
    long A_timeSwapBuff = 0L;
    long A_updatedTime = 0L;
    long A_accumulatedTime = 0L;

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            A_timeInMilliseconds = SystemClock.uptimeMillis() - A_startTime;
            A_updatedTime = A_timeSwapBuff + A_timeInMilliseconds;
            int hours = (int) (A_updatedTime / 3600000);
            int mins = (int) ((A_updatedTime - hours * 3600000) / 60000);
            int secs = (int) ((A_updatedTime - hours * 3600000 - mins * 60000) / 1000);
            int decs = (int) ((A_updatedTime - hours * 3600000 - mins * 60000 - secs * 1000) / 100);
            A_TimeCounter.setText(hours + ":" + mins + ":"
                    + String.format("%02d", secs)
                    + ":"
                    + String.format("%02d", decs)
            );
            customHandler.postDelayed(this, 0);
        }
    };


    //Team A
    //Functions to Update scores
    //TODO check wether it is better to keep all updates in one function (for all buttons) - some of the updated values will keep teh same - or if it is betterone function per button
    public void A_updateValues(int score, int tops, int bonuses) {
        TextView teamScore = (TextView) findViewById(R.id.id_A_teamScore);
        teamScore.setText(String.valueOf(score));
        TextView teamTops = (TextView) findViewById(R.id.id_A_TopsCounter);
        teamTops.setText(String.valueOf(tops));
        TextView teamBonus = (TextView) findViewById(R.id.id_A_BonusCounter);
        teamBonus.setText(String.valueOf(bonuses));
    }

    //    Update Chronometer
    //DINIS IS DOING THIS NOW :: TODO CRONOMETER
    public void A_updateTimer(String time) {
        TextView teamScore = (TextView) findViewById(R.id.id_A_TimeCounter);
        teamScore.setText(String.valueOf(time));
    }

    //Button Functions

    public void A_addTopPoints(View UpdateTeamsVairousGlobalScores) {
        //update Teams' score
        A_teamScorePoints += 1200;
        A_TopsCounter += 1;
        A_updateValues(A_teamScorePoints, A_TopsCounter, A_BonusCounter);
        //pauseCounter();
    }

    public void A_addBonus(View UpdateTeamsVairousGlobalScores) {
        A_BonusCounter += 1;
        A_updateValues(A_teamScorePoints, A_TopsCounter, A_BonusCounter);
    }

//    TODO: funtion to format number to #.###


    public void pauseCounter ()
    {
        A_timeSwapBuff += A_timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
    }

}





