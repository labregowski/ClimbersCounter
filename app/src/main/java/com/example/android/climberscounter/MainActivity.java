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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static android.R.attr.format;
import static android.R.attr.id;

import static com.example.android.climberscounter.R.id.id_A1_btnQuit;
import static com.example.android.climberscounter.R.id.id_A1_btnStart;
import static com.example.android.climberscounter.R.id.id_A1_numberOfTries;
import static com.example.android.climberscounter.R.id.id_A_BonusCounter;
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
    int limitofTries = 5;
    int A1_Bonus = 0;

    //TimeCounter Variables
    private TextView A_TimeCounter;  //TODO know why private !!
    private Handler customHandler = new Handler();
    private long A_startTime = 0L;
    long A_timeInMilliseconds = 0L;
    long A_timeSwapBuff = 0L;
    long A_updatedTime = 0L;
    long A_accumulatedTime = 0L;

    //Global methods to format numbers to european format #.###
    NumberFormat NumberFormatEU = NumberFormat.getNumberInstance(Locale.GERMAN);
    DecimalFormat decimalFormatEU = (DecimalFormat) NumberFormatEU;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//  A_Time Counter
        A_TimeCounter = (TextView) findViewById(R.id.id_A_TimeCounter);

//  A_Buttons - variables declaration
        Button A1startButton;
        Button A1quitButton;
        Button A1bonusButton;
        Button A1topButton;
        Button Resetbtn;

//  A1_Start Button - just starts counter
        A1startButton = (Button) findViewById(R.id.id_A1_btnStart);
        A1startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                A1_incrementNumberofTries();
                A_startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
                disableButton(R.id.id_A1_btnStart);
                enableButton(R.id.id_A1_btnQuit);
                //TODO only if local variable BONUS is zero
                enableButton(R.id.id_A1_btnTop);
                if (A1_Bonus == 0) {
                    enableButton(R.id.id_A1_btnBonus);
                } else {
                }
                ;
            }
        });
//   A1_Quit Button - pauses counter and increases number of attemps
        A1quitButton = (Button) findViewById(R.id.id_A1_btnQuit);
        A1quitButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                pauseCounter();
                                                //A1_incrementNumberofTries();
                                                disableButton(R.id.id_A1_btnQuit);
                                                disableButton(R.id.id_A1_btnBonus);
                                                disableButton(R.id.id_A1_btnTop);
                                                // Enables StartButton only if #tries <5 !! 5 as variable
                                                if (A1_numberOfTries < limitofTries) {
                                                    enableButton(R.id.id_A1_btnStart);
                                                } else {
                                                }
                                                ;
                                            }
                                        }
        );
//   A1_Bonus Button - Increases Bonus counter
        A1bonusButton = (Button) findViewById(R.id.id_A1_btnBonus);
        A1bonusButton.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View view) {
                                                 A1_Bonus = 1;
                                                 A_incrementBonusCounter();
                                                 disableButton(R.id.id_A1_btnBonus);
                                             }
                                         }
        );

//   A1_Top Button  -  pauses counter and increases topCounter
        A1topButton = (Button) findViewById(R.id.id_A1_btnTop);
        A1topButton.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View view) {
                                               pauseCounter();
                                               A_incrementTopCounterAndScore();
                                               disableButton(R.id.id_A1_btnQuit);
                                               disableButton(R.id.id_A1_btnBonus);
                                               disableButton(R.id.id_A1_btnTop);
                                               disableButton(R.id.id_A1_btnStart);
                                               //TODO shows the RouteScores
                                           }
                                       }
        );

