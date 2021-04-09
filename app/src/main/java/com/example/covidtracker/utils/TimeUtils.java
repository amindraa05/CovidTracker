package com.example.covidtracker.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimeUtils {

    public static final String TIME_FORMAT = "HH:mm";

    public static int[] getArrayTime(String time){
        if (isValidTimeFormat(time)){
            String[] stringArrayTime = time.split(":");
            int[] integerArrayTime = new int[2];
            for (int i = 0; i < 2; i++) integerArrayTime[i] = Integer.parseInt(stringArrayTime[i]);
            return new int[] {integerArrayTime[0], integerArrayTime[1]};
        } else return null;
    }

    public static boolean isValidTimeFormat(String time){
        if (time != null){
            try {
                SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
                timeFormat.parse(time);
                return true;
            } catch (ParseException e) {
                return false;
            }
        } else return false;
    }
}
