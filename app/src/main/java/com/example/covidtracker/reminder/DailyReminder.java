package com.example.covidtracker.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.covidtracker.api.RetrofitClient;
import com.example.covidtracker.model.IndonesiaResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.covidtracker.reminder.ReminderHelper.showNotification;
import static com.example.covidtracker.utils.TimeUtils.getArrayTime;

public class DailyReminder extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();
    private static final int REQUEST_DAILY_REMINDER = 100;
    private static final String EXTRA_TITLE = "extra_title";
    private static final String EXTRA_MESSAGE = "extra_message";

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String title = intent.getStringExtra(EXTRA_TITLE);
        final String message = intent.getStringExtra(EXTRA_MESSAGE);
        final int notifId = 1;

        RetrofitClient.INSTANCE.getInstance().getIndonesia().enqueue(new Callback<ArrayList<IndonesiaResponse>>(){
            @Override
            public void onResponse(@NotNull Call<ArrayList<IndonesiaResponse>> call, @NotNull Response<ArrayList<IndonesiaResponse>> response) {
                try {
                    if (response.isSuccessful()){
                        IndonesiaResponse indonesiaResponse = response.body().get(0);
                        String totalCases = indonesiaResponse.getPositif();
                        showNotification(context, title, totalCases, notifId);
                    }
                } catch (Exception e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<IndonesiaResponse>> call, @NotNull Throwable t) {
                t.getMessage();
            }
        });
    }

    public void setReminder(Context context, String time){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        intent.putExtra(EXTRA_TITLE, "Total kasus hari ini");
        intent.putExtra(EXTRA_MESSAGE, "xxx");

        int[] arrayTime = getArrayTime(time);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, arrayTime[0]);
        calendar.set(Calendar.MINUTE, arrayTime[1]);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_DAILY_REMINDER, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

        Log.d(TAG, "setReminder");
    }

}