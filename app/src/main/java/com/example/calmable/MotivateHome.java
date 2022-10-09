package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MotivateHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivate_home);
    }


    public void btnGoSuccessStory (View view) {
        startActivity(new Intent(getApplicationContext() , SuccesStoryActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void btnGoCreativity (View view) {
        startActivity(new Intent(getApplicationContext() , CreativityActivity.class));
    }

    public void btnGoSelfImpt (View view) {
        startActivity(new Intent(getApplicationContext() , SelfImprovementActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}