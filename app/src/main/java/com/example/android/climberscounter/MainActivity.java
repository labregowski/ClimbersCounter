package com.example.android.climberscounter;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.os.Handler;

import junit.framework.Test;

import java.lang.reflect.Method;
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
    int
            A_teamScorePoints = 0,
            A_TopsCounter = 0,
            A_BonusCounter = 0,
            A1_numberOfTries = 0,
            limitofTries = 5,
            A1_Bonus = 0,
            A1_Top =0;

    //TimeCounter Variables
    public TextView A_TimeCounter;  //TODO know better difference between public and private in this case!!
    public Handler customHandler = new Handler();
    public long
            A_startTime = 0L,
            A_timeInMilliseconds = 0L,
            A_timeSwapBuff = 0L,
            A_updatedTime = 0L,
            A_InstanceSavedTime = 0L;
    public String
            A_elapsedTime = "00:00:00",
            A_runningState = "paused";

    //Variables for the instanceSet - I made these for debugging (as per instructor advice).will kee them here just to know I can do it like this
    public static String
            A_InstanceSavedTime_Key = "A_InstanceSavedTime_key",
            A_runningState_Key = "A_runningState_key";

    //Global methods to format numbers to european format #.###
    NumberFormat NumberFormatEU = NumberFormat.getNumberInstance(Locale.GERMAN);
    DecimalFormat decimalFormatEU = (DecimalFormat) NumberFormatEU;

    //Save variable values   when user turns mobile
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putInt("A_teamScorePoints", A_teamScorePoints);
        savedInstanceState.putInt("A_TopsCounter", A_TopsCounter);
        savedInstanceState.putInt("A_BonusCounter", A_BonusCounter);
        savedInstanceState.putInt("A1_numberOfTries", A1_numberOfTries);
        savedInstanceState.putInt("A1_Bonus", A1_Bonus);
        savedInstanceState.putInt("A1_Top", A1_Top);
        savedInstanceState.putString("A_elapsedTime", A_elapsedTime);
        savedInstanceState.putString(A_runningState_Key, A_runningState);
        A_InstanceSavedTime = A_updatedTime;
        savedInstanceState.putLong(A_InstanceSavedTime_Key, A_InstanceSavedTime);

        super.onSaveInstanceState(savedInstanceState);
    }

    //Retrieve variable values  after user ends turning mobile
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        A_teamScorePoints = savedInstanceState.getInt("A_teamScorePoints");
        A_TopsCounter = savedInstanceState.getInt("A_TopsCounter");
        A_BonusCounter = savedInstanceState.getInt("A_BonusCounter");
        A_elapsedTime = savedInstanceState.getString("A_elapsedTime");
        A_InstanceSavedTime = savedInstanceState.getLong(A_InstanceSavedTime_Key);
        A_runningState = savedInstanceState.getString(A_runningState_Key);
        A1_Bonus = savedInstanceState.getInt("A1_Bonus");
        A1_Top = savedInstanceState.getInt("A1_Top");
        A1_numberOfTries = savedInstanceState.getInt("A1_numberOfTries");

        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
        update_anyIntTextView(R.id.id_A_TopsCounter, A_TopsCounter);
        update_anyIntTextView(R.id.id_A_BonusCounter, A_BonusCounter);
        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
        update_anyTimer(R.id.id_A_TimeCounter, A_elapsedTime);

        if (A1_Top != 0 || A1_numberOfTries == 5 ) {
            disableButton(R.id.id_A1_btnStart);
        }
        ;
