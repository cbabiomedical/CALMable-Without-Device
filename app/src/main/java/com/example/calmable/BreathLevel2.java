package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.MessageFormat;

public class BreathLevel2 extends AppCompatActivity {

    public static int x2;
    public static int BreathScore2;

    private ImageView imageView;
    public int counter, counter2;
    private TextView breathsTxt, timeTxt, sessionTxt, guideTxt, timerseconds, timerminutes;
    private Button startButton, backButton2;
    public static Prefs2 prefs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_level2);
        imageView = findViewById(R.id.imageView2);
        timerseconds= (TextView) findViewById(R.id.timerseconds);
        timerminutes= (TextView) findViewById(R.id.timerminutes);

        /////////////////////////////////////////////////////////////////
        breathsTxt = findViewById(R.id.breathsTakenTxt);
        //timeTxt = findViewById(R.id.last);
        //sessionTxt = findViewById(R.id.todayminutes);
        //guideTxt = findViewById(R.id.guideTxt);
        prefs2 = new Prefs2(this);

        //startIntroAnimation();

        //sessionTxt.setText(MessageFormat.format("{0} min today", prefs2.getSessions()));
        breathsTxt.setText(MessageFormat.format("You have completed {0} Breaths", prefs2.getBreaths()));

        Log.d("---get breaths value2--", String.valueOf(prefs2.getBreaths()));
        x2 = prefs2.getBreaths();

        if(x2 == 4){ //put here 4
            BreathScore2 = BreathScore2 + 50;

            FirebaseFirestore database = FirebaseFirestore.getInstance();

            database.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .update("coins", FieldValue.increment(BreathScore2));
        }

        Log.d("----x2 value----", String.valueOf(x2));

        //timeTxt.setText(prefs.getDate());

        //button to go back
        backButton2 = findViewById(R.id.backbutton2);
        backButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                onPause();

                Intent intent = new Intent(BreathLevel2.this, BreathPatterns.class);
                startActivity(intent);
                //startActivity(new Intent(getActivity(),breathLevel1.class));

            }
        });

        startButton = findViewById(R.id.startbutton);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startAnimation();
                startButton.setVisibility(View.GONE);
                timerminutes.setText(" Seconds");
                new CountDownTimer(121000, 1000){
                    public void onTick(long millisUntilFinished){
                        timerseconds.setText(String.valueOf(counter));
                        counter++;
                        /*if(counter == 20){
                            counter--;
                        }*/

                    }
                    public  void onFinish(){
                        timerseconds.setText(" Done !");
                        timerminutes.setText("");
                    }
                }.start();
                //////////////////////////////////
                /*new CountDownTimer(20000, 20000){
                    public void onTick(long millisUntilFinished){
                        timerminutes.setText(String.valueOf(counter2));
                        counter2++;
                    }
                    public  void onFinish(){
                        timerminutes.setText("02: ");
                    }
                }.start();*/
            }
        });

    }

    /*private void startIntroAnimation(){
        ViewAnimator
                .animate(guideTxt)
                .scale(0, 1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Breathe");
                    }
                })
                .start();
    }*/
    private void startAnimation(){
        ViewAnimator
                .animate(imageView)
                .alpha(0,1)
                /*.onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Inhale... Exhale");
                    }
                })*/

                ///////////////////// 1 //////////////////////
                .decelerate()
                .duration(1)
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(5000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(2000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(8000)
                ///////////////////// 2 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(5000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(2000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(8000)
                ///////////////////// 3 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(5000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(2000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(8000)
                ///////////////////// 4 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(5000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(2000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(8000)
                ///////////////////// 5 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(5000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(2000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(8000)
                ///////////////////// 6 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(5000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(2000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(8000)
                ///////////////////// 7 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(5000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(2000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(8000)
                ///////////////////// 8 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(5000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(2000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(8000)

                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        //guideTxt.setText("Good Job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs2.setSessions(prefs2.getSessions() + 1);
                        prefs2.setBreaths(prefs2.getBreaths() + 1);
                        prefs2.setDate(SystemClock.currentThreadTimeMillis());

                        //counting score of completing breathing exercises of level 1
                        BreathScore2 = BreathScore2 + 5;
                        Log.d("---get breath score---", String.valueOf(BreathScore2));

                        FirebaseFirestore database = FirebaseFirestore.getInstance();

                        database.collection("users")
                                .document(FirebaseAuth.getInstance().getUid())
                                .update("coins", FieldValue.increment(BreathScore2));

                    }
                })
                .start();
    }
}