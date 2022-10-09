package com.example.calmable;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs4 {
    private SharedPreferences preferences4;

    public Prefs4(Activity activity){
        this.preferences4 = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    public void setDate(long milliseconds){
        preferences4.edit().putLong("seconds", 0);
    }

    public void setSessions(int session){
        preferences4.edit().putInt("sessions", session).apply();
    }

    public int getSessions(){
        return preferences4.getInt("sessions", 0);
    }

    public void setBreaths(int breaths){
        preferences4.edit().putInt("breaths", breaths).apply();
    }

    public int getBreaths(){
        return preferences4.getInt("breaths", 0);
    }

}


