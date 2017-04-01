package com.example.android.climberscounter;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Variables for the instanceSet - I made these for debugging (as per instructor advice).will kee them here just to know I can do it like this
    public static String
            A_InstanceSavedTime_Key = "A_InstanceSavedTime_key",
            A_runningState_Key = "A_runningState_key";
    //TimeCounter Variables
    public TextView A_timeCounter;
    public TextView A1_timeCounter;
    public Handler A_customHandler = new Handler();
    public long
            A_startTime = 0L,
            A_timeInMilliseconds = 0L,
            A_timeSwapBuff = 0L,
            A_updatedTime = 0L,
            A1_updatedTime = 0L,
            A1_InstanceSavedTime = 0L,
            A1_startTime = 0L;

    public String
            A_elapsedTime = "00:00:0",
            A_runningState = "paused",

            A1_elapsedTime= "00:00:0";

    //  A_Timer:RUN
    public Runnable A_timerThread = new Runnable() {
        public void run() {

            A_timeInMilliseconds = SystemClock.uptimeMillis() - A1_startTime;
            A1_updatedTime = A_timeSwapBuff + A_timeInMilliseconds + A1_InstanceSavedTime;
            A_updatedTime = A1_updatedTime + 3600000;

            A_timeCounter.setText(stringifyTime(A_updatedTime));
            A1_timeCounter.setText(stringifyTime(A1_updatedTime));

            A_customHandler.postDelayed(this, 0);
            A_runningState = "running";
        }
    };
//    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//    toolbar.setNavigationIcon(R.drawable.nmeblacksmall);
    //variables Team A
    // Global variables initialization
    int
            A_teamScorePoints = 0,
            A_topsCounter = 0,
            A_bonusCounter = 0,
            limitofTries = 5,

    A1_numberOfTries = 0,
            A1_Bonus = 0,
            A1_Top = 0,
            A1_topPoints = 0;
    //Global methods to format numbers to european format #.###
    NumberFormat NumberFormatEU = NumberFormat.getNumberInstance(Locale.GERMAN);
    DecimalFormat decimalFormatEU = (DecimalFormat) NumberFormatEU;
//    setSupportActionBar(toolbar);

    //Save variable values when user turns mobile
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putInt("A_teamScorePoints", A_teamScorePoints);
        savedInstanceState.putInt("A_topsCounter", A_topsCounter);
        savedInstanceState.putInt("A_bonusCounter", A_bonusCounter);
//        savedInstanceState.putString("A_elapsedTime", A_elapsedTime);
        savedInstanceState.putString("A_elapsedTime", stringifyTime(A_updatedTime));
        savedInstanceState.putString("A1_elapsedTime", stringifyTime(A1_updatedTime));
        savedInstanceState.putString(A_runningState_Key, A_runningState);

        savedInstanceState.putInt("A1_numberOfTries", A1_numberOfTries);
        savedInstanceState.putInt("A1_Bonus", A1_Bonus);
        savedInstanceState.putInt("A1_Top", A1_Top);
        savedInstanceState.putInt("A1_topPoints", A1_topPoints);


        A1_InstanceSavedTime = A1_updatedTime;
        savedInstanceState.putLong(A_InstanceSavedTime_Key, A1_InstanceSavedTime);

        super.onSaveInstanceState(savedInstanceState);
    }

    //Retrieve variable values  after user ends turning mobile
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        A_teamScorePoints = savedInstanceState.getInt("A_teamScorePoints");
        A_topsCounter = savedInstanceState.getInt("A_topsCounter");
        A_bonusCounter = savedInstanceState.getInt("A_bonusCounter");
        A_elapsedTime = savedInstanceState.getString("A_elapsedTime");
        A1_elapsedTime =savedInstanceState.getString("A1_elapsedTime");
        A1_InstanceSavedTime = savedInstanceState.getLong(A_InstanceSavedTime_Key);
        A_runningState = savedInstanceState.getString(A_runningState_Key);
        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
        update_anyIntTextView(R.id.id_A_topsCounter, A_topsCounter);
        update_anyIntTextView(R.id.id_A_bonusCounter, A_bonusCounter);
        update_anyTimer(R.id.id_A_timeCounter, A_elapsedTime);

        A1_Bonus = savedInstanceState.getInt("A1_Bonus");
        A1_Top = savedInstanceState.getInt("A1_Top");
        A1_topPoints = savedInstanceState.getInt("A1_topPoints");
        A1_numberOfTries = savedInstanceState.getInt("A1_numberOfTries");
        update_anyTimer(R.id.id_A1_timeCounter, A1_elapsedTime);
        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
        update_anyIntTextView(R.id.id_A1_topPoints, A1_topPoints);
        if (A_runningState == "running") {
            A_setRunningStatus();
        }else {
            // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
            A1_updatedTime=A1_InstanceSavedTime;
            A_updatedTime=A1_updatedTime+3600000;
        }
        ;

        if (A1_Top != 0 || A1_numberOfTries == 5) {
            disableButton(R.id.id_A1_btnStart);
        }
        ;
        if (A1_topPoints != 0) {
            enableView(R.id.id_A1_topPoints);
            enableView(R.id.A1_topPointsWrapper);
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
//TODO - know why this was crashing  seemed a good methodology
//        if (savedInstanceState != null) {
//            // Restore value of members from saved state
//            A_topsCounter = savedInstanceState.getInt("A_topsCounter");
//            A_bonusCounter = savedInstanceState.getInt("A_bonusCounter");
//            A_teamScorePoints = savedInstanceState.getInt("A_teamScorePoints");
//            A1_numberOfTries = savedInstanceState.getInt("A1_numberOfTries");
//        } else {
//
//            reset ();
//        }

        setContentView(R.layout.activity_main);

//  A_Time Counter
        A_timeCounter = (TextView) findViewById(R.id.id_A_timeCounter);
        A1_timeCounter = (TextView) findViewById(R.id.id_A1_timeCounter);

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
            }
        });

