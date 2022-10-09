package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SleepStory5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_story5);
    }

    //to change the light theme to dark theme
    public void btnTheme (View view){
        startActivity(new Intent(getApplicationContext(), Sleep5DarkTheme.class));
    }
}