package com.example.calmable;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs3 {
    private SharedPreferences preferences3;

    public Prefs3(Activity activity){
        this.preferences3 = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    public void setDate(long milliseconds){
        preferences3.edit().putLong("seconds", 0);
    }

    public void setSessions(int session){
        preferences3.edit().putInt("sessions", session).apply();
    }

    public int getSessions(){
        return preferences3.getInt("sessions", 0);
    }

    public void setBreaths(int breaths){
        preferences3.edit().putInt("breaths", breaths).apply();
    }

    public int getBreaths(){
        return preferences3.getInt("breaths", 0);
    }

}

