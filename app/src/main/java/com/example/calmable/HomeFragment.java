package com.example.calmable;



import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.calmable.scan.ScanActivity;


public class HomeFragment extends Fragment {

    private Button stressHomeBtn, sleepyHomeBtn,motivateHomeBtn, happyHomeBtn, breathHomeBtn ,reportHomeBtn, connectWatch;

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView txtProgress = (TextView) view.findViewById(R.id.txtProgress);
        TextView txtProgress2 = (TextView) view.findViewById(R.id.txtPastProgress);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);

//        TextView htRate = (TextView) view.findViewById(R.id.htRate);
//
//        for (String list : deviceActivity.listOfRate) {
//
//            if (list != null) {
//                htRate.setText(list);
//
//                Log.d("TAG", "fragment : -------" + list);
//            }
//        }



        int CurrentHeartRate = 78; //user's current heart rate
        int PastHeartRate = 112; //user's past heart rate
        int PastTime = 5; //time gap between user's current heart rate

        String chr = getColoredSpanned(Integer.toString(CurrentHeartRate), "#800000");
        String phr = getColoredSpanned(Integer.toString(PastHeartRate), "#000000");
        String t = getColoredSpanned(Integer.toString(PastTime), "#000000");
        String BPM = getColoredSpanned("\u1D2E\u1D3E\u1D39","#000000");
        txtProgress.setText(Html.fromHtml(chr+" "+BPM));
        txtProgress2.setText(Html.fromHtml(phr+" "+"BPM, "+t+"m ago"));


//        //"I'm stressed" button
//        stressHomeBtn = (Button) view.findViewById(R.id.stressHomeBtn);
//        stressHomeBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//
//                startActivity(new Intent(getActivity(), StressHome.class));
//
//            }
//        });

        //"I'm Sleepy" button
        sleepyHomeBtn = (Button) view.findViewById(R.id.sleepyHomebtn);
        sleepyHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), SleepyHome.class));

            }
        });

        //"I wanna motivate" button
        motivateHomeBtn = (Button) view.findViewById(R.id.motivateHomeBtn);
        motivateHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), MotivateHome.class));

            }
        });

        //"I'm Happy" button
        happyHomeBtn = (Button) view.findViewById(R.id.happyHomeBtn);
        happyHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), HappyHome.class));

            }
        });

        //"Take a deep breath" button
        breathHomeBtn = (Button) view.findViewById(R.id.breathHomeBtn);
        breathHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), BreathPatterns.class));

            }
        });

        //"Report" button
        reportHomeBtn = (Button) view.findViewById(R.id.reportHomeBtn);
        reportHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), ReportHome.class));

            }
        });


        //  test --> connect watch
        connectWatch = (Button) view.findViewById(R.id.connectWatch);
        connectWatch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(getActivity() , ScanActivity.class));

            }
        });

        return view;

    }

}


