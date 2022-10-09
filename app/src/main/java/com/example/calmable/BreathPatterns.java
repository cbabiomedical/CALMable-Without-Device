package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BreathPatterns extends AppCompatActivity {

    private Button level1Btn, level2Btn, level3Btn, level4Btn, level5Btn, level6Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_patterns);

        //button to go to level 1
        level1Btn = findViewById(R.id.level1Btn);
        level1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatternMethod();
            }
            private void PatternMethod() {
                Log.d("--BreathLevel1.x value-", String.valueOf(BreathLevel1.x));
                if(BreathLevel1.x == 0){
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern1Info.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern1.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        /*0
        Log.d("USERLOGIN", "----------------------a----------------------------");
        Log.d("USERLOGIN", "----------------------b----------------------------");
        Log.d("USERLOGIN", "----------------------c----------------------------");
        */

        //button to go to level 2
        level2Btn = findViewById(R.id.level2Btn);
        level2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatternMethod2();
            }
            private void PatternMethod2() {
                Log.d("-BreathLevel2.x2 value-", String.valueOf(BreathLevel2.x2));
                if(BreathLevel2.x2 == 0){
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern2Info.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern2.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        //button to go to level 3
        level3Btn = findViewById(R.id.level3Btn);
        level3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatternMethod3();
            }
            private void PatternMethod3() {
                Log.d("-BreathLevel3.x3 value-", String.valueOf(BreathLevel3.x3));
                if(BreathLevel3.x3 == 0){
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern3Info.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern3.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        //button to go to level 4
        level4Btn = findViewById(R.id.level4Btn);
        level4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatternMethod4();
            }
            private void PatternMethod4() {
                Log.d("-BreathLevel4.x4 value-", String.valueOf(BreathLevel4.x4));
                if(BreathLevel4.x4 == 0){
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern4Info.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern4.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        //button to go to level 5
        level5Btn = findViewById(R.id.level5Btn);
        level5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatternMethod5();
            }
            private void PatternMethod5() {
                Log.d("-BreathLevel5.x5 value-", String.valueOf(BreathLevel5.x5));
                if(BreathLevel5.x5 == 0){
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern5Info.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern5.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        //button to go to level 6
        level6Btn = findViewById(R.id.level6Btn);
        level6Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatternMethod6();
            }
            private void PatternMethod6() {
                Log.d("-BreathLevel6.x6 value-", String.valueOf(BreathLevel6.x6));
                if(BreathLevel6.x6 == 0){
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern6Info.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Intent intent = new Intent(BreathPatterns.this, BreathPattern6.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

    }


    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}