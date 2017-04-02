package com.example.android.climberscounter;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static com.example.android.climberscounter.R.id.id_A1_btnStart;
import static com.example.android.climberscounter.R.id.id_A2_btnStart;
import static com.example.android.climberscounter.R.id.id_A3_btnStart;
import static com.example.android.climberscounter.R.id.id_B1_btnStart;
import static com.example.android.climberscounter.R.id.id_B2_btnStart;
import static com.example.android.climberscounter.R.id.id_B3_btnStart;

public class MainActivity extends AppCompatActivity {
    //Variables for the instanceSet - I made these for debugging (as per instructor advice).will kee them here just to know I can do it like this
    public static String
            A1_InstanceSavedTime_Key = "A1_InstanceSavedTime_key",
            A2_InstanceSavedTime_Key = "A2_InstanceSavedTime_key",
            A1_runningState_Key = "A1_runningState_key",
            A2_runningState_Key = "A2_runningState_key",

            B1_InstanceSavedTime_Key = "B1_InstanceSavedTime_key",
            B2_InstanceSavedTime_Key = "B2_InstanceSavedTime_key",
            B1_runningState_Key = "B1_runningState_key",
            B2_runningState_Key = "B2_runningState_key";
    //TimeCounter Variables
    public Handler A1_customHandler = new Handler(),
                   A2_customHandler = new Handler(),

                   B1_customHandler = new Handler(),
                   B2_customHandler = new Handler();

    public TextView A_timeCounter,
                    A1_timeCounter,
                    A2_timeCounter,

                    B_timeCounter,
                    B1_timeCounter,
                    B2_timeCounter;
    public long
            A_updatedTime = 0L,

            A1_timeInMilliseconds = 0L,
            A1_timeSwapBuff = 0L,
            A1_startTime = 0L,
            A1_updatedTime = 0L,
            A1_InstanceSavedTime = 0L,
            A2_timeInMilliseconds = 0L,
            A2_timeSwapBuff = 0L,
            A2_startTime = 0L,
            A2_updatedTime = 0L,
            A2_InstanceSavedTime = 0L,

            B_updatedTime = 0L,

            B1_timeInMilliseconds = 0L,
            B1_timeSwapBuff = 0L,
            B1_startTime = 0L,
            B1_updatedTime = 0L,
            B1_InstanceSavedTime = 0L,
            B2_timeInMilliseconds = 0L,
            B2_timeSwapBuff = 0L,
            B2_startTime = 0L,
            B2_updatedTime = 0L,
            B2_InstanceSavedTime = 0L;

    public String
            A_elapsedTime = "00:00:0",
            A1_runningState = "paused",
            A2_runningState = "paused",

            A1_elapsedTime= "00:00:0",
            A2_elapsedTime= "00:00:0",

            B_elapsedTime = "00:00:0",
            B1_runningState = "paused",
            B2_runningState = "paused",

            B1_elapsedTime= "00:00:0",
            B2_elapsedTime= "00:00:0";

    //  A1_Timer:RUN
    public Runnable A1_timerThread = new Runnable() {
        public void run() {

            A1_timeInMilliseconds = SystemClock.uptimeMillis() - A1_startTime;
            A1_updatedTime = A1_timeSwapBuff + A1_timeInMilliseconds + A1_InstanceSavedTime;
            A_updatedTime = A1_updatedTime + A2_updatedTime;

            A_timeCounter.setText(stringifyTime(A_updatedTime));
            A1_timeCounter.setText(stringifyTime(A1_updatedTime));

            A1_customHandler.postDelayed(this, 0);
            A1_runningState = "running";
        }
    };

    //  A2_Timer:RUN
    public Runnable A2_timerThread = new Runnable() {
        public void run() {

            A2_timeInMilliseconds = SystemClock.uptimeMillis() - A2_startTime;
            A2_updatedTime = A2_timeSwapBuff + A2_timeInMilliseconds + A2_InstanceSavedTime;
            A_updatedTime = A1_updatedTime + A2_updatedTime;

            A_timeCounter.setText(stringifyTime(A_updatedTime));
            A2_timeCounter.setText(stringifyTime(A2_updatedTime));

            A2_customHandler.postDelayed(this, 0);
            A2_runningState = "running";
        }
    };

    //  B1_Timer:RUN
    public Runnable B1_timerThread = new Runnable() {
        public void run() {

            B1_timeInMilliseconds = SystemClock.uptimeMillis() - B1_startTime;
            B1_updatedTime = B1_timeSwapBuff + B1_timeInMilliseconds + B1_InstanceSavedTime;
            B_updatedTime = B1_updatedTime + B2_updatedTime;

            B_timeCounter.setText(stringifyTime(B_updatedTime));
            B1_timeCounter.setText(stringifyTime(B1_updatedTime));

            B1_customHandler.postDelayed(this, 0);
            B1_runningState = "running";
        }
    };
    //  B2_Timer:RUN
    public Runnable B2_timerThread = new Runnable() {
        public void run() {

            B2_timeInMilliseconds = SystemClock.uptimeMillis() - B2_startTime;
            B2_updatedTime = B2_timeSwapBuff + B2_timeInMilliseconds + B2_InstanceSavedTime;
            B_updatedTime = B1_updatedTime + B2_updatedTime;

            B_timeCounter.setText(stringifyTime(B_updatedTime));
            B2_timeCounter.setText(stringifyTime(B2_updatedTime));

            B2_customHandler.postDelayed(this, 0);
            B2_runningState = "running";
        }
    };

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
            A1_topPoints = 0,

            A2_numberOfTries = 0,
            A2_Bonus = 0,
            A2_Top = 0,
            A2_topPoints = 0;

    //variables Team B
// Global variables initialization
    int
            B_teamScorePoints = 0,
            B_topsCounter = 0,
            B_bonusCounter = 0,
//            limitofTries = 5,

    B1_numberOfTries = 0,
            B1_Bonus = 0,
            B1_Top = 0,
            B1_topPoints = 0,

    B2_numberOfTries = 0,
            B2_Bonus = 0,
            B2_Top = 0,
            B2_topPoints = 0;

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
        savedInstanceState.putString("A_elapsedTime", stringifyTime(A_updatedTime));
        savedInstanceState.putString("A1_elapsedTime", stringifyTime(A1_updatedTime));
        savedInstanceState.putString("A2_elapsedTime", stringifyTime(A2_updatedTime));
        savedInstanceState.putString(A1_runningState_Key, A1_runningState);
        savedInstanceState.putString(A2_runningState_Key, A2_runningState);

