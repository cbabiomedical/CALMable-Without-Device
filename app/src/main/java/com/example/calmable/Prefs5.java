package com.example.calmable;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs5 {
    private SharedPreferences preferences5;

    public Prefs5(Activity activity){
        this.preferences5 = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    public void setDate(long milliseconds){
        preferences5.edit().putLong("seconds", 0);
    }

    public void setSessions(int session){
        preferences5.edit().putInt("sessions", session).apply();
    }

    public int getSessions(){
        return preferences5.getInt("sessions", 0);
    }

    public void setBreaths(int breaths){
        preferences5.edit().putInt("breaths", breaths).apply();
    }

    public int getBreaths(){
        return preferences5.getInt("breaths", 0);
    }

}


