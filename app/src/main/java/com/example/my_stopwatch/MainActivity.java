package com.example.my_stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private TextView timer;
    private MaterialButton startStopButton, ResetButton;

    private Handler handler = new Handler();
    private Runnable runnable;

    private long startTime = 0L;
    private long elapsedTime = 0L;
    private boolean isRunning = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        int purple = ContextCompat.getColor(getApplicationContext(), R.color.purple);
        int red = ContextCompat.getColor(getApplicationContext(), R.color.red);
        int enabledText = ContextCompat.getColor(getApplicationContext(), R.color.black);
        int enabledButton = ContextCompat.getColor(getApplicationContext(), R.color.grey);
        int disabledText = ContextCompat.getColor(getApplicationContext(), R.color.disabledButtonTextColor);
        int disabledButton = ContextCompat.getColor(getApplicationContext(), R.color.disabledButtonBgColor);

        timer = findViewById(R.id.timer);
        startStopButton = findViewById(R.id.start_stop_button);
        ResetButton = findViewById(R.id.reset_button);

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning) {
                    startTimer();
                    startStopButton.setText("Stop");
                    startStopButton.setBackgroundColor(red);
                    ResetButton.setEnabled(false);
                    ResetButton.setBackgroundColor(disabledButton);
                    ResetButton.setTextColor(disabledText);
                }
                else {
                    stopTimer();
                    ResetButton.setEnabled(true);
                    ResetButton.setBackgroundColor(enabledButton);
                    ResetButton.setTextColor(enabledText);
                    startStopButton.setText("Resume");
                    startStopButton.setBackgroundColor(purple);
                }
            }
        });

        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    resetTimer();
                    startStopButton.setText("Start");
                    startStopButton.setBackgroundColor(purple);
                    ResetButton.setEnabled(false);
                    ResetButton.setBackgroundColor(disabledButton);
                    ResetButton.setTextColor(disabledText);
            }
        });
    }
    private void startTimer() {
        startTime = System.currentTimeMillis() - elapsedTime;
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                elapsedTime = System.currentTimeMillis() - startTime;
                int milliSec = (int) (elapsedTime % 1000) / 10;
                int sec = (int) (elapsedTime / 1000) % 60;
                int min = (int) (elapsedTime / (1000 * 60)) % 60;

                String formatTime = String.format("%02d:%02d.%02d", min, sec, milliSec);
                timer.setText(formatTime);
                handler.postDelayed(this, 10);
            }
        },10);
        isRunning = true;
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
        isRunning = false;
    }

    private void resetTimer() {
        stopTimer();
        elapsedTime = 0L;
        startTime = 0L;
        timer.setText("00:00.00");
        isRunning = false;
    }
}