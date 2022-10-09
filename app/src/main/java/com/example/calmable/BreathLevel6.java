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

public class BreathLevel6 extends AppCompatActivity {

    public static int x6;
    public static int BreathScore6;

    private ImageView imageView;
    public int counter, counter2;
    private TextView breathsTxt, timeTxt, sessionTxt, guideTxt, timerseconds, timerminutes;
    private Button startButton, backButton2;
    public static Prefs6 prefs6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_level6);
        imageView = findViewById(R.id.imageView2);
        timerseconds= (TextView) findViewById(R.id.timerseconds);
        timerminutes= (TextView) findViewById(R.id.timerminutes);

        /////////////////////////////////////////////////////////////////
        breathsTxt = findViewById(R.id.breathsTakenTxt);
        //timeTxt = findViewById(R.id.last);
        //sessionTxt = findViewById(R.id.todayminutes);
        //guideTxt = findViewById(R.id.guideTxt);
        prefs6 = new Prefs6(this);

        //startIntroAnimation();

        //sessionTxt.setText(MessageFormat.format("{0} min today", prefs6.getSessions()));
        breathsTxt.setText(MessageFormat.format("You have completed {0} Breaths", prefs6.getBreaths()));

        Log.d("---get breaths value6--", String.valueOf(prefs6.getBreaths()));
        x6 = prefs6.getBreaths();

        if(x6 == 14){ //put here 14
            BreathScore6 = BreathScore6 + 50;

            FirebaseFirestore database = FirebaseFirestore.getInstance();

            database.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .update("coins", FieldValue.increment(BreathScore6));
        }

        Log.d("----x6 value----", String.valueOf(x6));

        //timeTxt.setText(prefs.getDate());

        //button to go back
        backButton2 = findViewById(R.id.backbutton2);
        backButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                onPause();

                Intent intent = new Intent(BreathLevel6.this, BreathPatterns.class);
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
                        guideTxt.setText("Inhale... Hold... Exhale");
                    }
                })*/

                ///////////////////// 1 //////////////////////
                .decelerate()
                .duration(1)
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                //.rotation(360)
                .accelerate()
                .duration(4000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(7000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(9000)
                ///////////////////// 2 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(7000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(9000)
                ///////////////////// 3 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(7000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(9000)
                ///////////////////// 4 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(7000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(9000)
                ///////////////////// 5 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(7000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(9000)
                ///////////////////// 6 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(7000)

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(9000)

                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        //guideTxt.setText("Good Job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs6.setSessions(prefs6.getSessions() + 1);
                        prefs6.setBreaths(prefs6.getBreaths() + 1);
                        prefs6.setDate(SystemClock.currentThreadTimeMillis());

                        //counting score of completing breathing exercises of level 1
                        BreathScore6 = BreathScore6 + 5;
                        Log.d("---get breath score---", String.valueOf(BreathScore6));

                        FirebaseFirestore database = FirebaseFirestore.getInstance();

                        database.collection("users")
                                .document(FirebaseAuth.getInstance().getUid())
                                .update("coins", FieldValue.increment(BreathScore6));

                    }
                })
                .start();
    }
}