        savedInstanceState.putInt("A1_numberOfTries", A1_numberOfTries);
        savedInstanceState.putInt("A1_Bonus", A1_Bonus);
        savedInstanceState.putInt("A1_Top", A1_Top);
        savedInstanceState.putInt("A1_topPoints", A1_topPoints);
        savedInstanceState.putInt("A2_numberOfTries", A2_numberOfTries);
        savedInstanceState.putInt("A2_Bonus", A2_Bonus);
        savedInstanceState.putInt("A2_Top", A2_Top);
        savedInstanceState.putInt("A2_topPoints", A2_topPoints);


        A1_InstanceSavedTime = A1_updatedTime;
        A2_InstanceSavedTime = A2_updatedTime;

        savedInstanceState.putLong(A1_InstanceSavedTime_Key, A1_InstanceSavedTime);
        savedInstanceState.putLong(A2_InstanceSavedTime_Key, A2_InstanceSavedTime);



        savedInstanceState.putInt("B_teamScorePoints", B_teamScorePoints);
        savedInstanceState.putInt("B_topsCounter", B_topsCounter);
        savedInstanceState.putInt("B_bonusCounter", B_bonusCounter);
        savedInstanceState.putString("B_elapsedTime", stringifyTime(B_updatedTime));
        savedInstanceState.putString("B1_elapsedTime", stringifyTime(B1_updatedTime));
        savedInstanceState.putString("B2_elapsedTime", stringifyTime(B2_updatedTime));
        savedInstanceState.putString(B1_runningState_Key, B1_runningState);
        savedInstanceState.putString(B2_runningState_Key, B2_runningState);

        savedInstanceState.putInt("B1_numberOfTries", B1_numberOfTries);
        savedInstanceState.putInt("B1_Bonus", B1_Bonus);
        savedInstanceState.putInt("B1_Top", B1_Top);
        savedInstanceState.putInt("B1_topPoints", B1_topPoints);
        savedInstanceState.putInt("B2_numberOfTries", B2_numberOfTries);
        savedInstanceState.putInt("B2_Bonus", B2_Bonus);
        savedInstanceState.putInt("B2_Top", B2_Top);
        savedInstanceState.putInt("B2_topPoints", B2_topPoints);


        B1_InstanceSavedTime = B1_updatedTime;
        B2_InstanceSavedTime = B2_updatedTime;

        savedInstanceState.putLong(B1_InstanceSavedTime_Key, B1_InstanceSavedTime);
        savedInstanceState.putLong(B2_InstanceSavedTime_Key, B2_InstanceSavedTime);

        super.onSaveInstanceState(savedInstanceState);
    }

    //Retrieve variable values  after user ends turning mobile
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//
//        super.onRestoreInstanceState(savedInstanceState);
//
//        A_teamScorePoints = savedInstanceState.getInt("A_teamScorePoints");
//        A_topsCounter = savedInstanceState.getInt("A_topsCounter");
//        A_bonusCounter = savedInstanceState.getInt("A_bonusCounter");
//        A_elapsedTime = savedInstanceState.getString("A_elapsedTime");
//
//        A1_elapsedTime =savedInstanceState.getString("A1_elapsedTime");
//        A1_InstanceSavedTime = savedInstanceState.getLong(A1_InstanceSavedTime_Key);
//        A2_elapsedTime =savedInstanceState.getString("A2_elapsedTime");
//        A2_InstanceSavedTime = savedInstanceState.getLong(A2_InstanceSavedTime_Key);
//
//        A1_runningState = savedInstanceState.getString(A1_runningState_Key);
//        A2_runningState = savedInstanceState.getString(A2_runningState_Key);
//        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
//        update_anyIntTextView(R.id.id_A_topsCounter, A_topsCounter);
//        update_anyIntTextView(R.id.id_A_bonusCounter, A_bonusCounter);
//        update_anyTimer(R.id.id_A_timeCounter, A_elapsedTime);
//
//        A1_Bonus = savedInstanceState.getInt("A1_Bonus");
//        A1_Top = savedInstanceState.getInt("A1_Top");
//        A1_topPoints = savedInstanceState.getInt("A1_topPoints");
//        A1_numberOfTries = savedInstanceState.getInt("A1_numberOfTries");
//
//        A2_Bonus = savedInstanceState.getInt("A2_Bonus");
//        A2_Top = savedInstanceState.getInt("A2_Top");
//        A2_topPoints = savedInstanceState.getInt("A2_topPoints");
//        A2_numberOfTries = savedInstanceState.getInt("A2_numberOfTries");
//
//
//        update_anyTimer(R.id.id_A1_timeCounter, A1_elapsedTime);
//        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
//        update_anyIntTextView(R.id.id_A1_topPoints, A1_topPoints);
//
//        update_anyTimer(R.id.id_A2_timeCounter, A2_elapsedTime);
//        update_anyIntTextView(R.id.id_A2_numberOfTries, A2_numberOfTries);
//        update_anyIntTextView(R.id.id_A2_topPoints, A2_topPoints);
//
//        B_teamScorePoints = savedInstanceState.getInt("B_teamScorePoints");
//        B_topsCounter = savedInstanceState.getInt("B_topsCounter");
//        B_bonusCounter = savedInstanceState.getInt("B_bonusCounter");
//        B_elapsedTime = savedInstanceState.getString("B_elapsedTime");
//
//        B1_elapsedTime =savedInstanceState.getString("B1_elapsedTime");
//        B1_InstanceSavedTime = savedInstanceState.getLong(B1_InstanceSavedTime_Key);
//        B2_elapsedTime =savedInstanceState.getString("B2_elapsedTime");
//        B2_InstanceSavedTime = savedInstanceState.getLong(B2_InstanceSavedTime_Key);
//
//        B1_runningState = savedInstanceState.getString(B1_runningState_Key);
//        B2_runningState = savedInstanceState.getString(B2_runningState_Key);
//        update_anyIntTextView(R.id.id_B_teamScore, B_teamScorePoints);
//        update_anyIntTextView(R.id.id_B_topsCounter, B_topsCounter);
//        update_anyIntTextView(R.id.id_B_bonusCounter, B_bonusCounter);
//        update_anyTimer(R.id.id_B_timeCounter, B_elapsedTime);
//
//        B1_Bonus = savedInstanceState.getInt("B1_Bonus");
//        B1_Top = savedInstanceState.getInt("B1_Top");
//        B1_topPoints = savedInstanceState.getInt("B1_topPoints");
//        B1_numberOfTries = savedInstanceState.getInt("B1_numberOfTries");
//
//        B2_Bonus = savedInstanceState.getInt("B2_Bonus");
//        B2_Top = savedInstanceState.getInt("B2_Top");
//        B2_topPoints = savedInstanceState.getInt("B2_topPoints");
//        B2_numberOfTries = savedInstanceState.getInt("B2_numberOfTries");
//
//
//        update_anyTimer(R.id.id_B1_timeCounter, B1_elapsedTime);
//        update_anyIntTextView(R.id.id_B1_numberOfTries, B1_numberOfTries);
//        update_anyIntTextView(R.id.id_B1_topPoints, B1_topPoints);
//
//        update_anyTimer(R.id.id_B2_timeCounter, B2_elapsedTime);
//        update_anyIntTextView(R.id.id_B2_numberOfTries, B2_numberOfTries);
//        update_anyIntTextView(R.id.id_B2_topPoints, B2_topPoints);
//
//
//        if (A1_runningState.equals("running") ) {
//            A1_setRunningStatus();
//        }else {
//            // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
//            A1_updatedTime=A1_InstanceSavedTime;
//            A_updatedTime=A1_updatedTime+A2_updatedTime;
//        }
//        ;
//        if (A2_runningState.equals("running") ) {
//            A2_setRunningStatus();
//        }else {
//            // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
//            A2_updatedTime=A2_InstanceSavedTime;
//            A_updatedTime=A1_updatedTime+A2_updatedTime;
//        }
//        ;
//
//        if (A1_Top != 0 || A1_numberOfTries == 5) {
//            disableButton(id_A1_btnStart);
//        }
//        ;
//        if (A2_Top != 0 || A2_numberOfTries == 5) {
//            disableButton(id_A2_btnStart);
//        }
//        ;
//        if (A1_topPoints != 0) {
//            enableView(R.id.id_A1_topPoints);
//            enableView(R.id.A1_topPointsWrapper);
//        }
//        ;
//        if (A2_topPoints != 0) {
//            enableView(R.id.id_A2_topPoints);
//            enableView(R.id.A2_topPointsWrapper);
//        }
//        ;
//
//        if (B1_runningState.equals("running") ) {
//            B1_setRunningStatus();
//        }else {
//            // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
//            B1_updatedTime=B1_InstanceSavedTime;
//            B_updatedTime=B1_updatedTime+B2_updatedTime;
//        }
//        ;
//        if (B2_runningState.equals("running") ) {
//            B2_setRunningStatus();
//        }else {
//            // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
//            B2_updatedTime=B2_InstanceSavedTime;
//            B_updatedTime=B1_updatedTime+B2_updatedTime;
//        }
//        ;
//
//        if (B1_Top != 0 || B1_numberOfTries == 5) {
//            disableButton(id_B1_btnStart);
//        }
//        ;
//        if (B2_Top != 0 || B2_numberOfTries == 5) {
//            disableButton(id_B2_btnStart);
//        }
//        ;
//        if (B1_topPoints != 0) {
//            enableView(R.id.id_B1_topPoints);
//            enableView(R.id.B1_topPointsWrapper);
//        }
//        ;
//        if (B2_topPoints != 0) {
//            enableView(R.id.id_B2_topPoints);
//            enableView(R.id.B2_topPointsWrapper);
//        }
//        ;
//
//        // Restore UI state from the savedInstanceState.
//        // This bundle has also been passed to onCreate.
//    }

    //Retrieve variable values  after coming from background


