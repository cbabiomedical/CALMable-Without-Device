package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SleepStory2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_story2);
    }

    //to change the light theme to dark theme
    public void btnTheme (View view){
        startActivity(new Intent(getApplicationContext(), Sleep2DarkTheme.class));
    }
}