// Reset Button
        Resetbtn = (Button) findViewById(R.id.id_btnReset);
        Resetbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                reset();
            }
        });
    }

    //  A_Timer:RUN
    public Runnable updateTimerThread = new Runnable() {
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

    //  A_Timer:PAUSE
    public void pauseCounter() {
        A_timeSwapBuff += A_timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
    }

    public void A1_incrementNumberofTries() {
        A1_numberOfTries += 1;
        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
//        TextView updatedAttempt = (TextView) findViewById(R.id.id_A1_numberOfTries);
//        updatedAttempt.setText(String.format("%01d", A1_numberOfTries));
    }

    //  A_Increment Bonus Counter
    public void A_incrementBonusCounter() {
        A_BonusCounter += 1;
        update_anyIntTextView(R.id.id_A_BonusCounter, A_BonusCounter);

        //A_updateValues(A_teamScorePoints, A_TopsCounter, A_BonusCounter);
    }

    //  A_Increment Top Counter



    public void A_incrementTopCounterAndScore() {
        //    Points Per Route at each Try
        int[] R1T = {1500, 1200, 1080, 972, 875};
        int i= A1_numberOfTries - 1; // -1 because array starts at 0.
        A_teamScorePoints += R1T[i];
        A_TopsCounter += 1;
        update_anyIntTextView(R.id.id_A_TopsCounter, A_TopsCounter);
        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
        //A_updateValues(A_teamScorePoints, A_TopsCounter, A_BonusCounter);
    }

    //
    // Deprecated - Came up with the "update_anyIntTextView" method
    // A_Methods to Update scores
    //
//    public void A_updateValues(int score, int tops, int bonuses) {
//
//        TextView teamScore = (TextView) findViewById(R.id.id_A_teamScore);
//       teamScore.setText(decimalFormatEU.format(score));
//        TextView teamTops = (TextView) findViewById(R.id.id_A_TopsCounter);
//        teamTops.setText(String.valueOf(tops));
//        TextView teamBonus = (TextView) findViewById(R.id.id_A_BonusCounter);
//        teamBonus.setText(String.valueOf(bonuses));
//    }

    //  Initialize all values
    public void reset() {
        //TODO: I'm almost sure there should be a way of eseting all values to initial state - need to investigate. mabe something with clear()
        //stop counter
        pauseCounter();
        //  A_reset variables and apply
        A_teamScorePoints = 0;
        A_TopsCounter = 0;
        A_BonusCounter = 0;
        A1_Bonus = 0;
        A1_numberOfTries = 0;
        A_startTime = 0;
        A_timeInMilliseconds = 0;
        A_timeSwapBuff = 0;
        A_updatedTime = 0;
        String A_elapsedTime = "00:00:00";
        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
        update_anyIntTextView(R.id.id_A_TopsCounter, A_TopsCounter);
        update_anyIntTextView(R.id.id_A_BonusCounter, A_BonusCounter);
        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
        update_anyTimer(R.id.id_A_TimeCounter, A_elapsedTime);
        //  A_reset button states
        disableButton(R.id.id_A1_btnQuit);
        disableButton(R.id.id_A1_btnBonus);
        disableButton(R.id.id_A1_btnTop);
        enableButton(R.id.id_A1_btnStart);
    }

//Methods to update values

    //Methods to update textViews
    public void update_anyIntTextView(int thisTextViewIid, int updatedScore) {
        TextView scoreView = (TextView) findViewById(thisTextViewIid);
        scoreView.setText(decimalFormatEU.format(updatedScore));
        // scoreView.setText(String.valueOf(updatedScore));
    }

    public void update_anyTimer(int thisTextViewIid, String updatedTime) {
        TextView timerView = (TextView) findViewById(thisTextViewIid);
        timerView.setText(String.valueOf(updatedTime));
    }

    //Disables Button
    public void disableButton(int thisbuttonId) {
        Button currentButton = (Button) findViewById(thisbuttonId);
        currentButton.setEnabled(false);
    }

    //Enables Button
    public void enableButton(int thisbuttonId) {
        Button currentButton = (Button) findViewById(thisbuttonId);
        currentButton.setEnabled(true);
    }

//    //Resets values to original state
//    public void resetView(int thisViewId) {
//        View currentView = (View) findViewById(thisViewId);
//        //currentView.setText
//    }

    //TODO changes buttons to invisible

}