//    public void onResume(Bundle savedInstanceState) {
//
//        super.onResume(savedInstanceState);
//
//        A_teamScorePoints = savedInstanceState.getInt("A_teamScorePoints");
//        A_topsCounter = savedInstanceState.getInt("A_topsCounter");
//        A_bonusCounter = savedInstanceState.getInt("A_bonusCounter");
//        A_elapsedTime = savedInstanceState.getString("A_elapsedTime");
//
//        A1_elapsedTime =savedInstanceState.getString("A1_elapsedTime");
//        A1_InstanceSavedTime = savedInstanceState.getLong(A1_InstanceSavedTime_Key);
//        A2_elapsedTime =savedInstanceState.getString("A2_elapsedTime");
//        A2_InstanceSavedTime = savedInstanceState.getLong(A2_InstanceSavedTime_Key);
//
//        A1_runningState = savedInstanceState.getString(A1_runningState_Key);
//        A2_runningState = savedInstanceState.getString(A2_runningState_Key);
//        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
//        update_anyIntTextView(R.id.id_A_topsCounter, A_topsCounter);
//        update_anyIntTextView(R.id.id_A_bonusCounter, A_bonusCounter);
//        update_anyTimer(R.id.id_A_timeCounter, A_elapsedTime);
//
//        A1_Bonus = savedInstanceState.getInt("A1_Bonus");
//        A1_Top = savedInstanceState.getInt("A1_Top");
//        A1_topPoints = savedInstanceState.getInt("A1_topPoints");
//        A1_numberOfTries = savedInstanceState.getInt("A1_numberOfTries");
//
//        A2_Bonus = savedInstanceState.getInt("A2_Bonus");
//        A2_Top = savedInstanceState.getInt("A2_Top");
//        A2_topPoints = savedInstanceState.getInt("A2_topPoints");
//        A2_numberOfTries = savedInstanceState.getInt("A2_numberOfTries");
//
//
//        update_anyTimer(R.id.id_A1_timeCounter, A1_elapsedTime);
//        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
//        update_anyIntTextView(R.id.id_A1_topPoints, A1_topPoints);
//
//        update_anyTimer(R.id.id_A2_timeCounter, A2_elapsedTime);
//        update_anyIntTextView(R.id.id_A2_numberOfTries, A2_numberOfTries);
//        update_anyIntTextView(R.id.id_A2_topPoints, A2_topPoints);
//
//        B_teamScorePoints = savedInstanceState.getInt("B_teamScorePoints");
//        B_topsCounter = savedInstanceState.getInt("B_topsCounter");
//        B_bonusCounter = savedInstanceState.getInt("B_bonusCounter");
//        B_elapsedTime = savedInstanceState.getString("B_elapsedTime");
//
//        B1_elapsedTime =savedInstanceState.getString("B1_elapsedTime");
//        B1_InstanceSavedTime = savedInstanceState.getLong(B1_InstanceSavedTime_Key);
//        B2_elapsedTime =savedInstanceState.getString("B2_elapsedTime");
//        B2_InstanceSavedTime = savedInstanceState.getLong(B2_InstanceSavedTime_Key);
//
//        B1_runningState = savedInstanceState.getString(B1_runningState_Key);
//        B2_runningState = savedInstanceState.getString(B2_runningState_Key);
//        update_anyIntTextView(R.id.id_B_teamScore, B_teamScorePoints);
//        update_anyIntTextView(R.id.id_B_topsCounter, B_topsCounter);
//        update_anyIntTextView(R.id.id_B_bonusCounter, B_bonusCounter);
//        update_anyTimer(R.id.id_B_timeCounter, B_elapsedTime);
//
//        B1_Bonus = savedInstanceState.getInt("B1_Bonus");
//        B1_Top = savedInstanceState.getInt("B1_Top");
//        B1_topPoints = savedInstanceState.getInt("B1_topPoints");
//        B1_numberOfTries = savedInstanceState.getInt("B1_numberOfTries");
//
//        B2_Bonus = savedInstanceState.getInt("B2_Bonus");
//        B2_Top = savedInstanceState.getInt("B2_Top");
//        B2_topPoints = savedInstanceState.getInt("B2_topPoints");
//        B2_numberOfTries = savedInstanceState.getInt("B2_numberOfTries");
//
//
//        update_anyTimer(R.id.id_B1_timeCounter, B1_elapsedTime);
//        update_anyIntTextView(R.id.id_B1_numberOfTries, B1_numberOfTries);
//        update_anyIntTextView(R.id.id_B1_topPoints, B1_topPoints);
//
//        update_anyTimer(R.id.id_B2_timeCounter, B2_elapsedTime);
//        update_anyIntTextView(R.id.id_B2_numberOfTries, B2_numberOfTries);
//        update_anyIntTextView(R.id.id_B2_topPoints, B2_topPoints);
//
//
//        if (A1_runningState.equals("running") ) {
//            A1_setRunningStatus();
//        }else {
//            // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
//            A1_updatedTime=A1_InstanceSavedTime;
//            A_updatedTime=A1_updatedTime+A2_updatedTime;
//        }
//        ;
//        if (A2_runningState.equals("running") ) {
//            A2_setRunningStatus();
//        }else {
//            // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
//            A2_updatedTime=A2_InstanceSavedTime;
//            A_updatedTime=A1_updatedTime+A2_updatedTime;
//        }
//        ;
//
//        if (A1_Top != 0 || A1_numberOfTries == 5) {
//            disableButton(id_A1_btnStart);
//        }
//        ;
//        if (A2_Top != 0 || A2_numberOfTries == 5) {
//            disableButton(id_A2_btnStart);
//        }
//        ;
//        if (A1_topPoints != 0) {
//            enableView(R.id.id_A1_topPoints);
//            enableView(R.id.A1_topPointsWrapper);
//        }
//        ;
//        if (A2_topPoints != 0) {
//            enableView(R.id.id_A2_topPoints);
//            enableView(R.id.A2_topPointsWrapper);
//        }
//        ;
//
//        if (B1_runningState.equals("running") ) {
//            B1_setRunningStatus();
//        }else {
//            // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
//            B1_updatedTime=B1_InstanceSavedTime;
//            B_updatedTime=B1_updatedTime+B2_updatedTime;
//        }
//        ;
//        if (B2_runningState.equals("running") ) {
//            B2_setRunningStatus();
//        }else {
//            // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
//            B2_updatedTime=B2_InstanceSavedTime;
//            B_updatedTime=B1_updatedTime+B2_updatedTime;
//        }
//        ;
//
//        if (B1_Top != 0 || B1_numberOfTries == 5) {
//            disableButton(id_B1_btnStart);
//        }
//        ;
//        if (B2_Top != 0 || B2_numberOfTries == 5) {
//            disableButton(id_B2_btnStart);
//        }
//        ;
//        if (B1_topPoints != 0) {
//            enableView(R.id.id_B1_topPoints);
//            enableView(R.id.B1_topPointsWrapper);
//        }
//        ;
//        if (B2_topPoints != 0) {
//            enableView(R.id.id_B2_topPoints);
//            enableView(R.id.B2_topPointsWrapper);
//        }
//        ;
//
//        // Restore UI state from the savedInstanceState.
//        // This bundle has also been passed to onCreate.
//    }

    @Override
    protected void onStart() {
        super.onStart();
        reset();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//TODO - know why this was crashing  seemed a good methodology
        if (savedInstanceState != null) {
            // Restore value of members from saved state

            A_teamScorePoints = savedInstanceState.getInt("A_teamScorePoints");
            A_topsCounter = savedInstanceState.getInt("A_topsCounter");
            A_bonusCounter = savedInstanceState.getInt("A_bonusCounter");
            A_elapsedTime = savedInstanceState.getString("A_elapsedTime");

            A1_elapsedTime =savedInstanceState.getString("A1_elapsedTime");
            A1_InstanceSavedTime = savedInstanceState.getLong(A1_InstanceSavedTime_Key);
            A1_runningState = savedInstanceState.getString(A1_runningState_Key);
            A1_Bonus = savedInstanceState.getInt("A1_Bonus");
            A1_Top = savedInstanceState.getInt("A1_Top");
            A1_topPoints = savedInstanceState.getInt("A1_topPoints");
            A1_numberOfTries = savedInstanceState.getInt("A1_numberOfTries");


            A2_elapsedTime =savedInstanceState.getString("A2_elapsedTime");
            A2_InstanceSavedTime = savedInstanceState.getLong(A2_InstanceSavedTime_Key);
            A2_runningState = savedInstanceState.getString(A2_runningState_Key);
            A2_Bonus = savedInstanceState.getInt("A2_Bonus");
            A2_Top = savedInstanceState.getInt("A2_Top");
            A2_topPoints = savedInstanceState.getInt("A2_topPoints");
            A2_numberOfTries = savedInstanceState.getInt("A2_numberOfTries");

            update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
            update_anyIntTextView(R.id.id_A_topsCounter, A_topsCounter);
            update_anyIntTextView(R.id.id_A_bonusCounter, A_bonusCounter);
            update_anyTimer(R.id.id_A_timeCounter, A_elapsedTime);

            update_anyTimer(R.id.id_A1_timeCounter, A1_elapsedTime);
            update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
            update_anyIntTextView(R.id.id_A1_topPoints, A1_topPoints);

            update_anyTimer(R.id.id_A2_timeCounter, A2_elapsedTime);
            update_anyIntTextView(R.id.id_A2_numberOfTries, A2_numberOfTries);
            update_anyIntTextView(R.id.id_A2_topPoints, A2_topPoints);

            B_teamScorePoints = savedInstanceState.getInt("B_teamScorePoints");
            B_topsCounter = savedInstanceState.getInt("B_topsCounter");
            B_bonusCounter = savedInstanceState.getInt("B_bonusCounter");
            B_elapsedTime = savedInstanceState.getString("B_elapsedTime");

            B1_elapsedTime =savedInstanceState.getString("B1_elapsedTime");
            B1_InstanceSavedTime = savedInstanceState.getLong(B1_InstanceSavedTime_Key);
            B1_runningState = savedInstanceState.getString(B1_runningState_Key);
            B1_Bonus = savedInstanceState.getInt("B1_Bonus");
            B1_Top = savedInstanceState.getInt("B1_Top");
            B1_topPoints = savedInstanceState.getInt("B1_topPoints");
            B1_numberOfTries = savedInstanceState.getInt("B1_numberOfTries");

            B2_runningState = savedInstanceState.getString(B2_runningState_Key);
            B2_elapsedTime =savedInstanceState.getString("B2_elapsedTime");
            B2_InstanceSavedTime = savedInstanceState.getLong(B2_InstanceSavedTime_Key);
            B2_Bonus = savedInstanceState.getInt("B2_Bonus");
            B2_Top = savedInstanceState.getInt("B2_Top");
            B2_topPoints = savedInstanceState.getInt("B2_topPoints");
            B2_numberOfTries = savedInstanceState.getInt("B2_numberOfTries");

            update_anyIntTextView(R.id.id_B_teamScore, B_teamScorePoints);
            update_anyIntTextView(R.id.id_B_topsCounter, B_topsCounter);
            update_anyIntTextView(R.id.id_B_bonusCounter, B_bonusCounter);
            update_anyTimer(R.id.id_B_timeCounter, B_elapsedTime);

            update_anyTimer(R.id.id_B1_timeCounter, B1_elapsedTime);
            update_anyIntTextView(R.id.id_B1_numberOfTries, B1_numberOfTries);
            update_anyIntTextView(R.id.id_B1_topPoints, B1_topPoints);

            update_anyTimer(R.id.id_B2_timeCounter, B2_elapsedTime);
            update_anyIntTextView(R.id.id_B2_numberOfTries, B2_numberOfTries);
            update_anyIntTextView(R.id.id_B2_topPoints, B2_topPoints);

            if (A1_runningState.equals("running") ) {
                A1_setRunningStatus();
            }else {
                // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
                A1_updatedTime=A1_InstanceSavedTime;
                A_updatedTime=A1_updatedTime+A2_updatedTime;
            }
            ;
            if (A2_runningState.equals("running") ) {
                A2_setRunningStatus();
            }else {
                // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
                A2_updatedTime=A2_InstanceSavedTime;
                A_updatedTime=A1_updatedTime+A2_updatedTime;
            }
            ;

            if (A1_Top != 0 || A1_numberOfTries == 5) {
                disableButton(id_A1_btnStart);
            }
            ;
            if (A2_Top != 0 || A2_numberOfTries == 5) {
                disableButton(id_A2_btnStart);
            }
            ;
            if (A1_topPoints != 0) {
                enableView(R.id.id_A1_topPoints);
                enableView(R.id.A1_topPointsWrapper);
            }
            ;
            if (A2_topPoints != 0) {
                enableView(R.id.id_A2_topPoints);
                enableView(R.id.A2_topPointsWrapper);
            }
            ;

            if (B1_runningState.equals("running") ) {
                B1_setRunningStatus();
            }else {
                // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
                B1_updatedTime=B1_InstanceSavedTime;
                B_updatedTime=B1_updatedTime+B2_updatedTime;
            }
            ;
            if (B2_runningState.equals("running") ) {
                B2_setRunningStatus();
            }else {
                // if doesn't run again, time variables would be lost (counters would be reset in the second turn of screen). Thus,this else part
                B2_updatedTime=B2_InstanceSavedTime;
                B_updatedTime=B1_updatedTime+B2_updatedTime;
            }
            ;

            if (B1_Top != 0 || B1_numberOfTries == 5) {
                disableButton(id_B1_btnStart);
            }
            ;
            if (B2_Top != 0 || B2_numberOfTries == 5) {
                disableButton(id_B2_btnStart);
            }
            ;
            if (B1_topPoints != 0) {
                enableView(R.id.id_B1_topPoints);
                enableView(R.id.B1_topPointsWrapper);
            }
            ;
            if (B2_topPoints != 0) {
                enableView(R.id.id_B2_topPoints);
                enableView(R.id.B2_topPointsWrapper);
            }
            ;

        } else {
                //nothing here, for now
        }

       setContentView(R.layout.activity_main);

//  A_Time Counter
        A_timeCounter = (TextView) findViewById(R.id.id_A_timeCounter);
        A1_timeCounter = (TextView) findViewById(R.id.id_A1_timeCounter);
        A2_timeCounter = (TextView) findViewById(R.id.id_A2_timeCounter);

        B_timeCounter = (TextView) findViewById(R.id.id_B_timeCounter);
        B1_timeCounter = (TextView) findViewById(R.id.id_B1_timeCounter);
        B2_timeCounter = (TextView) findViewById(R.id.id_B2_timeCounter);

//  Reset Button
        Button Resetbtn;
//  A_Buttons - variables declaration
        Button  A1startButton,
                A1quitButton,
                A1bonusButton,
                A1topButton,

                A2startButton,
                A2quitButton,
                A2bonusButton,
                A2topButton,

                B1startButton,
                B1quitButton,
                B1bonusButton,
                B1topButton,

                B2startButton,
                B2quitButton,
                B2bonusButton,
                B2topButton;

//  A1_Start Button - just starts counter
        A1startButton = (Button) findViewById(id_A1_btnStart);
        A1startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                A1_incrementNumberofTries();
                A1_setRunningStatus();
            }
        });

