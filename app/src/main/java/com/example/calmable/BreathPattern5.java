package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

public class BreathPattern5 extends AppCompatActivity {

    public static int x5;

    //adding the audio
    MediaPlayer mysong, mysong2,mysong4;

    private ImageView imageView;
    public int counter, counter2;
    private TextView breathsTxt, timeTxt, sessionTxt, guideTxt, timerseconds, timerminutes, info;
    private Button startButton;
    public static Prefs5 prefs5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_pattern5);

        //to inhale
        mysong = MediaPlayer.create(this,R.raw.audiomass);
        //to exhale
        mysong2 = MediaPlayer.create(this,R.raw.beach_housetr);
        mysong4 = MediaPlayer.create(this,R.raw.audio4);


        imageView = findViewById(R.id.imageView2);
        timerseconds= (TextView) findViewById(R.id.timerseconds);
        timerminutes= (TextView) findViewById(R.id.timerminutes);
        info = (TextView) findViewById(R.id.info);

        /////////////////////////////////////////////////////////////////
        breathsTxt = findViewById(R.id.breathsTakenTxt);
        //timeTxt = findViewById(R.id.last);
        //sessionTxt = findViewById(R.id.todayminutes);
        //guideTxt = findViewById(R.id.guideTxt);
        prefs5 = new Prefs5(this);

        //startIntroAnimation();

        //sessionTxt.setText(MessageFormat.format("{0} min today", prefs5.getSessions()));
        breathsTxt.setText(MessageFormat.format("You have completed {0} breaths", prefs5.getBreaths()));

        Log.d("---get breaths value5--", String.valueOf(prefs5.getBreaths()));

        x5 = prefs5.getBreaths();

        //timeTxt.setText(prefs.getDate());


        startButton = findViewById(R.id.startbutton);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startAnimation();
                timerminutes.setText(" Seconds");
                new CountDownTimer(161000, 1000){
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

    //to got to info of the breath pattern 5 page
    public void btnInfo (View view){
        startActivity(new Intent(getApplicationContext(), BreathPattern5Info.class));
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

                ///////////////////// 1 //////////////////////
                .decelerate()
                .duration(1)
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                //.rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to inhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to exhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong2.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong2.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })
                ///////////////////// 2 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to inhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to exhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong2.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong2.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })
                ///////////////////// 3 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to inhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)


                //adding a music sound to exhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong2.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong2.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })
                ///////////////////// 4 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to inhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to exhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong2.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong2.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })
                ///////////////////// 5 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to inhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to exhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong2.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong2.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })
                ///////////////////// 6 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to inhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to exhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong2.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong2.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })
                ///////////////////// 7 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to inhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to exhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong2.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong2.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })
                ///////////////////// 8 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to inhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to exhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong2.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong2.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })
                ///////////////////// 9 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to inhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to exhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong2.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong2.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })
                ///////////////////// 10 //////////////////////
                .thenAnimate(imageView)
                .scale(0.002f, 1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to inhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(1.5f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                .thenAnimate(imageView)
                .scale(1.5f, 0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

                //adding a music sound to exhale part only
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        mysong2.start();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mysong2.pause();
                            }
                        }, 4 * 1000);
                    }
                })

                .thenAnimate(imageView)
                .scale(0.002f)
                .rotation(360)
                .accelerate()
                .duration(4000)

//                //adding a music sound to holding part only
//                .onStart(new AnimationListener.Start() {
//                    @Override
//                    public void onStart() {
//                        mysong4.start();
//                        Handler handler=new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mysong4.pause();
//                            }
//                        }, 4 * 1000);
//                    }
//                })

                /////////////////////////
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        //guideTxt.setText("Good Job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs5.setSessions(prefs5.getSessions() + 1);
                        prefs5.setBreaths(prefs5.getBreaths() + 1);
                        prefs5.setDate(SystemClock.currentThreadTimeMillis());


                    }
                })
                .start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mysong.release();
        mysong2.release();
        mysong4.release();
    }
}