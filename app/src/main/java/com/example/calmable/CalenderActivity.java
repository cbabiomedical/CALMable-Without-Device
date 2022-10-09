package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.Property;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;

public class CalenderActivity extends AppCompatActivity {
    //initialize variable
    CustomCalendar customCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_activity);

        //assign variable
        customCalendar = findViewById(R.id.custom_calender);

        //initialize description hash map
        HashMap<Object, Property> descHashmap = new HashMap<>();

        //initialize default property
        Property defaultProperty = new Property();

        //initialize default resource
        defaultProperty.layoutResource = R.layout.default_view;

        //initialize and assign variable
        defaultProperty.dateTextViewResource = R.id.text_view;

        //put object and property
        descHashmap.put("default",defaultProperty);

        //for current date
        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("current",currentProperty);

        //for happy mood
        Property happyProperty = new Property();
        happyProperty.layoutResource = R.layout.happy_view;
        happyProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("happy",happyProperty);

        //for awesome mood
        Property awesomeProperty = new Property();
        awesomeProperty.layoutResource = R.layout.awesome_view;
        awesomeProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("awesome",awesomeProperty);

        //for relaxed mood
        Property relaxedProperty = new Property();
        relaxedProperty.layoutResource = R.layout.relaxed_view;
        relaxedProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("relaxed",relaxedProperty);

        //for sleepy mood
        Property sleepyProperty = new Property();
        sleepyProperty.layoutResource = R.layout.sleepy_view;
        sleepyProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("sleepy",sleepyProperty);

        //for sad mood
        Property sadProperty = new Property();
        sadProperty.layoutResource = R.layout.sad_view;
        sadProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("sad",sadProperty);

        //set desc hashmap on custom calender
        customCalendar.setMapDescToProp(descHashmap);

        //initialize date hashmap
        HashMap<Integer,Object> dateHashMap = new HashMap<>();

        //initialize calender
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        String day = dayFormat.format(new Date());
        Log.d("Date--------",day);

        //initializing shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable" , Context.MODE_PRIVATE);

        Intent intent = getIntent();
        //int happyValue = intent.getIntExtra("happyValue", 0);

        Realm.init(getApplicationContext());
        //Realm realm = Realm.getDefaultInstance();
        Realm realm = Realm.getInstance(RealmUtility.getDefaultConfig());

        //realm.beginTransaction();
        //Note note = realm.createObject(Note.class);
        //note.setTitle(title);
        //note.setDescription(description);
//        long createdTime = System.currentTimeMillis();
//        note.setCreatedTime(createdTime);
//        realm.commitTransaction();
        //String formatedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        //holder.timeOutput.setText(formatedTime);
//        Date date = null;
//        date = formatter.parse(events.getEventDate() + " " + events.getEventTime());

        int happyValue = sharedPreferences.getInt("happyValue",0);
        int awesomeValue = sharedPreferences.getInt("awesomeValue",0);
        int relaxedValue = sharedPreferences.getInt("relaxedValue",0);
        int sleepyValue = sharedPreferences.getInt("sleepyValue",0);
        int sadValue = sharedPreferences.getInt("sadValue",0);

        //put values to dateHashMap
        dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"current");

        if (happyValue == 1) {

            dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"happy");
            //dateHashMap.put(31,"happy");

            //dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"happy");
            //dateHashMap.put(Integer.valueOf(day),"happy");

        }
        if (awesomeValue == 2) {
            //dateHashMap.put(2,"awesome");
            dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH), "awesome");
            Log.d("Day valu--------", String.valueOf(Integer.valueOf(day)));
        }
        if (relaxedValue == 3) {

            dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"relaxed");

            // dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"relaxed");
            //dateHashMap.put(23,"relaxed");

        }
        if (sleepyValue == 4) {
            dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"sleepy");
        }
        if (sadValue == 5) {
            dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"sad");
        }

//        dateHashMap.put(20,"happy");
//        dateHashMap.put(2,"awesome");
//        dateHashMap.put(4,"sad");
//        dateHashMap.put(10,"sleepy");
//        dateHashMap.put(11,"relaxed");

        //set date
        customCalendar.setDate(calendar,dateHashMap);

    }

    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ProfileMain.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}