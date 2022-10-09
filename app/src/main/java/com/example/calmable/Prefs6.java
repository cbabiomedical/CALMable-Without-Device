package com.example.calmable;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs6 {
    private SharedPreferences preferences6;

    public Prefs6(Activity activity){
        this.preferences6 = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    public void setDate(long milliseconds){
        preferences6.edit().putLong("seconds", 0);
    }

    public void setSessions(int session){
        preferences6.edit().putInt("sessions", session).apply();
    }

    public int getSessions(){
        return preferences6.getInt("sessions", 0);
    }

    public void setBreaths(int breaths){
        preferences6.edit().putInt("breaths", breaths).apply();
    }

    public int getBreaths(){
        return preferences6.getInt("breaths", 0);
    }

}