//   A1_Quit Button - pauses counter and increases number of attempts
        A1quitButton = (Button) findViewById(R.id.id_A1_btnQuit);
        A1quitButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                pauseCounter();
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
                                               A_incrementTopCounterAndScore(1);
                                               disableButton(R.id.id_A1_btnQuit);
                                               disableButton(R.id.id_A1_btnBonus);
                                               disableButton(R.id.id_A1_btnTop);
                                               disableButton(R.id.id_A1_btnStart);
                                               enableView(R.id.id_A1_topPoints);
                                               enableView(R.id.A1_topPointsWrapper);

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

    public String stringifyTime(long time) {
        int hours = (int) (time / 3600000);
        int mins = (int) ((time - hours * 3600000) / 60000);
        int secs = (int) ((time - hours * 3600000 - mins * 60000) / 1000);
        int decs = (int) ((time - hours * 3600000 - mins * 60000 - secs * 1000) / 100);

        String stringifiedTime = hours + ":"
                + String.format("%02d", mins) + ":"
                + String.format("%02d", secs) + ":"
                + String.format("%01d", decs);

        return stringifiedTime;
    }

    //  A_Timer:PAUSE
    public void pauseCounter() {
        A_timeSwapBuff += A_timeInMilliseconds;
        A_customHandler.removeCallbacks(A_timerThread);
        A_runningState = "paused";
    }

    public void A1_incrementNumberofTries() {
        A1_numberOfTries += 1;
        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
    }

    //  A_Increment Bonus Counter
    public void A_incrementBonusCounter() {
        A_bonusCounter += 1;
        update_anyIntTextView(R.id.id_A_bonusCounter, A_bonusCounter);

        //A_updateValues(A_teamScorePoints, A_topsCounter, A_bonusCounter);
    }

    //  A_Increment Top Counter
    public void A_incrementTopCounterAndScore(int route) {
        A_topsCounter += 1;
        switch (route) {
            case 1:
                //    Points Per Route at each Try
                int[] R1T = {1500, 1200, 1080, 972, 875};
                int i = A1_numberOfTries - 1; // -1 because array starts at 0.
                A_teamScorePoints += R1T[i];
                A1_topPoints += R1T[i];
                A1_Top += 1;
                break;
            case 2:
                int[] R2T = {2500, 2000, 1800, 1620, 1458};
                int ii = A1_numberOfTries - 1;
                A_teamScorePoints += R2T[ii];
                A1_topPoints += R2T[ii];
                A1_Top += 1;
                break;
            default:
                break;
        }

        update_anyIntTextView(R.id.id_A_topsCounter, A_topsCounter);
        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
        update_anyIntTextView(R.id.id_A1_topPoints, A1_topPoints);

//        int[] R2T ={};
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
        A_topsCounter = 0;
        A1_numberOfTries = 0;
        A_bonusCounter = 0;
        A1_Top = 0;
        A1_Bonus = 0;
        A1_topPoints = 0;

        A_startTime = 0;
        A1_startTime = 0;
        A_timeInMilliseconds = 0;
        A_timeSwapBuff = 0;
        A1_updatedTime = 0;
        A_elapsedTime = "00:00:0";

        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
        update_anyIntTextView(R.id.id_A_topsCounter, A_topsCounter);
        update_anyIntTextView(R.id.id_A_bonusCounter, A_bonusCounter);
        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
        update_anyTimer(R.id.id_A_timeCounter, A_elapsedTime);
        update_anyTimer(R.id.id_A1_timeCounter, A_elapsedTime);
        //  A_reset button states
        disableButton(R.id.id_A1_btnQuit);
        disableButton(R.id.id_A1_btnBonus);
        disableButton(R.id.id_A1_btnTop);
        enableButton(R.id.id_A1_btnStart);

        disableView(R.id.id_A1_topPoints);
        disableView(R.id.A1_topPointsWrapper);

    }

    //Method to set running status (chronometer and button's status)
    public void A_setRunningStatus() {
        A1_startTime = SystemClock.uptimeMillis();
        A_customHandler.postDelayed(A_timerThread, 0);
        disableButton(R.id.id_A1_btnStart);
        enableButton(R.id.id_A1_btnQuit);
        enableButton(R.id.id_A1_btnTop);
        if (A1_Bonus == 0) {
            enableButton(R.id.id_A1_btnBonus);
        }
        ;
    }

    ;

//Methods to update textViews and ButtonViews

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
//        currentButton.setEnabled(false);
        currentButton.setVisibility(View.GONE);
    }

    //Enables Button
    public void enableButton(int thisbuttonId) {
        Button currentButton = (Button) findViewById(thisbuttonId);
//        currentButton.setEnabled(true);
        currentButton.setVisibility(View.VISIBLE);
    }

    //    Disables View
    public void disableView(int thisViewId) {
        View currentView = (View) findViewById(thisViewId);
        currentView.setVisibility(View.GONE);
    }

    //    Enables View
    public void enableView(int thisViewId) {
        View currentView = (View) findViewById(thisViewId);
        currentView.setVisibility(View.VISIBLE);
    }
}

//TODO changes buttons to invisible