//        if (A1_numberOfTries == 5) {
//            disableButton(R.id.id_A1_btnStart);
//        }
//        ;
        if (A_runningState == "running") {
            A_setRunningStatus();
        }
        ;

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
    }

    @Override
    protected void onStart() {
        super.onStart();
        reset();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//TODO - know why this was crashing  seemed a good metodology
//        if (savedInstanceState != null) {
//            // Restore value of members from saved state
//            A_TopsCounter = savedInstanceState.getInt("A_TopsCounter");
//            A_BonusCounter = savedInstanceState.getInt("A_BonusCounter");
//            A_teamScorePoints = savedInstanceState.getInt("A_teamScorePoints");
//            A1_numberOfTries = savedInstanceState.getInt("A1_numberOfTries");
//        } else {
//
//            reset ();
//        }

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
                A_setRunningStatus();
//                A_startTime = SystemClock.uptimeMillis();
//                customHandler.postDelayed(updateTimerThread, 0);
//                disableButton(R.id.id_A1_btnStart);
//                enableButton(R.id.id_A1_btnQuit);
//                enableButton(R.id.id_A1_btnTop);
//                if (A1_Bonus == 0) {
//                    enableButton(R.id.id_A1_btnBonus);
//                } else {
//                }
//                ;
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

    //  TODO SOlving: when recovering savedInstanceSet,it stops chronometer and then restarts nt qite from zero
    //  A_Timer:RUN
    public Runnable updateTimerThread = new Runnable() {
        public void run() {

            A_timeInMilliseconds = SystemClock.uptimeMillis() - A_startTime;
            A_updatedTime = A_timeSwapBuff + A_timeInMilliseconds + A_InstanceSavedTime;
            int hours = (int) (A_updatedTime / 3600000);
            int mins = (int) ((A_updatedTime - hours * 3600000) / 60000);
            int secs = (int) ((A_updatedTime - hours * 3600000 - mins * 60000) / 1000);
            int decs = (int) ((A_updatedTime - hours * 3600000 - mins * 60000 - secs * 1000) / 100);
            A_elapsedTime = hours + ":" + mins + ":"
                    + String.format("%02d", secs)
                    + ":"
                    + String.format("%02d", decs);
            A_TimeCounter.setText(A_elapsedTime);
            customHandler.postDelayed(this, 0);
            A_runningState = "running";
        }
    };

    //  A_Timer:PAUSE
    public void pauseCounter() {
        A_timeSwapBuff += A_timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
        A_runningState = "paused";
    }

    public void A1_incrementNumberofTries() {
        A1_numberOfTries += 1;
        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
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
        int i = A1_numberOfTries - 1; // -1 because array starts at 0.
        A_teamScorePoints += R1T[i];
        A_TopsCounter += 1;
        update_anyIntTextView(R.id.id_A_TopsCounter, A_TopsCounter);
        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
        A1_Top += 1;
//        int[] R2T ={2500, 2000, 1800, 1620, 1458};
//        int[] R3T ={320, 256, 230, 208, 187};
    }

//
//        Deprecated - Came up with the "update_anyIntTextView" method
//        A_Methods to Update scores
//
//        public void A_updateValues(int score, int tops, int bonuses) {
//
//        TextView teamScore = (TextView) findViewById(R.id.id_A_teamScore);
//        teamScore.setText(decimalFormatEU.format(score));
//        TextView teamTops = (TextView) findViewById(R.id.id_A_TopsCounter);
//        teamTops.setText(String.valueOf(tops));
//        TextView teamBonus = (TextView) findViewById(R.id.id_A_BonusCounter);
//        teamBonus.setText(String.valueOf(bonuses));
//    }

    //  Initialize all values
    public void reset() {
        //stop counter
        pauseCounter();
        //  A_reset variables and apply
        A_teamScorePoints = 0;
        A_TopsCounter = 0;
        A_BonusCounter = 0;
        A1_Top=0;
        A1_Bonus = 0;
        A1_numberOfTries = 0;
        A_startTime = 0;
        A_timeInMilliseconds = 0;
        A_timeSwapBuff = 0;
        A_updatedTime = 0;
        A_elapsedTime = "00:00:00";
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

    //Method to set running status (chronometer and button's status)
    public void A_setRunningStatus() {
        A_startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
        disableButton(R.id.id_A1_btnStart);
        enableButton(R.id.id_A1_btnQuit);
        enableButton(R.id.id_A1_btnTop);
        if (A1_Bonus == 0) {
            enableButton(R.id.id_A1_btnBonus);
        }
        ;
    }
    ;

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
}

//TODO changes buttons to invisible