//  A2_Start Button - just starts counter
        A2startButton = (Button) findViewById(id_A2_btnStart);
        A2startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                A2_incrementNumberofTries();
                A2_setRunningStatus();
            }
        });

//  B1_Start Button - just starts counter
        B1startButton = (Button) findViewById(id_B1_btnStart);
        B1startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                B1_incrementNumberofTries();
                B1_setRunningStatus();
            }
        });

//  B2_Start Button - just starts counter
        B2startButton = (Button) findViewById(id_B2_btnStart);
        B2startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                B2_incrementNumberofTries();
                B2_setRunningStatus();
            }
        });

//   A1_Quit Button - pauses counter and increases number of attempts
        A1quitButton = (Button) findViewById(R.id.id_A1_btnQuit);
        A1quitButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                A1pauseCounter();
                                                disableButton(R.id.id_A1_btnQuit);
                                                disableButton(R.id.id_A1_btnBonus);
                                                disableButton(R.id.id_A1_btnTop);
                                                // Enables StartButton only if #tries <5 !! 5 as variable
                                                if (A1_numberOfTries < limitofTries) {
                                                    enableButton(id_A1_btnStart);
                                                }
                                                ;
                                                if ((A2_numberOfTries < limitofTries) && (A2_Top==0) ) {
                                                    enableButton(id_A2_btnStart);
                                                }
                                                ;
                                                enableButton(id_A3_btnStart); //NOTE: For now, Button3 is there just for demo, so, no need to check TODO set 12 routes
                                            }
                                        }
        );

