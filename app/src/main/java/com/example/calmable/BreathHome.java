package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BreathHome extends AppCompatActivity {

    private Button level1Btn, level2Btn, level3Btn, level4Btn, level5Btn, level6Btn;
    //private Prefs prefs;
    /*private Prefs2 prefs2;
    private Prefs3 prefs3;
    private Prefs4 prefs4;
    private Prefs5 prefs5;
    private Prefs6 prefs6;*/
    //public static int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_home);

        /*prefs = new Prefs(this);
        prefs2 = new Prefs2(this);
        prefs3 = new Prefs3(this);
        prefs4 = new Prefs4(this);
        prefs5 = new Prefs5(this);
        prefs6 = new Prefs6(this);*/

        //button to go to level 1
        level1Btn = findViewById(R.id.level1Btn);
        level1Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BreathHome.this, BreathLevel1.class);
                startActivity(intent);
                //startActivity(new Intent(getActivity(),breathLevel1.class));

            }
        });

        /*0
        Log.d("USERLOGIN", "----------------------a----------------------------");
        Log.d("USERLOGIN", "----------------------b----------------------------");
        Log.d("USERLOGIN", "----------------------c----------------------------");
        */

        //button to go to level 2
        level2Btn = findViewById(R.id.level2Btn);
        //int x = prefs.setBreaths(prefs.getBreaths() + 1);;

        //Log.d("----tag----", String.valueOf(BreathLevel1.prefs.getBreaths()));
        level2Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ExerciseMethod();
            }

            private void ExerciseMethod() {
                Log.d("--BreathLevel1.x value-", String.valueOf(BreathLevel1.x));
                if(BreathLevel1.x < 5){
                    level2Btn.setClickable(false);
                }else{
                    level2Btn.setClickable(true);
                    Intent intent = new Intent(BreathHome.this, BreathLevel2.class);
                    startActivity(intent);
                }
            }
        });

        //button to go to level 3
        level3Btn = findViewById(R.id.level3Btn);
        level3Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ExerciseMethod3();
            }

            private void ExerciseMethod3() {
                Log.d("--BreathLevel2.x value-", String.valueOf(BreathLevel2.x2));
                if(BreathLevel2.x2 < 5){
                    level3Btn.setClickable(false);
                }else{
                    level3Btn.setClickable(true);
                    Intent intent = new Intent(BreathHome.this, BreathLevel3.class);
                    startActivity(intent);
                }
            }
        });

        //button to go to level 4
        level4Btn = findViewById(R.id.level4Btn);
        level4Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ExerciseMethod4();
            }

            private void ExerciseMethod4() {
                Log.d("-BreathLevel3.x3 value-", String.valueOf(BreathLevel3.x3));
                if(BreathLevel3.x3 < 8){
                    level4Btn.setClickable(false);
                }else{
                    level4Btn.setClickable(true);
                    Intent intent = new Intent(BreathHome.this, BreathLevel4.class);
                    startActivity(intent);
                }
            }
        });

        //button to go to level 5
        level5Btn = findViewById(R.id.level5Btn);
        level5Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ExerciseMethod5();
            }

            private void ExerciseMethod5() {
                Log.d("-BreathLevel4.x4 value-", String.valueOf(BreathLevel4.x4));
                if(BreathLevel4.x4 < 8){
                    level5Btn.setClickable(false);
                }else{
                    level5Btn.setClickable(true);
                    Intent intent = new Intent(BreathHome.this, BreathLevel5.class);
                    startActivity(intent);
                }
            }
        });

        //button to go to level 6
        level6Btn = findViewById(R.id.level6Btn);
        level6Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ExerciseMethod6();
            }

            private void ExerciseMethod6() {
                Log.d("-BreathLevel5.x5 value-", String.valueOf(BreathLevel5.x5));
                if(BreathLevel5.x5 < 10){
                    level6Btn.setClickable(false);
                }else{
                    level6Btn.setClickable(true);
                    Intent intent = new Intent(BreathHome.this, BreathLevel6.class);
                    startActivity(intent);
                }
            }
        });

    }
}