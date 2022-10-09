package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgLogo;
    private TextView mainText;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // assign variable
        imgLogo = findViewById(R.id.imgLogo);
        mainText = findViewById(R.id.mainTest);

        //  Set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // initialize animation
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(

                imgLogo,
                PropertyValuesHolder.ofFloat("scaleX" , 1.2f),
                PropertyValuesHolder.ofFloat("scaleY" , 1.2f)

        );

        //set duration
        objectAnimator.setDuration(500);
        //set repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // set repeat mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //start animation
        //objectAnimator.start();


        //set animate text
        animatedText("C A L M a b l e");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this , LoginUserActivity.class));
                finish();
            }
        } ,  4000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //wen runnable is run
            //set text
            mainText.setText(charSequence.subSequence(0,index++));

            //check condition
            if  (index <= charSequence.length()) {
                //when index is equal to text length
                //run handler
                handler.postDelayed(runnable, delay);
            }
        }
    };

    //animation text method
    public void animatedText(CharSequence cs) {
        charSequence = cs;

        index = 0;
        mainText.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, delay);
    }
}