//   A2_Quit Button - pauses counter and increases number of attempts
        A2quitButton = (Button) findViewById(R.id.id_A2_btnQuit);
        A2quitButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                A2pauseCounter();
                                                disableButton(R.id.id_A2_btnQuit);
                                                disableButton(R.id.id_A2_btnBonus);
                                                disableButton(R.id.id_A2_btnTop);
                                                // Enables StartButton only if #tries <5 !! 5 as variable
                                                if (A2_numberOfTries < limitofTries) {
                                                    enableButton(id_A2_btnStart);
                                                }
                                                ;
                                                if ((A1_numberOfTries < limitofTries) && (A1_Top==0) ) {
                                                    enableButton(id_A1_btnStart);
                                                }
                                                ;
                                                enableButton(id_A3_btnStart); //NOTE: For now, Button3 is there just for demo, so, no need to check TODO set 12 routes
                                            }
                                        }
        );

//   B1_Quit Button - pauses counter and increases number of attempts
        B1quitButton = (Button) findViewById(R.id.id_B1_btnQuit);
        B1quitButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                B1pauseCounter();
                                                disableButton(R.id.id_B1_btnQuit);
                                                disableButton(R.id.id_B1_btnBonus);
                                                disableButton(R.id.id_B1_btnTop);
                                                // Enables StartButton only if #tries <5 !! 5 as variable
                                                if (B1_numberOfTries < limitofTries) {
                                                    enableButton(id_B1_btnStart);
                                                }
                                                ;
                                                if ((B2_numberOfTries < limitofTries) && (B2_Top==0) ) {
                                                    enableButton(id_B2_btnStart);
                                                }
                                                ;
                                                enableButton(id_B3_btnStart); //NOTE: For now, Button3 is there just for demo, so, no need to check TODO set 12 routes
                                            }
                                        }
        );

