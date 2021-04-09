package com.example.covidtracker.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    private static final String PREFERENCE_NAME = "user_preference";
    private static final String REMINDER_TIME = "reminder_time";
    private static final String LANGUAGE = "language";

    @SuppressLint("CommitPrefEdits")
    public UserPreference(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getReminderTime(){
        return sharedPreferences.getString(REMINDER_TIME, "17:00"); // Default jam
    }

    public void setReminderTime(String reminderTime){
        editor.putString(REMINDER_TIME, reminderTime);
        editor.apply();
    }

    public String getLanguage(){
        return sharedPreferences.getString(LANGUAGE, "in");
    }

    public void setLanguage(String language){
        editor.putString(LANGUAGE, language);
        editor.apply();
    }


}
