package com.example.calmable;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs2 {
    private SharedPreferences preferences2;

    public Prefs2(Activity activity){
        this.preferences2 = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    public void setDate(long milliseconds){
        preferences2.edit().putLong("seconds", 0);
    }

    public void setSessions(int session){
        preferences2.edit().putInt("sessions", session).apply();
    }

    public int getSessions(){
        return preferences2.getInt("sessions", 0);
    }

    public void setBreaths(int breaths){
        preferences2.edit().putInt("breaths", breaths).apply();
    }

    public int getBreaths(){
        return preferences2.getInt("breaths", 0);
    }

}