//   B2_Quit Button - pauses counter and increases number of attempts
        B2quitButton = (Button) findViewById(R.id.id_B2_btnQuit);
        B2quitButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                B2pauseCounter();
                                                disableButton(R.id.id_B2_btnQuit);
                                                disableButton(R.id.id_B2_btnBonus);
                                                disableButton(R.id.id_B2_btnTop);
                                                // Enables StartButton only if #tries <5 !! 5 as variable
                                                if (B2_numberOfTries < limitofTries) {
                                                    enableButton(id_B2_btnStart);
                                                }
                                                ;
                                                if ((B1_numberOfTries < limitofTries) && (B1_Top==0) ) {
                                                    enableButton(id_B1_btnStart);
                                                }
                                                ;
                                                enableButton(id_B3_btnStart); //NOTE: For now, Button3 is there just for demo, so, no need to check TODO set 12 routes
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

//   A2_Bonus Button - Increases Bonus counter
        A2bonusButton = (Button) findViewById(R.id.id_A2_btnBonus);
        A2bonusButton.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View view) {
                                                 A2_Bonus = 1;
                                                 A_incrementBonusCounter();
                                                 disableButton(R.id.id_A2_btnBonus);
                                             }
                                         }
        );

//   B1_Bonus Button - Increases Bonus counter
        B1bonusButton = (Button) findViewById(R.id.id_B1_btnBonus);
        B1bonusButton.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View view) {
                                                 B1_Bonus = 1;
                                                 B_incrementBonusCounter();
                                                 disableButton(R.id.id_B1_btnBonus);
                                             }
                                         }
        );

//   B2_Bonus Button - Increases Bonus counter
        B2bonusButton = (Button) findViewById(R.id.id_B2_btnBonus);
        B2bonusButton.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View view) {
                                                 B2_Bonus = 1;
                                                 B_incrementBonusCounter();
                                                 disableButton(R.id.id_B2_btnBonus);
                                             }
                                         }
        );

//   A1_Top Button  -  pauses counter and increases topCounter
        A1topButton = (Button) findViewById(R.id.id_A1_btnTop);
        A1topButton.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View view) {
                                               A1pauseCounter();
                                               A_incrementTopCounterAndScore(1); //NOTE: in Route2 it will be (2)
                                               disableButton(R.id.id_A1_btnQuit);
                                               disableButton(R.id.id_A1_btnBonus);
                                               disableButton(R.id.id_A1_btnTop);
                                               disableButton(id_A1_btnStart);
                                               enableView(R.id.id_A1_topPoints);
                                               enableView(R.id.A1_topPointsWrapper);

                                               if ((A2_numberOfTries < limitofTries) && (A2_Top==0) ) {
                                                   enableButton(id_A2_btnStart);
                                               }
                                               ;
                                               enableButton(id_A3_btnStart); //NOTE: For now, Button3 is there just for demo, so, no need to check TODO set 12 routes
                                           }
                                       }
        );

//   A2_Top Button  -  pauses counter and increases topCounter
        A2topButton = (Button) findViewById(R.id.id_A2_btnTop);
        A2topButton.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View view) {
                                               A2pauseCounter();
                                               A_incrementTopCounterAndScore(2); //NOTE: in Route2 itwill be (2)
                                               disableButton(R.id.id_A2_btnQuit);
                                               disableButton(R.id.id_A2_btnBonus);
                                               disableButton(R.id.id_A2_btnTop);
                                               disableButton(id_A2_btnStart);
                                               enableView(R.id.id_A2_topPoints);
                                               enableView(R.id.A2_topPointsWrapper);

                                               if ((A1_numberOfTries < limitofTries) && (A1_Top==0) ) {
                                                   enableButton(id_A1_btnStart);
                                               }
                                               ;
                                               enableButton(id_A3_btnStart); //NOTE: For now, Button3 is there just for demo, so, no need to check TODO set 12 routes
                                           }
                                       }
        );


//   B1_Top Button  -  pauses counter and increases topCounter
        B1topButton = (Button) findViewById(R.id.id_B1_btnTop);
        B1topButton.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View view) {
                                               B1pauseCounter();
                                               B_incrementTopCounterBndScore(1); //NOTE: in Route2 it will be (2)
                                               disableButton(R.id.id_B1_btnQuit);
                                               disableButton(R.id.id_B1_btnBonus);
                                               disableButton(R.id.id_B1_btnTop);
                                               disableButton(id_B1_btnStart);
                                               enableView(R.id.id_B1_topPoints);
                                               enableView(R.id.B1_topPointsWrapper);

                                               if ((B2_numberOfTries < limitofTries) && (B2_Top==0) ) {
                                                   enableButton(id_B2_btnStart);
                                               }
                                               ;
                                               enableButton(id_B3_btnStart); //NOTE: For now, Button3 is there just for demo, so, no need to check TODO set 12 routes
                                           }
                                       }
        );

