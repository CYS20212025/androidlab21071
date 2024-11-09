package com.example.quicktimer;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class SoundSettingsActivity extends AppCompatActivity {
    private RadioGroup soundOptionsGroup;
    private Button saveButton;
    private MediaPlayer mediaPlayer;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_settings);

        soundOptionsGroup = findViewById(R.id.soundOptionsGroup);
        saveButton = findViewById(R.id.saveButton);
        preferences = getSharedPreferences("quicktimer_prefs", MODE_PRIVATE);

        saveButton.setOnClickListener(view -> saveSelectedSound());

        soundOptionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (mediaPlayer != null) mediaPlayer.release();
            switch (checkedId) {
                case R.id.soundOption1:
                    mediaPlayer = MediaPlayer.create(this, R.raw.notification_sound1);
                    break;
                case R.id.soundOption2:
                    mediaPlayer = MediaPlayer.create(this, R.raw.notification_sound2);
                    break;
                case R.id.soundOption3:
                    mediaPlayer = MediaPlayer.create(this, R.raw.notification_sound3);
                    break;
            }
            mediaPlayer.start();
        });
    }

    private void saveSelectedSound() {
        int selectedId = soundOptionsGroup.getCheckedRadioButtonId();
        SharedPreferences.Editor editor = preferences.edit();
        switch (selectedId) {
            case R.id.soundOption1:
                editor.putString("selected_sound", "notification_sound1");
                break;
            case R.id.soundOption2:
                editor.putString("selected_sound", "notification_sound2");
                break;
            case R.id.soundOption3:
                editor.putString("selected_sound", "notification_sound3");
                break;
        }
        editor.apply();
    }
}
