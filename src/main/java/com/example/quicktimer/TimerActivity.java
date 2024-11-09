package com.example.quicktimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {
    private EditText inputHours, inputMinutes, inputSeconds;
    private TextView timerDisplay;
    private Button startButton, pauseButton, resetButton;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        inputHours = findViewById(R.id.inputHours);
        inputMinutes = findViewById(R.id.inputMinutes);
        inputSeconds = findViewById(R.id.inputSeconds);
        timerDisplay = findViewById(R.id.timerDisplay);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(view -> startTimer());
        pauseButton.setOnClickListener(view -> pauseTimer());
        resetButton.setOnClickListener(view -> resetTimer());
    }

    private void startTimer() {
        if (!isRunning) {
            int hours = Integer.parseInt(inputHours.getText().toString());
            int minutes = Integer.parseInt(inputMinutes.getText().toString());
            int seconds = Integer.parseInt(inputSeconds.getText().toString());
            timeLeftInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;

            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    isRunning = false;
                    timerDisplay.setText("Time's up!");
                    playSound();
                }
            }.start();

            isRunning = true;
        }
    }

    private void pauseTimer() {
        if (isRunning) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    private void resetTimer() {
        countDownTimer.cancel();
        isRunning = false;
        timeLeftInMillis = 0;
        updateTimer();
    }

    private void updateTimer() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        timerDisplay.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    private void playSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.notification_sound);
        mediaPlayer.start();
    }
}