//   B2_Top Button  -  pauses counter and increases topCounter
        B2topButton = (Button) findViewById(R.id.id_B2_btnTop);
        B2topButton.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View view) {
                                               B2pauseCounter();
                                               B_incrementTopCounterBndScore(2); //NOTE: in Route2 it is 2
                                               disableButton(R.id.id_B2_btnQuit);
                                               disableButton(R.id.id_B2_btnBonus);
                                               disableButton(R.id.id_B2_btnTop);
                                               disableButton(id_B2_btnStart);
                                               enableView(R.id.id_B2_topPoints);
                                               enableView(R.id.B2_topPointsWrapper);

                                               if ((B1_numberOfTries < limitofTries) && (B1_Top==0) ) {
                                                   enableButton(id_B1_btnStart);
                                               }
                                               ;
                                               enableButton(id_B3_btnStart); //NOTE: For now, Button3 is there just for demo, so, no need to check TODO set 12 routes
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

//Stringify anyting
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
    public void A1pauseCounter() {
        A1_timeSwapBuff += A1_timeInMilliseconds;
        A1_customHandler.removeCallbacks(A1_timerThread);
        A1_runningState = "paused";
    }

//  B_Timer:PAUSE
    public void B1pauseCounter() {
        B1_timeSwapBuff += B1_timeInMilliseconds;
        B1_customHandler.removeCallbacks(B1_timerThread);
        B1_runningState = "paused";
    }

    public void A2pauseCounter() {
        A2_timeSwapBuff += A2_timeInMilliseconds;
        A2_customHandler.removeCallbacks(A2_timerThread);
        A2_runningState = "paused";
    }

    public void B2pauseCounter() {
        B2_timeSwapBuff += B2_timeInMilliseconds;
        B2_customHandler.removeCallbacks(B2_timerThread);
        B2_runningState = "paused";
    }

//TODO - prepare a method that uses incoming variable to increment number of tries (not to have all these similar methods
    public void A1_incrementNumberofTries() {
        A1_numberOfTries += 1;
        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
    }
    public void A2_incrementNumberofTries() {
        A2_numberOfTries += 1;
        update_anyIntTextView(R.id.id_A2_numberOfTries, A2_numberOfTries);
    }

//  A_Increment Bonus Counter
    public void A_incrementBonusCounter() {
        A_bonusCounter += 1;
        update_anyIntTextView(R.id.id_A_bonusCounter, A_bonusCounter);
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
                int ii = A2_numberOfTries - 1;
                A_teamScorePoints += R2T[ii];
                A2_topPoints += R2T[ii];
                A2_Top += 1;
                break;
            default:
                break;
        }

        update_anyIntTextView(R.id.id_A_topsCounter, A_topsCounter);
        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
        update_anyIntTextView(R.id.id_A1_topPoints, A1_topPoints);
        update_anyIntTextView(R.id.id_A2_topPoints, A2_topPoints);
    }

    public void B1_incrementNumberofTries() {
        B1_numberOfTries += 1;
        update_anyIntTextView(R.id.id_B1_numberOfTries, B1_numberOfTries);
    }

    public void B2_incrementNumberofTries() {
        B2_numberOfTries += 1;
        update_anyIntTextView(R.id.id_B2_numberOfTries, B2_numberOfTries);
    }

//  B_Increment Bonus Counter
    public void B_incrementBonusCounter() {
        B_bonusCounter += 1;
        update_anyIntTextView(R.id.id_B_bonusCounter, B_bonusCounter);
    }

//  B_Increment Top Counter
    public void B_incrementTopCounterBndScore(int route) {
        B_topsCounter += 1;
        switch (route) {
            case 1:
                //    Points Per Route at each Try
                int[] R1T = {1500, 1200, 1080, 972, 875};
                int i = B1_numberOfTries - 1; // -1 because array starts at 0.
                B_teamScorePoints += R1T[i];
                B1_topPoints += R1T[i];
                B1_Top += 1;
                break;
            case 2:
                int[] R2T = {2500, 2000, 1800, 1620, 1458};
                int ii = B2_numberOfTries - 1;
                B_teamScorePoints += R2T[ii];
                B2_topPoints += R2T[ii];
                B2_Top += 1;
                break;
            default:
                break;
        }

        update_anyIntTextView(R.id.id_B_topsCounter, B_topsCounter);
        update_anyIntTextView(R.id.id_B_teamScore, B_teamScorePoints);
        update_anyIntTextView(R.id.id_B1_topPoints, B1_topPoints);
        update_anyIntTextView(R.id.id_B2_topPoints, B2_topPoints);
    }

//        Update 3 views atthe same time
// Deprecated - Came up with the "update_anyIntTextView" method
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
        A1pauseCounter();
        A2pauseCounter();

        B1pauseCounter();
        B2pauseCounter();

        //  A_reset variables and apply
        A_teamScorePoints = 0;
        A_topsCounter = 0;
        A_bonusCounter = 0;

        A1_numberOfTries = 0;
        A1_Top = 0;
        A1_Bonus = 0;
        A1_topPoints = 0;

        A2_numberOfTries = 0;
        A2_Top = 0;
        A2_Bonus = 0;
        A2_topPoints = 0;

        A1_timeInMilliseconds = 0;
        A1_timeSwapBuff = 0;
        A1_startTime = 0;
        A1_updatedTime = 0;
        A1_InstanceSavedTime = 0L;

        A2_timeInMilliseconds = 0;
        A2_timeSwapBuff = 0;
        A2_startTime = 0;
        A2_updatedTime = 0;
        A2_InstanceSavedTime = 0L;

        A_elapsedTime = "00:00:0";
        A1_elapsedTime = "00:00:0";
        A2_elapsedTime = "00:00:0";

        update_anyIntTextView(R.id.id_A_teamScore, A_teamScorePoints);
        update_anyIntTextView(R.id.id_A_topsCounter, A_topsCounter);
        update_anyIntTextView(R.id.id_A_bonusCounter, A_bonusCounter);
        update_anyTimer(R.id.id_A_timeCounter, A_elapsedTime);

        update_anyIntTextView(R.id.id_A1_numberOfTries, A1_numberOfTries);
        update_anyTimer(R.id.id_A1_timeCounter, A1_elapsedTime);

        update_anyIntTextView(R.id.id_A2_numberOfTries, A2_numberOfTries);
        update_anyTimer(R.id.id_A2_timeCounter, A2_elapsedTime);

        //  A1_reset button states
        disableButton(R.id.id_A1_btnQuit);
        disableButton(R.id.id_A1_btnBonus);
        disableButton(R.id.id_A1_btnTop);
        enableButton(id_A1_btnStart);
        disableView(R.id.id_A1_topPoints);
        disableView(R.id.A1_topPointsWrapper);


        //  A2_reset button states
        disableButton(R.id.id_A2_btnQuit);
        disableButton(R.id.id_A2_btnBonus);
        disableButton(R.id.id_A2_btnTop);
        enableButton(id_A2_btnStart);
        disableView(R.id.id_A2_topPoints);
        disableView(R.id.A2_topPointsWrapper);

        enableButton(id_A3_btnStart); //NOTE: For now, Button3 is there just for demo, so, no need to check TODO set 12 routes

        //  B_reset variables and apply
        B_teamScorePoints = 0;
        B_topsCounter = 0;
        B_bonusCounter = 0;

        B1_numberOfTries = 0;
        B1_Top = 0;
        B1_Bonus = 0;
        B1_topPoints = 0;

        B2_numberOfTries = 0;
        B2_Top = 0;
        B2_Bonus = 0;
        B2_topPoints = 0;

        B1_timeInMilliseconds = 0;
        B1_timeSwapBuff = 0;
        B1_startTime = 0;
        B1_updatedTime = 0;
        B1_InstanceSavedTime = 0L;

        B2_timeInMilliseconds = 0;
        B2_timeSwapBuff = 0;
        B2_startTime = 0;
        B2_updatedTime = 0;
        B2_InstanceSavedTime = 0L;

        B_elapsedTime = "00:00:0";
        B1_elapsedTime = "00:00:0";
        B2_elapsedTime = "00:00:0";

        update_anyIntTextView(R.id.id_B_teamScore, B_teamScorePoints);
        update_anyIntTextView(R.id.id_B_topsCounter, B_topsCounter);
        update_anyIntTextView(R.id.id_B_bonusCounter, B_bonusCounter);
        update_anyTimer(R.id.id_B_timeCounter, B_elapsedTime);

        update_anyIntTextView(R.id.id_B1_numberOfTries, B1_numberOfTries);
        update_anyTimer(R.id.id_B1_timeCounter, B1_elapsedTime);

        update_anyIntTextView(R.id.id_B2_numberOfTries, B2_numberOfTries);
        update_anyTimer(R.id.id_B2_timeCounter, B2_elapsedTime);

        //  B1_reset button states
        disableButton(R.id.id_B1_btnQuit);
        disableButton(R.id.id_B1_btnBonus);
        disableButton(R.id.id_B1_btnTop);
        enableButton(id_B1_btnStart);
        disableView(R.id.id_B1_topPoints);
        disableView(R.id.B1_topPointsWrapper);

        //  B2_reset button states
        disableButton(R.id.id_B2_btnQuit);
        disableButton(R.id.id_B2_btnBonus);
        disableButton(R.id.id_B2_btnTop);
        enableButton(id_B2_btnStart);
        disableView(R.id.id_B2_topPoints);
        disableView(R.id.B2_topPointsWrapper);

        enableButton(id_B3_btnStart); //NOTE: For now, Button3 is there just for demo, so, no need to check TODO set 12 routes
}

    //Method to set running status (chronometer and button's status)
    public void A1_setRunningStatus() {
        A1_startTime = SystemClock.uptimeMillis();
        A1_customHandler.postDelayed(A1_timerThread, 0);
        disableButton(id_A1_btnStart);
        enableButton(R.id.id_A1_btnQuit);
        enableButton(R.id.id_A1_btnTop);
        if (A1_Bonus == 0) {
            enableButton(R.id.id_A1_btnBonus);
        }
        ;

        disableButton(id_A2_btnStart);//IMPORTANT - disable the other button - the atlete cannot perform two at same  time
        disableButton(id_A3_btnStart);
    }
    ;

    public void A2_setRunningStatus() {
        A2_startTime = SystemClock.uptimeMillis();
        A2_customHandler.postDelayed(A2_timerThread, 0);
        disableButton(id_A2_btnStart);
        enableButton(R.id.id_A2_btnQuit);
        enableButton(R.id.id_A2_btnTop);
        if (A2_Bonus == 0) {
            enableButton(R.id.id_A2_btnBonus);
        }
        ;

        disableButton(id_A1_btnStart);//IMPORTANT - disable the other button - the atlete cannot perform two at same  time
        disableButton(id_A3_btnStart);
    }
    ;

    public void B1_setRunningStatus() {
        B1_startTime = SystemClock.uptimeMillis();
        B1_customHandler.postDelayed(B1_timerThread, 0);
        disableButton(id_B1_btnStart);
        enableButton(R.id.id_B1_btnQuit);
        enableButton(R.id.id_B1_btnTop);
        if (B1_Bonus == 0) {
            enableButton(R.id.id_B1_btnBonus);
        }
        ;

        disableButton(id_B2_btnStart);//IMPORTBNT - disable the other button - the atlete cannot perform two at same  time
        disableButton(id_B3_btnStart);

    }
    ;

    public void B2_setRunningStatus() {
        B2_startTime = SystemClock.uptimeMillis();
        B2_customHandler.postDelayed(B2_timerThread, 0);
        disableButton(id_B2_btnStart);
        enableButton(R.id.id_B2_btnQuit);
        enableButton(R.id.id_B2_btnTop);
        if (B2_Bonus == 0) {
            enableButton(R.id.id_B2_btnBonus);
        }
        ;

        disableButton(id_B1_btnStart);//IMPORTBNT - disable the other button - the atlete cannot perform two at same  time
        disableButton(id_B3_btnStart);
    }
    ;
    //TODO understand better arrays and implement in method above for 12 routes
    //Method to set running status (chronometer and button's status)
        //            public  List<Integer> A_quitBtnIDs = new ArrayList<>(),
        //                    List<Integer> A_startButtons = new ArrayList<>();
        //
        //            public void setBtnIDs () {
        //                A_quitBtnIDs.clear();
        //                A_quitBtnIDs.add(R.id.id_A1_btnQuit);
        //                A_quitBtnIDs.add(R.id.id_A2_btnQuit);
        //
        //                A_startButtons.clear();
        //                A_startButtons.add(id_A1_btnStart);
        //                A_startButtons.add(id_A2_btnStart);
        //            }
        //
        //      calling buttons:            (A_quitBtnIDs.get(i));

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







